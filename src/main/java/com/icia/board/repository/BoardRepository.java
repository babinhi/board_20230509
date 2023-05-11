package com.icia.board.repository;

import com.icia.board.dto.BoardDTO;
import com.icia.board.dto.BoardFileDTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class BoardRepository {
    @Autowired
    private SqlSessionTemplate sql;

    public List<BoardDTO> pagingList(Map<String, Integer> pagingParam) {
        return sql.selectList("Member.paging", pagingParam);
    }

    public int boardCount() {
        return sql.selectOne("Member.count");
    }

    public List<BoardDTO> searchList(Map<String, Object> pagingParams) {
        return sql.selectList("Member.search", pagingParams);
    }

    public int boardSearchCount(Map<String, Object> pagingParams) {
        return sql.selectOne("Member.searchCount", pagingParams);
    }

    public BoardDTO save(BoardDTO boardDTO) {
        sql.insert("Board.save",boardDTO);
        System.out.println("레포짓" +"boardDTO = " + boardDTO);
        return boardDTO;
    }

    public void saveFile(BoardFileDTO boardFileDTO) {
        sql.insert("Board.saveFile", boardFileDTO);
    }

    public List<BoardDTO> findAll() {
        return sql.selectList("Board.findAll");
    }

    public void updateHits(Long id) {
        sql.update("Board.updateHits", id);
    }

    public BoardDTO findById(Long id) {
        return sql.selectOne("Board.findById", id);
    }

    public List<BoardFileDTO> findFile(Long id) {
        return sql.selectOne("Board.findFile", id);
    }
}
