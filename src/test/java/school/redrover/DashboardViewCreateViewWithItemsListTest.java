package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class DashboardViewCreateViewWithItemsListTest extends BaseTest {

    @Test
    public void testCreateFolders() {
        final List<String> folders = Arrays.asList("Freestyle", "Maven", "Pipeline", "Folder", "Multibranch", "Organization");

        getDriver().findElement(By.cssSelector("a.model-link")).click();
        getDriver().findElement(By.cssSelector(".jenkins-breadcrumbs a.model-link")).click();
        getDriver().findElement(By.cssSelector("a[href='/me/my-views']")).click();

        folders.forEach(this::createFolder);
        folders.sort(String::compareTo);
        List<WebElement> jobs = getDriver().findElements(By.cssSelector(".jenkins-table__link"));
        IntStream.range(0, jobs.size()).forEach(i -> Assert.assertEquals(jobs.get(i).getText(), folders.get(i)));

        IntStream.range(0, jobs.size()).forEach(i -> createView(folders.get(i)));
        List<WebElement> views = getDriver().findElements(By.cssSelector(".tab"));
        Assert.assertEquals(folders.size(), views.size() - 2);

    }

    private void createView(String folder) {
        getDriver().findElement(By.cssSelector(".addTab")).click();
        getDriver().findElement(By.cssSelector("#name")).sendKeys(folder);
        getDriver().findElement(By.cssSelector("label[for='hudson.model.MyView']")).click();
        getDriver().findElement(By.cssSelector("#ok")).click();
    }

    private void createFolder(String folder) {
        getDriver().findElement(By.cssSelector(".task-icon-link")).click();
        getDriver().findElement(By.id("name")).sendKeys(folder);
        getDriver().findElement(By.cssSelector(".icon-folder")).click();
        getDriver().findElement(By.cssSelector("#ok-button")).click();
        getDriver().findElement(By.cssSelector("#jenkins-name-icon")).click();
    }
}
