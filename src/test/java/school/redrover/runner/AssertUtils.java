package school.redrover.runner;

import io.qameta.allure.Allure;
import org.testng.Assert;

import java.util.List;

public final class AssertUtils {
    private static final String PREFIX = "Expected results:";

    public static AssertUtils allureAnnotation(String expectedResults) {
        Allure.step(String.format("%s %s", PREFIX, expectedResults));

        return new AssertUtils();
    }

    public static AssertUtils check() {
        return new AssertUtils();
    }

    public <T> void equals(T actual, T expected) {
        Assert.assertEquals(actual, expected);
    }

    public <T> void equals(T actual, T expected, String errorMessage) {
        Assert.assertEquals(actual, expected, errorMessage);
    }

    public void isTrue(boolean condition) {
        Assert.assertTrue(condition);
    }

    public void isTrue(boolean condition, String errorMessage) {
        Assert.assertTrue(condition, errorMessage);
    }

    public <T> void listContainsObject(List<T> list, T object, String errorMessage) {
        Assert.assertListContainsObject(list, object, errorMessage);
    }

}
