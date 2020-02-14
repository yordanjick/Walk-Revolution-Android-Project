import com.example.cse110_project.MainActivity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MainActivityUnitTests {
    public MainActivity mainActivity;
    @Before
    public void setup() {
        mainActivity = new MainActivity();
        mainActivity.heightSet = true;
        mainActivity.userHeight = 60;
    }
    @Test
    public void testStepToMiles() {
        double result = mainActivity.convertStepsToMiles(10000);
        assertEquals(3.91098484848, result, .001);
    }
}
