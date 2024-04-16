package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class SearchBoxTest extends BaseTest {

    private void openDashboard() {
        getDriver().findElement(By.id("jenkins-head-icon")).click();
    }

    public void createFolder(String folderName) {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.name("name")).sendKeys(folderName);
        getDriver().findElement(By.xpath("//label/span[text() ='Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        openDashboard();
    }

    @Test
    public void testFindFolderByOneLetter() {
        final String lowerCaseLetter = "f";
        List<String> folders = new ArrayList<>(List.of("Folder1", "Folder2", "Folder3"));
        folders.forEach(this::createFolder);
        WebElement searchBox = getDriver().findElement(By.id("search-box"));
        searchBox.sendKeys(lowerCaseLetter);
        searchBox.sendKeys(Keys.ENTER);

        WebElement ol = getDriver().findElement(By.xpath("//div[@id='main-panel']//ol"));
        List<WebElement> elements = ol.findElements(By.tagName("li"));
        elements.get(1).findElement(By.tagName("a")).click();
        WebElement actualElement = getDriver().findElement(By.xpath("//h1"));
        String expected = folders.get(0);

        Assert.assertEquals(actualElement.getText(), expected);
        Assert.assertTrue(getDriver().getCurrentUrl().contains(expected));
        Assert.assertTrue(getDriver().getTitle().contains(expected));
    }
}
