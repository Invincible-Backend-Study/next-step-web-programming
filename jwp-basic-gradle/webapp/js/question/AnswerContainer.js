import Answer from "./Answer.js";
import AnswerInput from "./AnswerInput.js";

export default class AnswerContainer {

    #$contentMain;
    #question;
    #answers;
    constructor($contentMain, {question,answers}) {
        this.#question = question;
        this.#answers = answers;
        this.#$contentMain = $contentMain;

        this.render();
    }

    /**
     * question
     * @param question
     */
    #addAnswer(question){

    }

    /**
     *
     * @param {number} questionId
     */
    #deleteAnswer(questionId){
        this.render();
    }

    render(){
        console.log(this.#$contentMain);
        const answersTemplate = this.#answers.map(answer => new Answer(answer, {deleteAnswer: this.#deleteAnswer}).render(answer)).join("");
        const answerInput = new AnswerInput();
        this.#$contentMain.innerHTML = `
        <div class="qna-comment">
            <div class="qna-comment-slipp">
                <p class="qna-comment-count"><strong id="questionCount">${this.#answers.length}</strong>개의 의견</p>
                <div class="qna-comment-slipp-articles">
                    ${answersTemplate}
                    ${answerInput.render(this.#question.questionId)}
                </div>
            </div>
        </div>
    `
    }

}