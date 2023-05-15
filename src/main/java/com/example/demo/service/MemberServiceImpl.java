package com.example.demo.service;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import com.example.demo.domain.*;
import com.example.demo.mapper.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberMapper mapper;

	@Autowired
	BoardService boardService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public boolean signUp(Member member) {
		// 암호를 암호화
		String plain = member.getPassword();
		member.setPassword(passwordEncoder.encode(plain));

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
		Member member = mapper.selectById(id);

		return member;
	}

	@Override
	public boolean remove(Member member) {
		Member dbMember = mapper.selectById(member.getId());
		int cnt = 0;
		// dbMember.getPassword().equals(member.getPassword()
		if (passwordEncoder.matches(member.getPassword(), dbMember.getPassword())) {
			// 암호가 같으면?

			// 이 회원이 작성한 게시물 row 삭제
			boardService.removeByWriter(member.getId());

			// 회원 테이블 삭제
			cnt = mapper.deleteById(member.getId());
		}
		return cnt == 1;
	}

	@Override
	public boolean modify(Member member, String inputPassword) {
		int cnt = 0;

		// 패스워드를 바꾸기 입력했다면
		if (!member.getPassword().isBlank()) {
			// 입력된 패스워드를 암호화
			String plain = member.getPassword();
			member.setPassword(passwordEncoder.encode(plain));
		}

		Member dbMember = mapper.selectById(member.getId());
		if (passwordEncoder.matches(inputPassword, dbMember.getPassword())) {
			// 암호가 같으면?
			cnt = mapper.update(member);
		}
		return cnt == 1;
	}

	@Override
	public Map<String, Object> checkId(String id) {
		Member member = mapper.selectById(id);
		return Map.of("available", member == null);
	}

	@Override
	public Map<String, Object> checkNickName(String nickName) {
		Member member = mapper.selectByNickName(nickName);
		return Map.of("available", member == null);
	}

	@Override
	public Map<String, Object> checkEmail(String email) {
		Member member = mapper.selectByEamil(email);
		return Map.of("available", member == null);
	}
	
	
	
	
	
	
	
	
	
	
	

}
