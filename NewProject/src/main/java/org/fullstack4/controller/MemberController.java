package org.fullstack4.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@Log4j2
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    @GetMapping("/login")
    public void login(){

    }

    @GetMapping("/mypage")
    public void mypage(){

    }

    @GetMapping("/logout")
    public void logout(){

    }

    @GetMapping("/join")
    public void join(){

    }

    @GetMapping("/findpwd")
    public void findpwd(){

    }

    @GetMapping("/changepwd")
    public void changepwd(){

    }
}
