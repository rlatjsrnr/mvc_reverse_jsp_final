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
		int num = Integer.parseInt(request.getParameter("qna_num"));
		qd.updateReadCount(num);
	}

	@Override
	public BoardVO boardReply(HttpServletRequest request) {
		
		return null;
	}

	@Override
	public void boardReplySubmit(HttpServletRequest request) {
		int qna_re_ref = Integer.parseInt(request.getParameter("qna_re_ref"));
		int qna_re_lev = Integer.parseInt(request.getParameter("qna_re_lev"));
		int qna_re_seq = Integer.parseInt(request.getParameter("qna_re_seq"));
		int qna_writer_num = Integer.parseInt(request.getParameter("qna_writer_num"));
		String qna_name = request.getParameter("qna_name");
		String qna_title = request.getParameter("qna_title");
		String qna_content = request.getParameter("qna_content");
		BoardVO board = new BoardVO(qna_name, qna_title, qna_content, qna_re_ref, qna_re_lev,qna_re_seq,qna_writer_num);
		int num = qd.boardReplySubmit(board);
		if(num > 0) {
			board = qd.getBoardVO(num);
			request.setAttribute("qna", board);			
		}
		
		
	}

	@Override
	public BoardVO getBoardVOByUpdate(HttpServletRequest request) {
		String qna_num = request.getParameter("qna_num");
		BoardVO board = qd.getBoardVO(Integer.parseInt(qna_num));
		if(board != null) {
			request.setAttribute("qna", board);
			return board;
		}else {
			return null;
		}
		
	}

	@Override
	public void boardUpdate(HttpServletRequest request, HttpServletResponse response) {
		String qna_num = request.getParameter("qna_num");
		String qna_name = request.getParameter("qna_name");
		String qna_title = request.getParameter("qna_title");
		String qna_content = request.getParameter("qna_content");
		String qna_writer_num = request.getParameter("qna_writer_num");
		
		BoardVO board = new BoardVO(qna_name, qna_title, qna_content, Integer.parseInt(qna_writer_num));
		board.setQna_num(Integer.parseInt(qna_num));
		qd.boardUpdate(board);
		board = qd.getBoardVO(Integer.parseInt(qna_num));
		request.setAttribute("qna", board);
	}

	@Override
	public void boardDelete(HttpServletRequest request, HttpServletResponse response) {
		String qna_num = request.getParameter("qna_num");
		String qna_writer_num = request.getParameter("qna_writer_num");
		if(qd.boardDelete(Integer.parseInt(qna_num), Integer.parseInt(qna_writer_num))) {
			String message = "삭제 성공";
			request.setAttribute("message", message);
			ArrayList<BoardVO> list = getBoardList(request);
			request.setAttribute("qnaList", list);
		}else {
			String message = "삭제 실패";
			request.setAttribute("message", message);
		}
	}

}
