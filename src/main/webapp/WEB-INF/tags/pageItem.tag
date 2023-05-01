<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ attribute name ="pageNum" %>

<li class="page-item">
<c:url value="/list" var="pageLink">
	<c:param name="page" value="${pageNum}" />
	<c:if test="${not empty param.search }">
		<c:param name="search" value="${param.search}"/>
	</c:if>
	<c:if test="${not empty param.type }">
		<c:param name="type" value="${param.type}"/>
	</c:if>
</c:url>
	<a class="page-link ${pageNum eq pageInfo.currentPageNumber ? 'active' : '' }" href="${pageLink}">
		<jsp:doBody/>
	</a>
</li>