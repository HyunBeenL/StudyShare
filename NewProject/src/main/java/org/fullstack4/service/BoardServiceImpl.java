package org.fullstack4.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.domain.BoardEntity;
import org.fullstack4.domain.MemberEntity;
import org.fullstack4.domain.ShareBbsEntity;
import org.fullstack4.dto.BoardDTO;
import org.fullstack4.dto.PageRequestDTO;
import org.fullstack4.dto.PageResponseDTO;
import org.fullstack4.exception.InsufficientStockException;
import org.fullstack4.repository.BoardRepository;
import org.fullstack4.repository.MemberRepository;
import org.fullstack4.repository.ShareRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final ShareRepository shareRepository;
    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;


    @Override
    public PageResponseDTO<BoardDTO> bbsListByPage(PageRequestDTO pageRequestDTO) {
        PageRequest pageable;
        if(pageRequestDTO.getSort_type() != null && !pageRequestDTO.getSort_type().isEmpty()) {
            pageable = PageRequest.of(pageRequestDTO.getPage(),
                    pageRequestDTO.getPage_size(),
                    Sort.by(pageRequestDTO.getSort_type()).descending().and(Sort.by("bbsIdx").descending()));
        }else{
            pageable = PageRequest.of(pageRequestDTO.getPage(),
                    pageRequestDTO.getPage_size(),
                    Sort.by("bbsIdx").descending());
        }
        Page<BoardEntity> result = boardRepository.search(pageable,
                pageRequestDTO.getSearch_types(),
                pageRequestDTO.getSearch_word(),
                pageRequestDTO.getSearch_date1(),
                pageRequestDTO.getSearch_date2(),
                pageRequestDTO.getUser_id());

        List<BoardDTO> boardDTOList = result.toList().stream()
                .map(entity ->modelMapper.map(entity,BoardDTO.class))
                .collect(Collectors.toList());

        PageResponseDTO<BoardDTO> responseDTO = PageResponseDTO.<BoardDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(boardDTOList)
                .total_count((int)result.getTotalElements())
                .build();

        return responseDTO;

    }

    @Override
    public PageResponseDTO<BoardDTO> shareBbsListByPage(PageRequestDTO pageRequestDTO) {
        PageRequest pageable = PageRequest.of(pageRequestDTO.getPage(), pageRequestDTO.getPage_size());
        Page<BoardEntity> result = boardRepository.shareBbsSearch(pageable,pageRequestDTO);
        log.info("==========================="+result.toList());

        List<BoardDTO> boardDTOList = result.toList().stream()
                .map(entity ->modelMapper.map(entity,BoardDTO.class))
                .collect(Collectors.toList());

        PageResponseDTO<BoardDTO> responseDTO = PageResponseDTO.<BoardDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(boardDTOList)
                .total_count((int)result.getTotalElements())
                .build();

        return responseDTO;
    }

    @Override
    public BoardDTO view(int bbsIdx) {
        BoardEntity boardEntity = boardRepository.findById(bbsIdx).orElse(null);
        BoardDTO boardDTO = modelMapper.map(boardEntity, BoardDTO.class);

        log.info("boardDTO : {}", boardDTO.toString());
        return boardDTO;
    }

    @Override
    public void delete(int bbsIdx) {
        BoardEntity boardEntity = boardRepository.findById(bbsIdx).orElse(null);
        boardRepository.delete(boardEntity);
    }

    @Override
    public void shareDelete(int idx) {
        ShareBbsEntity shareBbsEntity = shareRepository.findById(idx).orElse(null);
        shareRepository.delete(shareBbsEntity);
    }

    @Override
    public int modify(BoardDTO boardDTO) {
        BoardEntity boardEntity = modelMapper.map(boardDTO, BoardEntity.class);
        boardEntity.setModify_date(LocalDateTime.now());
        int idx = boardRepository.save(boardEntity).getBbsIdx();
        return idx;
    }

    @Override
    public int regist(BoardDTO boardDTO) {
        BoardEntity boardEntity = modelMapper.map(boardDTO, BoardEntity.class);
        int idx = boardRepository.save(boardEntity).getBbsIdx();
        return idx;
    }

    @Override
    @Transactional(rollbackFor = {InsufficientStockException.class, Exception.class})
    public void share(String myId, String[] userIdList,int bbs_idx) throws InsufficientStockException {
        for(int i = 0; i<userIdList.length; i++){
            if(shareRepository.countByBbsIdxAndResponseId(bbs_idx, userIdList[i])>0){
                throw new InsufficientStockException("이미 공유중인 아이디가 있습니다.");
            }
            if(myId.equals(userIdList[i])){
                throw new InsufficientStockException("자기 자신은 추가할 수 없습니다.");
            }

            MemberEntity member1 = memberRepository.findByUserId(userIdList[i]);
            MemberEntity member2 = memberRepository.findByUserId(myId);
            BoardDTO boardDTO = BoardDTO.builder()
                    .bbsIdx(bbs_idx)
                    .requestId(myId)
                    .request_name(member2.getUser_name())
                    .responseId(member1.getUserId())
                    .response_name(member1.getUser_name())
                    .send_date(LocalDateTime.now())
                    .build();


            ShareBbsEntity shareBbsEntity = modelMapper.map(boardDTO, ShareBbsEntity.class);
            shareRepository.save(shareBbsEntity);

            if(shareRepository.countByBbsIdx(bbs_idx) >5){
                throw new InsufficientStockException("5명 이상은 공유 할수 없습니다.");
            }

        }
    }

    @Override
    public List<BoardDTO> shareList(int bbsIdx) {
        List<ShareBbsEntity> shareBbsEntity = shareRepository.findByBbsIdx(bbsIdx);
        List<BoardDTO> shareBbsList = shareBbsEntity.stream()
                .map(entity ->modelMapper.map(entity,BoardDTO.class))
                .collect(Collectors.toList());
        return shareBbsList;
    }

    @Override
    public List<BoardDTO> todayList(String userId,LocalDate date) {
        List<BoardEntity> todayList = boardRepository.findByUserIdAndBbsExposureAndBbsDuration1LessThanEqualAndBbsDuration2GreaterThanEqual(userId, "Y", date,date);
        log.info("todayList = {}",todayList);
        List<BoardDTO> boardDTOList = todayList.stream()
                .map(entity ->modelMapper.map(entity,BoardDTO.class))
                .collect(Collectors.toList());
        return boardDTOList;
    }


}
