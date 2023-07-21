<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<jsp:include page="../../common/header.jsp" />
<section  class="wrap">
	<table class="list">
		<tr>
			<th colspan="4">
				<h1>공지사항</h1>
			</th>
		</tr>
		<tr>
			<th colspan="4">${pm}</th>
		</tr>
		<c:if test="${!empty member and member.u_id eq 'admin'}">
		<tr>
			<td colspan="4">
				<a href="#">공지글 작성</a>
			</td>
		</tr>
		</c:if>
		<!-- 공지 검색 및 한번에 보여줄 페이지 개수 선택 창 -->
		<tr>
			<td colspan="4">
				<form name="searchName" action="#" method="GET">
					<select name="searchType">
						<option value="title" ${param.searchType eq 'title' ? 'selected' : ''}>제목</option>
						<option value="content" ${param.searchType eq 'content' ? 'selected' : ''}>내용</option>
					</select>
					<input type="text" autofocus name="searchValue" value="${param.searchValue}"/>
					
					<input type="submit" value="검색"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					
					<select name="perPageNum" onchange="searchName.submit();">
						<c:forEach var="i" begin="10" end="25" step="5">
							<option value="${i}" ${param.perPageNum eq i ? 'selected' : ''}>${i}개씩 보기</option>
						</c:forEach>
					</select>
				</form>
			</td>
		</tr>
		<tr>
			<th>글번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>작성일</th>
		</tr>
		<c:choose>
			<c:when test="${!empty noticeList}">
				<!-- 게시글 목록 존재 시 -->
				<c:forEach var="n" items="${noticeList}">
					<tr>
						<td>${n.notice_num}</td>
						<td>
							<a href="<c:url value='/board/notice/notice_detail.jsp'/>?notice_num=${n.notice_num}">
								[${n.notice_category}] ${n.notice_title}
							</a>
						</td>
						<td>${n.notice_author}</td>
						<td>${n.notice_date}</td>
					</tr>
				</c:forEach>
				<!-- 페이징 블럭 출력 -->
				<tr>
					<th colspan="4">
						<c:if test="${pm.first}">
							<a href="notice_list.jsp${pm.makeQuery(1)}">[처음]</a>
						</c:if>
						<c:if test="${pm.prev}">
							<a href="notice_list.jsp${pm.makeQuery(pm.startPage-1)}">[이전]</a>
						</c:if>
						<c:forEach var="i" begin="${pm.startPage}" end="${pm.endPage}" step="1">
							<a href="notice_list.jsp${pm.makeQuery(i)}">[${i}]</a>						
						</c:forEach>
						<c:if test="${pm.next}">
							<a href="notice_list.jsp${pm.makeQuery(pm.endPage+1)}">[다음]</a>
						</c:if>
						<c:if test="${pm.last}">
							<a href="notice_list.jsp${pm.makeQuery(pm.maxPage)}">[마지막]</a>
						</c:if>
					</th>
				</tr>
			</c:when>
			<c:otherwise>
				<!-- 게시글 목록 미 존재 시 -->
				<tr>
					<td colspan="4">등록된 게시물이 없습니다.</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</table>
</section>
<jsp:include page="../../common/footer.jsp" />






