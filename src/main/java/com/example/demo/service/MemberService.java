package com.example.demo.service;

import java.util.*;

import org.springframework.security.core.*;

import com.example.demo.domain.*;

public interface MemberService {
	boolean signUp(Member member);

	List<Member> listMember();

	Member getInfo(String id);

	boolean remove(Member member);

	boolean modify(Member member, String inputPassword);

	Map<String, Object> checkId(String id);

	Map<String, Object> checkNickName(String nickName, Authentication authentication);

	Map<String, Object> checkEmail(String email, Authentication authentication);

	// Map<String, Object> checkUpdateNickName(String nickName, String id);

	// Map<String, Object> checkUpdateEmail(String email, String id);
}
