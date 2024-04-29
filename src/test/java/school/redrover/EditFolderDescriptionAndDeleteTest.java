package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;
@Ignore
public class EditFolderDescriptionAndDeleteTest extends BaseTest {
    private final String NAME_FOLDER = "FolderFolder";
    private final String DESCRIPTION_TEXT = "Добавили описание";
    private final String DESCRIPTION_LOCATOR = "description";
    private final String DESCRIPTION_BUTTON = "description-link";
    private final String BUTTON_SAVE = "button[name='Submit']";

    @Test
    public void testAddFolder() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        WebElement nameFolder = getDriver().findElement(By.id("name"));
        nameFolder.sendKeys(NAME_FOLDER);

        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.cssSelector(BUTTON_SAVE)).click();

        String checkNameFolder = getDriver().findElement(By.xpath("/html[1]/body[1]/div[2]/div[2]/h1[1]")).getText();

        Assert.assertEquals(checkNameFolder, NAME_FOLDER);

        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }
    @Ignore
    @Test(dependsOnMethods = "testAddFolder")
    public void testAddDescription() {
        getDriver().findElement(
                By.xpath("//a[@href='job/" + NAME_FOLDER + "/']")).click();
        getDriver().findElement(By.id(DESCRIPTION_BUTTON)).click();
        WebElement descriptionField = getDriver().findElement(By.name(DESCRIPTION_LOCATOR));
        descriptionField.sendKeys(DESCRIPTION_TEXT);

        getDriver().findElement(By.linkText("Preview")).click();
        String descriptionFolder = getDriver().findElement(By.className("textarea-preview")).getText();

        Assert.assertEquals(DESCRIPTION_TEXT, descriptionFolder);
        getDriver().findElement(By.cssSelector(BUTTON_SAVE)).click();

    }

    @Test(dependsOnMethods = "testAddDescription")
    public void testEditDescription() {
        getDriver().findElement(By.id(DESCRIPTION_BUTTON)).click();

        WebElement descriptionField = getDriver().findElement(By.name(DESCRIPTION_LOCATOR));
        descriptionField.clear();
        descriptionField.sendKeys("Изменили описание");
        getDriver().findElement(By.cssSelector(BUTTON_SAVE)).click();

    }

    @Test(dependsOnMethods = "testEditDescription")
    public void testDeleteDescription() {
        getDriver().findElement(By.id(DESCRIPTION_BUTTON)).click();
        getDriver().findElement(By.name(DESCRIPTION_LOCATOR)).clear();
        getDriver().findElement(By.cssSelector(BUTTON_SAVE)).click();
    }

    @Test(dependsOnMethods = "testAddFolder")
    public void testDeleteFolder() {
        getDriver().findElement(
                By.xpath("//table//a[@href='job/" + NAME_FOLDER + "/']")).click();
        getDriver().findElement(By.xpath("//a[@data-title='Delete Folder']")).click();
        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();

        List<WebElement> jobList = getDriver().findElements(
                By.xpath("//table//a[@href='job/" + NAME_FOLDER +"/']"));

        Assert.assertTrue(jobList.isEmpty());
    }

}


