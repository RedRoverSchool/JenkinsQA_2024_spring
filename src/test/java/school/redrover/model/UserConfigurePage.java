package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

public class UserConfigurePage extends BasePage {
    @FindBy(xpath = "//button[text()='Add new Token']")
    private WebElement addNewTokenButton;

    @FindBy(name = "tokenName")
    private WebElement tokenNameInputField;

    @FindBy(id = "api-token-property-token-save")
    private WebElement generateButton;

    @FindBy(xpath = "//span[@class='new-token-value visible']")
    private WebElement tokenValue;

    @FindBy(name = "tokenUuid")
    private  WebElement tokenUuid;

    @FindBy(name = "Submit")
    private WebElement saveButton;

    @FindBy(css = ".token-list-item>div")
    private WebElement tokenMessage;

    public UserConfigurePage(WebDriver driver) {
        super(driver);
    }

    public UserPage clickSaveButton() {
        saveButton.click();

        return new UserPage(getDriver());
    }

    public String[] getTokenUuidUser(String projectName) {
        new Actions(getDriver())
                .scrollToElement(addNewTokenButton)
                .scrollByAmount(0,150)
                .perform();

        addNewTokenButton.click();
        tokenNameInputField.sendKeys(projectName);
        generateButton.click();

        final String token = getWait5().until(ExpectedConditions.visibilityOf(tokenValue)).getText();
        final String uuid = tokenUuid.getAttribute("value");
        final String user = getDriver().getCurrentUrl().split("/")[4];

        return new String[]{token, uuid, user};
    }

    public String getTokenMessage() {

        return tokenMessage.getText();
    }
}
