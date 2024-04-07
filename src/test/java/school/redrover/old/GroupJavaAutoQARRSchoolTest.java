package school.redrover.old;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.Duration;

@Ignore
public class GroupJavaAutoQARRSchoolTest extends BaseTest {
    protected WebElement waitForElement(By locator) {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    @Test
    public void testReturnToHomePageFromInventoryDetailsCard() {
        getDriver().get("https://www.saucedemo.com");

        getDriver().findElement(By.name("user-name")).sendKeys("standard_user");
        getDriver().findElement(By.name("password")).sendKeys("secret_sauce");
        getDriver().findElement(By.name("login-button")).click();

        WebElement inventoryName = getDriver().findElement(By.xpath("//div[text() = 'Sauce Labs Bike Light']"));
        inventoryName.click();

        WebElement backToProductBtn = getDriver().findElement(By.xpath("//button[@id = 'back-to-products']"));
        backToProductBtn.click();

        WebElement headerProductsOnTheHomePage = getDriver().findElement(By.xpath("//span[text() = 'Products']"));
        String value = headerProductsOnTheHomePage.getText();

        Assert.assertEquals(value, "Products");
    }
    @Test
    public void testDemoQAPracticeForm(){
        getDriver().get("https://demoqa.com/automation-practice-form");

        WebElement firstName = waitForElement(By.id("firstName"));
        firstName.sendKeys("Bob");

        getDriver().findElement(By.xpath("//input[@id='lastName']")).sendKeys("Boboc");
        getDriver().findElement(By.xpath("//input[@id='userEmail']")).sendKeys("test@gmail.com");
        getDriver().findElement(By.xpath("//input[@id='userNumber']")).sendKeys("1234567890");
        getDriver().findElement(By.xpath("//input[@id='gender-radio-1']/following-sibling::label['::after']")).click();

        ((JavascriptExecutor) getDriver()).executeScript("window.scrollTo(0, 1600)");
        getDriver().findElement(By.id("submit")).click();

        WebElement title = waitForElement(By.id("example-modal-sizes-title-lg"));
        String value = title.getText();
        Assert.assertEquals(value, "Thanks for submitting the form");
    }

    @Test
    public void testTasksAreaWithDoubleclick() {
        getDriver().get("https://thecode.media/");

        WebElement searchArea = getDriver().findElement(By.className("tab-questions"));

        Action myAction = new Actions(getDriver()).doubleClick(searchArea).build();
        myAction.perform();

        String foundText = waitForElement(By.xpath("(//h1[@class='search__title'])")).getText();
        Assert.assertEquals(foundText, "Как решить");
    }

    @Test
    public void testCheckTextField() {
        getDriver().get("https://thecode.media/");

        WebElement searchButton = waitForElement(By.className("heading-search__open"));
        searchButton.click();

        WebElement searchText = waitForElement(By.className("heading-search__input"));
        searchText.sendKeys("Api");

        searchButton.click();

        String foundText = waitForElement(By.className("search__title")).getText();
        Assert.assertEquals(foundText,"api");
    }

    public void checkTable(String key, String value) {
        WebElement tableWithResult = waitForElement(By.cssSelector(".table-responsive"));

        WebElement cellWithKey = tableWithResult.findElement(By.xpath(".//*[contains(text(), '" + key + "')]"));

        String parentXpath = "./..";
        WebElement parentElement = cellWithKey.findElement(By.xpath(parentXpath));
        boolean hasValue = parentElement.getText().contains(value);

        if (!hasValue) {
            throw new AssertionError("Expected text not found: " + value);
        }
    }

    public void setDate(String year, String month, String day) {
        WebElement selectYearElement = getDriver().findElement(By.className("react-datepicker__year-select"));
        Select selectYear = new Select(selectYearElement);
        selectYear.selectByVisibleText(year);

        WebElement selectMonthElement = getDriver().findElement(By.className("react-datepicker__month-select"));
        Select selectMonth = new Select(selectMonthElement);
        selectMonth.selectByVisibleText(month);

        String daySelector = String.format(".react-datepicker__day.react-datepicker__day--0%s:not(.react-datepicker__day--outside-month)", day);
        WebElement dayElement = getDriver().findElement(By.cssSelector(daySelector));
        dayElement.click();
    }
    @Test
    void testAllFormFieldsAreFill() throws URISyntaxException, FileNotFoundException {
        getDriver().get("https://demoqa.com/automation-practice-form");

        ((JavascriptExecutor) getDriver()).executeScript("$('#fixedban').remove()");
        ((JavascriptExecutor) getDriver()).executeScript("$('footer').remove()");

        WebElement inputFirstName = waitForElement(By.id("firstName"));
        inputFirstName.sendKeys("test");
        String firstName = inputFirstName.getText();

        WebElement inputLastName = waitForElement(By.id("lastName"));
        inputLastName.sendKeys("test");
        String lastName = inputLastName.getText();

        WebElement inputUserEmail = waitForElement(By.id("userEmail"));
        inputUserEmail.sendKeys("test@test.ru");
        String userEmail = inputUserEmail.getText();

        String gender = "Female";
        WebElement radioGender = waitForElement(By.xpath("//label[@class='custom-control-label' and contains(text(), '" + gender + "')]"));
        radioGender.click();

        WebElement inputUserNumber = waitForElement(By.id("userNumber"));
        inputUserNumber.sendKeys("3752912312");
        String userMobile = inputUserNumber.getText();

        WebElement inputCalendar = waitForElement(By.id("dateOfBirthInput"));
        inputCalendar.click();
        setDate("1991", "December", "27");
        String userBirthDate = inputCalendar.getText();

        WebElement inputSubjects = waitForElement(By.id("subjectsInput"));
        inputSubjects.sendKeys("English");
        inputSubjects.sendKeys(Keys.ENTER);
        String subjects = inputSubjects.getText();

        String hobbies = "Reading";
        WebElement checkboxHobbies = waitForElement(By.xpath("//label[@class='custom-control-label' and contains(text(), '" + hobbies + "')]"));
        checkboxHobbies.click();

        WebElement inputUploadPicture = waitForElement(By.id("uploadPicture"));
        URL resourceUrl = getClass().getClassLoader().getResource("1.jpg");
        if (resourceUrl != null) {
            String pathToFile = Paths.get(resourceUrl.toURI()).toFile().getAbsolutePath();
            inputUploadPicture.sendKeys(pathToFile);
        } else {
            throw new FileNotFoundException("Resource not found: 1.jpg");
        }

        WebElement textAreaCurrentAddress = waitForElement(By.id("currentAddress"));
        textAreaCurrentAddress.sendKeys("221B Baker Street");
        String currentAddress = textAreaCurrentAddress.getText();

        WebElement selectState = waitForElement(By.id("react-select-3-input"));
        selectState.sendKeys("NCR");
        selectState.sendKeys(Keys.ENTER);
        String state = selectState.getText();

        WebElement selectCity = waitForElement(By.id("react-select-4-input"));
        selectCity.sendKeys("Gurgaon");
        selectCity.sendKeys(Keys.ENTER);
        String city = selectCity.getText();

        WebElement buttonSendForm = waitForElement(By.id("submit"));
        buttonSendForm.click();
        String  STUDENT_NAME = "Student Name",
                STUDENT_EMAIL = "Student Email",
                STUDENT_GENDER = "Gender",
                STUDENT_MOBILE = "Mobile",
                STUDENT_BIRTH = "Date of Birth",
                STUDENT_SUBJECTS = "Subjects",
                STUDENT_HOBBIES = "Hobbies",
                STUDENT_PICTURE = "Picture",
                STUDENT_ADDRESS = "Address",
                STUDENT_STATE_AND_CITY = "State and City";

        checkTable(STUDENT_NAME, firstName);
        checkTable(STUDENT_NAME, lastName);
        checkTable(STUDENT_EMAIL, userEmail);
        checkTable(STUDENT_GENDER, gender);
        checkTable(STUDENT_MOBILE, userMobile);
        checkTable(STUDENT_BIRTH, userBirthDate);
        checkTable(STUDENT_SUBJECTS, subjects);
        checkTable(STUDENT_HOBBIES, hobbies);
        checkTable(STUDENT_PICTURE, "1.jpg");
        checkTable(STUDENT_ADDRESS, currentAddress);
        checkTable(STUDENT_STATE_AND_CITY, state);
        checkTable(STUDENT_STATE_AND_CITY, city);
    }

    @Test
    public void testAuthorizationVprokat() {
        getDriver().get("https://vprokat.online/");

        getDriver().findElement(By.cssSelector("a[aria-label='Профиль']")).click();

        getDriver().findElement(By.cssSelector("[autocomplete=email]")).sendKeys("1716.06+111@gmail.com");
        getDriver().findElement(By.cssSelector("[autocomplete=current-password]")).sendKeys("parol123");
        getDriver().findElement(By.cssSelector("div[role='dialog'] button[type='submit']")).click();

        getDriver().findElement(By.cssSelector("a[aria-label='Профиль']")).click();

        String sidebarMenu = waitForElement(By.cssSelector("main[footer='[object Object]'] p[class='mb-2']")).getText();
        Assert.assertEquals(sidebarMenu, "1716.06+111@gmail.com");

        getDriver().findElement(By.cssSelector("main[footer='[object Object]'] button[type='button']")).click();
    }
    @Test
    public void addGiftCardToCart() {
        WebDriver driver = getDriver();
        String recipientName = "Very Good Person";
        String senderName = "Very Generous Person";

        driver.get("https://demowebshop.tricentis.com/");
        driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[4]/div[1]/div[1]/div[2]/ul/li[7]/a")).click();
        driver.findElement(By.linkText("$100 Physical Gift Card")).click();
        driver.findElement(By.className("recipient-name")).sendKeys(recipientName);
        driver.findElement(By.className("sender-name")).sendKeys(senderName);
        driver.findElement(By.xpath("//*[@id=\"add-to-cart-button-4\"]")).click();
        driver.findElement(By.cssSelector("#topcartlink > a")).click();

        String productName = driver.findElement(By.className("product-name")).getText();
        String price = driver.findElement(By.className("unit-price")).getText();

        Assert.assertEquals(productName, "$100 Physical Gift Card");
        Assert.assertEquals(price, "100.00");
    }
}
