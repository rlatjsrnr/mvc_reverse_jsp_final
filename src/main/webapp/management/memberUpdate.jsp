<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="s" %>
<jsp:include page="/common/header.jsp" />
<section class="wrap">
<form action="<c:url value='/management/memberSubmit.jsp'/>" method="POST">
	<table>
		<tr>
			<td colspan=2><h3>회원정보 수정</h3></td>
		</tr>
		<tr>
			<td>회원번호</td>
			<td>
				<input type="text" name="num" value="${updateMember.num}" readonly />
			</td>
		</tr>
		<tr>
			<td>아이디</td>
			<td>
				<input type="text" name="id" value="" readonly />
			</td>
		</tr>
		<tr>
			<td>비밀번호</td>
			<td>
				<input type="password" name="pass" value="" required />
				<button type="button" onclick="changePass(this);">비밀번호 보기</button>
			</td>
		</tr>
		<tr>
			<td>나이</td>
			<td>
				<input type="number" name="age" value="${updateMember.age}" required />
			</td>
		</tr>
		<tr>
			<td>성별</td>
			<td>
				<label>
					<input <c:out value="${updateMember.gender eq 'male' ? 'checked' : ''}" />
					type="radio" name="gender" value="male" /> 남성
				</label>
					<input <c:out value="${updateMember.gender eq 'female' ? 'checked' : ''}" />
					type="radio" id="female" name="gender" value="female"/>
				<label for="female"> 여성 </label>
			</td>
		</tr>
		<tr>
			<td colspan=2>
				<input type="submit" value="수정완료"/>
			</td>
		</tr>
	</table>
</form>
<script>
	function changePass(btn){
		let el = btn.parentNode.firstElementChild;
		console.log(el);
		let attr = el.getAttribute("type");
		if(attr == 'password'){
			el.setAttribute('type','text');
			btn.innerText = '비밀번호 감추기';
		}else{
			el.setAttribute('type','password');
			btn.innerHTML = '비밀번호 보기';
		}
	}
</script>
</section>
<jsp:include page="/common/footer.jsp" />























