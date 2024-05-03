package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import school.redrover.model.base.BasePage;

import java.util.List;

public class HomePage extends BasePage {

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
}
