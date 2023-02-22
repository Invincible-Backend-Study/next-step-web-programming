import Question from "./question/Question.js";
import QuestionHeader from "./question/QuestionHeader.js";
import QuestionContent from "./question/QuestionContent.js";
import AnswerContainer from "./question/AnswerContainer.js";

class QuestionApp{

    #question;
    #answers;
    async init(questionId){
        const data = await fetch(`/api/qna/show?questionId=${questionId}`).then(response=> response.json());

        console.log(data);
        if(!data.result.status){
            window.alert(data.result.status);
            return ;
        }

        this.#question = data.question;
        this.#answers = data.answers;
        console.log(data);
        const $container = document.querySelector(".container");
        const $contentMain = document.createElement("div");
        $contentMain.classList.add("content-main");

        const question = new Question();
        const questionHeader = new QuestionHeader(data.question);
        const questionContent = new QuestionContent(data.question);
        const answerComponent = new AnswerContainer($contentMain, {question:data.question, answers:this.#answers} );
        this.#render($container, questionHeader,questionContent, $contentMain);
    }

    #render($container, questionHeader,  questionContent, $contentMain) {
        $container.innerHTML = `
        <div class="col-md-12 col-sm-12 col-lg-10 col-lg-offset-1">
                <div class="panel panel-default">
                 ${questionHeader.render()}
                 <div class="content-main">
                    ${questionContent.render()}
                 </div>
                 ${$contentMain.innerHTML}
            </div>
        </div>
        `
    }
}
const urlSearchParams = new URLSearchParams(location.search);
const questionId = urlSearchParams.get("questionId");
new QuestionApp().init(questionId);