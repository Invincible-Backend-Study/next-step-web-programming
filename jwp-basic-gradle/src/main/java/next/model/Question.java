package next.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Question {
    private final int questionId;
    private final String writer;
    private final String title;
    private final String contents;
    private final Timestamp createdDate;
    private final int countOfAnswer;


    public Question(int questionId, String writer, String title, String contents, Timestamp createdDate, int countOfAnswer) {
        this.questionId = questionId;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDate = createdDate;
        this.countOfAnswer = countOfAnswer;
    }

    public Question(String writer, String title, String contents) {
        this(0,writer,title,contents,Timestamp.valueOf(LocalDateTime.now()),0);
    }

    public int getQuestionId() {
        return questionId;
    }

    public int getCountOfAnswer() {
        return countOfAnswer;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public String getContents() {
        return contents;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionId=" + questionId +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", createdDate=" + createdDate +
                ", counteofAnwer=" + countOfAnswer +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public String getWriter() {
        return writer;
    }
}

