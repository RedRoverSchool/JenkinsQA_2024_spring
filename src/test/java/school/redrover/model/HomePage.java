package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

import java.util.List;

public class HomePage extends BasePage {
    @FindBy(xpath = "//*[text()='New Item']/ancestor::div[contains(@class,'task')]")
    private WebElement newItemSideMenu;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public CreateNewItemPage clickNewItem() {
        getDriver().findElement(By.xpath("//a[.='New Item']")).click();

        return new CreateNewItemPage(getDriver());
    }

    public List<String> getItemList() {
        return getDriver().findElements(By.cssSelector("tr > td > .jenkins-table__link > span:first-child"))
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public WebElement getNewItemSideMenu() {

        return getWait2().until(ExpectedConditions.elementToBeClickable(newItemSideMenu));
    }

    public void clickNewItemSideMenu() {
        getNewItemSideMenu().click();
    }

    public CreateNewItemPage clickNewItemSideMenuChain() {
        getNewItemSideMenu().click();

        return new CreateNewItemPage(getDriver());
    }
}
