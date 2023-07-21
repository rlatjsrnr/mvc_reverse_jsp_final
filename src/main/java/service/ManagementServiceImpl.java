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
		pm.getCri().setPage(Integer.parseInt(request.getParameter("page")));		
		memberList = md.getMemberList(pm.getCri());
		int totalCount = md.getMemberTotalCount();
		pm.setTotalCount(totalCount);
		request.setAttribute("pm", pm);
		
		if(memberList != null) {
			return memberList;
		}
		
		return null;
	}

	@Override
	public boolean updateMember(HttpServletRequest request) {
		
		return false;
	}

	@Override
	public boolean deleteMember(HttpServletRequest request) {
		
		return false;
	}

}
