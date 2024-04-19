import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NodeDeleteTest extends BaseTest {

    @Test
    public void testNodeDeletion() {

        final String createdNode = "New_Node";

            getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
            getDriver().findElement(By.xpath("//a[@href='computer']")).click();
            getDriver().findElement(By.xpath("//a[@href='new']")).click();
            getDriver().findElement(By.id("name")).sendKeys(createdNode);
            getDriver().findElement(By.xpath("//label[@for='hudson.slaves.DumbSlave']")).click();
            getDriver().findElement(By.name("Submit")).click();
            getDriver().findElement(By.xpath("//button[@formnovalidate='formNoValidate' and @name='Submit']")).click();

            getDriver().findElement(By.xpath("//tr[@id='node_New_Node']//a[text()='New_Node']")).click();
            getDriver().findElement(By.xpath("//a[@data-title='Delete Agent']")).click();
            getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();

           Assert.assertNull(null,createdNode);
        }
    }
