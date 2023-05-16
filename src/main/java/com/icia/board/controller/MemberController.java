package com.icia.board.controller;

import com.icia.board.dto.*;
import com.icia.board.service.BoardService;
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
    @Autowired
    private BoardService boardService;

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
            return"redirect:/";
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
    @GetMapping("/update")
    public String updateForm(@RequestParam("id") Long id, Model model){
        MemberDTO memberDTO = memberService.findById(id);
        return "memberpages/MemberUpdate";
    }
    @PostMapping("/update")
    public String update(@ModelAttribute MemberDTO memberDTO, Model model){
        memberService.update(memberDTO);
        MemberDTO dto = memberService.findById(memberDTO.getId());
        model.addAttribute("member",dto);
        return "redirect:/";
    }
    @GetMapping("/updatePassword")
    public String updateForm(HttpSession session, Model model){
        String loginEmail = (String)session.getAttribute("loginEmail");
        MemberDTO memberDTO = memberService.findByMemberEmail(loginEmail);
        model.addAttribute("member", memberDTO);
        return "memberpages/PasswordCheck";
    }

    @PostMapping("/updatePassword")
    public String updatePass(HttpSession session, Model model){
        String loginEmail = (String)session.getAttribute("loginEmail");
        MemberDTO memberDTO = memberService.findByMemberEmail(loginEmail);
        model.addAttribute("member", memberDTO);
        return "memberpages/MemberUpdate";
    }
    @GetMapping("/manager")
    public String managerForm(Model model){
        List<MemberDTO> memberDTOList = memberService.findAll();
        System.out.println("넘어오나 체크 memberDTOList = " + memberDTOList);
        model.addAttribute("memberList", memberDTOList);
        return "memberpages/ManagerPage";
    }
    @GetMapping("/memberDelete")
    public String deleteManager(@ModelAttribute MemberDTO memberDTO){
        System.out.println("체크용 memberDTO = " + memberDTO);
        memberService.delete(memberDTO);
        return "redirect:manager";
    }
}
