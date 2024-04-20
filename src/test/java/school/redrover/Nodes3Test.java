package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

    public class Nodes3Test extends BaseTest {

        private static final By NEW_NODE_NAME = By.xpath("//input[@id='name']");

        @Test
        public void testCreateNewNode() {
            getDriver().findElement(By.xpath("//a[contains(.,'Manage Jenkins')]")).click();

            getDriver().findElement(By.xpath("//dt[.='Nodes']")).click();

            getDriver().findElement(By.cssSelector(".jenkins-button--primary")).click();
            getDriver().findElement(NEW_NODE_NAME).click();
            getDriver().findElement(NEW_NODE_NAME).sendKeys("New Node");

            getDriver().findElement(By.tagName("label")).click();
            getDriver().findElement(By.xpath("//button[@id='ok']")).click();
            getDriver().findElement(By.name("Submit")).click();
            getDriver().findElement(By.xpath("//a[.='Nodes']")).click();
            WebElement newNode = getDriver().findElement(By.xpath("//a[.='New Node']"));

            Assert.assertTrue(newNode.isDisplayed());
        }
    }