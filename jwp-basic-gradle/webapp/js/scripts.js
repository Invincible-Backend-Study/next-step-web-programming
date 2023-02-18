document.querySelector(".submit-write .btn[type=submit]").addEventListener("click",(e)=>{
    e.preventDefault()
    const textArea = document.querySelector(".submit-write .form-control")
    const data = {
        type:"post",
        url: "/qna/answer",
        data : {
            "contents" : textArea.value,
            "id":new URL(window.location.href).searchParams.get("id")
        },
        dataType: "json",
    }

    const answerTemplate = document.querySelector("#answerTemplate").innerHTML
    $.ajax(data)
    .done(data=>{
        const template = format(answerTemplate,data.writer, new Date(data.createdDate), data.contents, data.answerId, data.answerId)
        const element = document.createElement("div")
        element.innerHTML = template
        document.querySelector(".qna-comment-slipp-articles").append(element)
    })
    .fail((e,e2,e3)=>{
        console.log(e3)
    })
})

function format() {
    var args = Array.prototype.slice.call (arguments, 1);
    return arguments[0].replace (/\{(\d+)\}/g, function (match, index) {
       return args[index];
    });
 }
