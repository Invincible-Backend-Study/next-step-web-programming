String.prototype.format = function () {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function (match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;

    });
};


$(document).ready(function () {/* jQuery toggle layout */
    $('#btnToggle').click(function () {
        if ($(this).hasClass('on')) {
            $('#main .col-md-6').addClass('col-md-4').removeClass('col-md-6');
            $(this).removeClass('on');
        } else {
            $('#main .col-md-4').addClass('col-md-6').removeClass('col-md-4');
            $(this).addClass('on');
        }
    });
});

// 답변시 AJAX 동작
$(".answerWrite input[type=submit]").click(addAnswer);

function addAnswer(e) {
    e.preventDefault(); // submit이 자동 동작하는 것을 막음
    // form 데이터들을 자동으로 묵어준다.
    var queryString = $("form[name=answer]").serialize();

    $.ajax({
        type: 'post',
        url: '/api/qna/addAnswer',
        data: queryString,
        dataType: 'json',
        error: onError,
        success: onSuccess,
    });
}

// 서버 응답 성공시
function onSuccess(json, status) {
    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(json.writer, new Date(json.createdDate), json.contents, json.answerId);
    $(".qna-comment-slipp-articles").prepend(template);
}

function onError(xhr, status) {
    alert("error");
}
