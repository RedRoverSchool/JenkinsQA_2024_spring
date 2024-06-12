package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class PipelineDocumentationPage extends BasePage<PipelineDocumentationPage> {

    public PipelineDocumentationPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//*[@id='pipeline-syntax']")
    private WebElement pipelineSyntaxTitle;

    public String getPipelineSyntaxTitle() {
        return pipelineSyntaxTitle.getText();
    }
}
