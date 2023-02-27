

export default class Answer{

    createdDate;
    writer;
    answerId;
    contents;

    #emits
    constructor({createdDate, writer, answerId, contents},emits ){
        this.createdDate = createdDate;
        this.writer =writer;
        this.answerId = answerId;
        this.contents =contents;
        this.#emits= emits;
    }

    render(){
        const submitButton = document.createElement("button");
        submitButton.classList.add("link-delete-article");
        submitButton.textContent = "삭제";
        submitButton.addEventListener('click', (e)=>{
            console.log(1);
            window.alert("1");
            e.preventDefault();
            this.#emits.deleteAnswer(this.answerId);
        });

        return `<article class="article">
            <div class="article-header">
                <div class="article-header-thumb">
                    <img src="https://graph.facebook.com/v2.3/1324855987/picture" class="article-author-thumb" alt="">
                </div>
                <div class="article-header-text">
                    ${(this.writer)}
                    <div class="article-header-time">${new Date(this.createdDate).toYYYYMMDD_HHMMSS()}" /></div>
                </div>
            </div>
            <div class="article-doc comment-doc">
                <p>${(this.contents)}</p>
            </div>
            <div class="article-util">
                <ul class="article-util-list">
                    <li>
                        <a class="link-modify-article" href="/api/qna/updateAnswer?answerId=${(this.answerId)}">수정</a>
                    </li>
                    <li>
                        <form name = "answer-delete" class="form-delete" method="POST">
                            <input type="hidden" name="answerId" value="${(this.answerId)}" />
                            ${submitButton.outerHTML}
                        </form>
                    </li>
                </ul>
            </div>
        </article>
    `;
    }
}