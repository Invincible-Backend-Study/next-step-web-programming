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
        url: '/api/qna/addAnswer',
        data: queryString,
        dataType: 'json',
        error: onError,
        success: onSuccess,
    });
}

// 삭제시 AJAX 동작
$(".link-delete-article").click(deleteAnswer);

function deleteAnswer(e) {
    e.preventDefault();

    var queryString = $("form[name=form-delete]").serialize();
    var deleteBtn = $(this);

    $.ajax({
        type: 'post',
        url: '/api/qna/deleteAnswer',
        data: queryString,
        dataType: 'json',
        error: onError,
        success: function (json, status) {
            if (json.status) {
                deleteBtn.closest('article').remove();
            }
        }
    });
}

// 서버 응답 성공시
function onSuccess(json, status) {
    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(json.writer, new Date(json.createdDate), json.contents, json.answerId);
    $(".qna-comment-slipp-articles").prepend(template);
}

// 서버 응답 실패시
function onError(xhr, status) {
    alert("error");
}
