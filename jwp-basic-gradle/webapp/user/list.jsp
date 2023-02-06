<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/include/head.jspf" %>
<%@ include file="/include/nav.jspf" %>

<div class="container" id="main">
    <div class="col-md-10 col-md-offset-1">
        <div class="panel panel-default">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>#</th> <th>사용자 아이디</th> <th>이름</th> <th>이메일</th><th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${users}" var="user" varStatus="status">
                    <tr>
                        <th scope="row">${status.index}</th>
                        <td>${user.userId}</td>
                        <td>${user.name}</td>
                        <td>${user.email}</td>
                        <td><a href="/user/updateForm?userId=${user.userId}" class="btn btn-success" role="button">수정</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<%@ include file="/include/footer.jspf" %>
