package next.model;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import next.exception.CannotDeleteQuestionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class QuestionTest {

    Question question;

    User user;

    @BeforeEach
    void setUp() {
        question = new Question(1L, "test", "test", "Test", new Date(), 1);
        user = new User("test", "1234", "name", "mail");
    }

    @Test
    @DisplayName("같은 사용자의 답변이 달린 경우 삭제 가능")
    void canDelete_success_withSameWriter() {
        Answer answer = new Answer(1L, "test", "test", new Date(), 1L);

        assertThatNoException().isThrownBy(() -> question.canDelete(user, List.of(answer)));
    }

    @Test
    @DisplayName("답변이 없는 경우 삭제 가능")
    void canDelete_success_withNoAnswer() {
        assertThatNoException().isThrownBy(() -> question.canDelete(user, Collections.emptyList()));
    }

    @Test
    @DisplayName("다른 사용자의 질문이 있는 경우 삭제 불가능")
    void canDelete_fail_withOtherWriterAnswer() {
        Answer answer = new Answer(1L, "otherWriter", "test", new Date(), 1L);

        assertThatThrownBy(() -> question.canDelete(user, List.of(answer))).isInstanceOf(
                        CannotDeleteQuestionException.class)
                .hasMessageContaining("질문에 다른 사용자의 답변이 달려있으므로 삭제할 수 없습니다.");
    }

    @Test
    @DisplayName("다른 사용자가 작성한 질문은 삭제 불가능")
    void canDelete_fail_differentWriterOfQuestion() {
        User otherUser = new User("otherWriter", "1234", "name", "email");
        Answer answer = new Answer(1L, "test", "test", new Date(), 1L);

        assertThatThrownBy(
                () -> question.canDelete(otherUser, List.of(answer))).isInstanceOf(CannotDeleteQuestionException.class)
                .hasMessageContaining("다른 사용자의 글은 삭제할 수 없습니다.");
    }

}