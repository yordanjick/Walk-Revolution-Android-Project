import com.example.cse110_project.MainActivity;
import com.example.cse110_project.NumberFormatter;
import com.example.cse110_project.UserData;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MainActivityUnitTests {
    private long userHeight;
    @Before
    public void setup() {
        userHeight = 60;
    }
    @Test
    public void testStepToMiles() {
        double result = NumberFormatter.convertStepsToMiles(10000, userHeight);
        assertEquals(3.91098484848, result, .001);
    }
}
