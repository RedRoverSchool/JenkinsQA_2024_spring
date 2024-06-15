package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

import java.util.List;

public class UserConfigurePage extends BasePage<UserConfigurePage> {
    @FindBy(xpath = "//button[text()='Add new Token']")
    private WebElement addNewTokenButton;

    @FindBy(css = ".last .token-name")
    private WebElement tokenNameInputField;

    @FindBy(id = "api-token-property-token-save")
    private WebElement generateButton;

    @FindBy(xpath = "//span[@class='new-token-value visible']")
    private WebElement tokenValue;

    @FindBy(css = ".last .token-uuid-input")
    private  WebElement tokenUuid;

    @FindBy(name = "Submit")
    private WebElement saveButton;

    @FindBy(css = ".token-uuid-input")
    private List<WebElement> tokenUuidList;

    public UserConfigurePage(WebDriver driver) {
        super(driver);
    }

    @Step("Click 'Save' button")
    public UserPage clickSaveButton() {
        saveButton.click();

        return new UserPage(getDriver());
    }

    public String[] getTokenUuidUser(String projectName) {

        scrollIntoViewCenter(addNewTokenButton);

        addNewTokenButton.click();
        tokenNameInputField.sendKeys(projectName);
        generateButton.click();

        final String token = getWait5().until(ExpectedConditions.visibilityOf(tokenValue)).getText();
        final String uuid = tokenUuid.getAttribute("value");
        final String user = getDriver().getCurrentUrl().split("/")[4];

        return new String[]{token, uuid, user};
    }

    public List<String> getUuidlist() {

        return tokenUuidList.stream().map(e -> e.getAttribute("value")).toList();
    }
}
