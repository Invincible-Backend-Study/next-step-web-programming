<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
    <div class="col-md-12 col-sm-12 col-lg-10 col-lg-offset-1">
        <div class="panel panel-default">
            <header class="qna-header">
                <h2 class="qna-title">${questionWithAnswers.question.title}</h2>
            </header>
            <div class="content-main">
                <c:if test="${errorMessage != null}">
                    <div class="alert alert-danger" role="alert">${errorMessage}</div>
                </c:if>
                <article class="article">
                    <div class="article-header">
                        <div class="article-header-thumb">
                            <img src="https://graph.facebook.com/v2.3/100000059371774/picture"
                                 class="article-author-thumb" alt="">
                        </div>
                        <div class="article-header-text">
                            <a href="/users/92/kimmunsu"
                               class="article-author-name">${questionWithAnswers.question.writer}</a>
                            <a href="/questions/413" class="article-header-time" title="퍼머링크">
                                <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
                                                value="${questionWithAnswers.question.createdDate}"/>
                                <i class="icon-link"></i>
                            </a>
                        </div>
                    </div>
                    <div class="article-doc">
                        ${questionWithAnswers.question.contents}
                    </div>
                    <div class="article-util">
                        <ul class="article-util-list">
                            <li>
                                <a class="link-modify-article"
                                   href="/qna/updateQuestionForm?questionId=${questionWithAnswers.question.questionId}&writer=${questionWithAnswers.question.writer}">수정</a>
                            </li>
                            <li>
                                <form class="form-delete"
                                      action="/qna/deleteQuestion?questionId=${questionWithAnswers.question.questionId}"
                                      method="POST">
                                    <input type="hidden" name="_method" value="DELETE">
                                    <button class="link-delete-article-form" type="submit">삭제</button>
                                </form>
                            </li>
                            <li>
                                <a class="link-modify-article" href="/">목록</a>
                            </li>
                        </ul>
                    </div>
                </article>

                <div class="qna-comment">
                    <div class="qna-comment-slipp">
                        <p class="qna-comment-count"><strong>${questionWithAnswers.question.countOfAnswer}</strong>개의 의견
                        </p>
                        <div class="qna-comment-slipp-articles">

                            <c:forEach items="${questionWithAnswers.answers}" var="each">
                                <article class="article">
                                    <div class="article-header">
                                        <div class="article-header-thumb">
                                            <img src="https://graph.facebook.com/v2.3/1324855987/picture"
                                                 class="article-author-thumb" alt="">
                                        </div>
                                        <div class="article-header-text">
                                                ${each.writer}
                                            <div class="article-header-time"><fmt:formatDate
                                                    pattern="yyyy-MM-dd HH:mm:ss" value="${each.createdDate}"/></div>
                                        </div>
                                    </div>
                                    <div class="article-doc comment-doc">
                                        <p>${each.contents}</p>
                                    </div>
                                    <div class="article-util">
                                        <ul class="article-util-list">
                                            <li>
                                                <a class="link-modify-article"
                                                   href="/api/qna/updateAnswer?answerId=${each.answerId}">수정</a>
                                            </li>
                                            <li>
                                                <form class="form-delete" action="/api/qna/deleteAnswer" method="POST">
                                                    <input type="hidden" name="answerId" value="${each.answerId}"/>
                                                    <button type="submit" class="link-delete-article">삭제</button>
                                                </form>
                                            </li>
                                        </ul>
                                    </div>
                                </article>
                            </c:forEach>
                            <div class="answerWrite">
                                <form name="answer" method="post">
                                    <input type="hidden" name="questionId"
                                           value="${questionWithAnswers.question.questionId}">
                                    <div class="form-group col-lg-12">
                                        <textarea name="contents" id="contents" class="form-control"
                                                  placeholder=""></textarea>
                                    </div>
                                    <input class="btn btn-success pull-right" type="submit" value="답변하기"/>
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
                    <form class="form-delete" action="/api/qna/deleteAnswer" method="POST">
                        <input type="hidden" name="answerId" value="{4}"/>
                        <button type="submit" class="link-delete-article">삭제</button>
                    </form>
                </li>
            </ul>
        </div>
    </article>
</script>
<!-- script references -->
<jsp:include page="../script.jsp"/>
</body>
</html>