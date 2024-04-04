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

import java.awt.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


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
    public void testFreestyleProject() {
        preconditions();

        WebElement itemNameField = getDriver().findElement(By.id("name"));
        itemNameField.sendKeys("freestylePrTest39");

        WebElement freestyleProjSelect = getDriver().findElement(By.className("hudson_model_FreeStyleProject"));
        freestyleProjSelect.click();

        WebElement buttonOK = getDriver().findElement(By.id("ok-button"));
        buttonOK.click();
        WebElement buttonSave = getDriver().findElement(By.name("Submit"));
        buttonSave.click();

        String actualRes = getDriver().findElement(By.tagName("h1")).getText();

        Assert.assertEquals(actualRes, "Project freestylePrTest39");
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

    @Test
    public void testFieldValidation() throws InterruptedException {

        getDriver().get("http://allolosos.com.ua/ru/menu/pizza");

        WebElement button = getDriver().findElement(By.xpath("//a[@href='/ru/director']"));
        button.click();
        Thread.sleep(1000);

        WebElement textName = getDriver().findElement(By.id("feedbackdirector-name"));
        textName.sendKeys("Miroslav");
        String actualName = textName.getAttribute("value");
        String expectedName = "Miroslav";

        Assert.assertEquals(actualName, expectedName);

        WebElement textPhone = getDriver().findElement(By.id("feedbackdirector-phone"));
        textPhone.sendKeys("0502380088");
        String actualPhone = textPhone.getAttribute("value");
        String expectedPhone = "+38 (050) 238-00-88";

        Assert.assertEquals(actualPhone, expectedPhone);

        WebElement textMessage = getDriver().findElement(By.id("feedbackdirector-message"));
        textMessage.sendKeys("Thank you very much");
        String actualMessage = textMessage.getAttribute("value");
        String expectedMessage = "Thank you very much";

        Assert.assertEquals(actualMessage, expectedMessage);
    }

  @Test
      public void testGoToAnotherPage() {

        getDriver().get("https://the-internet.herokuapp.com/");
        getDriver().findElement(By.cssSelector("a[href='/abtest']")).click();
        String actualResult = getDriver().findElement(By.cssSelector("h3")).getText();

        Assert.assertEquals(actualResult, "A/B Test Control");
    }

    @Test
    public void testForConflict() {

        getDriver().get("https://the-internet.herokuapp.com/");

        WebDriverWait wait60 = new WebDriverWait(getDriver(), Duration.ofSeconds(60));

        wait60.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a [@href='/floating_menu']")));

        WebElement linkToDynamicallyLoadedPageElementsPage = getDriver().findElement(
                By.xpath("//a [@href='/exit_intent']"));
        linkToDynamicallyLoadedPageElementsPage.click();

        String expectedResult = "Exit Intent";

        String actualResult = getDriver().findElement(
                By.xpath("//h3")).getText();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testForConflict2() throws AWTException {

        getDriver().get("https://the-internet.herokuapp.com/");

        WebDriverWait wait60 = new WebDriverWait(getDriver(), Duration.ofSeconds(60));

        wait60.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a [@href='/exit_intent']")));

        WebElement linkToExitIntentPage = getDriver().findElement(
                By.xpath("//a [@href='/exit_intent']"));
        linkToExitIntentPage.click();

        Robot robot = new Robot();
        robot.mouseMove(600,0);

        wait60.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div [@class='modal-title']/h3")));

        String expectedResult = "This is a modal window";
        expectedResult = expectedResult.toUpperCase();

        String actualResult = getDriver().findElement(
                By.xpath("//div [@class='modal-title']/h3")).getText();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testLoginFormWithEmptyFields() {
        getDriver().get("https://the-internet.herokuapp.com/");

        getDriver().findElement(By.cssSelector("a[href='/login']")).click();
        getDriver().findElement(By.xpath("//*[@class='radius']")).click();
        String actualResult = getDriver().findElement(By.id("flash")).getText();

        Assert.assertTrue(actualResult.contains( "Your username is invalid!"));
    }

    @Test
    public void testDropDown() {

        getDriver().get("https://the-internet.herokuapp.com/");

        WebElement atrValue = getDriver().findElement(By.xpath("//a[@href='/dropdown']"));
        atrValue.click();
        WebElement dropdown = getDriver().findElement(By.xpath("//*[@id='dropdown']"));
        dropdown.click();
        List<String> exResult = new ArrayList<>();
        exResult.add("Option 1");
        exResult.add("Option 2");
        List<String> acResult = new ArrayList<>();
        acResult.add(getDriver().findElement(By.cssSelector("#dropdown > option:nth-child(2)")).getText());
        acResult.add(getDriver().findElement(By.cssSelector("#dropdown > option:nth-child(3)")).getText());

        Assert.assertEquals(acResult, exResult);
    }
    @Test
    public void testAddElement() {
        getDriver().get("https://the-internet.herokuapp.com/");
        WebElement atrValue = getDriver().findElement(By.cssSelector("#content > ul > li:nth-child(2) > a"));
        atrValue.click();
        WebElement header3 = getDriver().findElement(By.xpath("/html/body/div[2]/div/div/button"));
        Assert.assertEquals(header3.getText(), "Add Element");
    }
    @Test
    public void testDeleteBtn() {

        getDriver().get("https://the-internet.herokuapp.com/add_remove_elements/");

        WebElement addButton = getDriver().findElement(By.xpath("/html/body/div[2]/div/div/button"));
        addButton.click();

        boolean deleteButtonVisible = getDriver().findElement(By.xpath("/html/body/div[2]/div/div/div/button")).isDisplayed();

        Assert.assertTrue(deleteButtonVisible);
    }
}
