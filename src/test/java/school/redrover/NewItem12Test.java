package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class NewItem12Test extends BaseTest {
        @Test
        public void CreateNewItem(){

            getDriver().findElement(By.linkText("New Item")).click();
            getDriver().findElement(By.id("main-panel")).click();

            getDriver().findElement(By.id("name")).sendKeys("GBtest");
            getDriver().findElement(By.cssSelector("#j-add-item-type-standalone-projects > ul > li.hudson_matrix_MatrixProject > label > span")).click();
            WebElement okButton = getDriver().findElement(By.id("ok-button"));
            Assert.assertTrue(okButton.isEnabled());
            okButton.click();

            getDriver().findElement(By.cssSelector("button[name='Submit']")).click();

            WebElement projectName1 = getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/h1"));
            Assert.assertEquals(projectName1.getText(), "Project GBtest");

        }
}
