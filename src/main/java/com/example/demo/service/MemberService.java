package com.example.demo.service;

import java.util.*;

import com.example.demo.domain.*;

public interface MemberService {
	boolean signUp(Member member);

	List<Member> listMember();

	Member getInfo(String id);

	boolean remove(Member member);

	boolean modify(Member member, String inputPassword);

	Map<String, Object> checkId(String id);
}
