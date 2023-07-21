<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="s" %>
<jsp:include page="/common/header.jsp"/>
<section class="wrap">

<table border=1>
	<tr>
		<th colspan="4">회원정보</th>
	</tr>
	<tr>
		<th>회원번호</th>
		<th>이름</th>
		<th>회원등록일</th>
		<th>기타</th>
	</tr>
	<!-- 등록된 회원 정보가 있을 시 출력 -->
	<c:choose>
	<c:when test="${!empty requestScope.memberList}">
		<c:forEach var="m" items="${memberList}">
			<tr>
				<td>
					<!-- 회원번호 -->
					${m.num}
				</td>
				<td>
					<!-- 이름 -->
					${m.name}
				</td>
				<td>
					<!-- 회원등록일 -->
					${m.regdate}
				</td>
				<td>
					<!-- 기타 -->
					<a href="managementUpdate.mgc?num=${m.num}">수정</a> | 
					<a href="managementDelete.mgc?num=${m.num}">삭제</a>
				</td>
			</tr>
		</c:forEach>		
	
	<!-- 등록된 회원 목록정보가 존재할 시 페이징 처리 블럭 작성 -->
	<c:if test="${!empty pm}">
		<tr>
			<th colspan="4">${pm}</th>
		</tr>	
		<tr>
		<th colspan="4">
			<c:if test="${pm.first}">
							<a href="managementPage.mgc${pm.makeQuery(1)}">[&lt;&lt;]</a>
						</c:if>
						<c:if test="${pm.prev}">
							<a href="managementPage.mgc${pm.makeQuery(pm.startPage-1)}">[&lt;]</a>
						</c:if>
						<c:forEach var="i" begin="${pm.startPage}" end="${pm.endPage}">
							<c:choose>
								<c:when test="${pm.cri.page eq i}">
									<span style='color:red;'>[${i}]</span>
								</c:when>
								<c:otherwise>
									<a href="managementPage.mgc${pm.makeQuery(i)}">[${i}]</a>
								</c:otherwise>
							</c:choose>
						</c:forEach>	
						<c:if test="${pm.next}">
							<a href="managementPage.mgc${pm.makeQuery(pm.endPage+1)}">[&gt;]</a>							
						</c:if>	
						<c:if test="${pm.last}">
							<a href="managementPage.mgc${pm.makeQuery(pm.maxPage)}">[&gt;&gt;]</a>
						</c:if>
		</th>
	</tr>
	</c:if>	
	</c:when>
	<c:otherwise>
		<!-- 등록된 회원 정보가 없을 시 출력 -->
		<tr>
			<th>등록된 회원 정보가 없습니다.</th>
		</tr>
	</c:otherwise>
</c:choose>	
</table>
</section>
<jsp:include page="/common/footer.jsp" />










