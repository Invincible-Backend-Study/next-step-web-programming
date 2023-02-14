<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../layout/header.jsp"%>
<%@ include file="../layout/nav.jsp"%>


<div class="container" id="main">
    <div class="col-md-6 col-md-offset-3">
        <div class="panel panel-default content-main">
            <form name="question" method="post" action="${pageContext.request.contextPath}user/update">
                <div class="form-group">
                    <label for="userId">사용자 아이디</label>
                    <input class="form-control" type="hidden" id="userId" name="userId" value="${user.userId}" placeholder="User ID">
                    <input class="form-control" id="viewUserId" name="viewUserId" value="${user.userId}" disabled placeholder="User Id">
                </div>
                <div class="form-group">
                    <label for="password">비밀번호</label>
                    <input type="password" class="form-control" id="password" name="password" value="${user.password}" placeholder="Password">
                </div>
                <div class="form-group">
                    <label for="name">이름</label>
                    <input class="form-control" id="name" name="name" placeholder="Name" value="${user.name}">
                </div>
                <div class="form-group">
                    <label for="email">이메일</label>
                    <input type="email" class="form-control" id="email" name="email" value="${user.email}"placeholder="Email">
                </div>
                <button type="submit" class="btn btn-success clearfix pull-right">회원가입</button>
                <div class="clearfix"></div>
            </form>
        </div>
    </div>
</div>

<%@include file="../layout/footer.jsp"%>
