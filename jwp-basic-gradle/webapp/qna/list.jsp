<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="kr">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <title>SLiPP Java Web Programming</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link href="../css/bootstrap.min.css" rel="stylesheet">
    <!--[if lt IE 9]>
        <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <link href="../css/styles.css" rel="stylesheet">
</head>
<body>
<jsp:include page="../template/header.jsp"></jsp:include>

<div class="container" id="main">
   <div class="col-md-12 col-sm-12 col-lg-10 col-lg-offset-1">
      <div class="panel panel-default qna-list">
          <ul class="list">
              <c:forEach items="${questions}" var="question" varStatus="status">
                  <li>
                      <div class="wrap">
                          <div class="main">
                              <strong class="subject">
                                  <a href="/questions?questionId=${question.questionId}">${question.title}</a>
                              </strong>
                              <div class="auth-info">
                                  <i class="icon-add-comment"></i>
                                  <span class="time">${question.createdDate}</span>
                                  <a href="../user/profile.jsp" class="author">${question.writer}</a>
                              </div>
                              <div class="reply" title="댓글">
                                  <i class="icon-reply"></i>
                                  <span class="point">${question.countOfAnswer}</span>
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
                  <a href="/qna/form.jsp" class="btn btn-primary pull-right" role="button">질문하기</a>
              </div>
          </div>
        </div>
    </div>
</div>

<!-- script references -->
<jsp:include page="../template/footer.jsp"></jsp:include>
	</body>
</html>