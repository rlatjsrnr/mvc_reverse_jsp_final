package service;

import java.util.ArrayList;

import beans.MemberVO;
import jakarta.servlet.http.HttpServletRequest;
import repositories.ManagementDAO;
import repositories.ManagementDAOImpl;
import util.Criteria;
import util.PageMaker;

public class ManagementServiceImpl implements ManagementService {
	ManagementDAO md = new ManagementDAOImpl();
	
	@Override
	public ArrayList<MemberVO> getMemberList(HttpServletRequest request) {
		ArrayList<MemberVO> memberList = null;
		PageMaker pm = new PageMaker();
		String page = request.getParameter("page");
		if(page == null) {
			page = "1";
		}
		pm.getCri().setPage(Integer.parseInt(page));		
		memberList = md.getMemberList(pm.getCri());
		int totalCount = md.getMemberTotalCount();
		pm.setTotalCount(totalCount);
		request.setAttribute("pm", pm);
		
		if(memberList != null) {
			return memberList;
		}
		
		return null;
	}
	public MemberVO getMember(HttpServletRequest request) {
		MemberVO member = null;
		String num = request.getParameter("num");
		member = md.getMember(Integer.parseInt(num));
		if(member != null) {
			return member;
		}
		return null;
	}
	@Override
	public boolean updateMember(HttpServletRequest request) {
		String num = request.getParameter("num");		
		String pass = request.getParameter("pass");		
		String age = request.getParameter("age");
		String gender = request.getParameter("gender");
		MemberVO member = md.getMember(Integer.parseInt(num));
		member.setPass(pass);
		member.setAge(Integer.parseInt(age));
		member.setGender(gender);
		int result = md.updateMember(member);
		if(result > 0) {
			String message = "회원 정보 수정 성공";
			request.setAttribute("message", message);
			return true;
		}else {
			String message = "회원 정보 수정 실패";
			request.setAttribute("message", message);
			return false;
		}
		
				
	}

	@Override
	public boolean deleteMember(HttpServletRequest request) {
		String num = request.getParameter("num");	
		int result = md.deleteMember(Integer.parseInt(num));
		
		if(result > 0) {
			String message = "회원 삭제 성공";
			request.setAttribute("message", message);
			return true;
		}else {
			String message = "회원 삭제 실패";
			request.setAttribute("message", message);
			return false;
		}		
	}
}
