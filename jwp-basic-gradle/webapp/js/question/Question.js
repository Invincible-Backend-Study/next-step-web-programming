import QuestionHeader from "./QuestionHeader.js";
import QuestionContent from "./QuestionContent.js";

export default class Question {
    constructor() {
    }
    render(){
        const questionHeader = new QuestionHeader();
        const questionContent = new QuestionContent();
        return `
            ${questionHeader.render()}
            ${questionContent.render()}
        `
    }

}