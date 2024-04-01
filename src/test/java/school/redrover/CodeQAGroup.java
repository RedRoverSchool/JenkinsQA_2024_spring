package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

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
    @Test
    public void testLinkToHorizontalSliderPage() {

        getDriver().get("https://the-internet.herokuapp.com/");

        WebDriverWait wait60 = new WebDriverWait(getDriver(), Duration.ofSeconds(60));

        wait60.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a [@href='/horizontal_slider']")));

        WebElement linkToHorizontalSliderPage = getDriver().findElement(
                By.xpath("//a [@href='/horizontal_slider']"));
        linkToHorizontalSliderPage.click();

        String expectedResult = "Horizontal Slider";

        String actualResult = getDriver().findElement(
                By.xpath("//h3 [text() = 'Horizontal Slider']")).getText();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testHorizontalSliderMovedByMouse() {

        getDriver().get("https://the-internet.herokuapp.com/");

        WebDriverWait wait60 = new WebDriverWait(getDriver(), Duration.ofSeconds(60));

        wait60.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a [@href='/horizontal_slider']")));

        WebElement linkToHorizontalSliderPage = getDriver().findElement(
                By.xpath("//a [@href='/horizontal_slider']"));
        linkToHorizontalSliderPage.click();

        String expectedResult = "1";

        int x = getDriver().findElement(By.xpath("//input [@type='range']")).getLocation().getX() + 2;
        int y = getDriver().findElement(By.xpath("//input [@type='range']")).getLocation().getY();
        int width = getDriver().findElement(By.xpath("//input [@type='range']")).getSize().width;

        double getStepValue = Double.parseDouble(getDriver().findElement(
                By.xpath("//input [@type='range']")).getAttribute("step"));
        double getMaxValue = Double.parseDouble(getDriver().findElement(
                By.xpath("//input [@type='range']")).getAttribute("max"));
        int getOffset = (int) (width / (getMaxValue / getStepValue));

        int stepsQtt;

        if (expectedResult.contains(".")) {
            stepsQtt = (int) (Double.parseDouble(expectedResult) * 2);
        } else {
            stepsQtt = Integer.parseInt(expectedResult) * 2;
        }

        Actions mouse = new Actions(getDriver());

        for (int i = 0; i < stepsQtt; i++) {
            mouse.moveToLocation(x, y)
                    .clickAndHold()
                    .moveByOffset(getOffset, 0)
                    .release()
                    .perform();
            x += getOffset;
        }

        String actualResult = getDriver().findElement(By.xpath("//span [@id='range']")).getText();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testDropdownList() {

        getDriver().get("https://the-internet.herokuapp.com/");

        WebDriverWait wait60 = new WebDriverWait(getDriver(), Duration.ofSeconds(60));

        wait60.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a [@href='/dropdown']")));

        WebElement linkToDropdownListPage = getDriver().findElement(
                By.xpath("//a [@href='/dropdown']"));
        linkToDropdownListPage.click();

        String expectedResult = "Option 1, Option 2";

        StringBuilder actualResult = new StringBuilder();

        for (int i = 1; i <= 2; i++) {
            WebElement DropdownOption = getDriver().findElement(
                    By.xpath("//select [@id = 'dropdown' ]/option [@value = '" + i + "']"));

            if (i < 2) {
                actualResult.append(DropdownOption.getText()).append(", ");
            } else {
                actualResult.append(DropdownOption.getText());
            }
        }

        Assert.assertEquals(actualResult.toString(), expectedResult);
    }

    @Test
    public void testDynamicallyLoadedPageElements() {

        getDriver().get("https://the-internet.herokuapp.com/");

        String expectedResult = "Hello World!";

        WebDriverWait wait60 = new WebDriverWait(getDriver(), Duration.ofSeconds(60));

        wait60.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a [@href='/dynamic_loading']")));

        WebElement linkToDynamicallyLoadedPageElementsPage = getDriver().findElement(
                By.xpath("//a [@href='/dynamic_loading']"));
        linkToDynamicallyLoadedPageElementsPage.click();

        WebElement linkToDynamicallyLoadedPageElementsPage1 = getDriver().findElement(
                By.xpath("//a [@href='/dynamic_loading/1']"));
        linkToDynamicallyLoadedPageElementsPage1.click();

        WebElement startButton = getDriver().findElement(
                By.xpath("//div [@id='start']/button"));
        startButton.click();

        wait60.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div [@id='finish']")));

        String actualResult = getDriver().findElement(
                By.xpath("//div [@id='finish']")).getText();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testFloatingMenuPage() {

        getDriver().get("https://the-internet.herokuapp.com/");

        WebDriverWait wait60 = new WebDriverWait(getDriver(), Duration.ofSeconds(60));

        wait60.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a [@href='/floating_menu']")));

        WebElement linkToDynamicallyLoadedPageElementsPage = getDriver().findElement(
                By.xpath("//a [@href='/floating_menu']"));
        linkToDynamicallyLoadedPageElementsPage.click();

        wait60.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div [@id = 'menu']")));

        Actions scrollDown = new Actions(getDriver());
        scrollDown.scrollToElement(getDriver().findElement(
                By.xpath("//a [@target='_blank']"))).build().perform();

        boolean menuIsDisplayed = getDriver().findElement(
                By.xpath("//div [@id = 'menu']")).isDisplayed();

        Assert.assertTrue(menuIsDisplayed);
    }
}
