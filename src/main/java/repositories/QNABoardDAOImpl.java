package repositories;

import java.sql.*;
import java.util.ArrayList;

import beans.BoardVO;
import util.Criteria;
import util.DBCPUtil;

public class QNABoardDAOImpl implements QNABoardDAO {

	@Override
	public int getListCount() {
		Connection conn = DBCPUtil.getConnection();
		Statement stmt = null;
		ResultSet rs = null;		
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT count(*) FROM qna_board");
			
			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}finally {
			DBCPUtil.close(rs, stmt,conn);
		}
		return 0;
	}

	@Override
	public ArrayList<BoardVO> getBoardList(Criteria cri) {
		ArrayList<BoardVO> list = new ArrayList<>();
		Connection conn = DBCPUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
			pstmt = conn.prepareStatement("SELECT * FROM qna_board ORDER BY qna_re_ref DESC, qna_re_seq ASC limit ?, ?");
			pstmt.setInt(1, cri.getStartRow());
			pstmt.setInt(2, cri.getPerPageNum());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardVO vo = new BoardVO(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getInt(5),
						rs.getInt(6),
						rs.getInt(7),
						rs.getInt(8),
						rs.getInt(9),
						rs.getString(10),
						rs.getDate(11)
						);
				list.add(vo);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			list = null;
		}finally {
			DBCPUtil.close(rs, pstmt, conn);
		}
		return list;
	}

	@Override
	public boolean boardWrite(BoardVO board) {
		Connection conn = DBCPUtil.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement("INSERT INTO qna_board(qna_name, qna_title, qna_content, qna_writer_num) VALUES(?,?,?,?)");
			pstmt.setString(1, board.getQna_name());
			pstmt.setString(2, board.getQna_title());
			pstmt.setString(3, board.getQna_content());
			pstmt.setInt(4, board.getQna_writer_num());
			
			int result = pstmt.executeUpdate();
			
			DBCPUtil.close(pstmt);
			pstmt = conn.prepareStatement("UPDATE qna_board SET qna_re_ref = LAST_INSERT_ID() WHERE qna_num = LAST_INSERT_ID()");
			result = pstmt.executeUpdate();
			if(result > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		}finally {
			DBCPUtil.close(pstmt, conn);
		}
		return false;
	}

	@Override
	public BoardVO getBoardVO(int board_num) {
		BoardVO board = null;
		Connection conn = DBCPUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement("SELECT * FROM qna_board WHERE qna_num=?");
			pstmt.setInt(1, board_num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				board = new BoardVO(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getInt(5),
						rs.getInt(6),
						rs.getInt(7),
						rs.getInt(8),
						rs.getInt(9),
						rs.getString(10),
						rs.getDate(11)
						);
			}
			return board;
		} catch (SQLException e) {
			e.printStackTrace();			
		}finally {
			DBCPUtil.close(rs, pstmt, conn);
		}
		return board;
	}

	@Override
	public void updateReadCount(int board_num) {
		Connection conn = DBCPUtil.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement("UPDATE qna_board SET qna_readcount=qna_readcount+1 WHERE qna_num=?");
			pstmt.setInt(1, board_num);
			
			pstmt.executeUpdate();			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBCPUtil.close(pstmt, conn);
		}
	}

	@Override
	public void boardReplySubmit(BoardVO board) {

	}

	@Override
	public void boardUpdate(BoardVO board) {

	}

	@Override
	public boolean boardDelete(int board_num, int qna_writer_num) {
		return false;
	}

}
