package school.redrover.runner;

import io.qameta.allure.Allure;
import org.testng.Assert;

import java.util.List;

public final class AssertUtils {
    private static final String PREFIX = "Expected results: ";

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

    public <T> void notEquals(T actual, T expected) {
        Assert.assertNotEquals(actual, expected);
    }

    public <T> void notEquals(T actual, T expected, String errorMessage) {
        Assert.assertNotEquals(actual, expected, errorMessage);
    }

    public void isTrue(boolean condition) {
        Assert.assertTrue(condition);
    }

    public void isTrue(boolean condition, String errorMessage) {
        Assert.assertTrue(condition, errorMessage);
    }

    public void isFalse(boolean condition) {
        Assert.assertFalse(condition);
    }

    public void isFalse(boolean condition, String errorMessage) {
        Assert.assertFalse(condition, errorMessage);
    }

    public void isNull(Object object) {
        Assert.assertNull(object);
    }

    public void isNull(Object object, String errorMessage) {
        Assert.assertNull(object, errorMessage);
    }

    public void notNull(Object object) {
        Assert.assertNotNull(object);
    }

    public void notNull(Object object, String errorMessage) {
        Assert.assertNotNull(object, errorMessage);
    }

    public <T> void listContainsObject(List<T> list, T object, String errorMessage) {
        Assert.assertListContainsObject(list, object, errorMessage);
    }

    public <T> void listNotContainsObject(List<T> list, T object, String errorMessage) {
        Assert.assertListNotContainsObject(list, object, errorMessage);
    }
}
