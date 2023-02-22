

export default class AnswerInput{

    render({questionId}) {
        return `
        <div class="answerWrite">
            <form name="answer" method="post">
                <input type="hidden" name="questionId" value="${questionId}">
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
    `
    }
}