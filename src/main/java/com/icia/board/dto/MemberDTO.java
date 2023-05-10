package com.icia.board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Setter
@Getter
@ToString
public class MemberDTO {
    public Long id;
    public String memberEmail;
    public String memberPassword;
    public String memberName;
    public String memberMobile;
    public int memberProfile;
    private List<MultipartFile> memberFile;
}
