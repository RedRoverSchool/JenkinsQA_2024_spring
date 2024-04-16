package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.runner.BaseTest;

public class CreatePipelineTest extends BaseTest {
    @FindBy
    WebElement new_item = getDriver().findElement(By.xpath("//span[text()='New Item']"));


    public  void createNewItem() {
        new_item.click();


    }

}
