
function deleteAnswer(obj, answerId) {
    if (confirm(answerId + '번 답변을 정말 삭제하시겠습니까?')) {
        fetch(`/answer?answerId=` + answerId, {method: 'DELETE'})
            .then(response => {
                if (response.ok) {
                    return response.json();
                } else {
                    alert('삭제에 실패했습니다.');
                }
            })
            .then(json => {
                console.table(json);
                var result = json.result;
                if (result.status) {
                    $(obj).closest('article').remove();
                    alert('삭제되었습니다.');
                } else {
                    alert('삭제에 실패했습니다. ' + result.message);
                }
            })
    }
}

function deleteQuestion(questionId) {
    if (confirm(questionId + '번글을 정말 삭제하시겠습니까?')) {
        fetch(`/questions?questionId=` + questionId, {method: 'DELETE'})
            .then(response => {
                if (response.ok) {
                    return response.json();
                } else {
                    alert('삭제에 실패했습니다.');
                }
            })
            .then(json => {
                console.table(json);
                var result = json.result;
                if (result.status) {
                    alert('삭제되었습니다.');
                    location.href = '/questions/list';
                } else {
                    alert('삭제에 실패했습니다. ' + result.message);
                }
            })
    }
}

function updateUser(event) {
    var form = event.target.closest('form');
    var formData = new FormData(form);
    var query = new URLSearchParams(formData).toString();

    fetch(`/users?${query}`, {method: 'PATCH'})
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                alert('실패했습니다..!');
            }
        })
        .then(json => {
            console.table(json);
            var result = json.result;
            if (result.status) {
                alert('성공했습니다.');
            } else {
                alert('실패했습니다. ' + result.message);
            }
        })
}
