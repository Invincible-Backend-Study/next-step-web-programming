import Question from "./question/Question.js";
import QuestionHeader from "./question/QuestionHeader.js";
import QuestionContent from "./question/QuestionContent.js";
import AnswerComponent from "./question/AnswerComponent.js";

class QuestionApp{
    init(){
        const $container = document.querySelector(".container");
        const question = new Question();
        const questionHeader = new QuestionHeader();
        const questionContent = new QuestionContent();

        this.#render($container, questionHeader,questionContent);
    }

    #render($container, questionHeader,  questionContent) {
        const answerComponent = new AnswerComponent();
        $container.innerHTML = `
        <div class="col-md-12 col-sm-12 col-lg-10 col-lg-offset-1">
                <div class="panel panel-default">
                 ${questionHeader.render()}
                 <div class="content-main">
                    ${questionContent.render()}
                    ${answerComponent.render()}
                 </div>
            </div>
        </div>
        `
    }
}

new QuestionApp().init();