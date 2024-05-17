package org.fullstack4.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.dto.MemberDTO;
import org.fullstack4.dto.PageRequestDTO;
import org.fullstack4.dto.PageResponseDTO;
import org.fullstack4.exception.InsufficientStockException;
import org.fullstack4.repository.MemberRepository;
import org.fullstack4.service.MemberServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;



@Log4j2
@RequestMapping("/member")
@RequiredArgsConstructor
@Controller
public class MemberController {
    private final MemberServiceImpl memberServiceImpl;

    @GetMapping("/login")
    public void login(){

    }

    @RequestMapping(value = "/login.dox", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String Login(@RequestParam HashMap<String, Object> map, HttpServletRequest req) throws Exception{
        HashMap<String, Object> resultMap = new HashMap<>();
        String id = req.getParameter("user_id").trim();
        String pwd = req.getParameter("pwd").trim();
        try {
            if (memberServiceImpl.login(id, pwd, req)) {
                resultMap.put("result", "success");
                resultMap.put("msg","성공적으로 로그인되었습니다.");
            } else {
                resultMap.put("result", "false");
                resultMap.put("msg","비밀번호가 틀렸습니다.");
            }
        }catch (InsufficientStockException e){
            resultMap.put("result", "false");
            resultMap.put("msg", e.getMessage());
        }
        return new Gson().toJson(resultMap);
    }
    @GetMapping("/mypage")
    public void mypage(HttpServletRequest req,Model model){
        HttpSession session = req.getSession();
        String id = session.getAttribute("user_id").toString();
        MemberDTO memberDTO = memberServiceImpl.view(id);
        model.addAttribute("memberDTO", memberDTO);
    }

    @PostMapping("/mypage")
    public String Postmypage(MemberDTO memberDTO){
        log.info("post start>>");
        log.info(memberDTO.toString());
        memberServiceImpl.modify(memberDTO);
        return "redirect:/member/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest req){
        HttpSession session = req.getSession();
        session.invalidate();
        return "redirect:/member/login";
    }

    @GetMapping("/join")
    public void join(){

    }
    @PostMapping("/join")
    public String Postjoin(MemberDTO memberDTO){
        try {
            memberServiceImpl.join(memberDTO);
            return "redirect:/member/login";
        }catch(Exception e){
            return e.getMessage();
        }
    }

    @GetMapping("/findpwd")
    public void findpwd(){

    }
    @RequestMapping(value = "/findpwd.dox", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String Findpwd(@RequestParam HashMap<String, Object> map, HttpServletRequest req) throws Exception{
        HashMap<String, Object> resultMap = new HashMap<>();
        HttpSession session = req.getSession();
        String id = req.getParameter("user_id");
        String pwd = memberServiceImpl.findpwd(id);
        if(pwd != null){
            resultMap.put("result", "success");
            resultMap.put("msg", pwd);
            resultMap.put("pwd", pwd);
        }else{
            resultMap.put("result", "false");
        }
        return new Gson().toJson(resultMap);
    }

    @GetMapping("/changepwd")
    public void changepwd(@RequestParam(defaultValue = "") String id,Model model){
        model.addAttribute("id", id);
    }

    @PostMapping("/changepwd")
    public void changepwd(HttpServletRequest req, HttpServletResponse resp){
        String id = req.getParameter("user_id");
        String pwd = req.getParameter("newpassword");

        int idx = memberServiceImpl.pwdmodify(id,pwd);
        if(idx > 0){
            try {
                resp.setContentType("text/html; charset=utf-8");
                PrintWriter w = resp.getWriter();
                w.write("<script>alert('수정되었습니다. 다시 로그인 해주세요.');location.href='/member/login';</script>");
                w.flush();
                w.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }else{
            try {
                resp.setContentType("text/html; charset=utf-8");
                PrintWriter w = resp.getWriter();
                w.write("<script>alert('오류가 발생했습니다.');history.go(-1);</script>");
                w.flush();
                w.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping(value = "/searchId.dox", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String SearchId(@RequestParam HashMap<String, Object> map, HttpServletRequest req) throws Exception{
        HashMap<String, Object> resultMap = new HashMap<>();
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        pageRequestDTO.setPage(Integer.parseInt(map.get("page").toString()));
        pageRequestDTO.setPage_size(5);
        pageRequestDTO.setPage_block_size(5);
        pageRequestDTO.setSearch_word(map.get("search_word").toString());
        PageResponseDTO<MemberDTO> responseDTO = memberServiceImpl.search(pageRequestDTO);

        log.info("responseDTO : {}", responseDTO);
        ArrayList<String> idList = new ArrayList<>();
        ArrayList<Integer> idxList = new ArrayList<>();
        if(responseDTO.getDtolist().size()>0) {
            for(int i=0; i<responseDTO.getDtolist().size(); i++) {
                idList.add(responseDTO.getDtolist().get(i).getUserId());
                idxList.add(responseDTO.getDtolist().get(i).getUser_idx());

            }

        }
        HttpSession session = req.getSession();
        String id = req.getParameter("user_id");
        if(responseDTO.getDtolist().size()>0){
            resultMap.put("result", "success");
            resultMap.put("msg", "검색 결과가 있습니다.");
            resultMap.put("responseDTO", responseDTO.toString());
            resultMap.put("idList", idList);
            resultMap.put("idxList", idxList);
        }else{
            resultMap.put("result", "false");
            resultMap.put("msg","검색 결과가 없습니다.");
        }
        return new Gson().toJson(resultMap);
    }

    @RequestMapping(value = "/idCheck.dox", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String checkId(@RequestParam HashMap<String, Object> map, HttpServletRequest req) throws Exception{
        HashMap<String, Object> resultMap = new HashMap<>();

        int count = memberServiceImpl.checkId(map.get("memberId1").toString());

        if(count > 0){
            resultMap.put("result", "fail");
            resultMap.put("msg", "이미 존재하는 아이디입니다.");
        }else{
            resultMap.put("result", "success");
            resultMap.put("msg", "사용하실 수 있는 아이디입니다.");
        }

        return new Gson().toJson(resultMap);
    }
}
