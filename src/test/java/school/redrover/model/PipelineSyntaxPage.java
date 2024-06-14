package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import school.redrover.model.base.BasePage;

import java.util.ArrayList;
import java.util.List;

public class PipelineSyntaxPage extends BasePage<PipelineSyntaxPage> {

    public PipelineSyntaxPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "[class$='dropdownList']")
    private WebElement sampleStepDropdownList;

    @FindBy(css = "[class='jenkins-form-item tr '] [tooltip^='Help for feature:']:not([tooltip*='Exclude'])")
    private List<WebElement> catchErrorTooltipList;

    @FindBy(className = "tippy-content")
    private WebElement tooltip;

    @FindBy(xpath = "//span[contains(text(), 'Declarative Online')]/..")
    private WebElement onlineDocumentationSidebarItem;

    @FindBy(xpath = "//a[contains(@href,'examples')]")
    private WebElement examplesReferenceSidebarItem;

    @Step("Select 'catchError' from 'Sample Step' option list")
    public PipelineSyntaxPage selectCatchError() {
        new Select(sampleStepDropdownList)
                .selectByValue("catchError: Catch error and set build result to failure");

        return this;
    }

    public List<String> getCatchErrorTooltipList() {
        return catchErrorTooltipList.stream()
                .map(element -> {
                    new Actions(getDriver())
                            .moveToElement(element)
                            .pause(500)
                            .perform();
                    return tooltip.getText();
                })
                .toList();
    }

    @Step("Click on 'Declarative Online Documentation' in the sidebar menu")
    public PipelineDocumentationPage clickSidebarDeclarativeOnlineDocumentation() {
        onlineDocumentationSidebarItem.click();

        return new PipelineDocumentationPage(getDriver());
    }

    @Step("Click on 'Examples Reference' in the sidebar menu")
    public PipelineExamplesPage clickSidebarExamplesReference() {
        examplesReferenceSidebarItem.click();

        return new PipelineExamplesPage(getDriver());
    }

    public List<String> getPipelineSyntaxSidebarList() {
        List<String> sidebarItemList = new ArrayList<>();
        List<WebElement> elementList = getDriver().findElements(By.xpath("//div[@id='tasks']/div"));
        for (WebElement element : elementList) {
            sidebarItemList.add(element.getText());
        }

        return sidebarItemList;
    }
}
