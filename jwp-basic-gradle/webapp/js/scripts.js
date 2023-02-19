
$(document).ready(function(){/* jQuery toggle layout */
  $('#btnToggle').click(function(){
    if ($(this).hasClass('on')) {
      $('#main .col-md-6').addClass('col-md-4').removeClass('col-md-6');
      $(this).removeClass('on');
    }
    else {
      $('#main .col-md-4').addClass('col-md-6').removeClass('col-md-4');
      $(this).addClass('on');
    }
  });
});



function onSuccess({answer, countOfComments}, status){
  const answerTemplate = $("#answerTemplate").html();
  const template = answerTemplate.format(
      answer.writer,
      new Date(answer.createdDate).toYYYYMMDD_HHMMSS(),
      answer.contents,
      answer.answerId,
  );
  $(".qna-comment-slipp-articles").prepend(template);
  $("#questionCount").text(countOfComments);
  $("form[name=answer]").each(function() {this.reset()});
}

function onError(xhr, status) {
  alert("error");
}

String.prototype.format = function(...args) {
  console.log(args);
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};

Date.prototype.toYYYYMMDD_HHMMSS = function(){
  const TIME_ZONE = 9 * 60 * 60 * 1000; // 9시간
  const date = new Date(this.getTime() + TIME_ZONE).toISOString().split('T')[0];
  const time = this.toTimeString().split(' ')[0];
  return `${date} ${time}`;
}

class App{
  init(){
    // add answer button
    const addAnswerButton = $(".answerWrite input[type=submit]");
    const deleteButton = $(".link-delete-article")

    addAnswerButton.click(this.#addAnswer);
    deleteButton.click(this.#deleteAnswer);
  }

  #addAnswer(e){
    e.preventDefault();
    const queryString = $("form[name=answer]").serialize();
    $.ajax({
      type:'post',
      url : '/api/qna/addAnswer',
      data:queryString,
      dataType: 'json',
      error: onError,
      success: onSuccess
    })
    this.init();
  }

  #deleteAnswer(e) {
    e.preventDefault();
    const queryString = $($(e)[0].target.form).serialize();
    $.ajax({
      type:'post',
      url : '/api/qna/deleteAnswer',
      data:queryString,
      dataType: 'json',
      error: (e)=>{
        console.log(e);
      },
      success: ({countOfComments}) =>{
        $(this).closest(".article").remove();
        $("#questionCount").text(countOfComments);
      }
    })
  }
}
const app = new App();
app.init();