package calculator;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import camp.nextstep.edu.missionutils.test.NsTest;
import java.io.ByteArrayInputStream;
import org.junit.jupiter.api.Test;

class ApplicationTest extends NsTest {
    @Test
    void 커스텀_구분자_사용() {
        assertSimpleTest(() -> {
            run("//;\\n1");
            assertThat(output()).contains("결과 : 1");
        });
    }

    @Test
    void 예외_테스트() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("-1,2,3"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 입력_테스트() {
        Calculator calculator = new Calculator();
        System.setIn(new ByteArrayInputStream("1".getBytes()));

        assertSimpleTest(() ->
                assertThat(calculator.inputStr()).isEqualTo("1")
        );
    }

    @Test
    void 커스텀구분자_테스트() {
        Calculator calculator = new Calculator();
        System.setIn(new ByteArrayInputStream("//a1b2.c\\n".getBytes()));
        calculator.inputStr();

        assertSimpleTest(() ->
                assertThat(calculator.checkCustomDelim()).get().isEqualTo("a1b2.c")
        );
    }

    @Test
    void 커스텀구분자_없음_테스트() {
        Calculator calculator = new Calculator();
        System.setIn(new ByteArrayInputStream("a1b2.c".getBytes()));
        calculator.inputStr();

        assertSimpleTest(() ->
                assertThat(calculator.checkCustomDelim()).isEmpty()
        );
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
