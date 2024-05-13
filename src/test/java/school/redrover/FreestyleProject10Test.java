package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

public class FreestyleProject10Test extends BaseTest {
    private static final String NEW_PROJECT_NAME = "New freestyle project";
    private static final String PROJECT_DESCRIPTION = "This is project description";

    private void createNewFreestyleProject(String projectName) {
        new HomePage(getDriver())
                .clickNewItem()
                .setItemName(projectName)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo();
    }

    @Ignore
    @Test
    public void testAddDescription() {
        createNewFreestyleProject(NEW_PROJECT_NAME);
        getDriver().findElement(By.xpath("//*[@class='jenkins-table__link model-link inside']")).click();
        getDriver().findElement(By.xpath("//*[@id='description-link']")).click();
        getDriver().findElement(By.xpath("//*[@name='description']")).sendKeys(PROJECT_DESCRIPTION);
        getDriver().findElement(By.xpath("//*[@class='jenkins-button jenkins-button--primary ']")).click();

        String descriptionText = getDriver().findElement(By.xpath("//*[@id='description']/div")).getText();
        Assert.assertEquals(descriptionText, PROJECT_DESCRIPTION);
    }
}
