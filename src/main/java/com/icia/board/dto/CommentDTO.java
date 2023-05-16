package com.icia.board.dto;

import lombok.*;
import java.sql.Timestamp;

@Setter
@Getter
@ToString
public class CommentDTO {
    private Long id;
    private Long boardId;
    private Long memberId;
    private String commentContents;
    private Timestamp commentCreatedDate;
}
