package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ManagementService;
import service.ManagementServiceImpl;
import util.PageMaker;

@WebServlet("*.mgc")
public class ManagementController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ManagementService ms = new ManagementServiceImpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String command = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/"));
		System.out.println("Management 요청 : " + command);

		String view = null;
		
		if (command.equals("/managementPage.mgc")) {
			// 회원 목록 페이지 요청			
			request.setAttribute("memberList", ms.getMemberList(request));			
			view = "/management/member.jsp";
		}
				
		if (command.equals("/managementUpdate.mgc")) {
			// 회원 목록 페이지 요청			
			request.setAttribute("updateMember", ms.getMember(request));			
			view = "/management/memberUpdate.jsp";
		}
		if (command.equals("/managementUpdateMember.mgc")) {
			// 회원 목록 페이지 요청			
			ms.updateMember(request);
			request.setAttribute("memberList", ms.getMemberList(request));
			view = "/management/member.jsp";
		}
		
		if (command.equals("/managementDelete.mgc")) {
			// 회원 목록 페이지 요청			
			ms.deleteMember(request);
			request.setAttribute("memberList", ms.getMemberList(request));
			view = "/management/member.jsp";
		}
		
		if (view != null && !view.trim().equals("")) {			
			request.getRequestDispatcher(view).forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
