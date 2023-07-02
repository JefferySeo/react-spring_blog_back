<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/reset.css" />
<link rel="stylesheet" href="css/style.css" />
</head>
<body>
	<div class="container">
		<h1>${view.title }</h1>
		<div class="content">
			${view.content }
		</div>
		<div>
			<c:forEach var="frow" items="${flist }" >
				<span>${frow.oldname } || ${frow.filesize }</span>
			</c:forEach>
		</div>
	</div>
</body>
</html>