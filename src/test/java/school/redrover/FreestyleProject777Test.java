package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject777Test  extends BaseTest{

    //fields
    private static final String PROJECT_NAME = "Project Name";

    // general webElements for this page
    private WebElement newItemButton() {

        return getDriver().findElement(By.xpath("//*[@id='tasks']/div[1]"));
    }
    private WebElement itemName() {

        return getDriver().findElement(By.xpath("//input[@id='name']"));
    }
    private WebElement submitButton() {

        return getDriver().findElement(By.xpath("//button[@id='ok-button']"));
    }
    private  WebElement textareaConfiguration() {

        return  getDriver().findElement(By.xpath("//*[@name='description']"));
    }
    private WebElement buttonSubmitConfiguration() {

        return getDriver().findElement(By.xpath("//button[@name='Submit']"));
    }
    private WebElement freestyleProjectButton() {

        return getDriver().findElement(By.xpath("//span[contains(text(),'Freestyle project')]"));
    };
    private WebElement createdProjectName() {

        return getDriver().findElement(By.xpath("//div[@id='main-panel']/div/div/h1"));
    }
    public WebElement chooseTypeOfProject(WebElement element) {
        element.click();
        submitButton().click();

        return element;
    }

    //Methods
    public void createNewProject(String name) {
        newItemButton().click();
        itemName().sendKeys(name);
    }

    public void submitConfiguration() {
        buttonSubmitConfiguration().click();
    }

    public  String getNameOfCreatedProject() {

        return   createdProjectName().getText();
    }

    //Tests
    @Test
    public void testCreateNewFreestyleProject() {
        createNewProject("Freestyle" + PROJECT_NAME);
        chooseTypeOfProject(freestyleProjectButton());
        submitConfiguration();

        Assert.assertEquals(getNameOfCreatedProject(), "FreestyleProject Name");
    }

}


