package school.redrover.fillFormTest.pages;

import school.redrover.fillFormTest.pages.components.CalendarComponent;
import school.redrover.fillFormTest.pages.components.TableWithResultComponent;
import org.openqa.selenium.*;
import school.redrover.fillFormTest.tests.BaseTest;

public class DemoqaFormPage extends BaseTest {
    TableWithResultComponent table = new TableWithResultComponent();
    CalendarComponent calendar = new CalendarComponent();

    private final By inputFirstNameLocator = By.id("firstName");
    private final By inputLastNameLocator = By.id("lastName");
    private final By inputUserEmailLocator = By.id("userEmail");
    private final By inputUserNumberLocator = By.id("userNumber");
    private final By inputCalendarLocator = By.id("dateOfBirthInput");
    private final By inputSubjectsLocator = By.id("subjectsInput");
    private final By textAreaCurrentAddressLocator = By.id("currentAddress");
    private final By selectStateLocator = By.id("react-select-3-input");
    private final By selectCityLocator = By.id("react-select-4-input");
    private final By buttonSendFormLocator = By.id("submit");

    public void openPage(String url) {
        open(url);
        executeJavaScript("document.querySelector('#fixedban').remove();");
        executeJavaScript("document.querySelector('footer').remove();");
    }

    public DemoqaFormPage addUserFirstName(String firstName) {
        WebElement inputFirstName = driver.findElement(inputFirstNameLocator);
        inputFirstName.sendKeys(firstName);
        return this;
    }

    public DemoqaFormPage addUserLastName(String lastName) {
        WebElement inputLastName = driver.findElement(inputLastNameLocator);
        inputLastName.sendKeys(lastName);
        return this;
    }

    public DemoqaFormPage addUserEmail(String email) {
        WebElement inputUserEmail = driver.findElement(inputUserEmailLocator);
        inputUserEmail.sendKeys(email);
        return this;
    }

    public DemoqaFormPage checkUserGender(String gender) {
        WebElement radioGender = driver.findElement(By.xpath("//label[text()='" + gender + "']"));
        radioGender.click();
        return this;
    }

    public DemoqaFormPage addUserPhone(String phoneNumber) {
        WebElement inputUserNumber = driver.findElement(inputUserNumberLocator);
        inputUserNumber.sendKeys(phoneNumber);
        return this;
    }

    public DemoqaFormPage setUserBirth(String year, String mouth, String day){
        WebElement inputCalendar = driver.findElement(inputCalendarLocator);
        inputCalendar.click();
        calendar.setDate(year, mouth, day);
        return this;
    }

    public DemoqaFormPage selectSubjects(String subjects) {
        WebElement inputSubjects = driver.findElement(inputSubjectsLocator);
        inputSubjects.sendKeys(subjects);
        inputSubjects.sendKeys(Keys.ENTER);
        return this;
    }

    public DemoqaFormPage selectHobbies(String hobby) {
        WebElement checkboxHobbies = driver.findElement(By.xpath("//label[text()='" + hobby + "']"));
        checkboxHobbies.click();
        return this;
    }

    public DemoqaFormPage addCurrentAddress(String address) {
        WebElement textAreaCurrentAddress = driver.findElement(textAreaCurrentAddressLocator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", textAreaCurrentAddress);
        textAreaCurrentAddress.sendKeys(address);
        return this;
    }

    public DemoqaFormPage choiceStateAndCity(String state, String city) {
        WebElement selectState = driver.findElement(selectStateLocator);
        WebElement selectCity = driver.findElement(selectCityLocator);
        selectState.sendKeys(state);
        selectState.sendKeys(Keys.ENTER);
        selectCity.sendKeys(city);
        selectCity.sendKeys(Keys.ENTER);
        return this;
    }

    public void sendForm() {
        WebElement buttonSendForm = driver.findElement(buttonSendFormLocator);
        buttonSendForm.click();
    }

    public DemoqaFormPage checkSubmittingForm(String key, String value) {
        table.checkTable(key, value);
        return this;
    }

    public void executeJavaScript(String script) {
        if (driver instanceof JavascriptExecutor) {
            ((JavascriptExecutor) driver).executeScript(script);
        } else {
            throw new IllegalStateException("This driver does not support JavaScript execution.");
        }
    }
}
