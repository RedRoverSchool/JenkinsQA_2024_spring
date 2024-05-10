package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.ItemPage;
import school.redrover.runner.BaseTest;

public class NewItemTest extends BaseTest {

    public WebElement okButton() {
        return getDriver().findElement(By.id("ok-button"));
    }

    public WebElement submitButton(){
        return getDriver().findElement(By.xpath("//button[@name = 'Submit']"));
    }

    @Test
    public void testAddItem() {
        new ItemPage(getDriver())
                .NewItemClick()
                .FreestyleProjectClick()
                .NewItemName("NewItemName")
                .clickButtonOK()
                .clickLogo();

        Assert.assertEquals(getDriver().findElement(By.xpath("//span[contains(.,'NewItemName')]")).getText(), "NewItemName");
    }

    @Test
    public void testGoToNewJobPage() {
        new ItemPage(getDriver())
                .NewItemClick();

        Assert.assertTrue(getDriver().findElement(By.xpath("//*[text()='Enter an item name']")).isDisplayed());
    }

    @Test
    public void testCreateFreestyleProject() {
        new ItemPage(getDriver())
                .NewItemClick()
                .NewItemName("MyNewProject")
                .FreestyleProjectClick()
                .clickButtonOK()
                .clickSaveBtn();

        Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(), "MyNewProject");
    }

    @Test
    public void testCreateNewPipeline() {
        new ItemPage(getDriver())
                .NewItemClick()
                .NewItemName("NewPipeline")
                .PipelineClic()
                .clickButtonOK()
                .clickSaveBtn();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1[@class='job-index-headline page-headline']"))
                .getText(), "NewPipeline");
    }

    @Ignore
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

    @Ignore
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

    @Ignore
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

    @Ignore
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
    @Test
    public void testCheckPage() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']"))
                .sendKeys("Test Project");
        boolean okButtonIsEnable = getDriver().findElement(By.xpath("//button[@id='ok-button']")).isEnabled();
        Assert.assertFalse(okButtonIsEnable);


    }
}