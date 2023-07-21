package repositories;

import beans.MemberVO;

// 회원 관련 Database 요청 처리
// database access object
public interface MemberDAO {

	// 회원가입
	public boolean memberJoin(MemberVO member);

	// 로그인 처리
	public MemberVO memberLogin(String id, String pass);

	// 회원정보 수정
	public boolean memberUpdate(MemberVO member);

	// id 값으로 사용자 정보 확인 - cookie
	public MemberVO getMemberById(String id);

	// 회원 탈퇴 처리 - joinYN
	public void withDrawMember(String id);

}
