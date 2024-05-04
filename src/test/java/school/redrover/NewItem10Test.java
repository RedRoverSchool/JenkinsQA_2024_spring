package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NewItem10Test extends BaseTest {

    private Actions actions;
    private Actions getActions(){
        if (actions == null) {
            actions = new Actions(getDriver());
        }
        return actions;
    }

    private final By nameNewItem = By.cssSelector(".job-index-headline.page-headline");
    private final By newNameField = By.xpath("//div[@class = 'setting-main']");

    private final By createJob = By.xpath("//span[text()='Create a job']");


    @Test
    public void createNewItemTest(){
        getDriver().findElement(createJob)
                .click();
        getDriver().findElement(By.className("jenkins-input"))
                .sendKeys("ItemNameIsNewJob90");
        getDriver().findElement(By.className("hudson_model_FreeStyleProject"))
                .click();
        getDriver().findElement(By.id("ok-button"))
                .click();
        getDriver().findElement(By.cssSelector(".jenkins-button.jenkins-button--primary"))
                .click();

        Assert.assertEquals(getDriver().findElement(nameNewItem).getText()
                , "ItemNameIsNewJob90");
    }

    @Test (dependsOnMethods = "createNewItemTest")
    public void renameNewItemTest() {
        getDriver().findElement(By.cssSelector(".jenkins-table__link"))
                .click();
        getDriver().findElement(By.xpath("//div[@id = 'tasks']/div[7]"))
                .click();
        getDriver().findElement(By.xpath("//div[@class = 'setting-main']"))
                .click();
        getActions().doubleClick(getDriver().findElement(newNameField))
                .sendKeys("NewName123")
                .release()
                .perform();
        getActions().click(getDriver().findElement(By.cssSelector(".jenkins-button.jenkins-button--primary ")))
                .release()
                .perform();

        Assert.assertEquals(getDriver().findElement(nameNewItem).getText()
                , "NewName123");
    }

    @Test (dependsOnMethods = "renameNewItemTest")
    public void deleteNewItemTest() {

        getActions().click(getDriver().findElement(By.cssSelector(".jenkins-table__link.model-link.inside")))
                .release()
                .perform();
        getDriver().findElement(By.xpath("//div[@id = 'tasks']/div[6]"))
                .click();
        getDriver().findElement(By.xpath("//button[@data-id = 'ok']"))
                .click();

        Assert.assertTrue(getDriver().findElement(createJob).isDisplayed());


    }
}