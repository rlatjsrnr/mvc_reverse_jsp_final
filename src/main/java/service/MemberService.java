package service;

import beans.MemberVO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repositories.MemberDAO;
import repositories.MemberDAOImpl;

public interface MemberService {

	MemberDAO md = new MemberDAOImpl();
	
	// 회원가입 처리
	void memberJoin(HttpServletRequest request, HttpServletResponse response);
	
	// 로그인 처리
	/*
	 * @return true - 로그인 성공
	 * 
	 * @return false - 로그인 실패
	 */
	boolean memberLogin(HttpServletRequest request, HttpServletResponse response);

	// 회원 정보 수정
	void memberUpdate(HttpServletRequest request, HttpServletResponse response);

	// 로그아웃
	void logOut(HttpServletRequest request, HttpServletResponse response);

	// 회원 탈퇴
	void withDraw(HttpServletRequest request, HttpServletResponse response);

	// 자동 로그인 체크
	static void loginCheck(HttpServletRequest request) {

		Cookie[] cookies = request.getCookies();
		for(Cookie c : cookies) {
			String name = c.getName();
			if(name.equals("rememberMe")) {
				String id = c.getValue();
				MemberVO member = md.getMemberById(id);
				if(member != null) {
					request.getSession().setAttribute("member", member);					
				}
			}
		}
	}
} 