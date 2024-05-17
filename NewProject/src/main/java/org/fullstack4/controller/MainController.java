package org.fullstack4.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.common.CommonUtil;
import org.fullstack4.dto.BoardDTO;
import org.fullstack4.service.BoardServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Controller
public class MainController {
    private final BoardServiceImpl boardService;
    @GetMapping("/")
    public String getHello(HttpServletRequest req, Model model) {
        HttpSession session = req.getSession();
//        String user_id = session.getAttribute("user_id").toString();
//        List<BoardDTO> dtoList = boardService.todayList(user_id);
//        model.addAttribute("dtoList", dtoList);
//        log.info("dtoList : {}",dtoList);
//        if(session.getAttribute("user_id") != null) {
//            String user_id = session.getAttribute("user_id").toString();
//            List<BoardDTO> dtoList = boardService.todayList(user_id);
//            model.addAttribute("dtoList", dtoList);
//            log.info("dtoList : {}",dtoList);
//        }


        return "index";
    }

    @GetMapping("/main")
    public void getMain(HttpServletRequest req
            , Model model
            , @RequestParam(defaultValue = "") LocalDate date) {
        HttpSession session = req.getSession();
        if( date == null || date.equals("") ){
            date = LocalDate.now();
        }
        if(session.getAttribute("user_id") != null) {
            String user_id = session.getAttribute("user_id").toString();
            List<BoardDTO> dtoList = boardService.todayList(user_id,date);
            model.addAttribute("dtoList", dtoList);
            log.info("dtoList : {}",dtoList);
        }
        List<LocalDate> dayList = CommonUtil.TakeLocaldDate();
        List<String> dateList = CommonUtil.TakeDate();

        log.info("dateList : {}",dateList);
        log.info("dayList : {}",dayList);
        model.addAttribute("dateList", dateList);
        model.addAttribute("dayList", dayList);
    }
}
