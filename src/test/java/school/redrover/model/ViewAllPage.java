package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

public class ViewAllPage extends BasePage<ViewAllPage> {

    @FindBy(xpath = "(//li[@class='children'])[2]")
    private WebElement allDropDownChevron;

    public ViewAllPage(WebDriver driver) {
        super(driver);
    }

    @Step("Click 'All' dropdown menu on Breadcrumbs and select Project name")
    public FolderProjectPage clickBreadcrumbAllDropdownMenuAndSelectProjectName(String name) {
        allDropDownChevron.click();

        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class = 'jenkins-dropdown__item'][contains(@href," + name + ")]"))).click();

        return new FolderProjectPage(getDriver());
    }
}
