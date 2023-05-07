package com.example.demo.service;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import com.example.demo.domain.*;
import com.example.demo.mapper.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberMapper mapper;

	@Override
	public boolean signUp(Member member) {
		int cnt = mapper.createMember(member);

		return cnt == 1;
	}

	@Override
	public List<Member> listMember() {
		List<Member> list = mapper.selectAll();
		return list;
	}

	@Override
	public Member getInfo(String id) {
		Member member = mapper.getMember(id);

		return member;
	}
}
