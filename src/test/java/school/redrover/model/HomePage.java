package school.redrover.model;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;
import school.redrover.runner.TestUtils;

import java.awt.*;
import java.util.List;


public class HomePage extends BasePage {

    @FindBy(linkText = "Create a job")
    private WebElement createAJobLink;

    @FindBy(css = "[href='/computer/']")
    private WebElement nodesLink;

    @FindBy(css = "#executors tr [href]")
    private List<WebElement> nodesList;

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

    public CreateNewItemPage clickCreateAJob() {
        createAJobLink.click();

        return new CreateNewItemPage(getDriver());
    }


    public HomePage openItemDropdown(String projectName) {
        WebElement element = getDriver().findElement(By.cssSelector(String.format(
                "td>a[href = 'job/%s/']",
                TestUtils.asURL(projectName))));
        openElementDropdown(element);
        return this;
    }

    public DeleteDialog clickDeleteInDropdown(DeleteDialog dialog) {
        getDriver().findElement(TestUtils.DROPDOWN_DELETE).click();
        return dialog;
    }
  
    public NodesTablePage clickNodesLink() {
        nodesLink.click();

        return new NodesTablePage(getDriver());
    }

    public List<String> getNodesList() {
        return nodesList
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public boolean isNodeDisplayed(String name) {
        return getDriver().findElement(By.cssSelector("[href='/computer/" + name + "/']")).isDisplayed();
    }
}
