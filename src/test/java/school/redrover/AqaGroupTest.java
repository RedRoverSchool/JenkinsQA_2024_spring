package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class AqaGroupTest extends AqaGroupBaseTest {

    private static final String URL = "https://demoqa.com/alerts";
    private static final String BUTTONS_URL = "https://demoqa.com/buttons";
    private static final String BROWSER_WINDOWS_URL = "https://demoqa.com/browser-windows";
    private static final String BUTTONS_URL_ET = "https://testpages.eviltester.com/styled/dynamic-buttons-disabled.html";
    private static final String ALERTS_URL = "https://testpages.eviltester.com/styled/alerts/alert-test.html";
    private static final String FAKE_ALERTS_URL = "https://testpages.eviltester.com/styled/alerts/fake-alert-test.html";
    private static final String ALERT_DISPLAYED = "fake alert box is displayed";
    private static final By OPEN_MODAL_DIALOG_BUTTON = By.id("modaldialog");
    private static final By MODAL_DIALOG_OK_BUTTON = By.id("dialog-ok");
    private static final By MODAL_DIALOG_TEXT = By.id("dialog-text");
    private static final String URL_LETCODE = "https://letcode.in/edit";
    private static final String MODAL_WINDOW_URL = "https://tympanus.net/Development/ModalWindowEffects/";
    private static final String URL_MOB = "http://23.105.246.172:5000/login";
    private static final By EMAIL_INPUT = By.cssSelector("input.primaryInput");
    private static final By LOGIN_BUTTON = By.className("authButton");
    private static final By ERROR_MESSAGE = By.xpath("//*[contains(text(), 'Неправильный')]");

    private String calc(String x) {
        return String.valueOf(Math.log(Math.abs(12 * Math.sin(Integer.parseInt(x)))));
    }


    @Test
    public void testAlert() {
        getDriver().get(URL);
        getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(15000));

        getDriver().findElement(By.id("alertButton")).click();
        getDriver().switchTo().alert().accept();
        Assert.assertTrue(
                getDriver().findElement(By.id("alertButton")).isDisplayed(),
                "alert is not accepted");
    }

    @Test
    public void testWaitingAlert() {
        getDriver().get(URL);

        getDriver().findElement(By.id("timerAlertButton")).click();
        Alert alert = getWait15().until(ExpectedConditions.alertIsPresent());
        alert.accept();

        Assert.assertTrue(
                getDriver().findElement(By.id("timerAlertButton")).isDisplayed(),
                "alert is not accepted");
    }

    @Test
    public void testAcceptConfirmAlert() {
        getDriver().get(URL);
        getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(15000));

        scrollIntoView(getDriver().findElement(By.id("confirmButton"))).click();
        getDriver().switchTo().alert().accept();

        Assert.assertEquals(
                getDriver().findElement(By.id("confirmResult")).getText(),
                "You selected Ok",
                "alert is not accepted");
    }

    @Test
    public void testDismissConfirmAlert() {
        getDriver().get(URL);
        getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(15000));

        scrollIntoView(getDriver().findElement(By.id("confirmButton"))).click();
        getDriver().switchTo().alert().dismiss();

        Assert.assertEquals(
                getDriver().findElement(By.id("confirmResult")).getText(),
                "You selected Cancel",
                "alert is not dismissed");
    }

    @Test
    public void testPromptAlert() {
        getDriver().get(URL);
        getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(15000));

        scrollIntoView(getDriver().findElement(By.id("promtButton"))).click();
        Alert alert = getDriver().switchTo().alert();
        alert.sendKeys("Irina");
        alert.accept();

        Assert.assertEquals(
                getDriver().findElement(By.id("promptResult")).getText(),
                "You entered Irina",
                "unexpected result");
    }

    @Test
    public void testCSSMediaQueriesSizing() {
        getDriver().get("https://testpages.eviltester.com/styled/css-media-queries.html");

        getDriver().manage().window().setSize(new Dimension(1220, 1080));

        Assert.assertTrue(getWait15().until(ExpectedConditions.visibilityOfElementLocated(By.className("s1200"))).isDisplayed());
    }

    @Test
    public void testCookieControlledAdmin() {
        getDriver().get("https://testpages.eviltester.com/styled/cookies/adminview.html");

        getDriver().findElement(By.id("username")).sendKeys("Admin");
        getDriver().findElement(By.id("password")).sendKeys("AdminPass");
        getDriver().findElement(By.id("login")).click();

        Assert.assertEquals(getDriver().manage().getCookieNamed("loggedin").getValue(),
                "Admin");

        Assert.assertEquals(getDriver().findElement(By.id("adminh1")).getText(),
                "Admin View");
    }

    @Test
    public void testAdminLoginWithCookie() {
        getDriver().get("https://testpages.eviltester.com");

        getDriver().manage().addCookie(new Cookie("loggedin", "Admin"));

        getDriver().get("https://testpages.eviltester.com/styled/cookies/adminview.html");

        Assert.assertEquals(
                getDriver().findElement(By.id("adminh1")).getText(),
                "Admin View");
    }

    @Test
    public void testAdminLoginWithCookieAsSuperAdmin() {
        getDriver().get("https://testpages.eviltester.com");

        getDriver().manage().addCookie(new Cookie("loggedin", "Admin"));

        getDriver().get("https://testpages.eviltester.com/styled/cookies/superadminview.html");

        Assert.assertEquals(
                getDriver().findElement(By.id("adminh1")).getText(),
                "Admin View");
    }

    @Test
    public void testSuperAdminLoginWithCookieAsAdmin() {
        getDriver().get("https://testpages.eviltester.com");

        getDriver().manage().addCookie(new Cookie("loggedin", "SuperAdmin"));

        getDriver().get("https://testpages.eviltester.com/styled/cookies/adminview.html");

        Assert.assertEquals(
                getDriver().findElement(By.id("adminh1")).getText(),
                "Admin View");
    }

    @Test
    public void testSuperAdminLoginWithCookie() {
        getDriver().get("https://testpages.eviltester.com");

        getDriver().manage().addCookie(new Cookie("loggedin", "SuperAdmin"));

        getDriver().get("https://testpages.eviltester.com/styled/cookies/superadminview.html");

        Assert.assertEquals(
                getDriver().findElement(By.id("superadminh1")).getText(),
                "Super Admin View");
    }

    @Test
    public void testDoubleClickButton() {
        getDriver().get(BUTTONS_URL);

        new Actions(getDriver())
                .doubleClick(scrollIntoView(getDriver().findElement(By.id("doubleClickBtn"))))
                .perform();

        Assert.assertEquals(
                getDriver().findElement(By.id("doubleClickMessage")).getText(),
                "You have done a double click",
                "Double click attempt failed.");
    }

    @Test
    public void testRightClickButton() {
        getDriver().get(BUTTONS_URL);

        new Actions(getDriver())
                .contextClick(scrollIntoView(getDriver().findElement(By.id("rightClickBtn"))))
                .perform();

        Assert.assertEquals(
                getDriver().findElement(By.id("rightClickMessage")).getText(),
                "You have done a right click",
                "Right click attempt failed.");
    }

    @Test
    public void testDynamicClickButton() {
        getDriver().get(BUTTONS_URL);

        scrollIntoView(getDriver().findElement(By.xpath("//*[@id='rightClickBtn']/../following-sibling::div/button")))
                .click();

        Assert.assertEquals(
                getDriver().findElement(By.id("dynamicClickMessage")).getText(),
                "You have done a dynamic click",
                "Right click attempt failed.");
    }

    @DataProvider(name = "windowDataProvider")
    public Object[][] windowDataProvider() {
        return new Object[][]{{"tabButton"}, {"windowButton"}};
    }

    @Test(dataProvider = "windowDataProvider")
    public void testBrowserWindowOpen(String buttonId) {
        getDriver().get(BROWSER_WINDOWS_URL);

        getWait5().until(ExpectedConditions.elementToBeClickable(By.id(buttonId))).click();

        getWait5().until(ExpectedConditions.numberOfWindowsToBe(2));

        String original = getDriver().getWindowHandle();
        for (String handle : getDriver().getWindowHandles()) {
            if (!handle.equals(original)) {
                getDriver().switchTo().window(handle);
                break;
            }
        }

        Assert.assertEquals(
                getDriver().findElement(By.id("sampleHeading")).getText(),
                "This is a sample page");
    }

    @Test
    public void testRadioButton() {
        getDriver().get("https://demoqa.com/radio-button");

        getDriver().findElement(By.xpath("//*[@for='impressiveRadio']")).click();

        Assert.assertTrue(
                getDriver().findElement(By.className("text-success")).isDisplayed(),
                "radiobutton is not selected");
    }

    @Test
    public void testPracticeFillForm() {
        getDriver().get("https://demoqa.com/automation-practice-form");

        scrollIntoView(getDriver().findElement(By.id("firstName"))).sendKeys("Irina");
        scrollIntoView(getDriver().findElement(By.id("lastName"))).sendKeys("Kuperman");
        scrollIntoView(getDriver().findElement(By.id("userEmail"))).sendKeys("ama@ama.com");
        scrollIntoView(getDriver().findElement(By.cssSelector("[for='gender-radio-2'"))).click();
        scrollIntoView(getDriver().findElement(By.id("userNumber"))).sendKeys("1234567890");

        scrollIntoView(getDriver().findElement(By.id("submit"))).click();

        Assert.assertEquals(
                getDriver().findElement(By.id("example-modal-sizes-title-lg")).getText(),
                "Thanks for submitting the form");
    }

    @Test
    public void test2GetTextLinks() {
        getDriver().get("https://demoqa.com/links");

        scrollIntoView(getDriver().findElement(By.id("no-content"))).click();

        WebElement link = getWait15().until(ExpectedConditions.visibilityOfElementLocated(By.id("linkResponse")));

        Assert.assertEquals(
                link.getText(),
                "Link has responded with staus 204 and status text No Content",
                "wrong answer");
    }

    @Test
    public void testSimpleDynamicButtons() {
        getDriver().get("https://testpages.eviltester.com/styled/dynamic-buttons-simple.html");

        getDriver().findElement(By.id("button00")).click();
        getDriver().findElement(By.id("button01")).click();
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("button02")));
        getDriver().findElement(By.id("button02")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("button03")));
        getDriver().findElement(By.id("button03")).click();

        Assert.assertEquals(getDriver().findElement(By.id("buttonmessage")).getText(), "All Buttons Clicked", "Not All Buttons Clicked");
    }

    @Test
    public void testXHTTPMessages() {
        getDriver().get("https://testpages.eviltester.com/styled/sync/xhttp-messages.html");

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(60));
        wait.until(ExpectedConditions.textToBePresentInElement(getDriver().findElement(By.id("messagesstatus")), "Message Count: 0 : 0"));

        Assert.assertEquals(getDriver().findElement(By.id("messagesstatus")).getText(), "Message Count: 0 : 0", "message count is not zero");
    }

    @Test
    public void testDisabledDynamicButtons() {
        getDriver().get(BUTTONS_URL_ET);

        getWait15().until(ExpectedConditions.elementToBeClickable(By.id("button00"))).click();
        getWait15().until(ExpectedConditions.elementToBeClickable(By.id("button01"))).click();
        getWait15().until(ExpectedConditions.elementToBeClickable(By.id("button02"))).click();
        getWait15().until(ExpectedConditions.elementToBeClickable(By.id("button03"))).click();

        Boolean found = getWait15().until(ExpectedConditions.textToBePresentInElementLocated(By.id("buttonmessage"),
                "All Buttons Clicked"));
        Assert.assertTrue(found, "Text \"All Buttons Clicked\" not found");
    }

    @DataProvider(name = "alertDataProvider")
    public Object[][] alertDataProvider() {
        return new Object[][]{
                {"alertexamples", "alertexplanation", "You triggered and handled the alert dialog", null, true},
                {"confirmexample", "confirmexplanation", "You clicked OK, confirm returned true.", null, true},
                {"confirmexample", "confirmexplanation", "You clicked Cancel, confirm returned false.", null, false},
                {"promptexample", "promptexplanation", "You clicked OK. 'prompt' returned some random input", "some random input", true},
                {"promptexample", "promptexplanation", "You clicked Cancel. 'prompt' returned null", "some random input", false}
        };
    }

    @Test(dataProvider = "alertDataProvider")
    public void testAlerts(String buttonId, String messageId, String expected, String keys, boolean accept) {
        getDriver().get(ALERTS_URL);

        getDriver().findElement(By.id(buttonId)).click();

        Alert alert = getWait15().until(ExpectedConditions.alertIsPresent());

        if (keys != null) {
            alert.sendKeys(keys);
        }

        if (accept) {
            alert.accept();
        } else {
            alert.dismiss();
        }

        Assert.assertEquals(
                getDriver().findElement(By.id(messageId)).getText(),
                expected);
    }

    @Test
    public void testExpandingDivWithClickableLink() {
        getDriver().get("https://testpages.eviltester.com/styled/expandingdiv.html");

        new Actions(getDriver())
                .moveToElement(getDriver().findElement(By.className("expand")))
                .pause(500)
                .moveToElement(getDriver().findElement(By.cssSelector(".expand p a")))
                .click()
                .pause(500)
                .perform();

        Assert.assertTrue(getDriver().getCurrentUrl().contains("expandeddiv"), "Unexpected URL.");
    }

    @DataProvider(name = "fakeAlertDataProvider")
    public Object[][] fakeAlertDataProvider() {
        return new Object[][]{
                {By.id("fakealert"), MODAL_DIALOG_OK_BUTTON},
                {OPEN_MODAL_DIALOG_BUTTON, MODAL_DIALOG_OK_BUTTON}
        };
    }

    @Test(dataProvider = "fakeAlertDataProvider")
    public void testFakeAlertCloseByClickOnOkButton(By triggerAlertButton, By okAlertButton) {
        getDriver().get(FAKE_ALERTS_URL);

        getDriver().findElement(triggerAlertButton).click();

        WebElement message = getDriver().findElement(By.id("dialog-text"));

        getDriver().findElement(okAlertButton).click();

        Assert.assertFalse(message.isDisplayed(), ALERT_DISPLAYED);
    }

    @DataProvider(name = "fakeAlertKeysProvider")
    public Object[][] fakeAlertKeysProvider() {
        return new Object[][]{{Keys.ENTER}, {Keys.ESCAPE}};
    }

    @Test(dataProvider = "fakeAlertKeysProvider")
    public void testFakeModalAlertCloseBySendKeyToOkAlertButton(Keys key) {
        getDriver().get(FAKE_ALERTS_URL);

        getDriver().findElement(OPEN_MODAL_DIALOG_BUTTON).click();

        WebElement message = getDriver().findElement(MODAL_DIALOG_TEXT);

        getDriver().findElement(MODAL_DIALOG_OK_BUTTON).sendKeys(key);

        Assert.assertFalse(message.isDisplayed(), ALERT_DISPLAYED);
    }

    @Test
    public void testFakeModalAlertCloseByJsDispatchEventToDocument() {
        getDriver().get(FAKE_ALERTS_URL);

        getDriver().findElement(OPEN_MODAL_DIALOG_BUTTON).click();

        WebElement message = getDriver().findElement(MODAL_DIALOG_TEXT);

        ((JavascriptExecutor) getDriver()).executeScript(
                "document.dispatchEvent(new KeyboardEvent('keydown', {'key': 'Esc'}));");

        Assert.assertFalse(message.isDisplayed(), ALERT_DISPLAYED);
    }

    @Test
    public void testFakeModalAlertCloseByJsClickOnBackground() {
        getDriver().get(FAKE_ALERTS_URL);

        getDriver().findElement(OPEN_MODAL_DIALOG_BUTTON).click();

        WebElement message = getDriver().findElement(MODAL_DIALOG_TEXT);

        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].click();",
                getDriver().findElement(By.cssSelector(".faded-background.active")));

        Assert.assertFalse(message.isDisplayed(), ALERT_DISPLAYED);
    }

    @Test
    public void testFakeModalAlertCloseByMoveToLocationAndClickOnBackground() {
        getDriver().get(FAKE_ALERTS_URL);

        getDriver().findElement(OPEN_MODAL_DIALOG_BUTTON).click();

        WebElement message = getDriver().findElement(MODAL_DIALOG_TEXT);

        new Actions(getDriver())
                .moveToLocation(0, 0)
                .click()
                .perform();

        Assert.assertFalse(message.isDisplayed(), ALERT_DISPLAYED);
    }

    @Test
    public void testBasicAuthorization() {
        getDriver().get("https://authorized:password001@testpages.eviltester.com/styled/auth/basic-auth-results.html");
        Assert.assertEquals(
                getDriver().findElement(By.id("status")).getText(),
                "Authenticated");
    }

    @Test
    public void testHiddenElement() {
        getDriver().get("https://the-internet.herokuapp.com/dynamic_loading/1");

        getDriver().findElement(By.tagName("button")).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("finish")));

        Assert.assertEquals(
                getDriver().findElement(By.id("finish")).getText(),
                "Hello World!",
                "error");
    }

    @Test
    public void testRenderedElement() {
        getDriver().get("https://the-internet.herokuapp.com/dynamic_loading/2");

        getDriver().findElement(By.tagName("button")).click();
        getWait5().until(ExpectedConditions.presenceOfElementLocated(By.id("finish")));

        Assert.assertEquals(
                getDriver().findElement(By.id("finish")).getText(),
                "Hello World!",
                "error");
    }

    @Test
    public void testAddElement() {
        getDriver().get("https://the-internet.herokuapp.com/add_remove_elements/");

        getDriver().findElement(By.cssSelector(".example button")).click();

        Assert.assertEquals(
                getDriver().findElement(By.className("added-manually")).getText(),
                "Delete",
                "not added delete");
    }

    @Test
    public void testDeleteElement() {
        getDriver().get("https://the-internet.herokuapp.com/add_remove_elements/");

        getDriver().findElement(By.cssSelector(".example button")).click();
        getDriver().findElement(By.cssSelector(".example button")).click();
        getDriver().findElement(By.className("added-manually")).click();
        getDriver().findElement(By.className("added-manually")).click();

        Assert.assertTrue(
                getDriver().findElements(By.className("added-manually")).isEmpty(),
                "delete(s) present");
    }

    @Test
    public void testAuth() {
        getDriver().get("http://admin:admin@the-internet.herokuapp.com/basic_auth");

        Assert.assertEquals(
                getDriver().findElement(By.tagName("p")).getText(),
                "Congratulations! You must have the proper credentials.");
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testFindBrokenPic() {
        getDriver().get("https://the-internet.herokuapp.com/broken_images");

        List<WebElement> lst = getDriver().findElements(By.xpath("//*[@class ='example']/img"));

        Assert.assertFalse(lst.isEmpty());

        for (WebElement pic : lst) {
            Assert.assertNotEquals(
                    pic.getDomProperty("naturalWidth"),
                    "0",
                    "broken image is detected");
        }
    }

    @Test
    public void testCheckBox() {
        getDriver().get("https://the-internet.herokuapp.com/checkboxes");

        WebElement checkBox1 = getDriver().findElement(By.xpath("//*[@type = 'checkbox']"));
        WebElement checkBox2 = getDriver().findElement(By.xpath("//*[@type = 'checkbox'][2]"));
        checkBox1.click();
        checkBox2.click();

        Assert.assertTrue(
                checkBox1.isSelected(),
                "checkbox 1 is not checked");
        Assert.assertFalse(
                checkBox2.isSelected(),
                "checkbox 2 is checked");
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testFindBrokenPicTestFinal() {
        getDriver().get("https://the-internet.herokuapp.com/broken_images");

        LogEntries logs = getDriver().manage().logs().get(LogType.BROWSER);

        Assert.assertFalse(logs.getAll().isEmpty());

        for (LogEntry entry : logs) {
            Assert.assertTrue(
                    entry.getMessage().contains("Failed to load resource: the server responded with a status of 404"),
                    "broken image is detected");
        }
    }

    @Test
    public void testDragAndDrop() {
        getDriver().get("https://testpages.eviltester.com/styled/drag-drop-javascript.html");

        WebElement dragMe1 = getDriver().findElement(By.id("draggable1"));
        WebElement dropHere1 = getDriver().findElement(By.id("droppable1"));

        Actions actions = new Actions(getDriver());
        actions
                .dragAndDrop(dragMe1, dropHere1)
                .build()
                .perform();

        Assert.assertEquals(
                dropHere1.getText(),
                "Dropped!",
                "error");
    }

    @Test
    public void testDragAndDropBoth() {
        getDriver().get("https://testpages.eviltester.com/styled/drag-drop-javascript.html");

        WebElement dragMe1 = getDriver().findElement(By.id("draggable1"));
        WebElement dragMe2 = getDriver().findElement(By.id("draggable2"));
        WebElement dropHere1 = getDriver().findElement(By.id("droppable1"));

        Actions actions = new Actions(getDriver());
        actions
                .dragAndDrop(dragMe1, dropHere1)
                .build()
                .perform();

        actions
                .dragAndDrop(dragMe2, dropHere1)
                .build()
                .perform();

        Assert.assertEquals(
                dropHere1.getText(),
                "Get Off Me!",
                "error");
    }

    @Test
    public void testRedirectSlow() {
        getDriver().get("https://testpages.eviltester.com/styled/javascript-redirect-test.html");

        getDriver().findElement(By.id("delaygotobasic")).click();
        getWait15().until(ExpectedConditions.urlContains("redirected"));

        Assert.assertEquals(
                getDriver().findElement(By.className("explanation")).getText(),
                "You have been successfully redirected.");
    }

    @Test
    public void testRedirectFast() {
        getDriver().get("https://testpages.eviltester.com/styled/javascript-redirect-test.html");

        getDriver().findElement(By.id("delaygotobounce")).click();
        getWait15().until(ExpectedConditions.urlContains("redirected"));

        Assert.assertEquals(
                getDriver().findElement(By.className("explanation")).getText(),
                "You have been successfully redirected.");
    }

    @Test
    public void testBasicAjax() {
        getDriver().get("https://testpages.eviltester.com/styled/basic-ajax-test.html");

        Select category = new Select(getDriver().findElement(By.id("combo1")));
        category.selectByValue("2");

        getWait15().until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[value='10']")));

        Select language = new Select(getDriver().findElement(By.id("combo2")));
        language.selectByValue("11");

        getDriver().findElement(By.className("styled-click-button")).click();

        Assert.assertEquals(
                getDriver().findElement(By.id("_valueid")).getText(), "2");

        Assert.assertEquals(
                getDriver().findElement(By.id("_valuelanguage_id")).getText(), "11");
    }

    @Test
    public void testKeysEventDisplay() {
        getDriver().get("https://testpages.eviltester.com/styled/key-click-display-test.html");

        getDriver().findElement(By.id("button")).sendKeys("a");

        Assert.assertEquals(
                getDriver().findElement(By.id("event1")).getText(),
                "down a 65");
    }

    @Test
    public void testSendKeys() {
        getDriver().get(URL_LETCODE);
        getDriver().findElement(By.id("fullName")).sendKeys("Ira");
        Assert.assertEquals(
                getDriver().findElement(By.id("fullName")).getAttribute("value"),
                "Ira");
    }

    @Test
    public void testKeyboardTAB() {
        getDriver().get(URL_LETCODE);
        getDriver().findElement(By.id("join")).sendKeys(" at all" + Keys.TAB);
//        getDriver().findElement(By.id("join")).sendKeys(" at all\t");
        Assert.assertEquals(
                getDriver().switchTo().activeElement().getAttribute("value"),
                "ortonikc");
    }

    @Test
    public void testClear() {
        getDriver().get(URL_LETCODE);
        getDriver().findElement(By.id("clearMe")).clear();
        Assert.assertEquals(
                getDriver().findElement(By.id("clearMe")).getAttribute("value"),
                "");
    }

    @Test
    public void testIsEnabled() {
        getDriver().get(URL_LETCODE);
        Assert.assertFalse(getDriver().findElement(By.id("noEdit")).isEnabled());
    }

    @Test
    public void testReadonly() {
        getDriver().get(URL_LETCODE);
        Assert.assertEquals(
                getDriver().findElement(By.id("dontwrite")).getAttribute("readonly"),
                "true");
    }

    @Test
    public void testHugeForm() {
        getDriver().get("https://suninjuly.github.io/huge_form.html");

        List<WebElement> elements = getDriver().findElements(By.tagName("input"));
        for (WebElement item : elements) {
            item.sendKeys("Мой ответ");
        }

        getDriver().findElement(By.cssSelector("button.btn")).click();

        Assert.assertTrue(
                getWait15()
                        .until(ExpectedConditions.alertIsPresent())
                        .getText()
                        .startsWith("Congrats, you've passed the task!"),
                "You shall not pass");
    }

    @Test
    public void testTextToBePresent() {
        getDriver().get("https://suninjuly.github.io/explicit_wait2.html");

        WebElement button_book = getDriver().findElement(By.id("book"));
        getWait15().until(ExpectedConditions.textToBePresentInElementLocated(By.id("price"), "$100"));
        button_book.click();

        getDriver().findElement(By.id("answer")).sendKeys(calc(getDriver().findElement(By.id("input_value")).getText()));
        getDriver().findElement(By.id("solve")).click();

        Assert.assertTrue(
                getWait15()
                        .until(ExpectedConditions.alertIsPresent())
                        .getText()
                        .startsWith("Congrats, you've passed the task!"),
                "You shall not pass");
    }

    @Test
    public void testClientSideDelay() {
        getDriver().get("http://uitestingplayground.com/clientdelay");

        getWait25().until(ExpectedConditions.elementToBeClickable(By.id("ajaxButton"))).click();

        Assert.assertTrue(
                getWait25()
                        .until(ExpectedConditions.visibilityOfElementLocated(By.className("bg-success")))
                        .getText()
                        .startsWith("Data calculated"),
                "Label text wrong");
    }

    @Test
    public void testOverlappedElement() {
        getDriver().get("http://uitestingplayground.com/overlapped");

        final String myName = "myName";
        WebElement name = scrollIntoView(getDriver().findElement(By.id("name")));
        name.sendKeys(myName);

        Assert.assertEquals(
                name.getAttribute("value"),
                myName,
                "input field contains wrong value");
    }

    @Test
    public void testHoverParagraph() {
        getDriver().get("https://testpages.eviltester.com/styled/csspseudo/css-hover.html");

        new Actions(getDriver())
                .moveToElement(getDriver().findElement(By.id("hoverpara")))
                .perform();

        Assert.assertEquals(
                getDriver().findElement(By.id("hoverparaeffect")).getText(),
                "You can see this paragraph now that you hovered on the above 'button'.");
    }

    @Test
    public void testModalWindow() {
        getDriver().get(MODAL_WINDOW_URL);

        getDriver().findElement(By.cssSelector("[data-modal = 'modal-1']")).click();
        getDriver().findElement(By.className("md-close")).click();

        Assert.assertTrue(getDriver().findElement(By.cssSelector("[data-modal = 'modal-1']")).isEnabled());
    }

    @Test
    public void testDragDrop() {
        getDriver().get("https://testpages.eviltester.com/styled/drag-drop-javascript.html");

        WebElement draggable = getDriver().findElement(By.id("draggable1"));
        WebElement droppable = getDriver().findElement(By.id("droppable1"));

        new Actions(getDriver())
                .dragAndDrop(draggable, droppable)
                .perform();

        Assert.assertEquals(
                getDriver().findElement(By.id("droppable1")).getText(),
                "Dropped!",
                "Не удалось перетащить элемент");
    }

    @Test
    public void testRefreshPage() {
        getDriver().get("https://testpages.eviltester.com/styled/refresh");

        String text = getDriver().findElement(By.id("embeddedrefreshdatevalue")).getText();
        new Actions(getDriver()).pause(1000).perform();
        getDriver().findElement(By.id("button")).click();

        Assert.assertNotEquals(
                getDriver().findElement(By.id("embeddedrefreshdatevalue")).getText(),
                text);
    }

    @Test
    public void testHoverDiv() {
        getDriver().get("https://testpages.eviltester.com/styled/csspseudo/css-hover.html");

        new Actions(getDriver())
                .moveToElement(getDriver().findElement(By.id("hoverdivpara")))
                .perform();

        Assert.assertEquals(
                getDriver().findElement(By.id("hoverdivparaeffect")).getText(),
                "You can see this child div content now that you hovered on the above 'button'.");
    }

    @DataProvider(name = "modalWindowsDataProvider")
    public Object[][] modalWindowsDataProvider() {
        return new Object[][]{
                {1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}, {9}, {10},
                {11}, {12}, {13}, {14}, {15}, {16}, {17}, {18}, {19}
        };
    }

    @Test(dataProvider = "modalWindowsDataProvider")
    public void testModalWindowsClose(int windowNumber) {
        getDriver().get(MODAL_WINDOW_URL);

        getDriver().findElement(By.cssSelector("[data-modal = 'modal-" + windowNumber + "']")).click();

        getWait5().until(ExpectedConditions.elementToBeClickable(By.cssSelector(".md-show .md-close")));

        new Actions(getDriver())
                .moveToLocation(0, 0)
                .click()
                .perform();

        Assert.assertTrue(
                getWait5().until(ExpectedConditions.invisibilityOfElementLocated(By.className("md-content"))));
    }

    @Test
    public void testIsEnabled2() {
        getDriver().get(URL_LETCODE);

        Assert.assertTrue(getDriver().findElement(By.id("dontwrite")).isEnabled());
    }

    @Test
    public void testButtonChangeItsName() {
        getDriver().get("http://uitestingplayground.com/textinput");

        getDriver().findElement(By.id("newButtonName")).sendKeys("carolync");
        getDriver().findElement(By.id("updatingButton")).click();

        Assert.assertEquals(getDriver().findElement(By.id("updatingButton")).getText(), "carolync");
    }

    @Test
    public void testWaitUntilPageLoad() {
        getDriver().get("http://uitestingplayground.com/home");

        getDriver().findElement(By.xpath("//*[contains(text(), 'Load Delay')]")).click();
        Assert.assertTrue(getWait60().until(ExpectedConditions.urlContains("http://uitestingplayground.com/loaddelay")), "Error of redirection!");
    }

    @Test
    public void testHiddenButtons() {
        getDriver().get("http://uitestingplayground.com/visibility");

        String xpath = "//button[@id='removedButton' or @id='zeroWidthButton' or @id='overlappedButton' or @id='transparentButton'" +
                " or @id='invisibleButton' or @id='notdisplayedButton' or @id='offscreenButton']";

        getDriver().findElement(By.id("hideButton")).click();

        Assert.assertFalse(getDriver().findElement(By.xpath(xpath)).isDisplayed(), "Not all the buttons are hidden!");
    }

    @Test
    public void testRemovesPassword() {
        getDriver().get(URL_MOB);

        getWait15().until(ExpectedConditions.visibilityOfElementLocated(EMAIL_INPUT)).sendKeys("yyyyyyyyyy@mail.xx");
        getDriver().findElement(LOGIN_BUTTON).click();

        Assert.assertEquals(getWait15().until(ExpectedConditions.presenceOfElementLocated(ERROR_MESSAGE)).getText(), "Неправильный логин или пароль");
    }

    @DataProvider(name = "visibilityDataProvider")
    public Object[][] visibilityDataProvider() {
        return new Object[][]{
                {By.id("removedButton")}, {By.id("zeroWidthButton")}, {By.id("Overlapped")},
                {By.id("transparentButton")}, {By.id("invisibleButton")}, {By.id("notdisplayedButton")},
                {By.id("offscreenButton")}
        };
    }

    @Test(dataProvider = "visibilityDataProvider")
    public void testVisibility(By locator) {
        getDriver().get("http://uitestingplayground.com/visibility");

        getDriver().findElement(By.id("hideButton")).click();

        Assert.assertTrue(getWait5().until(ExpectedConditions.invisibilityOfElementLocated(locator)));
    }

    @Parameters({"user_role", "isActive"})
    @Test
    public void testUserRole(@Optional("Admin") String role, @Optional("true") Boolean isActive) {
        Assert.assertTrue(role.equals("Admin") && isActive);
    }

    @Test
    public void testOpenCloseAlertPopUp() {
        getDriver().get("http://uitestingplayground.com/classattr");

        String blueButtonXpath = "//button[contains(concat(' ', (@class), ' '),' btn-primary ')]";
        getDriver().findElement(By.xpath(blueButtonXpath)).click();
        getWait5().until(ExpectedConditions.alertIsPresent()).accept();

        Assert.assertTrue(getWait5().until(ExpectedConditions.elementToBeClickable(By
                        .xpath(blueButtonXpath))).isEnabled(),
                "Alert hasn't been closed!");
    }

    @Test
    public void testScrollToFindButton() {
        getDriver().get("http://uitestingplayground.com/scrollbars");

        Assert.assertTrue(scrollIntoView(getDriver().findElement(By.id("hidingButton"))).isEnabled());
    }

    @Test
    public void testDoubleClickAtFakeLink() {
        getDriver().get("http://uitestingplayground.com/mouseover");

        new Actions(getDriver()).moveToElement(getDriver().findElement(By.linkText("Click me"))).doubleClick().build().perform();

        Assert.assertEquals(getDriver().findElement(By.id("clickCount")).getText(), "2",
                "Double click doesn't work!");
    }

    @Test
    public void testSuccessfulLogin() {
        getDriver().get("http://uitestingplayground.com/sampleapp");

        String login = "carolync";

        getDriver().findElement(By.xpath("//input[@name='UserName']")).sendKeys(login);
        getDriver().findElement(By.xpath("//input[@name='Password']")).sendKeys("pwd");
        getDriver().findElement(By.id("login")).click();

        Assert.assertTrue(getWait5().until(ExpectedConditions.textToBePresentInElementLocated(By.id("loginstatus"),
                "Welcome, " + login)), "Unsuccessful login attempt!");
    }

    @Test
    public void testGetValFromDynamicTable() {
        getDriver().get("http://uitestingplayground.com/dynamictable");

        String cpuValueRegEX = "^.* ([\\d.]+%).*";
        String rowChrome = getDriver().findElement(By.xpath("//span[text()= 'Chrome']/..")).getText();
        String cpuValue = rowChrome.replaceAll(cpuValueRegEX, "$1");
        String warningValue = getDriver().findElement(By.className("bg-warning")).getText().replaceAll(cpuValueRegEX, "$1");

        Assert.assertEquals(cpuValue, warningValue, "CPU values aren't equivalents!");
    }
}