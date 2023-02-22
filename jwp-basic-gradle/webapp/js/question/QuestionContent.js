export default class QuestionContent {
    #question;
    /**
     * @typedef { object } question
     * @property {title} title
     * @param {Question}question
     */
    constructor(question) {
        this.#question = question ;
    }


    render(){
        return `
                <article class="article">
                    <div class="article-header">
                        <div class="article-header-thumb">
                            <img src="https://graph.facebook.com/v2.3/100000059371774/picture" class="article-author-thumb" alt="">
                        </div>
                        <div class="article-header-text">
                            <a href="/users/92/kimmunsu" class="article-author-name">${this.#question.writer}</a>
                            <a href="/questions/413" class="article-header-time" title="퍼머링크">
                                ${new Date(this.#question.createdDate).toYYYYMMDD_HHMMSS()}
                                <i class="icon-link"></i>
                            </a>
                        </div>
                    </div>
                    <div class="article-doc">
                        ${this.#question.contents}
                    </div>
                    <div class="article-util">
                        <ul class="article-util-list">
                            <li>
                                <a class="link-modify-article" href="#">수정</a>
                            </li>
                            <li>
                                <form class="form-delete" action="#" method="POST">
                                    <input type="hidden" name="_method" value="DELETE">
                                    <button class="link-delete-article" type="submit">삭제</button>
                                </form>
                            </li>
                            <li>
                                <a class="link-modify-article" href="/">목록</a>
                            </li>
                        </ul>
                    </div>
                </article>
        `
    }
}