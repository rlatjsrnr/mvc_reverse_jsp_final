package repositories;

import java.sql.*;
import java.util.ArrayList;

import com.mysql.cj.xdevapi.Result;

import beans.NoticeVO;
import util.DBCPUtil;
import util.SearchCriteria;

public class NoticeDAOImpl implements NoticeDAO {

	@Override
	public boolean noticeWrite(NoticeVO vo) {
		Connection conn = DBCPUtil.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement("INSERT INTO notice_board(notice_category, notice_author, notice_title, notice_content) VALUES(?,?,?,?)");
			pstmt.setString(1, vo.getNotice_category());
			pstmt.setString(2, vo.getNotice_author());
			pstmt.setString(3, vo.getNotice_title());
			pstmt.setString(4, vo.getNotice_content());
			
			int result = pstmt.executeUpdate();
			if(result > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBCPUtil.close(pstmt, conn);
		}
		return false;
	}

	@Override
	public ArrayList<NoticeVO> noticeList(SearchCriteria cri) {
		Connection conn = DBCPUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<NoticeVO> noticeList = new ArrayList<>();		
		try {
			if(cri.getSearchValue() != null) {
				if(cri.getSearchType().equals("title")) {
					pstmt = conn.prepareStatement(
							"SELECT * FROM notice_board WHERE notice_title LIKE ? ORDER BY notice_num DESC limit ?, ?"
							);					
				}else {
					pstmt = conn.prepareStatement(
							"SELECT * FROM notice_board WHERE notice_content LIKE ? ORDER BY notice_num DESC limit ?, ?"
							);
				}
				pstmt.setString(1, "%"+cri.getSearchValue()+"%");
				pstmt.setInt(2, cri.getStartRow());
				pstmt.setInt(3, cri.getPerPageNum());
			}else {
				pstmt = conn.prepareStatement("SELECT * FROM notice_board ORDER BY notice_num DESC limit ?, ?");
				pstmt.setInt(1, cri.getStartRow());
				pstmt.setInt(2, cri.getPerPageNum());
			}
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				NoticeVO notice = new NoticeVO(
										rs.getInt(1),
										rs.getString(2),
										rs.getString(3),
										rs.getString(4),
										rs.getString(5),
										rs.getDate(6)
									);
				noticeList.add(notice);
			}
			return noticeList;
		} catch (SQLException e) {
			e.printStackTrace();			
		}finally {
			DBCPUtil.close(rs, pstmt, conn);
		}		
		return null;
	}

	@Override
	public int getTotalCount(SearchCriteria cri) {
		Connection conn = DBCPUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			if(cri.getSearchValue() != null) {
				if(cri.getSearchType().equals("title")) {
					pstmt = conn.prepareStatement("SELECT * FROM notice_board WHERE notice_title LIKE ? ORDER BY notice_num DESC limit ?, ?");					
				}else {
					pstmt = conn.prepareStatement("SELECT * FROM notice_board WHERE notice_content LIKE ? ORDER BY notice_num DESC limit ?, ?");
				}
				pstmt.setString(1, "%"+cri.getSearchValue()+"%");
				pstmt.setInt(2, cri.getStartRow());
				pstmt.setInt(3, cri.getPerPageNum());
			}else {
				pstmt = conn.prepareStatement("SELECT * FROM notice_board ORDER BY notice_num DESC limit ?, ?");
				pstmt.setInt(1, cri.getStartRow());
				pstmt.setInt(2, cri.getPerPageNum());
			}
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}finally {
			DBCPUtil.close(rs, pstmt, conn);
		}
		return 0;
	}

	@Override
	public NoticeVO noticeDetail(int notice_num) {
		NoticeVO notice = null;
		Connection conn = DBCPUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement("SELECT * FROM notice_board WHERE notice_num=?");
			pstmt.setInt(1, notice_num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				notice = new NoticeVO(
							rs.getInt(1),
							rs.getString(2),
							rs.getString(3),
							rs.getString(4),
							rs.getString(5),
							rs.getDate(6)
						);				
			}
			return notice;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return notice;
	}

	@Override
	public boolean noticeUpdate(NoticeVO vo) {
		Connection conn = DBCPUtil.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement("UPDATE notice_board SET notice_category=?, notice_title=?, notice_content=? WHERE notice_num=?");
			pstmt.setString(1, vo.getNotice_category());
			pstmt.setString(2, vo.getNotice_title());
			pstmt.setString(3, vo.getNotice_content());
			pstmt.setInt(4, vo.getNotice_num());
			
			int result = pstmt.executeUpdate();
			if(result > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBCPUtil.close(pstmt, conn);
		}
		
		return false;
	}

	@Override
	public boolean noticeDelete(int notice_num) {
		Connection conn = DBCPUtil.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement("DELETE FROM notice_board WHERE notice_num=?");
			pstmt.setInt(1, notice_num);
			int result = pstmt.executeUpdate();
			if(result > 0) {				
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally {
			DBCPUtil.close(pstmt, conn);
		}
		return false;
	}

}
