package com.icia.board.controller;

import com.icia.board.dto.CommentDTO;
import com.icia.board.dto.MemberDTO;
import com.icia.board.service.CommentService;
import com.icia.board.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private MemberService memberService;

        @PostMapping("/save")
    public ResponseEntity saveForm(@ModelAttribute CommentDTO commentDTO , HttpSession session){
        //프론트(boardDetail.jsp)에서 컨트롤러(CommentController)로 데이터가 잘 오는지 확인하는 과정(sout찍어보기)
        //sout에는 작성자, 댓글내용,보드아이디값,멤버아이디값이 콘솔에 잘 찍힘(서버에는 아직 저장되지 않아서 id,commentCreateDate는 null이 찍힌다
        System.out.println("댓글작성 눌렀다 commentDTO = " + commentDTO);
        String loginEmail = (String) session.getAttribute("loginEmail");
        MemberDTO memberDTO = memberService.findByMemberEmail(loginEmail);
        System.out.println(" 안녕 memberDTO = " + memberDTO);
        commentDTO.setMemberId(memberDTO.getId());
        System.out.println("여긴 memberDTO = " + memberDTO);
        System.out.println(commentDTO);
        commentService.save(commentDTO);

        List<CommentDTO> commentDTOList = commentService.findAll(commentDTO.getBoardId());
        System.out.println("목록 commentDTOList = " + commentDTOList);
        return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
    }

}
