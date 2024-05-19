package org.fullstack4.controller;


import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.common.FileUtil;
import org.fullstack4.domain.BoardEntity;
import org.fullstack4.domain.GoodBbsEntity;
import org.fullstack4.dto.BoardDTO;
import org.fullstack4.dto.PageRequestDTO;
import org.fullstack4.dto.PageResponseDTO;
import org.fullstack4.exception.InsufficientStockException;
import org.fullstack4.service.BoardServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
    public void bbsList(@Valid PageRequestDTO pageRequestDTO, Model model,HttpServletRequest request){
        HttpSession session = request.getSession();
        String user_id = session.getAttribute("user_id").toString();
        pageRequestDTO.setUser_id(user_id);

        PageResponseDTO<BoardDTO> responseDTO = boardService.bbsListByPage(pageRequestDTO);
        log.info("responseDTO : {}", responseDTO);
        model.addAttribute("responseDTO", responseDTO);


    }

    @GetMapping("/sharelist")
    public void sharelist(@Valid PageRequestDTO pageRequestDTO, Model model,HttpServletRequest request){
        HttpSession session = request.getSession();
        String user_id = session.getAttribute("user_id").toString();
        pageRequestDTO.setUser_id(user_id);
        PageResponseDTO<BoardDTO> dto = boardService.shareBbsListByPage(pageRequestDTO);
        log.info("responseDTO : {}", dto);
        model.addAttribute("responseDTO", dto);

    }

    @GetMapping("/view")
    public void bbsView(@RequestParam int bbsIdx, Model model,HttpServletRequest req){
        HttpSession session = req.getSession();
        String id = session.getAttribute("user_id").toString();
        BoardDTO boardDTO = boardService.view(bbsIdx);
        List<BoardDTO> shareList = boardService.shareList(bbsIdx);
        boolean goodcheck = boardService.viewGood(bbsIdx,id);
        log.info("goodcheck : {}", goodcheck);
        log.info("shareList : {}", shareList);
        model.addAttribute("shareList", shareList);
        model.addAttribute("boardDTO", boardDTO);
        model.addAttribute("goodcheck", goodcheck);
    }

    @GetMapping("/modify")
    public void bbsModify(@RequestParam int bbsIdx, Model model){
        BoardDTO boardDTO = boardService.view(bbsIdx);
        String[] partlist = boardDTO.getBbs_part().split(",");
        String[] taglist = boardDTO.getBbs_tag().split(",");
        model.addAttribute("boardDTO", boardDTO);
        model.addAttribute("partlist", Arrays.asList(partlist));
        model.addAttribute("taglist", Arrays.asList(taglist));
    }

    @PostMapping("/modify")
    public String PostBbsModify(BoardDTO boardDTO, Model model,MultipartFile bbs_file1,HttpServletRequest req){
        HttpSession session = req.getSession();
        String directory= "D:\\StudyShare\\NewProject\\src\\main\\resources\\static\\upload";
        boardDTO.setUserId(session.getAttribute("user_id").toString());

        boolean filedel = req.getParameter("fileDel") == null?false:true;


        if (bbs_file1 != null && !bbs_file1.isEmpty()) {
            Map<String, String> map = FileUtil.FileUpload(bbs_file1, directory);
            boardDTO.setBbs_file(map.get("newName").toString());
            boardDTO.setFileorgname(map.get("orgName").toString());
        }else{
            if(!filedel){
                if(req.getParameter("orgFileName") != null && !req.getParameter("orgFileName").isEmpty()) {
                    boardDTO.setBbs_file(req.getParameter("orgFileName"));
                    boardDTO.setFileorgname(req.getParameter("orgSaveFileName"));
                }
            }
        }
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


        String directory= "D:\\StudyShare\\NewProject\\src\\main\\resources\\static\\upload";
        HttpSession session = req.getSession();
        boardDTO.setUserId(session.getAttribute("user_id").toString());
        if(bbs_file1 !=null && !bbs_file1.isEmpty()) {
            Map<String, String> map = FileUtil.FileUpload(bbs_file1, directory);
            boardDTO.setBbs_file(map.get("newName").toString());
            boardDTO.setFileorgname(map.get("orgName").toString());
        }

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

    @RequestMapping(value = "/shareDelete.dox", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String bbsShareDelete(@RequestParam HashMap<String, Object> map) throws Exception{
        HashMap<String, Object> resultMap = new HashMap<>();
        int idx = Integer.parseInt(map.get("idx").toString());
        boardService.shareDelete(idx);
        return new Gson().toJson(resultMap);
    }


    @RequestMapping(value = "/bbsshare.dox", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String bbsShare(@RequestParam HashMap<String, Object> map,HttpServletRequest req) throws Exception{
        HashMap<String, Object> resultMap = new HashMap<>();
        HttpSession session = req.getSession();
        String idList = map.get("idList").toString();
        int bbsIdx = Integer.parseInt(map.get("bbsIdx").toString());

        log.info("bbsIdx = " + bbsIdx);

        idList = idList.replace("\"","");
        idList = idList.replace("[","");
        idList = idList.replace("]","");

        String[] userIdList = idList.split(",");
        String myId = session.getAttribute("user_id").toString();

        try{
            boardService.share(myId,userIdList,bbsIdx);
            resultMap.put("result","success");
            resultMap.put("msg","공유 목록이 추가됬습니다.");
        }catch(InsufficientStockException e){
            resultMap.put("result","fail");
            resultMap.put("msg", e.getMessage());
        }



        return new Gson().toJson(resultMap);
    }

    @RequestMapping(value = "/bbsgood.dox", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String bbsGood(@RequestParam HashMap<String,Object> map,HttpServletRequest req){
        HashMap<String, Object> resultMap = new HashMap<>();
        HttpSession session = req.getSession();
        String id = session.getAttribute("user_id").toString();
        int bbsIdx = Integer.parseInt(map.get("bbsIdx").toString());
        Boolean checkYN = Boolean.valueOf(map.get("checkYN").toString());
        if(checkYN){
            boardService.addGood(id,bbsIdx);
            resultMap.put("result","success");
            resultMap.put("msg","해당글을 추천하셨습니다.");
        }else if(!checkYN) {
            boardService.removeGood(id, bbsIdx);
            resultMap.put("result","success");
            resultMap.put("msg", "취소하셨습니다.");
        }


        return new Gson().toJson(resultMap);
    }
}
