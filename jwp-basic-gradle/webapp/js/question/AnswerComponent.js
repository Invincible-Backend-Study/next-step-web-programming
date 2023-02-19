
export default class AnswerComponent{
    render(){
        const  each = {
            writer:"1234",
            contents:"1234",
            answerId:"1234",
        }
        const question = {
            questionId:"1234"
        }
        return `
        <div class="qna-comment">
                    <div class="qna-comment-slipp">
                        <p class="qna-comment-count"><strong id="questionCount">${question.countOfComment}</strong>개의 의견</p>
                        <div class="qna-comment-slipp-articles">
                                <article class="article">
                                    <div class="article-header">
                                        <div class="article-header-thumb">
                                            <img src="https://graph.facebook.com/v2.3/1324855987/picture" class="article-author-thumb" alt="">
                                        </div>
                                        <div class="article-header-text">
                                                ${each.writer}
                                            <div class="article-header-time"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${each.createdDate}" /></div>
                                        </div>
                                    </div>
                                    <div class="article-doc comment-doc">
                                        <p>${each.contents}</p>
                                    </div>
                                    <div class="article-util">
                                        <ul class="article-util-list">
                                            <li>
                                                <a class="link-modify-article" href="/api/qna/updateAnswer?answerId=${each.answerId}">수정</a>
                                            </li>
                                            <li>
                                                <form name = "answer-delete" class="form-delete" method="POST">
                                                    <input type="hidden" name="answerId" value="${each.answerId}" />
                                                    <button type="submit" class="link-delete-article">삭제</button>
                                                </form>
                                            </li>
                                        </ul>
                                    </div>
                                </article>
                            <div class="answerWrite">
                                <form name="answer" method="post">
                                    <input type="hidden" name="questionId" value="${question.questionId}">
                                    <div class="form-group col-lg-4" style="padding-top:10px;">
                                        <input class="form-control" id="writer" name="writer" placeholder="이름">
                                    </div>
                                    <div class="form-group col-lg-12">
                                        <textarea name="contents" id="contents" class="form-control" placeholder=""></textarea>
                                    </div>
                                    <input class="btn btn-success pull-right" type="submit" value="답변하기" />
                                    <div class="clearfix" />
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
        
        `
    }

}