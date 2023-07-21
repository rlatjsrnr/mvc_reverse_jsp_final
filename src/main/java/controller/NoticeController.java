package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.NoticeService;
import service.NoticeServiceImpl;

@WebServlet("*.do")
public class NoticeController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	// 공지 사항 관련 요청을 처리할 클래스
	NoticeService ns = new NoticeServiceImpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String command = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/") + 1);
		System.out.println("NoticeController 요청 : " + command);

		String view = null;

		boolean isSuccess = false;

		String suc = "/board/notice/notice_success.jsp";
		String fail = "/board/notice/notice_fail.jsp";

		if (command.equals("notice.do")) {
			// 공지 목록
			ns.noticeList(request);
			view = "/board/notice/notice_list.jsp";
		}
		
		if (command.equals("noticeWrite.do")) {
			// 공지 목록
			if(ns.noticeWrite(request)) {
				view = suc;
			}else {
				view = fail;
			}
			
		}
		
		if (command.equals("noticeDetail.do")) {
			// 공지 목록
			ns.noticeDetail(request);
			view = "/board/notice/notice_detail.jsp";
		}
		

		if (view != null && !view.equals("")) {
			request.getRequestDispatcher(view).forward(request, response);
		} else {
			request.getRequestDispatcher(fail).forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
