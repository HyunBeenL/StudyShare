package org.fullstack4.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.dto.MemberDTO;
import org.fullstack4.repository.MemberRepository;
import org.fullstack4.service.MemberServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;



@Log4j2
@RequestMapping("/member")
@RequiredArgsConstructor
@Controller
public class MemberController {
    private final MemberServiceImpl memberServiceImpl;
    private final HttpSession httpSession;
    private final MemberRepository memberRepository;

    @GetMapping("/login")
    public void login(){

    }

    @RequestMapping(value = "/login.dox", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String Login(@RequestParam HashMap<String, Object> map, HttpServletRequest req) throws Exception{
        HashMap<String, Object> resultMap = new HashMap<>();
        String id = req.getParameter("user_id");
        String pwd = req.getParameter("pwd");
        if(memberServiceImpl.login(id, pwd,req)) {
            resultMap.put("result", "success");
        }else{
            resultMap.put("result", "false");
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
        }else{
            resultMap.put("result", "false");
        }
        return new Gson().toJson(resultMap);
    }

    @GetMapping("/changepwd")
    public void changepwd(){

    }
}
