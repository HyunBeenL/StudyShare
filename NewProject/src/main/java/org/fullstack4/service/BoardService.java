package org.fullstack4.service;

import org.fullstack4.dto.BoardDTO;
import org.fullstack4.dto.PageRequestDTO;
import org.fullstack4.dto.PageResponseDTO;


public interface BoardService {
    PageResponseDTO<BoardDTO> bbsListByPage(PageRequestDTO pageRequestDTO);
    BoardDTO view(int bbsIdx);
    void delete(int bbsIdx);
    int modify(BoardDTO boardDTO);
    int regist(BoardDTO boardDTO);
}
