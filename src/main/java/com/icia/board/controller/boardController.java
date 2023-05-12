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
        boardService.save(boardDTO);
        System.out.println("작성하기 눌렀다 boardDTO = " + boardDTO);
        return "redirect:/board/paging";
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

        //검색어 q값이 없으면 일반 페이징 처리를해라
        if(q.equals("")){
            // 사용자가 요청한 페이지에 해당하는 글 목록 데이터
            // 사용자가 3페이지를 보고싶다 하면 3페이지에 해당하는 내용 목록들
            boardDTOList = boardService.pagingList(page);
            // 하단에 보여줄 페이지 번호 목록 데이터
            pageDTO = boardService.pagingParam(page);

            //검색어 q값이 있으면 검색어가 포함된 페이징 처리를 해라
        }else{
            // 검색어가 있으면 검색어가 포함된 페이징 처리를 해라
            boardDTOList = boardService.searchList(page,type,q);
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

    @GetMapping("/detail")
    public String detailForm(@RequestParam("id") Long id,
                             @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                             Model model) {
        //조회수를 1씩 증가시키는 메소드이다
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        model.addAttribute("page", page);
        if (boardDTO.getFileAttached() == 1) {
            // 파일이 있는 게시글을 선택하면
            List<BoardFileDTO> boardFileDTO = boardService.findFile(id);
            model.addAttribute("boardFileList", boardFileDTO);
            System.out.println("boardFileDTO = " + boardFileDTO);
        }
        return "boardpages/boardDetail";
    }

    @GetMapping("/board_delete")
    public String delete(@RequestParam("id") Long id){
        BoardDTO boardDTO = boardService.findById(id);
        boardService.delete(boardDTO);
        return "redirect:/boardpages/paging";
    }
    @GetMapping("/update")
    public String updateForm(@RequestParam("id") Long id, Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        return "/boardpages/boardUpdate";

    }

    @PostMapping("/update")
    public String update(@ModelAttribute BoardDTO boardDTO, Model model) {
        System.out.println("수줭버튼 누른 후 boardDTO = " + boardDTO);
        boardService.update(boardDTO);
        //수정된 내용의 id를 findById메소드에 매개변수로 보낸후 dto변수에 담아옴
        BoardDTO dto = boardService.findById(boardDTO.getId());
        System.out.println("(수정) dto = " + dto);
        model.addAttribute("board", dto);
        if (boardDTO.getFileAttached() == 1) {
            // 파일이 있는 게시글을 선택하면
            List<BoardFileDTO> boardFileDTO = boardService.findFile(dto.getId());
            model.addAttribute("boardFileList", boardFileDTO);
            System.out.println("boardFileDTO = " + boardFileDTO);
        }
        //resirect로 요청하면 수정한건데 조회수가 올라가니까 밑에 리턴처럼 해줘야됌
//        return "redirect:/board?id="+boardDTO.getId();

        //수정한 객체를 다시 모델에 담아가서 detail.jsp에 뿌려준다
        return "boardpages/boardDetail";
    }
}
