package next.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import next.dao.template.DataTest;
import next.model.Answer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class AnswerDaoTest extends DataTest {

    private AnswerDao answerDao = new AnswerDao();

    @Test
    void 데이터가_정상적으로_생성되는가(){
        var data = answerDao.insert(new Answer("1234","1234",1L));

        org.assertj.core.api.Assertions.assertThat(data.getWriter()).isEqualTo("1234");
    }
}