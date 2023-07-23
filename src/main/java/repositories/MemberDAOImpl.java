package repositories;

import java.sql.*;

import beans.MemberVO;
import util.DBCPUtil;

public class MemberDAOImpl implements MemberDAO {

	@Override
	public boolean memberJoin(MemberVO member) {
		Connection conn = DBCPUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement("SELECT * FROM mvc_member WHERE id=?");
			pstmt.setString(1, member.getId());
			rs = pstmt.executeQuery();
			
			if(!rs.next()) {
				DBCPUtil.close(rs,pstmt);
				pstmt = conn.prepareStatement("INSERT INTO mvc_member(id, pass, name, age, gender) VALUES(?,?,?,?,?)");
				pstmt.setString(1, member.getId());
				pstmt.setString(2, member.getPass());
				pstmt.setString(3, member.getName());
				pstmt.setInt(4, member.getAge());
				pstmt.setString(5, member.getGender());
				int result = pstmt.executeUpdate();
				
				if(result > 0) {
					return true;
				}				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally {
			DBCPUtil.close(rs, pstmt, conn);
		}
		return false;
	}

	@Override
	public MemberVO memberLogin(String id, String pass) {
		MemberVO member = null;
		Connection conn = DBCPUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement("SELECT * FROM mvc_member WHERE id=? AND pass=? AND joinYN='Y'");
			pstmt.setString(1, id);
			pstmt.setString(2, pass);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
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
				return member;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}finally {
			DBCPUtil.close(rs, pstmt, conn);
		}		
		return null;
	}

	@Override
	public boolean memberUpdate(MemberVO member) {
		return false;
	}

	@Override
	public MemberVO getMemberById(String id) {
		MemberVO member = null;
		Connection conn = DBCPUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement("SELECT * FROM mvc_member WHERE id=?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
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
				return member;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBCPUtil.close(rs, pstmt, conn);
		}
		return null;
	}

	@Override
	public boolean withDrawMember(String id) {
		Connection conn = DBCPUtil.getConnection();
		PreparedStatement pstmt = null;		
		try {
			pstmt = conn.prepareStatement("UPDATE mvc_member SET joinYN='N' WHERE id=?");
			pstmt.setString(1, id);
			int result = pstmt.executeUpdate();
			if(result > 0) {				
				return true;
			}else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally {
			DBCPUtil.close(pstmt, conn);
		}		
	}
}
