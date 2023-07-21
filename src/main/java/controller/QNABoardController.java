package controller;

import java.io.IOException;
import java.util.ArrayList;

import beans.BoardVO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.QNABoardService;

@WebServlet("*.qna")
public class QNABoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	QNABoardService qs;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String command = request.getRequestURI().substring(request.getContextPath().length() + 1);
		System.out.println("command : " + command);

		String nextPage = null;

		if (command.equals("boardList.qna")) {
			// 질문과 답변 게시물 목록
			ArrayList<BoardVO> list = qs.getBoardList(request);
			request.setAttribute("list", list);
			nextPage = "/board/qna/qna_list.jsp";
		}

		if (nextPage != null && !nextPage.trim().equals("")) {
			RequestDispatcher rd = request.getRequestDispatcher(nextPage);
			rd.forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
