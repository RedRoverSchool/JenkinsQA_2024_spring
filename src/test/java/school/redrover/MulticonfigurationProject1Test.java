package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.Random;


public class MulticonfigurationProject1Test extends BaseTest {
    final String PROJECT_NAME = generateRandomText(20);

    private void createMulticonfigurationProject(){
        getDriver().findElement(By.xpath("//*[@href='newJob']")).click();
        getDriver().findElement(By.xpath("//*[@class='jenkins-input']")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.xpath("//*[@class='hudson_matrix_MatrixProject']")).click();
        getDriver().findElement(By.xpath("//*[@type='submit']")).click();
    }

    private String generateRandomNumber(){
        Random r = new Random();
        int randomNumber = r.nextInt(100) + 1;
        return String.valueOf(randomNumber);
    }

    private String generateRandomText(int length){
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();
    }

    @Test
    public void testSearchForCreatedProject(){
        createMulticonfigurationProject();

        getDriver().findElement(By.id("jenkins-name-icon")).click();
        getDriver().findElement(By.id("search-box")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.id("search-box")).sendKeys(Keys.ENTER);

        Assert.assertTrue(getDriver().getCurrentUrl().contains("/job/" + PROJECT_NAME + "/"));
    }

    @Test
    public void testAddDiscardOldBuildsConfigurationsToProject(){
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

        getDriver().findElement(By.xpath("//*[@href='/job/" + PROJECT_NAME + "/configure']")).click();
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

    @Test
    public void testAddDescriptionToMulticonfigurationProject(){
        final String randomText = generateRandomText(100);
        createMulticonfigurationProject();

        getDriver().findElement(By.xpath("//*[@href='/job/" + PROJECT_NAME + "/']")).click();
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.cssSelector(".jenkins-input   ")).sendKeys(randomText);
        getDriver().findElement(By.xpath("//*[@class='jenkins-button jenkins-button--primary ']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("(//*[@id='description']/div)[1]")).getText(), randomText);
    }

    @Test
    public void testVerifyThatDisabledIconIsDisplayedOnDashboard(){
        createMulticonfigurationProject();

        getDriver().findElement(By.xpath("//*[@href='/job/" + PROJECT_NAME + "/']")).click();
        getDriver().findElement(By.xpath("//*[@id=\"disable-project\"]/button")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//*[@tooltip='Disabled']")).isDisplayed());
    }
}
