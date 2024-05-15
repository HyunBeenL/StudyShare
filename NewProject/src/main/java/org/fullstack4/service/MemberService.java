package org.fullstack4.service;

import jakarta.servlet.http.HttpServletRequest;
import org.fullstack4.dto.MemberDTO;
import org.fullstack4.exception.InsufficientStockException;

public interface MemberService {
    boolean login(String id, String pwd, HttpServletRequest req);
    void join(MemberDTO memberDTO) throws InsufficientStockException;
    void modify(MemberDTO memberDTO);
    void pwdmodify(String id, String pwd);
    void delete(String id);
    String findpwd(String id);
    MemberDTO view(String id);
}
