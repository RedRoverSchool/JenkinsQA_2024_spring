package school.redrover.cucumber;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import school.redrover.model.CreateNewItemPage;
import school.redrover.model.CreateNewViewPage;
import school.redrover.model.FreestyleConfigPage;
import school.redrover.model.FreestyleProjectPage;
import school.redrover.model.HomePage;
import school.redrover.model.OrganizationFolderConfigPage;
import school.redrover.model.OrganizationFolderProjectPage;
import school.redrover.model.PipelineConfigPage;
import school.redrover.model.PipelineProjectPage;
import school.redrover.model.ViewPage;
import school.redrover.runner.CucumberDriver;
import school.redrover.runner.ProjectUtils;

import java.util.List;

public class ViewSteps {

    private HomePage homePage;

    private CreateNewItemPage createNewItemPage;

    private FreestyleConfigPage freestyleConfigPage;

    private FreestyleProjectPage freestyleProjectPage;

    private PipelineConfigPage pipelineConfigPage;

    private PipelineProjectPage pipelineProjectPage;

    private OrganizationFolderConfigPage organizationFolderConfigPage;

    private OrganizationFolderProjectPage organizationFolderProjectPage;

    private CreateNewViewPage createNewViewPage;

    private ViewPage viewPage;

    @When("Go to New Item via Create a job")
    public void goToNewItemViaCreateAJob() {
        createNewItemPage = new HomePage(CucumberDriver.getDriver()).clickCreateAJob();
    }

    @And("Enter Item name {string}")
    public void enterItemName(String name) {
        createNewItemPage = createNewItemPage.setItemName(name);
    }

    @And("Set Item type as Freestyle project, click Ok and go to Configure page")
    public void setItemTypeAsFreestyleAndClickOk() {
        freestyleConfigPage = createNewItemPage.selectFreestyleAndClickOk();
    }

    @And("Save configuration and go to Freestyle project page")
    public void saveConfigAndGoToFreestyleProject() {
        freestyleProjectPage = freestyleConfigPage.clickSaveButton();
    }

    @And("Go to home page")
    public void goHome() {
        ProjectUtils.get(CucumberDriver.getDriver());
        homePage = new HomePage(CucumberDriver.getDriver());
    }

    @And("Go to New Job to create another item")
    public void goToNewJobToCreateAnotherItem() {
        createNewItemPage = homePage.clickNewItem();
    }

    @And("Set Item type as Pipeline, click Ok and go to Configure page")
    public void setItemTypeAsPipelineAndClickOk() {
        pipelineConfigPage = createNewItemPage.selectPipelineAndClickOk();
    }

    @And("Save configuration and go to Pipeline project page")
    public void saveConfigAndGoToPipelinePage() {
        pipelineProjectPage = pipelineConfigPage.clickSaveButton();
    }

    @And("Set Item type as Organization Folder, click Ok and go to Configure page")
    public void setItemTypeAsOrganizationFolderAndClickOk() {
        organizationFolderConfigPage = createNewItemPage.selectOrganizationFolderAndClickOk();
    }

    @And("Save configuration and go to Organization Folder page")
    public void saveConfigAndGoToOrganizationFolderPage() {
        organizationFolderProjectPage = organizationFolderConfigPage.clickSaveButton();
    }

    @And("Click '+' to create New View")
    public void clickPlusToCreateNewView() {
        createNewViewPage = homePage.clickPlusToCreateView();
    }

    @And("Type View name {string}")
    public void typeViewName(String name) {
        createNewViewPage = createNewViewPage.setViewName(name);
    }

    @And("Set View type as 'My View'")
    public void setViewTypeAsMyView() {
        createNewViewPage = createNewViewPage.clickMyViewRadioButton();
    }

    @And("Click Create button upon choosing My View")
    public void clickCreateButtonUponChoosingMyView() {
        viewPage = createNewViewPage.clickCreateButtonUponChoosingMyView();
    }

    @Then("Sorting column text is {string}")
    public void assertSortingColumnText(String name) {
        Assert.assertEquals(viewPage.getNameColumnText().replace("\n ", ""), name);
    }

    @Then("Sorted items by name in view list order should be:")
    public void assertProjectNamesListInViewOrder(List<String> expectedSortedItemsByNameList) {
        Assert.assertEquals(viewPage.getProjectNames(), expectedSortedItemsByNameList);
    }
}
