
export default class QuestionHeader{
    #question

    /**
     * @typedef { object } question
     * @property {title} title
     * @param {Question}question
     */
    constructor(question) {
        this.#question = question;
    }

    render(){
        return `<header class="qna-header">
            <h2 class="qna-title">${this.#question.title}</h2>
        </header>`
    }
}