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
		if(md.memberJoin(member)) {
			String message = "회원가입 성공";
			request.setAttribute("message", message);
		}else {
			String message = "회원가입 실패";
			request.setAttribute("message", message);
		}
	}

	@Override
	public boolean memberLogin(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String pass= request.getParameter("pass");
		MemberVO member = md.memberLogin(id, pass);
		if(member != null) {
			request.getSession().setAttribute("member", member);
			String message = "로그인 성공";
			Cookie cookie = new Cookie("rememberMe", member.getId());
			cookie.setMaxAge(60*60*24*15);
			cookie.setPath("/");
			response.addCookie(cookie);
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
		md.withDrawMember(id);
		String message = "탈퇴완료";
		logOut(request, response);
		request.setAttribute("message", message);		
	}
	
}
