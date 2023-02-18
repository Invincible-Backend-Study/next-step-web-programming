String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};

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

function onError(xhr, status, error) {
  console.log("Error occurred: " + error);
}

function onSuccess(json, status){
  var answerTemplate = $("#answerTemplate").html();
  var template = answerTemplate.format(json.writer, new Date(json.createdDate), json.contents, json.answerId, json.answerId);
  $(".qna-comment-slipp-articles").prepend(template);
}

$(".answerWrite input[type=submit]").click(addAnswer);
function addAnswer(e) {
  e.preventDefault();  // submit 이 자동으로 동작하는 것을 막는다.
  //form 데이터들을 자동으로 묶어준다.
  var queryString = $("form[name=answer]").serialize();

  $.ajax({
    type : 'post',
    url : '/answer',
    data : queryString,
    dataType : 'json',
    error : onError,
    success : onSuccess,
  })
}

