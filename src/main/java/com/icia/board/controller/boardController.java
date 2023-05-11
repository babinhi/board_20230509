package com.icia.board.controller;

import com.icia.board.dto.BoardDTO;
import com.icia.board.dto.BoardFileDTO;
import com.icia.board.dto.CommentDTO;
import com.icia.board.dto.PageDTO;
import com.icia.board.service.BoardService;
import com.icia.board.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/save")
    public String saveForm(){
        return "boardpages/boardSave";
    }
    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO) throws IOException {
        System.out.println("여긴 컨트롤러" +"boardDTO = " + boardDTO);
        boardService.save(boardDTO);
        return"redirect:/board/paging";
    }
    @GetMapping("/paging")
    public String findAll(Model model){
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        return "boardpages/boardPaging";
    }

//    @GetMapping("/paging")
//    public String paging(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
//                         @RequestParam(value = "q", required = false, defaultValue = "") String q,
//                         @RequestParam(value = "type", required = false, defaultValue = "boardTitle") String type,
//                         Model model) {
//        System.out.println("page = " + page + ", q = " + q);
//        List<BoardDTO> boardDTOList = null;
//        PageDTO pageDTO = null;
//        if (q.equals("")) {
//            // 사용자가 요청한 페이지에 해당하는 글 목록 데이터
//            boardDTOList = boardService.pagingList(page);
//            // 하단에 보여줄 페이지 번호 목록 데이터
//            pageDTO = boardService.pagingParam(page);
//        } else {
//            boardDTOList = boardService.searchList(q, type, page);
//            pageDTO = boardService.pagingSearchParam(page, type, q);
//        }
//        model.addAttribute("boardList", boardDTOList);
//        model.addAttribute("paging", pageDTO);
//        model.addAttribute("q", q);
//        model.addAttribute("type", type);
//        return"redirect:/paging";
//    } //검색과 페이징을 합친것

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
