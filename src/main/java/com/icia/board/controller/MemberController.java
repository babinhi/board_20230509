package com.icia.board.controller;

import com.icia.board.dto.*;
import com.icia.board.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

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
    public String save(@ModelAttribute MemberDTO memberDTO) throws IOException {
        System.out.println("memberDTO = " + memberDTO);
        memberService.save(memberDTO);
        return "memberpages/Saveok";
    }
    @GetMapping("/login")
    public String loginForm(){
        return "memberpages/Memberlogin";
    }
    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO, Model model, HttpSession session) {
        boolean loginResult = memberService.login(memberDTO);
        if (loginResult) {
            session.setAttribute("loginEmail", memberDTO.getMemberEmail());
            return"redirect:/board/paging";
        } else {
            return "memberpages/LoginError";
        }
    }

    @GetMapping("/myPage")
    public String myPage(@RequestParam("loginEmail") String loginEmail, Model model){
        MemberDTO memberDTO = memberService.findByMemberEmail(loginEmail);
        model.addAttribute("member",memberDTO);
        if(memberDTO.getMemberProfile() == 1){
            MemberFileDTO memberFileDTO = (MemberFileDTO) memberService.findFile(memberDTO.getId());
            System.out.println("memberFileDTO = " + memberFileDTO);
            model.addAttribute("memberFile", memberFileDTO);
        }
        return "/memberpages/MemberPage";
    }
    @GetMapping("logout")
    public String logout(HttpSession session){
        session.removeAttribute("loginEmail");
        return "redirect:/";
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
