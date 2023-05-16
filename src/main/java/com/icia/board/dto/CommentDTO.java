package com.icia.board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.sql.Timestamp;

@Setter
@Getter
@ToString
public class CommentDTO {
    private Long id;
    private Long boardId;
    private Long memberId;
    private String commentWriter;
    private String commentContents;
    private Timestamp commentCreatedDate;
}
