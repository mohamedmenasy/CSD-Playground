import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NumberProgramTest {
    NumberProgram numberProgram = null;

    @Before
    public void setup() {
        numberProgram = new NumberProgram();
    }

    @After
    public void cleanUp() {
        numberProgram = null;
    }

    @Test
    public void tetFindGreatestNumber() {
        int expectedValue = 6;
        int actualValue = (int) numberProgram.findGreaterNumber(5, 6);
        Assert.assertEquals(expectedValue, actualValue);

        double expectedDoubleValue = -5.1;
        double actualDoubleValue = numberProgram.findGreaterNumber(-5.5, -5.1);
        Assert.assertEquals(expectedDoubleValue, actualDoubleValue,0.0);

    }
}
