package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.Random;


public class MulticonfigurationProject1Test extends BaseTest {
    final String projectName = "NewMulticonfigurationProject";

    private void createMulticonfigurationProject(){
        getDriver().findElement(By.xpath("//*[@href='newJob']")).click();
        getDriver().findElement(By.xpath("//*[@class='jenkins-input']")).sendKeys(projectName);
        getDriver().findElement(By.xpath("//*[@class='hudson_matrix_MatrixProject']")).click();
        getDriver().findElement(By.xpath("//*[@type='submit']")).click();
    }

    private String generateRandomNumber(){
        Random r = new Random();
        int randomNumber = r.nextInt(100) + 1;
        return String.valueOf(randomNumber);
    }

    @Test
    public void testSearchForCreatedProject(){
        createMulticonfigurationProject();

        getDriver().findElement(By.id("jenkins-name-icon")).click();
        getDriver().findElement(By.id("search-box")).sendKeys(projectName);
        getDriver().findElement(By.id("search-box")).sendKeys(Keys.ENTER);

        Assert.assertTrue(getDriver().getCurrentUrl().contains("/job/" + projectName + "/"));
    }

    @Test
    public void testAddConfigurationsToProject(){
        final String daysToKeep = generateRandomNumber();
        final String numToKeep = generateRandomNumber();
        final String artifactDaysToKeep = generateRandomNumber();
        final String artifactNumToKeep = generateRandomNumber();

        createMulticonfigurationProject();

        getDriver().findElement(By.xpath("//label[text()='Discard old builds']")).click();
        getDriver().findElement(By.xpath("//*[@name='_.daysToKeepStr']")).sendKeys(daysToKeep);
        getDriver().findElement(By.xpath("//*[@name='_.numToKeepStr']")).sendKeys(numToKeep);

        getDriver().findElement(By.xpath("(//*[@class='jenkins-button advanced-button advancedButton'])[1]")).click();
        getDriver().findElement(By.xpath("//*[@name='_.artifactDaysToKeepStr']")).sendKeys(artifactDaysToKeep);
        getDriver().findElement(By.xpath("//*[@name='_.artifactNumToKeepStr']")).sendKeys(artifactNumToKeep);

        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.xpath("//*[@href='/job/NewMulticonfigurationProject/configure']")).click();
        getDriver().findElement(By.xpath("(//*[@class='jenkins-button advanced-button advancedButton'])[1]")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//*[@name='_.daysToKeepStr']")).getAttribute("value"), daysToKeep);
        Assert.assertEquals(
                getDriver().findElement(By.xpath("//*[@name='_.numToKeepStr']")).getAttribute("value"), numToKeep);
        Assert.assertEquals(
                getDriver().findElement(By.xpath("//*[@name='_.artifactDaysToKeepStr']")).getAttribute("Value"), artifactDaysToKeep);
        Assert.assertEquals(
                getDriver().findElement(By.xpath("//*[@name='_.artifactNumToKeepStr']")).getAttribute("Value"), artifactNumToKeep);
    }
}
