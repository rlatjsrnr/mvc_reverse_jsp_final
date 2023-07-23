package service;

import java.util.*;

import beans.BoardVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repositories.QNABoardDAO;
import repositories.QNABoardDAOImpl;
import util.*;

public class QNABoardServiceImpl implements QNABoardService {

	QNABoardDAO qd = new QNABoardDAOImpl();
		
	@Override
	public ArrayList<BoardVO> getBoardList(HttpServletRequest request) {
		String page = request.getParameter("page");
		String perPageNum = request.getParameter("perPageNum");
		if(page == null) {
			page = "1";
		}
		if(perPageNum == null) {
			perPageNum = "10";
		}
		ArrayList<BoardVO> list = null;
		PageMaker pm = new PageMaker();
		pm.getCri().setPage(Integer.parseInt(page));
		pm.getCri().setPerPageNum(Integer.parseInt(perPageNum));
		pm.setTotalCount(qd.getListCount());
		request.setAttribute("pm", pm);		
		
		list = qd.getBoardList(pm.getCri());
		
		return list;
	}

	@Override
	public boolean boardWrite(HttpServletRequest request) {
		String name = request.getParameter("qna_name");
		String title = request.getParameter("qna_title");
		String content = request.getParameter("qna_content");
		String writer_num = request.getParameter("qna_writer_num");
		
		BoardVO vo = new BoardVO(name, title, content, Integer.parseInt(writer_num));		
		if(qd.boardWrite(vo)) {
			String message = "글 작성 성공";
			request.setAttribute("message", message);
			request.setAttribute("qna", vo);
			return true;
		}else {
			String message = "글 작성 실패";
			request.setAttribute("message", message);
			return false;
		}
		
	}

	@Override
	public BoardVO getBoardVO(HttpServletRequest request) {
		String num = request.getParameter("qna_num");
		BoardVO board = qd.getBoardVO(Integer.parseInt(num));
		if(board != null) {
			request.setAttribute("qna", board);			
		}		
		return board;
	}

	@Override
	public void updateReadCount(HttpServletRequest request) {
	}

	@Override
	public BoardVO boardReply(HttpServletRequest request) {
		return null;
	}

	@Override
	public void boardReplySubmit(HttpServletRequest request) {

	}

	@Override
	public BoardVO getBoardVOByUpdate(HttpServletRequest request) {
		return null;
	}

	@Override
	public void boardUpdate(HttpServletRequest request, HttpServletResponse response) {

	}

	@Override
	public void boardDelete(HttpServletRequest request, HttpServletResponse response) {

	}

}
