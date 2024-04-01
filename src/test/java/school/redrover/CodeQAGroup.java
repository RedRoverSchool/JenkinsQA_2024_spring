package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class CodeQAGroup extends BaseTest {

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
    public void freestyleProjectTest() {
        preconditions();

        WebElement itemNameField = getDriver().findElement(By.id("name"));
        itemNameField.sendKeys("freestylePrTest33");

        WebElement freestyleProjSelect = getDriver().findElement(By.className("hudson_model_FreeStyleProject"));
        freestyleProjSelect.click();

        WebElement buttonOK = getDriver().findElement(By.id("ok-button"));
        buttonOK.click();
        WebElement buttonSave = getDriver().findElement(By.name("Submit"));
        buttonSave.click();

        String actualRes = getDriver().findElement(By.tagName("h1")).getText();
        Assert.assertEquals(actualRes, "Project freestylePrTest33");

        getDriver().quit();
    }
}