package next.controller.qna.dto;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AnswerCreateDtoTest {

    @Test
    @DisplayName("본문 내용 없는 답변 생성시 예외 발생 테스트")
    void createAnswerWithEmptyContents() {
        // then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new AnswerCreateDto("sdf", "", 3L))
                .withMessageContaining("[ERROR] 본문 내용을 반드시 입력해야 합니다.");
    }
}