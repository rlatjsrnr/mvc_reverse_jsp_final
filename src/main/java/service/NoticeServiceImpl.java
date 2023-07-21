package service;

import java.util.ArrayList;

import beans.NoticeVO;
import jakarta.servlet.http.HttpServletRequest;
import repositories.NoticeDAO;
import repositories.NoticeDAOImpl;
import util.Criteria;
import util.PageMaker;
import util.SearchCriteria;
import util.SearchPageMaker;

public class NoticeServiceImpl implements NoticeService {
	NoticeDAO nd = new NoticeDAOImpl();

	@Override
	public boolean noticeWrite(HttpServletRequest request) {
		String notice_category = request.getParameter("notice_category");
		String notice_author = request.getParameter("notice_author");
		String notice_title = request.getParameter("notice_title");
		String notice_content = request.getParameter("notice_content");
		
		NoticeVO notice = new NoticeVO(notice_category, notice_author, notice_title, notice_content);
		if(nd.noticeWrite(notice)) {
			String message = "글 작성 성공";
			request.setAttribute("message", message);
			return true;
		}else {
			String message = "글 작성 실패";
			request.setAttribute("message", message);
			return false;
		}
	}

	@Override
	public void noticeList(HttpServletRequest request) {		
		String page = request.getParameter("page");		
		String perPageNum = request.getParameter("perPageNum");
		String searchType = request.getParameter("searchType");
		String searchValue = request.getParameter("searchValue");
		if(page == null) {
			page="1";
		}
		if(perPageNum == null) {
			perPageNum = "10";
		}		
		SearchCriteria cri = new SearchCriteria();
		cri.setPage(Integer.parseInt(page));
		cri.setPerPageNum(Integer.parseInt(perPageNum));
		cri.setSearchType(searchType);
		cri.setSearchValue(searchValue);
		SearchPageMaker pm = new SearchPageMaker();
		pm.setCri(cri);
		pm.setTotalCount(nd.getTotalCount(cri));		
		ArrayList<NoticeVO> noticeList = nd.noticeList(cri);
		request.setAttribute("noticeList", noticeList);		
		request.setAttribute("pm", pm);
	}

	@Override
	public void noticeDetail(HttpServletRequest request) {
		String notice_num = request.getParameter("notice_num");
		NoticeVO notice = nd.noticeDetail(Integer.parseInt(notice_num));
		if(notice != null) {
			request.setAttribute("notice", notice);
		}
		
	}

	@Override
	public boolean noticeUpdate(HttpServletRequest request) {
		return false;
	}

	@Override
	public boolean noticeDelete(HttpServletRequest request) {
		return false;
	}

}
