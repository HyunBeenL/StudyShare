package org.fullstack4.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequestMapping("/bbs")
@RequiredArgsConstructor
public class BbsController {
    @GetMapping("/view")
    public void bbsView(){

    }

    @GetMapping("/list")
    public void bbsList(){

    }

    @GetMapping("/modify")
    public void bbsModify(){

    }
    @GetMapping("/regist")
    public void bbsRegist(){

    }
}
