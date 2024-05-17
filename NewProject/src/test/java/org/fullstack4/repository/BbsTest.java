//package org.fullstack4.repository;
//
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import lombok.extern.log4j.Log4j2;
//import org.fullstack4.domain.BoardEntity;
//import org.fullstack4.domain.QBoardEntity;
//import org.fullstack4.domain.ShareBbsEntity;
//import org.fullstack4.dto.BoardDTO;
//import org.fullstack4.dto.PageRequestDTO;
//import org.fullstack4.dto.PageResponseDTO;
//import org.fullstack4.repository.pagingSearch.BoardPagingSearchImpl;
//import org.junit.jupiter.api.Test;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//@Log4j2
//@SpringBootTest
//public class BbsTest {
//    @Autowired
//    private BoardRepository boardRepository;
//    @Autowired
//    private ShareRepository shareRepository;
//
//    @Autowired
//    private ModelMapper modelMapper;
//    @Test
//    public void Test(){
//        List<BoardEntity> todayList = boardRepository.findByUserIdAndBbsExposureAndBbsDuration1LessThanEqualAndBbsDuration2GreaterThanEqual("test0", "Y", LocalDate.now(),LocalDate.now());
//        log.info("todayList = {}",todayList);
//        List<BoardDTO> boardDTOList = todayList.stream()
//                .map(entity ->modelMapper.map(entity,BoardDTO.class))
//                .collect(Collectors.toList());
//        log.info("boardDTOList = {}",boardDTOList);
//    }
////    @Autowired(required = false)
////    private BoardPagingSearchImpl board;
////
////
////    @Test
////    public void test() {
////        List<ShareBbsEntity> shareEntity = shareRepository.findByRequestId("test0");
////        List<BoardEntity> boardEntities = new ArrayList<>();
////        for(int i=0; i<shareEntity.size(); i++){
////            BoardEntity boardEntity = boardRepository.findById(shareEntity.get(i).getBbsIdx()).orElse(null);
////            boardEntities.add(boardEntity);
////        }
////        log.info("boardEntities " + boardEntities.toString());
////    }
//////
//////    @Test
//////    public void testRegist(){
//////        log.info("=====================================");
//////        log.info("MemberRepositoryTest>> start ");
//////        IntStream.rangeClosed(0,10)
//////                .forEach(i->{
//////                    BoardEntity bbs = BoardEntity.builder()
//////                            .bbs_category("test"+i)
//////                            .bbs_title("제목"+i)
//////                            .bbs_content("내용"+i)
//////                            .user_id("test"+i)
//////                            .bbs_tag("tag"+i)
//////                            .build();
//////
//////                    BoardEntity bbsresult = boardRepository.save(bbs);
//////                    log.info("result : {}", bbsresult);
//////                });
//////
//////        log.info("=====================================");
//////
//////    }
//////
//////
//////    @Test
//////    public void testSearch() {
//////        log.info("============================");
//////        log.info("BoardRepositoryTest >> testSearch START");
//////
//////        PageRequestDTO pageRequestDTO = new PageRequestDTO();
//////        pageRequestDTO.setPage_size(10);
//////        pageRequestDTO.setPage(3);
//////        pageRequestDTO.setPage_block_size(10);
//////
//////        // 여기가 order by와 limit 부분
//////        PageRequest pageable = PageRequest.of(pageRequestDTO.getPage(), pageRequestDTO.getPage_size(),Sort.by("bbsIdx").descending());
//////        Page<BoardEntity> result = boardRepository.search(pageable,new String[]{},"test","2020-02-11","2020-02-01");
//////
//////        List<BoardDTO> boardDTOList = result.toList().stream()
//////                .map(entity ->modelMapper.map(entity,BoardDTO.class))
//////                .collect(Collectors.toList());
//////
//////        PageResponseDTO<BoardDTO> responseDTO = PageResponseDTO.<BoardDTO>withAll()
//////                .pageRequestDTO(pageRequestDTO)
//////                .dtoList(boardDTOList)
//////                .total_count((int)result.getTotalElements())
//////                .build();
//////
//////        log.info("result : {}", result);
//////        log.info("result.toList() : {}", responseDTO);
//////        log.info("BoardRepositoryTest >> testSearch END");
//////        log.info("============================");
//////    }
//}
