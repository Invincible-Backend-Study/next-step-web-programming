<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/include/head.jspf" %>
<%@ include file="/include/nav.jspf" %>

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
                          <img src="https://graph.facebook.com/v2.3/100000059371774/picture" class="article-author-thumb" alt="">
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
                              <a class="link-modify-article" href="/questions/423/form">수정</a>
                          </li>
                          <li>
                              <form class="form-delete" action="/qna/deleteQuestion" method="POST">
                                  <input type="hidden" name="_method" value="DELETE">
                                  <button class="link-delete-article" type="submit">삭제</button>
                              </form>
                          </li>
                          <li>
                              <a class="link-modify-article" href="/index.html">목록</a>
                          </li>
                      </ul>
                  </div>
              </article>

              <div class="qna-comment">
                  <div class="qna-comment-slipp">
                      <p class="qna-comment-count"><strong>${question.countOfAnswer}</strong>개의 의견</p>
                      <div class="qna-comment-slipp-articles">
                          <c:forEach items="${answers}" var="as" varStatus="status">
                              <article class="article" id="answer-1406">
                                                            <div class="article-header">
                                                                <div class="article-header-thumb">
                                                                    <img src="https://graph.facebook.com/v2.3/1324855987/picture" class="article-author-thumb" alt="">
                                                                </div>
                                                                <div class="article-header-text">
                                                                    <a href="/users/1/${as.writer}" class="article-author-name">${as.writer}</a>
                                                                    <a href="#answer-1434" class="article-header-time" title="퍼머링크">
                                                                        ${as.createdDate}
                                                                    </a>
                                                                </div>
                                                            </div>
                                                            <div class="article-doc comment-doc">
                                                                <p>${as.contents}</p>
                                                            </div>
                                                            <div class="article-util">
                                                                <ul class="article-util-list">
                                                                    <li>
                                                                        <a class="link-modify-article" href="/questions/413/answers/1405/form">수정</a>
                                                                    </li>
                                                                    <li>
                                                                        <form class="form-delete" action="/qna/deleteAnswer" method="POST">
                                                                            <input type="hidden" name="answerId" value="${as.answerId}">
                                                                            <button type="submit" class="link-delete-article">삭제</button>
                                                                        </form>
                                                                    </li>
                                                                </ul>
                                                            </div>
                                                        </article>
                          </c:forEach>
                      </div>
                      <form class="submit-write">
                        <div class="form-group" style="padding:14px;">
                            <textarea id="contents" name="contents" class="form-control" placeholder="Update your status"></textarea>
                        </div>
                        <button class="btn btn-success pull-right" type="submit">Post</button>
                        <div class="clearfix" />
                    </form>
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
                            <form class="form-delete" action="/qna/deleteAnswer" method="POST">
                                <input type="hidden" name="answerId" value="{4}" />
                                <button type="submit" class="link-delete-article">삭제</button>
                            </form>
                        </li>
                    </ul>
                    </div>
                </article>
            </script>
          </div>
        </div>
    </div>
</div>

<!-- script references -->
<script src="../js/jquery-2.2.0.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/scripts.js"></script>
	</body>
</html>