package com.icia.board.controller;

import com.icia.board.dto.*;
import com.icia.board.service.BoardService;
import com.icia.board.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/board")
public class boardController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private BoardService boardService;

    @GetMapping("/boardSave")
    public String boardSaveForm(HttpSession session, Model model){
        String loginEmail = (String)session.getAttribute("loginEmail");
        MemberDTO memberDTO = memberService.findByMemberEmail(loginEmail);
        System.out.println("글쓰기 눌렀다 memberDTO = " + memberDTO);
        model.addAttribute("member", memberDTO);
        return "boardpages/boardSave";
    }
    @PostMapping("/boardSave")
    public String boardSave(HttpSession session, @ModelAttribute BoardDTO boardDTO) throws IOException {
        String loginEmail = (String) session.getAttribute("loginEmail");
        MemberDTO memberDTO = memberService.findByMemberEmail(loginEmail);
        boardDTO.setMemberId(memberDTO.getId());
        System.out.println("야야야 boardDTO = " + boardDTO);
        boardService.boardSave(boardDTO);
        System.out.println("작성하기 눌렀다 boardDTO = " + boardDTO);
        return"redirect:/";
    }
    @GetMapping("/boardList")
    public String findAll(Model model){
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        System.out.println("게시판 눌렀다 boardDTOList = " + boardDTOList);
        return "boardpages/boardList";
    }
    @GetMapping("/paging")
    public String paging(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                         @RequestParam(value = "q", required = false, defaultValue = "") String q,
                         @RequestParam(value = "type",required = false, defaultValue = "boardTitle") String type,
                         Model model) {
        System.out.println("페이징 page = " + page + ", q = " + q+",type= "+type);
        List<BoardDTO> boardDTOList = null;
        PageDTO pageDTO = null;
        // 사용자가 요청한 페이지에 해당하는 글 목록 데이터
        // 사용자가 3페이지를 보고싶다 하면 3페이지에 해당하는 내용 목록들
        if(q.equals("")){
            // 사용자가 요청한 페이지에 해당하는 글 목록 데이터
            // 사용자가 3페이지를 보고싶다 하면 3페이지에 해당하는 내용 목록들
            boardDTOList = boardService.pagingList(page);
            // 하단에 보여줄 페이지 번호 목록 데이터
            pageDTO = boardService.pagingParam(page);

            //검색어 q값이 있으면 검색어가 포함된 페이징 처리를 해라
        }else{
            // 검색어가 있으면 검색어가 포함된 페이징 처리를 해라
            boardDTOList = boardService.searchList(type, q, page);
            pageDTO = boardService.pagingSearchParam(page,type,q);
        }
        // 페이지에 들어가는 글 목록들
        model.addAttribute("boardList", boardDTOList);
        // 하단에 보여줄 페이지 목록들
        model.addAttribute("paging", pageDTO);
        model.addAttribute("q", q);
        model.addAttribute("type",type);
        return "boardpages/boardPaging";
    }

    @GetMapping
    public String findById(@RequestParam("id") Long id, Model model,
                           @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                           @RequestParam(value = "q", required = false, defaultValue = "") String q,
                           @RequestParam(value = "type", required = false, defaultValue = "boardTitle") String type) {
        System.out.println("id = " + id + ", model = " + model);
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        model.addAttribute("page", page);
        model.addAttribute("q", q);
        model.addAttribute("type", type);

        if (boardDTO.getFileAttached() == 1) {
            List<BoardFileDTO> boardFileDTO = boardService.findFile(id);
            model.addAttribute("boardFileList", boardFileDTO);
            System.out.println("boardFileDTO = " + boardFileDTO);
        }
        return "boardpages/boardDetail";

    }
}
