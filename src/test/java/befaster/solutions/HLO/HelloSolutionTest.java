package befaster.solutions.HLO;

import befaster.solutions.SUM.SumSolution;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloSolutionTest {
    private HelloSolution helloSolution;

    @BeforeEach
    public void setUp() {
        helloSolution = new HelloSolution();
    }

    @Test
    public void sayHello() {
        assertThat(helloSolution.hello("bob")).isEqualTo("Hello bob");
    }

    @Test
    public void sayHello2() {
        assertThat(helloSolution.hello("bob")).isEqualTo("hello");
    }
}



