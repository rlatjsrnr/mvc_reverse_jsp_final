package service;

import beans.MemberVO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repositories.MemberDAO;
import repositories.MemberDAOImpl;

public class MemberServiceImpl implements MemberService {
	
	MemberDAO md = new MemberDAOImpl(); 
	
	@Override
	public void memberJoin(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		String name = request.getParameter("name");
		String age = request.getParameter("age");
		String gender = request.getParameter("gender");
		int intAge = Integer.parseInt(age);
		MemberVO member = new MemberVO(id, pass, name, intAge, gender);
		String message = null;
		if(md.memberJoin(member)) {
			message = "회원가입 성공";			
		}else {
			message = "사용중인 아이디입니다. 회원가입 실패";			
		}
		request.setAttribute("message", message);
	}

	@Override
	public boolean memberLogin(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String pass= request.getParameter("pass");
		String login = request.getParameter("login");
		MemberVO member = md.memberLogin(id, pass);
		if(member != null) {
			request.getSession().setAttribute("member", member);
			String message = "로그인 성공";
			if(login != null) {
				Cookie cookie = new Cookie("rememberMe", member.getId());
				cookie.setMaxAge(60*60*24*15);
				cookie.setPath("/");
				response.addCookie(cookie);				
			}
			request.setAttribute("message", message);
			return true;
		}
		String message = "로그인 실패";
		request.setAttribute("message", message);
		return false;
	}

	@Override
	public void memberUpdate(HttpServletRequest request, HttpServletResponse response) {
		
	}

	@Override
	public void logOut(HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie = new Cookie("rememberMe", "");
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		request.getSession().removeAttribute("member");
		String message = "로그아웃";
		request.setAttribute("message", message);
	}

	@Override
	public void withDraw(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String message = null;
		if(md.withDrawMember(id)) {
			message = "탈퇴완료";
			logOut(request, response);			
		}else {
			message = "탈퇴 요청 처리 실패";			
		}
		request.setAttribute("message", message);
	}
	
}
