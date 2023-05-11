package com.icia.board.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@ToString
public class BoardDTO {
    private Long id;
    private String boardTitle;
    private String boardWriter;
    private String boardContents;
    private Long memberId;
    private Timestamp boardCreatedDate;
    private int boardHits;
    private int fileAttached;
    private List<MultipartFile> boardFile;

}
