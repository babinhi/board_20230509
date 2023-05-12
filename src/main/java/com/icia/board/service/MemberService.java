package com.icia.board.service;

import com.icia.board.dto.BoardDTO;
import com.icia.board.dto.BoardFileDTO;
import com.icia.board.dto.MemberDTO;
import com.icia.board.dto.MemberFileDTO;
import com.icia.board.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public void save(MemberDTO memberDTO) throws IOException {

        if(memberDTO.getMemberFile().isEmpty()){
            System.out.println("파일없음");
            memberDTO.setMemberProfile(0);
            memberRepository.save(memberDTO);
        }else{
            //파일있으면
            /*
                1. 파일첨부 여부 값 1로 세팅하고 DB에 제목 등 내용 member_table에 저장 처리
                2. 파일의 이름을 가져오고 DTO 필드값에 세팅
                3. 저장용 파일 이름 만들고 DTO 필드값에 세팅
                4. 로컬에 파일 저장
                5. member_file_table에 저장 처리
             */
            System.out.println("파일있음");
            memberDTO.setMemberProfile(1);
            MemberDTO dto = memberRepository.save(memberDTO);
            System.out.println("dto = " + dto);
            for (MultipartFile memberFile : memberDTO.getMemberFile()) {
                // 원본 파일 이름 가져오기
                String OriginalFilename = memberFile.getOriginalFilename();
                System.out.println("OriginalFilename = " + OriginalFilename);
                // 저장용 이름 만들기
                String storedFileName = System.currentTimeMillis() + "-" + OriginalFilename;
                System.out.println("storedFileName = " + storedFileName);
                // 저장을 위한 BoardFileDTO 세팅
                MemberFileDTO memberFileDTO = new MemberFileDTO();
                memberFileDTO.setOriginalFileName(OriginalFilename);
                memberFileDTO.setStoredFileName(storedFileName);
                memberFileDTO.setMemberId(dto.getId());
                // 로컬에 파일 저장
                // 저장할 경로 설정(저장할 폴더 + 저장할이름)
                String savePath = "D:\\springframework_img\\" + storedFileName;
                // 저장처리
                memberFile.transferTo(new File(savePath));
                memberRepository.saveFile(memberFileDTO);
            }
        }
//                    memberRepository.save(memberDTO);

    }


    public MemberDTO findByMemberEmail(String loginEmail) {
        return memberRepository.findByMemberEmail(loginEmail);
    }

    public boolean login(MemberDTO memberDTO) {
        MemberDTO dto = memberRepository.login(memberDTO);
        if (dto != null) {
            return true;
        } else {
            return false;
        }
    }

    public MemberFileDTO findFile(Long id) {
        return memberRepository.findFile(id);

    }

    public void update(MemberDTO memberDTO) {
        memberRepository.update(memberDTO);
    }

    public MemberDTO findById(Long id) {
        return memberRepository.findById(id);
    }
}


