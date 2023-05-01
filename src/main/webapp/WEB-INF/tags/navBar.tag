<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="current" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<nav class="navbar navbar-expand-lg bg-body-tertiary mb-5">
  <div class="container-lg">
    <a class="navbar-brand" href="/list">
    	<img alt="projcet-1" src="/img/spring-logo.png" height="25" style="margin-bottom: 5px">
    </a> <!-- 프로젝트 브랜드 -->
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link ${current eq 'list' ? 'active' : '' }" href="/list">목록</a>
        </li>
        <li class="nav-item">
          <a class="nav-link ${current eq 'add' ? 'active' : ''}" href="/add">글쓰기</a>
        </li>
      </ul>
      <form action="/list" class="d-flex" role="search">
      	<select name="type">
      		<option value="all">전체</option>
      		<option ${(param.type == "title")? "selected" : ""} value="title">제목</option>
      		<option ${(param.type == "body")? "selected" : ""} value="body">본문</option>
      		<option ${(param.type == "writer")? "selected" : ""} value="writer">작성자</option>
      	</select>
        <input value="${param.search}" name="search" class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
        <button class="btn btn-outline-success" type="submit">
        	<i class="fa-solid fa-magnifying-glass"></i>
        </button>
      </form>
    </div>
  </div>
</nav>
