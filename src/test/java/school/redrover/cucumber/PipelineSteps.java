package school.redrover.cucumber;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import school.redrover.model.CreateNewItemPage;
import school.redrover.model.HomePage;
import school.redrover.model.PipelineConfigPage;
import school.redrover.model.PipelineProjectPage;
import school.redrover.model.ProjectRenamePage;
import school.redrover.model.base.BaseProjectPage;
import school.redrover.runner.CucumberDriver;
import school.redrover.runner.ProjectUtils;

import java.util.List;

public class PipelineSteps {
    private CreateNewItemPage createNewItemPage;
    private PipelineConfigPage pipelineConfigPage;
    private PipelineProjectPage pipelineProjectPage;
    private HomePage homePage;
    private ProjectRenamePage<PipelineProjectPage> projectRenamePage;
    private BaseProjectPage baseProjectPage;

    @When("Go to New Item")
    public void goToNewJob() {
        createNewItemPage = new HomePage(CucumberDriver.getDriver()).clickNewItem();
    }

    @And("Enter job name {string}")
    public void enterJobName(String name) {
        createNewItemPage.typeItemName(name);
    }

    @And("Set Pipeline type and click Ok leading to Configure page")
    public void setJobType() {
        pipelineConfigPage = createNewItemPage.selectPipelineAndClickOk();
    }

    @And("Save configuration leading to Pipeline project page")
    public void saveConfigAndGoToPipelinePage() {
        pipelineProjectPage = pipelineConfigPage.clickSaveButton();
    }

    @And("Return home")
    public void goHome() {
        ProjectUtils.get(CucumberDriver.getDriver());
        homePage = new HomePage(CucumberDriver.getDriver());
    }

    @Then("Pipeline job name {string} is listed on Dashboard")
    public void assertPipelineJobName(String jobName) {
        Assert.assertTrue(homePage.getItemList().contains(jobName));
    }


    @When("Click {string} leading to Pipeline Project page")
    public void clickCreatedPipeline(String jobName) {
        pipelineProjectPage = new HomePage(CucumberDriver.getDriver())
                .clickSpecificPipelineName(jobName);
    }

    @And("Go to Rename on Sidebar and go to Rename page")
    public void clickRenameOnSidebar() {
        projectRenamePage = pipelineProjectPage.clickRenameOnSidebar();
    }

    @And("Clear input field and type {string} in the input field")
    public void clearInputField(String newJobName) {
        projectRenamePage.clearNameInputField();
        projectRenamePage.typeNewName(newJobName);
    }

    @And("Click Rename to confirm and go to Pipeline Project page")
    public void clickRenameToConfirmAndGoToPipelineProjectPage() {
        projectRenamePage.clickRenameButtonWhenRenamedViaSidebar();
    }

    @When("Click on Green build triangle for {string}")
    public void clickOnGreenBuildTriangleForSpecificJob(String jobName) {
        homePage = new HomePage(CucumberDriver.getDriver())
                .clickScheduleBuildForItemAndWaitForBuildSchedulePopUp(jobName);
    }

    @Then("Permalinks build information is displayed")
    public void permalinksInformationIsDisplayed() {
        List<String> permalinkList = pipelineProjectPage.getPermalinkList();
        System.out.println(permalinkList);

        Assert.assertTrue(permalinkList.contains("Last build (#1)"));
    }

    @And("Go to Delete Pipeline on sidebar leading to Delete Dialog")
    public void goToDeletePipelineOnSidebarLeadingToDeleteDialog() {
        baseProjectPage = pipelineProjectPage.clickDeleteOnSidebar();
    }

    @And("Click Yes leading to return home")
    public void clickYes() {
        homePage = baseProjectPage.clickYesWhenDeletedItemOnHomePage();
    }

    @Then("Pipeline job name {string} is not listed on Dashboard")
    public void pipelineJobNameIsNotListedOnDashboard(String jobName) {
        Assert.assertFalse(homePage.isItemExists(jobName));
    }
}


