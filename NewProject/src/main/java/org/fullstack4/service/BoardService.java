package org.fullstack4.service;

import org.fullstack4.domain.GoodBbsEntity;
import org.fullstack4.dto.BoardDTO;
import org.fullstack4.dto.PageRequestDTO;
import org.fullstack4.dto.PageResponseDTO;
import org.fullstack4.exception.InsufficientStockException;

import java.time.LocalDate;
import java.util.List;


public interface BoardService {
    PageResponseDTO<BoardDTO> bbsListByPage(PageRequestDTO pageRequestDTO);
    PageResponseDTO<BoardDTO> shareBbsListByPage(PageRequestDTO pageRequestDTO);
    BoardDTO view(int bbsIdx);
    void delete(int bbsIdx);
    void shareDelete(int idx);
    int modify(BoardDTO boardDTO);
    int regist(BoardDTO boardDTO);
    void share(String myId, String[] userIdList,int bbs_idx) throws InsufficientStockException;
    void addGood(String id, int bbsIdx);
    boolean viewGood(int bbsIdx,String id);
    void removeGood(String id, int bbsIdx);
    List<BoardDTO> shareList(int bbsIdx);
    List<BoardDTO> todayList(String userId, LocalDate date);

}
