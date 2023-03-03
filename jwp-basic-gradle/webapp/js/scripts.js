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

// html 템플릿에 값 삽입
String.prototype.format = function () {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function (match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;

    });
};

// 답변시 AJAX 동작
$(".answerWrite input[type=submit]").click(addAnswer);

function addAnswer(e) {
    e.preventDefault(); // submit이 자동 동작하는 것을 막음
    // form 데이터들을 자동으로 묵어준다.
    var queryString = $("form[name=answer]").serialize();

    $.ajax({
        type: 'post',
        url: '/api/answers/add',
        data: queryString,
        dataType: 'json',
        error: onError,
        success: onSuccess,
    });
}

// 서버 응답 성공시
function onSuccess(json, status) {
    if (json.result.status) {
        var answerTemplate = $("#answerTemplate").html();
        var template = answerTemplate.format(json.answer.writer, new Date(json.answer.createdDate), json.answer.contents, json.answer.answerId);
        $(".qna-comment-slipp-articles").prepend(template);
        return;
    }
    alert(json.result.message);
}

// 삭제시 AJAX 동작
$(".link-delete-article").click(deleteAnswer);

function deleteAnswer(e) {
    e.preventDefault();

    var deleteBtn = $(this);
    var queryString = deleteBtn.closest('form').serialize();

    $.ajax({
        type: 'post',
        url: '/api/answers/delete',
        data: queryString,
        dataType: 'json',
        error: onError,
        success: function (json, status) {
            if (json.result.status) {
                deleteBtn.closest('article').remove();
            }
        }
    });
}

// 서버 응답 실패시
function onError(xhr, status) {
    alert("error");
}
