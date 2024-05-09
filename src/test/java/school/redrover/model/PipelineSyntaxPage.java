package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class PipelineSyntaxPage extends BasePage {

    public PipelineSyntaxPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//span[contains(text(), 'Online Documentation')]/..")
    private WebElement onlineDocumentationButton;

    @FindBy(xpath = "//a[contains(@href,'examples')]")
    private WebElement examplesReferenceButton;

    public PipelineDocumentationPage clickOnlineDocumentation() {
        onlineDocumentationButton.click();

        return new PipelineDocumentationPage(getDriver());
    }

    public PipelineExamplesPage clickExamplesReference() {
        examplesReferenceButton.click();

        return new PipelineExamplesPage(getDriver());
    }
}
