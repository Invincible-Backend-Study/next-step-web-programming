
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



function onSuccess(json, status){
  const answerTemplate = $("#answerTemplate").html();
  console.log(json);
  const template = answerTemplate.format(json.writer, new Date(json.createdDate), json.contents, json.answerId, json.answerId);
  $(".qna-comment-slipp-articles").prepend(template);
}

function onError(xhr, status) {
  alert("error");
}

String.prototype.format = function() {
  const args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};
class App{
  init(){
    const _this = this;
    // add answer button
    const addAnswerButton = $(".answerWrite input[type=submit]");
    const deleteButton = $(".link-delete-article")

    addAnswerButton.click(_this.#addAnswer);
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
  }
}
const app = new App();
app.init();