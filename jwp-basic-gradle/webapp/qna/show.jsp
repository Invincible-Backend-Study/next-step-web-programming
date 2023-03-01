<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
    <div class="col-md-12 col-sm-12 col-lg-12">
        <div class="panel panel-default">
            <header class="qna-header">
                <h2 class="qna-title">${question.title}</h2>
            </header>
            <div class="content-main">
                <article class="article">
                    <div class="article-header">
                        <div class="article-header-thumb">
                            <img src="https://graph.facebook.com/v2.3/100000059371774/picture"
                                 class="article-author-thumb" alt="">
                        </div>
                        <div class="article-header-text">
                            <a href="/users/92/kimmunsu" class="article-author-name">${question.writer}</a>
                            <a href="/questions/413" class="article-header-time" title="퍼머링크">
                                ${question.createdDate}
                                <i class="icon-link"></i>
                            </a>
                        </div>
                    </div>
                    <div class="article-doc">
                        ${question.contents}
                    </div>
                    <div class="article-util">
                        <ul class="article-util-list">
                            <li>
                                <a class="link-modify-article" href="/question/form?questionId=${question.questionId}">수정</a>
                            </li>
                            <li>
                                <button class="link-delete-article" type="button"
                                        onclick="deleteQuestion('${question.questionId}')">삭제
                                </button>
                            </li>
                            <li>
                                <a class="link-modify-article" href="/question/list">목록</a>
                            </li>
                        </ul>
                    </div>
                </article>


                <div class="qna-comment">
                    <div class="qna-comment-slipp">
                        <p class="qna-comment-count"><strong>${question.countOfAnswer}</strong>개의 의견</p>
                        <div class="qna-comment-slipp-articles">
                            <c:forEach items="${answers}" var="answer" varStatus="status">
                                <article class="article" id="answer-1405">
                                    <div class="article-header">
                                        <div class="article-header-thumb">
                                            <img src="https://graph.facebook.com/v2.3/1324855987/picture"
                                                 class="article-author-thumb" alt="">
                                        </div>
                                        <div class="article-header-text">
                                            <a href="/users/1/자바지기" class="article-author-name">${answer.writer}</a>
                                            <a href="#answer-1434" class="article-header-time" title="퍼머링크">
                                                    ${answer.createdDate}
                                            </a>
                                        </div>
                                    </div>
                                    <div class="article-doc comment-doc">
                                        <p>${answer.contents}</p>
                                    </div>



<%--                                    <div class="article-util">--%>
<%--                                        <ul class="article-util-list">--%>
<%--                                            <li>--%>
<%--                                                <a class="link-modify-article" href="">수정</a>--%>
<%--                                            </li>--%>
<%--                                            <li>--%>
<%--                                                <form name="form-delete" class="form-delete" method="POST">--%>
<%--                                                    <input type="hidden" name="_method" value="DELETE">--%>
<%--                                                    <button type="submit" class="link-delete-article" onclick="deleteAnswer('${answer.answerId}')">삭제</button>--%>
<%--                                                </form>--%>
<%--                                            </li>--%>
<%--                                        </ul>--%>
<%--                                    </div>--%>


                                    <div class="article-util">
                                        <ul class="article-util-list">
                                            <li>
                                                <a class="link-modify-article" href="">수정</a>
                                            </li>
                                            <li>
                                                <button type="submit" class="link-delete-article" onclick="deleteAnswer(this, '${answer.answerId}')">삭제</button>
                                            </li>
                                        </ul>
                                    </div>
                                </article>
                            </c:forEach>

                            <div class="answerWrite">
                                <form name="answer" class="submit-write">
                                    <input type="hidden" name="questionId" value="${question.questionId}">
                                    <div class="form-group" style="padding:14px;">
                                        <textarea class="form-control" name="contents"
                                                  placeholder="Update your status"></textarea>
                                    </div>
                                    <input class="btn btn-success pull-right" type="submit">Post</input>
                                    <div class="clearfix"/>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<script type="text/template" id="answerTemplate">
    <article class="article">
        <div class="article-header">
            <div class="article-header-thumb">
                <img src="https://graph.facebook.com/v2.3/1324855987/picture" class="article-author-thumb" alt="">
            </div>
            <div class="article-header-text">
                {0}
                <div class="article-header-time">{1}</div>
            </div>
        </div>
        <div class="article-doc comment-doc">
            {2}
        </div>
        <div class="article-util">
            <ul class="article-util-list">
                <li>
                    <a class="link-modify-article" href="/api/qna/updateAnswer/{3}">수정</a>
                </li>
                <li>
                    <button type="submit" class="link-delete-article" onclick="deleteAnswer(this, '{3}')">삭제</button>
                </li>
            </ul>
        </div>
    </article>
</script>
<!-- script references -->
<jsp:include page="../template/footer.jsp"></jsp:include>
</body>
</html>