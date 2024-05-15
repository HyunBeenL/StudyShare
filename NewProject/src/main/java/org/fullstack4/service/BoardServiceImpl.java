package org.fullstack4.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.domain.BoardEntity;
import org.fullstack4.dto.BoardDTO;
import org.fullstack4.dto.PageRequestDTO;
import org.fullstack4.dto.PageResponseDTO;
import org.fullstack4.repository.BoardRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final ModelMapper modelMapper;


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
        Page<BoardEntity> result = boardRepository.search(pageable
                , pageRequestDTO.getSearch_types()
                , pageRequestDTO.getSearch_word()
                ,pageRequestDTO.getSearch_date1()
                ,pageRequestDTO.getSearch_date2());

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
        return boardDTO;
    }

    @Override
    public void delete(int bbsIdx) {
        BoardEntity boardEntity = boardRepository.findById(bbsIdx).orElse(null);
        boardRepository.delete(boardEntity);
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


}
