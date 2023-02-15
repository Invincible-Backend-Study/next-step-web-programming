<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="kr">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <jsp:include page="../head.jsp"/>
    <title>SLiPP Java Web Programming</title>
</head>
<body>

<jsp:include page="../navigation.jsp"/>

<div class="container" id="main">
    <div class="col-md-10 col-md-offset-1">
        <div class="panel panel-default">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>#</th>
                    <th>사용자 아이디</th>
                    <th>이름</th>
                    <th>이메일</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="users" items="${users}" varStatus="status">
                    <tr>
                        <th scope="row">${status.count}</th>
                        <td>${users.userId}</td>
                        <td>${users.name}</td>
                        <td>${users.email}</td>
                        <td><a href="/users/updateForm?userId=${users.userId}" class="btn btn-success" role="button">수정</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- script references -->
<jsp:include page="../script.jsp"/>
</body>
</html>
