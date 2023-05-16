package com.example.demo.service;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.*;
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
	private BoardLikeMapper likeMapper;

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
	@Transactional(rollbackFor = Exception.class)
	public boolean remove(Member member) {
		Member dbMember = mapper.selectById(member.getId());
		int cnt = 0;
		// dbMember.getPassword().equals(member.getPassword()
		if (passwordEncoder.matches(member.getPassword(), dbMember.getPassword())) {
			// 암호가 같으면?

			// 이 회원이 작성한 게시물 row 삭제
			boardService.removeByWriter(member.getId());
			
			// 이 회원이 좋아요한 레코드 삭제
			likeMapper.removeByMemberId(member.getId());
			
			// 회원 테이블 삭제
			cnt = mapper.deleteById(member.getId());
		}
		return cnt == 1;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
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
	public Map<String, Object> checkNickName(String nickName, Authentication authentication) {
		Member member = mapper.selectByNickName(nickName);
		if (authentication != null) {
			Member checkMember = mapper.selectByNickName(authentication.getName());
			return Map.of("available", member == null || checkMember.getNickName().equals(nickName));
		} else {
			return Map.of("available", member == null);
		}
	}
	
	public Map<String, Object> checkEmail(String email, Authentication authentication) {
		Member member = mapper.selectByEmail(email);
		
		if (authentication != null) {
			Member checkMember = mapper.selectById(authentication.getName());
			
			return Map.of("available", member == null || checkMember.getEmail().equals(email));
		} else {
			return Map.of("available", member == null);
			
		}
		
	}

	/*
	 * @Override public Map<String, Object> checkUpdateNickName(String nickName,
	 * String id) { Member member = mapper.selectByNickName(nickName); Member
	 * checkMember = mapper.selectById(id); if
	 * (nickName.equals(checkMember.getNickName())) { return Map.of("available",
	 * true); } return Map.of("available", member == null); }
	 * 
	 * @Override public Map<String, Object> checkUpdateEmail(String email, String
	 * id) { Member member = mapper.selectByEamil(email); Member checkMember =
	 * mapper.selectById(id); if (email.equals(checkMember.getEmail())) { return
	 * Map.of("available", true); } return Map.of("available", member == null); }
	 */

}
