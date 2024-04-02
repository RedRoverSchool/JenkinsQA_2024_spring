package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class CodeQAGroupTest extends BaseTest {

    @Test
    public void testSlider() {

        getDriver().get("https://the-internet.herokuapp.com/horizontal_slider");

        WebElement slider = getDriver().findElement(By.xpath("/html/body/div[2]/div/div/div/input"));
        WebElement text = getDriver().findElement(By.xpath("//*[@id='range']"));

        double expectedValue = 0;
        Assert.assertEquals(Double.parseDouble(text.getText()), expectedValue);

        final double step = 0.5;
        for (int i = 0; i < 10; i++) {
            slider.sendKeys(Keys.ARROW_RIGHT);
            expectedValue += step;
            Assert.assertEquals(Double.parseDouble(text.getText()), expectedValue);
        }
        for (int i = 0; i < 11; i++) {
            Assert.assertEquals(Double.parseDouble(text.getText()), expectedValue);
            slider.sendKeys(Keys.ARROW_LEFT);
            expectedValue -= step;
        }
    }

    public void preconditions() {
        getDriver().get("http://localhost:8080/");

        WebElement loginArea = getDriver().findElement(By.cssSelector("#j_username"));
        loginArea.sendKeys("admin");

        WebElement passwordArea = getDriver().findElement(By.cssSelector("#j_password"));
        passwordArea.sendKeys("admin");

        WebElement buttonSubmit = getDriver().findElement(By.name("Submit"));
        buttonSubmit.click();

        WebElement newItemButton = getDriver().findElement(By.linkText("New Item"));
        newItemButton.click();
    }

    @Test
    public void testDropDownMenu() {
        getDriver().get("https://the-internet.herokuapp.com");

        WebElement dropDown = getDriver().findElement(By.linkText("Dropdown"));
        dropDown.click();
        WebElement dropDownMenu = getDriver().findElement(By.id("dropdown"));
        dropDownMenu.click();
        WebElement dropDownOption1 = getDriver().findElement(By.xpath("//*[@id='dropdown']/option[2]"));
        dropDownOption1.click();

        Assert.assertTrue(dropDownOption1.isDisplayed());
    }

    @Test
    public void testCheckBox() {
        getDriver().get("https://the-internet.herokuapp.com");

        getDriver().findElement(By.linkText("Checkboxes")).click();
        WebElement checkBox1 = getDriver().findElement(By.xpath("//*[@id='checkboxes']/input[1]"));
        checkBox1.click();

        Assert.assertTrue(checkBox1.isSelected());
    }
}
