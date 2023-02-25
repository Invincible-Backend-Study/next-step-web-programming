package next.controller.qna.dto;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class QuestionCreateDtoTest {

    @Test
    @DisplayName("제목 혹은 본문 내용 없는 답변 생성시 예외 발생 테스트")
    void createAnswerWithEmptyContents() {
        // then
        Assertions.assertAll(() -> {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> new QuestionCreateDto("testId", "", "testContents"))
                    .withMessageContaining("[ERROR] 제목 및 본문 내용을 반드시 입력해야 합니다.");
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> new QuestionCreateDto("testId", "testTitle", ""))
                    .withMessageContaining("[ERROR] 제목 및 본문 내용을 반드시 입력해야 합니다.");
        });
    }

}