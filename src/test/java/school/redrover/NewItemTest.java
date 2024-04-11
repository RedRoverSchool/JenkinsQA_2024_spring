package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NewItemTest extends BaseTest {

    public WebElement okButton(){
        return getDriver().findElement(By.id("ok-button"));
  };

    public WebElement submitButton(){
        return getDriver().findElement(By.xpath("//button[@name = 'Submit']"));
    }
      @Test
    public void testCreateNewFreestyleProject() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']"))
                .sendKeys("NewFreestyleProject");
        getDriver().findElement(By.xpath("//span[contains(text(),  'Freestyle project')]")).click();
        okButton().click();
        submitButton().click();
        String result = getDriver().findElement(By.xpath("//h1[@class='job-index-headline page-headline']"))
                .getText();

        Assert.assertEquals(result, "NewFreestyleProject");
    }

    @Test
    public void testCreateNewPipeline() {
        getDriver().findElement(By.xpath("//*[@id='tasks']/div[1]/span/a")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']"))
                .sendKeys("NewPipeline");
        getDriver().findElement(By.xpath("//span[contains(text(),  'Pipeline')]")).click();
        okButton().click();
        submitButton().click();
        String result = getDriver().findElement(By.xpath("//h1[@class='job-index-headline page-headline']"))
                .getText();

        Assert.assertEquals(result, "NewPipeline");
    }
    @Test
    public void testCreateMultiConfigurationProject() {
        getDriver().findElement(By.xpath("//*[@id='tasks']/div[1]/span/a")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']"))
                .sendKeys("MultiConfigurationProject");
        getDriver().findElement(By.xpath("//span[contains(text(),  'Multi-configuration project')]")).click();
        okButton().click();
        submitButton().click();
        String result = getDriver().findElement(By.xpath("//h1[@class='matrix-project-headline page-headline']")).getText();

        Assert.assertEquals(result, "Project MultiConfigurationProject");
    }

    @Test
    public void testCreateFolder() {
        getDriver().findElement(By.xpath("//*[@id='tasks']/div[1]/span/a")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']"))
                .sendKeys("Folder");
        getDriver().findElement(By.xpath("//span[contains(text(),  'Folder')]")).click();
        okButton().click();
        submitButton().click();
        String result = getDriver().findElement(By.xpath("//h1[contains (text(), 'Folder')]")).getText();

        Assert.assertEquals(result, "Folder");
    }

    @Test
    public void testCreateMultibranchPipeline() {
        getDriver().findElement(By.xpath("//*[@id='tasks']/div[1]/span/a")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']"))
                .sendKeys("MultibranchPipeline");
        getDriver().findElement(By.xpath("//span[contains(text(),  'Multibranch Pipeline')]")).click();
        okButton().click();
        submitButton().click();
        String result = getDriver().findElement(By.xpath("//h1")).getText();

        Assert.assertEquals(result, "MultibranchPipeline");
    }
    @Test
    public void testOrganizationFolder() {
        getDriver().findElement(By.xpath("//*[@id='tasks']/div[1]/span/a")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']"))
                .sendKeys("OrganizationFolder");
        getDriver().findElement(By.xpath("//span[contains(text(),  'Organization Folder')]")).click();
        okButton().click();
        submitButton().click();
        String result = getDriver().findElement(By.xpath("//h1")).getText();

        Assert.assertEquals(result, "OrganizationFolder");
    }
}
