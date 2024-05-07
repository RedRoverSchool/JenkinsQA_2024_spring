package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import school.redrover.model.base.BasePage;

import java.util.List;

public class NodesTablePage extends BasePage {

    @FindBy(css = "[href='new']")
    private WebElement newNodeButton;

    @FindBy(css = "button[href$='doDelete']")
    private WebElement deleteButton;

    @FindBy(css = "[data-id='ok']")
    private WebElement okIntoDeleteDialog;

    @FindBy(id = "computers")
    private WebElement table;

    @FindBy(css = "[href='configure']")
    private WebElement configureMonitorButton;

    @FindBy(css = "[href^='../computer/']:not([href$='configure'])")
    private List<WebElement> nodesInTableList;

    public NodesTablePage(WebDriver driver) {
        super(driver);
    }

    public CreateNodePage clickNewNodeButton() {
        newNodeButton.click();

        return new CreateNodePage(getDriver());
    }

    public NodesTablePage openDropDownChevron(String name) {
        WebElement dropdownChevron = getDriver().findElement(By.cssSelector("#node_" + name + " > td:nth-child(2) > a > button"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].dispatchEvent(new Event('mouseenter'));" +
            "arguments[0].dispatchEvent(new Event('click'));", dropdownChevron);

        return this;
    }

    public NodesTablePage deleteNodeViaOpenedDropDownChevron() {
        deleteButton.click();
        okIntoDeleteDialog.click();

        return this;
    }

    public boolean isConteinNode(String name) {
        return table.getText().contains(name);
    }

    public NodesConfigurePage clickConfigureMonitorButton() {
        configureMonitorButton.click();

        return new NodesConfigurePage(getDriver());
    }

    public boolean isNodeDisplayedInTable(String name) {
        return getDriver().findElement(By.cssSelector("[href='../computer/" + name + "/']")).isDisplayed();
    }

    public List<String> getNodesinTableList() {
        return nodesInTableList
                .stream()
                .map(WebElement::getText)
                .toList();
    }
}
