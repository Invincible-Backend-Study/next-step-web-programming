<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="layout/header.jsp" %>
<%@include file="layout/nav.jsp" %>

<div class="container" id="main">
    <div class="alert alert-danger" role="alert">${errorMessage}</div>
    <div class="col-md-12 col-sm-12 col-lg-10 col-lg-offset-1">
        <div class="panel panel-default qna-list">
            <ul class="list">
                <jsp:useBean id="questions" scope="request" type="java.util.List"/>
                <c:forEach items="${questions}" var="each">
                    <li>
                        <div class="wrap">
                            <div class="main">
                                <strong class="subject">
                                    <a href="/qna/show?questionId=${each.questionId}">${each.title}</a>
                                </strong>
                                <div class="auth-info">
                                    <i class="icon-add-comment"></i>
                                    <span class="time"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${each.createdDate}"/></span>
                                    <a href="#" class="author">${each.writer}</a>
                                </div>
                                <div class="reply" title="댓글">
                                    <i class="icon-reply"></i>
                                    <span class="point">${each.countOfComment}</span>
                                </div>
                            </div>
                        </div>
                    </li>
                </c:forEach>
            </ul>
            <div class="row">
                <div class="col-md-3"></div>
                <div class="col-md-6 text-center">
                    <ul class="pagination center-block" style="display:inline-block;">
                        <li><a href="#">«</a></li>
                        <li><a href="#">1</a></li>
                        <li><a href="#">2</a></li>
                        <li><a href="#">3</a></li>
                        <li><a href="#">4</a></li>
                        <li><a href="#">5</a></li>
                        <li><a href="#">»</a></li>
                    </ul>
                </div>

                <div class="col-md-3 qna-write">
                    <c:if test="${isLoggedIn}">
                        <a href="/qna/form" class="btn btn-primary pull-right" role="button">질문하기</a>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="layout/footer.jsp" %>