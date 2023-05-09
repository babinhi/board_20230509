package com.icia.board.controller;

import com.icia.board.dto.BoardDTO;
import com.icia.board.dto.MemberDTO;
import com.icia.board.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/member")

public class MemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping("/save")
    public String saveForm(){
        return "memberpages/Membersave";
    }
    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO)  {
        System.out.println("memberDTO = " + memberDTO);
        memberService.save(memberDTO);
        return "/";
    }
    @GetMapping("/login")
    public String loginForm(){
        return "memberpages/Memberlogin";
    }
    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO, Model model, HttpSession session) {
        boolean loginresult = memberService.login(memberDTO);
        if (loginresult) {
            session.setAttribute("loginEmail", memberDTO);
            return "/";
        } else {
            return "memberpages/Memberlogin";
        }
    }

    @PostMapping("/email-check")
    public ResponseEntity emailCheck(@RequestParam("memberEmail") String memberEmail){
        System.out.println("memberEmail = " + memberEmail);
        MemberDTO memberDTO =memberService.findByMemberEmail(memberEmail);
        if(memberDTO == null){
            return new ResponseEntity(HttpStatus.OK);
        }else {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }

    }
}
