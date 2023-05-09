package com.icia.board.repository;

import com.icia.board.dto.MemberDTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {
    @Autowired
    private SqlSessionTemplate sql;
    public MemberDTO save(MemberDTO memberDTO) {
        System.out.println("memberDTO = " + memberDTO);
        sql.insert("Member.save", memberDTO);
        return memberDTO;
    }

    public MemberDTO findByMemberEmail(String memberEmail) {
        return sql.selectOne("Member.findByMemberEmail", memberEmail);
    }


    public MemberDTO login(MemberDTO memberDTO) {
        return sql.selectOne("Member.login", memberDTO);
    }
}
