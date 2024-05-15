package org.fullstack4.controller;


import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.common.FileUtil;
import org.fullstack4.domain.BoardEntity;
import org.fullstack4.dto.BoardDTO;
import org.fullstack4.dto.PageRequestDTO;
import org.fullstack4.dto.PageResponseDTO;
import org.fullstack4.service.BoardServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
@Log4j2
@RequestMapping("/bbs")
@RequiredArgsConstructor
public class BoardController {

    private final BoardServiceImpl boardService;

    @GetMapping("/list")
//    public void bbsList(@RequestParam(defaultValue = "0") int page,
//                        @RequestParam(defaultValue = "10") int size,
//                        @RequestParam(defaultValue = "") String[] search_types,
//                        @RequestParam(defaultValue = "") String search_word,
//                        @RequestParam(defaultValue = "regDate") String sort_type
    public void bbsList(@Valid PageRequestDTO pageRequestDTO, Model model){

        PageResponseDTO<BoardDTO> responseDTO = boardService.bbsListByPage(pageRequestDTO);
        log.info("responseDTO : {}", responseDTO);
        model.addAttribute("responseDTO", responseDTO);


    }

    @GetMapping("/view")
    public void bbsView(@RequestParam int bbsIdx, Model model){
        BoardDTO boardDTO = boardService.view(bbsIdx);
        model.addAttribute("boardDTO", boardDTO);
    }

    @GetMapping("/modify")
    public void bbsModify(@RequestParam int bbsIdx, Model model){
        BoardDTO boardDTO = boardService.view(bbsIdx);
        model.addAttribute("boardDTO", boardDTO);
    }

    @PostMapping("/modify")
    public String PostBbsModify(BoardDTO boardDTO, Model model,HttpServletRequest req){
        HttpSession session = req.getSession();
        boardDTO.setUser_id(session.getAttribute("user_id").toString());
        int idx =boardService.modify(boardDTO);
        if(idx == boardDTO.getBbsIdx()) {
            return "redirect:/bbs/view?bbsIdx=" + idx;
        }else{
            return "redirect:/bbs/modify?bbsIdx="+boardDTO.getBbsIdx();
        }
    }

    @GetMapping("/regist")
    public void bbsRegist(){

    }

    @PostMapping("/regist")
    public String PostBbsRegist(BoardDTO boardDTO,
                                Model model,
                                MultipartFile bbs_file1,
                                HttpServletRequest req){


        String directory= "D:\\StudyShare\\NewProject\\src\\main\\resources\\upload";
        HttpSession session = req.getSession();
        boardDTO.setUser_id(session.getAttribute("user_id").toString());
        Map<String, String> map = FileUtil.FileUpload(bbs_file1,directory);
        boardDTO.setBbs_file(map.get("newName").toString());
        int idx = boardService.regist(boardDTO);
        if(idx>0) {
            return "redirect:/bbs/view?bbsIdx=" + idx;
        }else{
            return "redirect:/bbs/modify?bbsIdx="+boardDTO.getBbsIdx();
        }
    }

    @RequestMapping(value = "/delete.dox", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String bbsDelete(@RequestParam HashMap<String, Object> map) throws Exception{
        HashMap<String, Object> resultMap = new HashMap<>();
        int idx = Integer.parseInt(map.get("idx").toString());
        boardService.delete(idx);
        return new Gson().toJson(resultMap);
    }
}
