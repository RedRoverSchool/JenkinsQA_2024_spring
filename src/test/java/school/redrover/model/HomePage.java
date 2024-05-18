package school.redrover.model;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;
import school.redrover.runner.TestUtils;


public class HomePage extends BasePage {

    @FindBy(linkText = "Create a job")
    private WebElement createAJobLink;

    @FindBy(css = "[href='/computer/']")
    private WebElement nodesLink;

    @FindBy(css = "#executors tr [href]")
    private List<WebElement> nodesList;

    @FindBy(css = "td > a[href^='job']")
    private WebElement pipelineItem;

    @FindBy(linkText = "People")
    private WebElement peopleSideMenu;

    @FindBy(id = "tasks")
    private List<WebElement> sideMenus;

    @FindBy(xpath = "//h1")
    private WebElement h1Heading;

    @FindBy(linkText = "New Item")
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

    public MultiConfigurationPage clickMCPName(String projectName) {
        getDriver().findElement(By.cssSelector(String.format("[href = 'job/%s/']", projectName))).click();

        return new MultiConfigurationPage(getDriver());
    }

    public PipelinePage clickCreatedPipelineName() {
        pipelineItem.click();

        return new PipelinePage(getDriver());
    }

    private PeoplePage clickPeopleSideMenu() {
        getWait5().until(ExpectedConditions.visibilityOfAllElements(sideMenus));
        peopleSideMenu.click();

        return new PeoplePage(getDriver());
    }

    public UserConfigurePage openUserConfigurations() {
        return clickPeopleSideMenu()
                .clickUserIdLink()
                .clickConfigureSideMenu();
    }

    private CreateNewItemPage clickNewItemSideMenu() {
        getWait5().until(ExpectedConditions.elementToBeClickable(newItemSideMenu)).click();

        return new CreateNewItemPage(getDriver());
    }

    public FreestylePage createFreestyleProjectWithConfigurations(String projectName) {
        getWait5().until(ExpectedConditions.textToBePresentInElement(h1Heading, "Welcome to Jenkins!"));

        return clickNewItemSideMenu()
                .setItemName(projectName)
                .selectFreestyleAndClickOk()
                .scrollToBuildTriggersHeading()
                .clickTriggerBuildsRemotelyCheckbox()
                .inputAuthenticationToken(projectName)
                .clickAddTimestampsCheckbox()
                .clickSaveButton();
    }
}
