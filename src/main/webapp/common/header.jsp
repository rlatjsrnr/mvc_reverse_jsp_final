<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Test Member</title>
<c:url var="context" value="/"/>
<link href="${context}/css/header.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/footer.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/common.css" rel="stylesheet" type="text/css" />
</head>
<c:if test="${!empty requestScope.message}">
	<script>
		alert('${message}');
	</script>
</c:if>
<body>
	<header>
		<div>
			<ul>
				<li>
					<a href="<c:url value='/index.jsp'/>">home</a>
				</li>
				<c:choose>
					<c:when test="${!empty sessionScope.member}">
						<li><a href="${pageContext.request.contextPath}/member/info.jsp"> ${member.id}</a>님 방가</li>
						<c:if test="${member.id eq 'admin'}">
							<li><a href="${pageContext.request.contextPath}/managementPage.mgc">회원관리</a></li>
						</c:if>
						<li><a href="logOut.mc">로그아웃</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="${pageContext.request.contextPath}/member/login.jsp">로그인</a></li>
						<li><a href="${pageContext.request.contextPath}/member/join.jsp">회원가입</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
		<div>
			<ul>
				<li><a href="${pageContext.request.contextPath}/notice.do">공지사항</a></li>
				<li><a href="${pageContext.request.contextPath}/board/qna/qna_list.jsp">질문과답변</a></li>
			</ul>
		</div>
	</header>
	
	
	
	
	
	
	
	
	
	
	
	
	
	