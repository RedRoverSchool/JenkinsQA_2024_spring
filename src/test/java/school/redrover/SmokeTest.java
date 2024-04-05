package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class SmokeTest extends BaseTest {

    @Test
    public void test() {
        Assert.assertEquals(1, 1);
    }
}
