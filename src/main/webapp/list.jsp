<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/reset.css" />
<link rel="stylesheet" href="css/style.css" />
<style>
	<%@ include file="css/reset.css" %>
	<%@ include file="css/style.css" %>
</style>
</head>
<body>
	<h1 class="text-center title">목록 보기</h1>
	<p class="text-center titlep"><a href="logout">관리자 모드</a></p>
	<div class="container">
		<ul class="row">
		<!-- 루프 -->
		<c:forEach var = "list" items="${list }" varStatus="status">
			<li class="col-6">
				<a href="view?num=${list.num }" class="box">
					<img src="http://localhost:3000/data/img/${list.fileName}" alt="${list.fileName }" />
					<article class="content">
						<h3>${fn: substring(list.title, 0, 20) }</h3>
						<span>${list.categorya }</span><span>${list.categoryb }</span>
						<p>${fn: substring(list.content, 0, 70) }</p>
						<c:set var="wdate" value="${list.wdate }"/>
						<div class="wdate"><span>${fn:substring(wdate, 0, 10) }</span><span>${fn:substring(wdate, 10, 16) }</span></div>
					</article>
				</a>
			</li>
		</c:forEach>
		<!-- /루프 -->
		</ul>
	</div>
	<div class="container text-center">
		<ul class="pagination">
			<c:if test="${pagination.prev }">
				<li>
					<a href="?page=${pagination.startPage - 1 }" class="prne">이전</a>
				</li>
			</c:if>
			
			<c:forEach begin="${pagination.startPage }" end="${pagination.endPage }" var="idx">
				<li>
					
						<a href="?page=${idx }"  <c:if test="${pagination.page == idx }"> class="active" </c:if>>${idx }</a>
					
					
				</li>
			</c:forEach>
			
			<c:if test="${pagination.next && pagination.endPage > 0 }">
				<li>
					<a href="?page=${pagination.endPage + 1 }" class="prne">다음</a>
				</li>
			</c:if>
		</ul>
	
		<ul>
			<li>현재 페이지 : ${pagination.page }</li>
			<li>전체 글 수 : ${pagination.totalCnt }</li>
			<li>시작 페이지 번호 : ${pagination.startPage }</li>
			<li>마지막 페이지 번호 : ${pagination.endPage }</li>
			<li>목록 크기 : ${pagination.listSize }</li>
		</ul>
	</div>
	<div class="container text-right">
		<a href="insert">글쓰기</a>
	</div>
</body>
</html>