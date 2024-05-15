package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.NoSuchElementException;
import java.util.UUID;

public class FreestyleProject20Test extends BaseTest {
    final String projectName = "Freestyle-" + UUID.randomUUID();
    final String newProjectName = "New" + projectName;
    final String folderName = "Folder-" + UUID.randomUUID();

    public void createItem(String itemName, String item) {
        getDriver().findElement(By.linkText("New Item")).click();

        getDriver().findElement(By.id("name")).sendKeys(itemName);
        String itemClassName = switch (item) {
            case "Freestyle project" -> "hudson_model_FreeStyleProject";
            case "Folder" -> "com_cloudbees_hudson_plugins_folder_Folder";
            default -> "";
        };
        getDriver().findElement(By.className(itemClassName)).click();
        Assert. assertEquals(getDriver().findElement(By.className(itemClassName)).getAttribute("aria-checked"),
                "true", item + "is not checked");

        getDriver().findElement(By.id("ok-button")).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.name("Submit"))).click();
        Assert.assertTrue(getDriver().getCurrentUrl().contains(itemName),
                item + "is not created");
        getDriver().findElement(By.linkText("Dashboard")).click();
    }

    @Test
    public void testCreateFreestyleProject() {
        createItem(projectName,"Freestyle project");

        getDriver().findElement(By.linkText(projectName)).click();
        Assert.assertEquals(getDriver().findElement(By.cssSelector("#breadcrumbs > li:nth-child(3)")).getText(),
                projectName, "Wrong project is opened");
    }

    @Test(dependsOnMethods = "testCreateFreestyleProject")
    public void testAddDescription() {
        getDriver().findElement(By.linkText("Dashboard")).click();
        getDriver().findElement(By.linkText(projectName)).click();

        getDriver().findElement(By.linkText("Add description")).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.name("description"))).clear();
        getDriver().findElement(By.name("description")).sendKeys("Description for "+projectName);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id='description']/div[1]")).getText(),
                "Description for "+projectName);
    }

    @Test(dependsOnMethods = "testAddDescription")
    public void testRenameProject() {
        getDriver().findElement(By.linkText("Dashboard")).click();
        getDriver().findElement(By.linkText(projectName)).click();

        getDriver().findElement(By.linkText("Rename")).click();
        getDriver().findElement(By.name("newName")).clear();
        getDriver().findElement(By.name("newName")).sendKeys(newProjectName);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.className("job-index-headline")).getText(), newProjectName);
    }

    @Test(dependsOnMethods = "testRenameProject")
    public void testMoveToFolder() {
        getDriver().findElement(By.linkText("Dashboard")).click();
        createItem(folderName,"Folder");
        getDriver().findElement(By.linkText(newProjectName)).click();
        getDriver().findElement(By.linkText("Move")).click();

        Select select = new Select(getDriver().findElement(By.className("select")));
        select.selectByValue("/"+folderName);
        getDriver().findElement(By.name("Submit")).click();
        Assert.assertTrue(getDriver().findElement(By.id("main-panel")).getText().contains(
                "Full project name: "+folderName+"/"+newProjectName));
        getDriver().findElement(By.linkText("Dashboard")).click();

        getDriver().findElement(By.linkText(folderName)).click();
        try {
            getDriver().findElement(By.linkText(newProjectName));
            Assert.assertTrue(true);
        } catch (NoSuchElementException e) {
            Assert.fail("Freestyle project not found in folder");
        }
    }

    @Test(dependsOnMethods = "testMoveToFolder")
    public void testDeleteProject() {
        getDriver().findElement(By.linkText(folderName)).click();
        getDriver().findElement(By.linkText(newProjectName)).click();

        getDriver().findElement(By.linkText("Delete Project")).click();
        getDriver().findElement(By.className("jenkins-!-destructive-color")).click();

        getDriver().get("http://localhost:8080/job/"+folderName+"/job/"+newProjectName);
        Assert.assertEquals(getDriver().findElement(By.cssSelector("#error-description > h2")).getText(), "Not Found");
    }
}
