package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class Folder12Test extends BaseTest {

    private final String renamedFolder = "NewFolderTG";

    @Test
    public void testCreateNewFolder () {
        getDriver().findElement(By.cssSelector("[href$='/newJob']")).click();
        String folderName = "FolderTG";
        getDriver().findElement(By.id("name")).sendKeys(folderName);
        getDriver().findElement(By.cssSelector("div#j-add-item-type-nested-projects > ul > li > div:nth-of-type(2) > img")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("_.displayNameOrNull")).sendKeys(folderName);
        getDriver().findElement(By.name("_.description")).sendKeys(folderName);
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[1]/a")).click();
        WebElement element = getDriver().findElement(By.cssSelector("#job_FolderTG > td:nth-child(3) > a"));

        String resultText = element.getText();
        Assert.assertEquals(resultText, folderName);
    }

    @Test (dependsOnMethods = "testCreateNewFolder")
    public void testRenameFolder () {
        goToFolder("FolderTG");
        clickLinkMenuButton("/job/FolderTG/confirm-rename");
        renameFolder();

        WebElement renamedElement = getDriver().findElement(By.xpath ("//*[@id='job_NewFolderTG']/td[3]/a"));

        String resultText = renamedElement.getText();
        Assert.assertEquals(resultText, renamedFolder);
    }

    @Test (dependsOnMethods = "testRenameFolder")
    public void deleteFolder () {
        goToFolder("NewFolderTG");
        clickJSMenuButton("/job/NewFolderTG/doDelete");

        WebElement deleteContextMenuButton =  getDriver().findElement(By.cssSelector("*[data-id='ok"));
        deleteContextMenuButton.click();

        List<WebElement> foldersName = getDriver().findElements(By.xpath("//*[@id='job_NewFolderTG']/td[3]/a"));
        Assert.assertTrue(foldersName.isEmpty());
    }

    private void renameFolder() {
        getDriver().findElement(By.name("newName")).clear();
        getDriver().findElement(By.name("newName")).sendKeys(renamedFolder);
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[1]/a")).click();
    }

    private void goToFolder(String folder) {
        WebElement chevronElementr = getDriver().findElement(By.xpath("//tr[@id='job_" + folder + "']/td[3]/a/span"));
        chevronElementr.click();
    }

    private void clickLinkMenuButton(String href) {
        WebElement button = getDriver().findElement(By.cssSelector("*[href='"+ href + "']"));
        button.click();
    }

    private void clickJSMenuButton(String href) {
        WebElement button = getDriver().findElement(By.cssSelector("*[data-url='"+ href + "']"));
        button.click();
    }
}

