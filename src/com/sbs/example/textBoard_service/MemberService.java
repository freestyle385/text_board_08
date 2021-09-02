package com.sbs.example.textBoard_service;

import java.sql.Connection;

import com.sbs.example.textBoard_dao.MemberDao;


public class MemberService {

	private MemberDao memberDao;

	public MemberService(Connection conn) {
		memberDao = new MemberDao(conn);
	}
	
	public boolean isLoginIdDup(String loginId) {
		return memberDao.isLoginIdDup(loginId);
	}
	
	public void doJoin(String loginId, String loginPw, String name) {
		memberDao.doJoin(loginId, loginPw, name);
	}
}
