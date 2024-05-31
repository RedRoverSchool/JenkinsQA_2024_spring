package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class PipelineExamplesPage extends BasePage<PipelineExamplesPage> {

    public PipelineExamplesPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//h1[contains(@id,'examples')]")
    private WebElement pipelineExamplesTitle;

    public String getPipelineExamplesTitle() {
        return pipelineExamplesTitle.getText();
    }
}
