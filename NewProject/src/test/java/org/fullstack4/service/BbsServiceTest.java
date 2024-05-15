//package org.fullstack4.service;
//
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import lombok.extern.log4j.Log4j2;
//import org.fullstack4.domain.BoardEntity;
//import org.fullstack4.domain.QBoardEntity;
//import org.fullstack4.dto.BoardDTO;
//import org.fullstack4.dto.PageRequestDTO;
//import org.fullstack4.dto.PageResponseDTO;
//import org.junit.jupiter.api.Test;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//@Log4j2
//@SpringBootTest
//public class BbsServiceTest{
//
//    @Autowired
//    private BoardServiceImpl board;
//    @Autowired
//    private ModelMapper modelMapper;
//    @Autowired(required = false)
//    private JPAQueryFactory jpaQueryFactory;
//
//
//    @Test
//    public void test() {
//        PageRequestDTO pageRequestDTO = new PageRequestDTO();
//        pageRequestDTO.setPage_size(10);
//        pageRequestDTO.setPage(0);
//        pageRequestDTO.setPage_block_size(10);
//
//        PageResponseDTO<BoardDTO> responseDTO =  board.bbsListByPage(pageRequestDTO,new String[]{},"","bbsGood");
//        log.info("responseDTO:{}", responseDTO);
//    }
//}
