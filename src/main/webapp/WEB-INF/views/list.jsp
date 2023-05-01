<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>
	<my:navBar current="list" />

	<c:if test="${success eq 'removeSucess'}">
		<my:alert />
	</c:if>

	<div class="container-lg">
		<div>
			<h1>게시물 목록</h1>
		</div>
		<!-- table.table>thead>tr>th*4^^tbody -->
		<table class="table">
			<thead>
				<tr>
					<th>글번호</th>
					<th>제목</th>
					<th>작성자</th>
					<th>작성일시</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${boardList}" var="board">
					<tr>
						<td>${board.id}</td>
						<td><a href="/detail/${board.id}">${board.title}</a></td>
						<td>${board.writer}</td>
						<td><fmt:parseDate value="${board.inserted}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" /> <fmt:formatDate value="${parsedDateTime}" pattern="yyyy/MM/dd" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<div class="container-lg">
		<div class="row">
			<nav aria-label="Page navigation example">
				<ul class="pagination justify-content-center">

					<!-- 첫페이지 -->
					<c:if test="${pageInfo.currentPageNumber gt 1}">
						<li class="page-item">
							<c:url value="/list" var="pageLink">
								<c:param name="page" value="1"/>
								<c:if test="${not empty param.search }">
									<c:param name="search" value="${param.search}"/>
								</c:if>
								<c:if test="${not empty param.type }">
									<c:param name="type" value="${param.type}"/>
								</c:if>
							</c:url> 
							<a class="page-link" href="${pageLink}">
								<i class="fa-solid fa-angles-left"></i>
							</a>
						</li>
					</c:if>
					
					<!-- 이전버튼 -->
					<c:if test="${pageInfo.currentPageNumber gt 1}">
						<li class="page-item">
							<c:url value="/list" var="pageLink">
								<c:param name="page" value="${pageInfo.prevPageNumber}"/>
								<c:if test="${not empty param.search }">
									<c:param name="search" value="${param.search}"/>
								</c:if>
								<c:if test="${not empty param.type }">
									<c:param name="type" value="${param.type}"/>
								</c:if>
							</c:url> 
							<a class="page-link" href="${pageLink}">
								<i class="fa-solid fa-angle-left"></i>
							</a>
						</li>
					</c:if>
					
					<!-- 페이지네이션 -->
					<c:forEach begin="${pageInfo.leftPageNumber}" end="${pageInfo.rightPageNumber}" var="pageNum">
						<c:url value="/list" var="pageLink">
							<c:param name="page" value="${pageNum}" />
							<c:if test="${not empty param.search }">
								<c:param name="search" value="${param.search}"/>
							</c:if>
							<c:if test="${not empty param.type }">
								<c:param name="type" value="${param.type}"/>
							</c:if>
						</c:url>
						<li class="page-item"><a class="page-link ${pageNum eq pageInfo.currentPageNumber ? 'active' : '' }" href="${pageLink}">${pageNum}</a></li>
					</c:forEach>
					
					<!-- 다음버튼 -->
					<c:if test="${pageInfo.currentPageNumber lt pageInfo.lastPageNumber}">
						<li class="page-item">
							<c:url value="/list" var="pageLink">
								<c:param name="page" value="${pageInfo.nextPageNumber}"/>
								<c:if test="${not empty param.search }">
									<c:param name="search" value="${param.search}"/>
								</c:if>
								<c:if test="${not empty param.type }">
									<c:param name="type" value="${param.type}"/>
								</c:if>
							</c:url> 
							<a class="page-link" href="${pageLink}">
								<i class="fa-solid fa-angle-right"></i>
							</a>
						</li>
					</c:if>
					
					<!-- 마지막페이지 -->
					<c:if test="${pageInfo.currentPageNumber lt pageInfo.lastPageNumber}">
						<li class="page-item">
							<c:url value="/list" var="pageLink">
								<c:param name="page" value="${pageInfo.lastPageNumber}"/>
								<c:if test="${not empty param.search }">
									<c:param name="search" value="${param.search}"/>
								</c:if>
								<c:if test="${not empty param.type }">
									<c:param name="type" value="${param.type}"/>
								</c:if>
							</c:url> 
							<a class="page-link" href="${pageLink}">
								<i class="fa-solid fa-angles-right"></i>
							</a>
						</li>
					</c:if>
				</ul>
			</nav>
		</div>
	</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

</body>
</html>