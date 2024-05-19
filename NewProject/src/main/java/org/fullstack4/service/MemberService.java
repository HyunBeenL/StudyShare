package org.fullstack4.service;

import jakarta.servlet.http.HttpServletRequest;
import org.fullstack4.dto.MemberDTO;
import org.fullstack4.dto.PageRequestDTO;
import org.fullstack4.dto.PageResponseDTO;
import org.fullstack4.exception.InsufficientStockException;

public interface MemberService {
    boolean login(String id, String pwd, HttpServletRequest req) throws InsufficientStockException;
    void join(MemberDTO memberDTO) throws InsufficientStockException;
    void modify(MemberDTO memberDTO);
    int pwdmodify(String id, String pwd);
    void delete(String id);
    String findpwd(String id);
    MemberDTO view(String id);
    PageResponseDTO<MemberDTO> search(PageRequestDTO pageRequestDTO);
    int checkId(String id) throws InsufficientStockException;
    int checkEmail(String email1,String email2) throws InsufficientStockException;
}

