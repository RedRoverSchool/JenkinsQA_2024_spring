package school.redrover.model;

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

    @FindBy(xpath = "//span[contains(text(), 'Online Documentation')]/..")
    private WebElement onlineDocumentationSidebarItem;

    @FindBy(xpath = "//a[contains(@href,'examples')]")
    private WebElement examplesReferenceSidebarItem;

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

    public PipelineDocumentationPage clickOnlineDocumentation() {
        onlineDocumentationSidebarItem.click();

        return new PipelineDocumentationPage(getDriver());
    }

    public PipelineExamplesPage clickExamplesReference() {
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
