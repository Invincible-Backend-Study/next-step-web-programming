const deleteAnswerBtnClass = 'link-delete-article';
const answerFormClass = 'form-delete-answer';
const commentCount = document.querySelector(".qna-comment-count")
const submitAnswerBtn = document.querySelector(".submit-write .btn[type=submit]");

submitAnswerBtn?.addEventListener("click", (event) => {
  event.preventDefault();

  const textArea = document.querySelector(".submit-write .form-control");
  const data = {
    type: "post",
    url: "/qna/answer",
    data: {
      contents: textArea.value,
      id: new URL(window.location.href).searchParams.get("id"),
    },
    dataType: "json",
  };

  const answerTemplate = document.querySelector("#answerTemplate").innerHTML;
  $.ajax(data)
    .done(({ answer }) => {
      const template = formatAnswer(answerTemplate, answer.writer, new Date(answer.createdDate), answer.contents, answer.answerId, answer.answerId);
      const newAnswer = document.createElement("div");
      newAnswer.innerHTML = template;
      document.querySelector(".qna-comment-slipp-articles").append(newAnswer);
      commentCount.textContent = commentCount.textContent.replace(/[0-9]/g, (match, index) => {
        return Number(match) + 1
      })
    })
    .fail((error) => {
      console.log(error);
    });
});


const qnaComment = document.querySelector(".qna-comment");
qnaComment?.addEventListener("click", (event) => {
  if (event.target.classList.contains(deleteAnswerBtnClass)) {
    event.preventDefault();
    const formData = new FormData(event.target.closest('form'));
    const data = {
      type: "post",
      data: { ...formDataToJson(formData), "questionId": new URL(window.location.href).searchParams.get("id") },
      url: "/qna/deleteAnswer",
      dataType: "json",
    };
    $.ajax(data)
      .done(() => {
        commentCount.textContent = commentCount.textContent.replace(/[0-9]/g, (match, index) => {
          return Number(match) - 1
        })
        event.target.closest("article").remove();
      })
      .fail((error) => {
        console.log(error);
      });
  }
});

const questionDeleteBtn = document.querySelector(".form-delete .link-delete-article")
questionDeleteBtn?.addEventListener("click", (e) => {
  e.preventDefault();
  const data = {
    type: "post",
    data: { "questionId": new URL(window.location.href).searchParams.get("id") },
    url: "/qna/deleteQuestion",
    dataType: "json",
  };

  $.ajax(data)
    .done(({fail,success}) => {
      if(fail){
        alert(fail)
      }
      if(success){
        alert(success)
        window.location.href = "http://localhost:8080/"
      }
    })
    .fail((error) => {
      console.log(error);
    });
})



function formDataToJson(formData) {
  const jsonObj = {};
  for (const [key, value] of formData.entries()) {
    jsonObj[key] = value;
  }
  return jsonObj;
}

function formatAnswer() {
  const args = Array.prototype.slice.call(arguments, 1);
  return arguments[0].replace(/\{(\d+)\}/g, function (match, index) {
    return args[index];
  });
}
