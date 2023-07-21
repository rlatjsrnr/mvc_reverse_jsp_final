package repositories;

import java.sql.*;
import java.util.*;

import beans.MemberVO;
import util.*;

public class ManagementDAOImpl implements ManagementDAO {

	@Override
	public ArrayList<MemberVO> getMemberList(Criteria cri) {
		ArrayList<MemberVO> memberList = new ArrayList<>();
		Connection conn = DBCPUtil.getConnection();
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT * FROM mvc_member ORDER BY num DESC limit ?, ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cri.getStartRow());
			pstmt.setInt(2, cri.getPerPageNum());
			rs = pstmt.executeQuery();
			MemberVO member = null;
			while(rs.next()) {
				member = new MemberVO(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getInt(5),
						rs.getString(6),
						rs.getString(7),
						rs.getDate(8),
						rs.getDate(9)
				);
				memberList.add(member);
			}
		} catch (SQLException e) {
			memberList = null;
			e.printStackTrace();
		}finally {
			DBCPUtil.close(rs, pstmt, conn);
		}
		
		return memberList;
	}

	@Override
	public int getMemberTotalCount() {
		Connection conn = DBCPUtil.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT count(*) FROM mvc_member");
			if(rs.next()) {
				return rs.getInt(1);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}finally {
			DBCPUtil.close(rs, stmt, conn);
		}
		return 0;
	}

	@Override
	public int updateMember(MemberVO vo) {
		
		return 0;
	}

	@Override
	public int deleteMember(int num) {
		
		return 0;
	}

}
