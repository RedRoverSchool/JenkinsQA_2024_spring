package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject2Test extends BaseTest {
    private static final String PROJECT_NAME = "First project";
    private static final String DESCRIPTION = "My first Freestyle Project";

    public void createFreestyleProject(String name) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(name);
        getDriver().findElement(By.xpath("//li[contains(@class, '_FreeStyleProject')]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//a[. = 'Dashboard']")).click();
    }

    @Test
    public void testDescriptionAddedByUsingAddDescriptionButton() {
        createFreestyleProject(PROJECT_NAME);
        getDriver().findElement(By.xpath("//a[.='" + PROJECT_NAME +"']")).click();
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).sendKeys(DESCRIPTION);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.
                xpath("//div[@id='description']/div")).getText(), DESCRIPTION);
    }
}
