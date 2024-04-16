package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class CreateNewProjectTest extends BaseTest {

    private static final String PROJECT_NAME = "Project Name";
    private static final String NEW_PROJECT_NAME = "New  Project Name";


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

    private WebElement pipelineProjectButton() {

        return  getDriver().findElement(By.xpath("//span[text()='Pipeline']"));


    }

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




    public void addDescriptionToProject(String description){
        textareaConfiguration().sendKeys(description);

    }

    public void submitConfiguration() {
        buttonSubmitConfiguration().click();
    }

    public  String getNameOfCreatedProject() {

       return   createdProjectName().getText();


    }




    //Tests+
    @Test
    public void testCreateNewFreestyleProject() {
        createNewProject("Freestyle" + PROJECT_NAME);
        chooseTypeOfProject(freestyleProjectButton());
        addDescriptionToProject("Project Freestyle");
        submitConfiguration();

        Assert.assertEquals(getNameOfCreatedProject(), "FreestyleProject Name");

    }
    @Test
    public void testCreateNewPipelineProject() {
        createNewProject("Pipeline" + PROJECT_NAME);
        chooseTypeOfProject(pipelineProjectButton());
        addDescriptionToProject("Project Pipeline");
        submitConfiguration();

        Assert.assertEquals(getNameOfCreatedProject(), "PipelineProject Name");

    }
}
