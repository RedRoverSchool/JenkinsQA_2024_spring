package school.redrover;
import school.redrover.runner.BaseTest;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.NoSuchElementException;
import java.util.UUID;

public class FreestyleProjectMoveToFolderTest extends BaseTest {
    @Test
    public void testCreateFreestyleProjectAndMoveToFolder() {
        String projectName = "Freestyle-" + UUID.randomUUID();
        createItem(projectName,"Freestyle project");
        String folderName = "Folder-" + UUID.randomUUID();
        createItem(folderName,"Folder");

        Actions actions = new Actions(getDriver());
        actions.moveToElement(getDriver().findElement(By.cssSelector("#job_"+projectName+" > td:nth-child(3) > a > span"))).build().perform();
        getDriver().findElement(By.cssSelector("button.jenkins-menu-dropdown-chevron[data-href='http://localhost:8080/job/"+projectName+"/']")).click();
        getDriver().findElement(By.xpath("//a[contains(., 'Move')]")).click();

        Select select = new Select(getDriver().findElement(By.className("select")));
        select.selectByValue("/"+folderName);
        getDriver().findElement(By.name("Submit")).click();
        Assert.assertTrue(getDriver().findElement(By.id("main-panel")).getText().contains("Full project name: "+folderName+"/"+projectName));
        getDriver().findElement(By.linkText("Dashboard")).click();

        getDriver().findElement(By.cssSelector("tr[id='job_"+folderName+"'] a")).click();
        try {
            getDriver().findElement(By.cssSelector("tr[id='job_"+projectName+"'] a"));
            Assert.assertTrue(true);
        } catch (NoSuchElementException e) {
            Assert.fail("Freestyle project not found in folder");
        }
    }

    public void createItem(String itemName, String item) {
        try {
            getDriver().findElement(By.linkText("New Item")).click();
        } catch (NoSuchElementException e) {
            getDriver().findElement(By.linkText("Create a job")).click();
        }
        getDriver().findElement(By.id("name")).sendKeys(itemName);
        String itemClassName = switch (item) {
            case "Freestyle project" -> "hudson_model_FreeStyleProject";
            case "Pipeline" -> "org_jenkinsci_plugins_workflow_job_WorkflowJob";
            case "Multi-configuration project" -> "hudson_matrix_MatrixProject";
            case "Folder" -> "com_cloudbees_hudson_plugins_folder_Folder";
            case "Multibranch Pipeline" -> "org_jenkinsci_plugins_workflow_multibranch_WorkflowMultiBranchProject";
            case "Organization Folder" -> "jenkins_branch_OrganizationFolder";
            default -> "";
        };
        getDriver().findElement(By.className(itemClassName)).click();
        Assert. assertEquals(getDriver().findElement(By.className(itemClassName)).getAttribute("aria-checked"),
                "true", item + "is not checked");

        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        Assert.assertEquals(getDriver().getCurrentUrl(), "http://localhost:8080/job/"+itemName+"/",
                item + "is not created");
        getDriver().findElement(By.linkText("Dashboard")).click();
    }
}
