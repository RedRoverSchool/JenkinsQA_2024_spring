package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import java.util.ArrayList;
import java.util.List;

public class OrganizationFolder4Test extends BaseTest {

    public void createOrganizationFolder(String name){
        getDriver().findElement(By.className("task-icon-link")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(name);
        getDriver().findElement(By.className("jenkins_branch_OrganizationFolder")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    private final List<String> getExpectedList = List.of("Back", "Snippet Generator", "Declarative Directive Generator",
            "Declarative Online Documentation", "Steps Reference",
            "Global Variables Reference", "Online Documentation", "Examples Reference",
            "IntelliJ IDEA GDSL");

    private List<String> getActualList(){
        List<String> actualList = new ArrayList<>();
        for (int i=1; i<=9; i++){
            String xPath = "//*[@id=\"tasks\"]/div["+ i + "]/span/a/span[2]";
            actualList.add(getDriver().findElement(By.xpath(xPath)).getText());
        }
        return actualList;
    }

    @Test
    public void testPipelineSyntaxMenuList(){
        String setOrganizationFolder = "TestOrganizationFolder";
        createOrganizationFolder(setOrganizationFolder);

        getDriver().findElement(By.id("jenkins-name-icon")).click();

        WebElement currentOrganizationFolder = getDriver().
                findElement(By.xpath("//span[text()='" + setOrganizationFolder + "']/..")) ;
        new Actions(getDriver()).moveToElement(currentOrganizationFolder).perform();

        WebElement menuForCurrentOrganizationFolder = getDriver().
                findElement(By.xpath("//*[@id='job_"+ setOrganizationFolder + "']/td[3]/a"));
        menuForCurrentOrganizationFolder.click();

        WebElement pipelineSyntaxMenu = getDriver().
                findElement(By.xpath("//*[@href='/job/"+ setOrganizationFolder +"/pipeline-syntax']"));
        pipelineSyntaxMenu.click();

        for (int i=0; i<getActualList().size(); i++){
            Assert.assertEquals(getActualList().get(i), getExpectedList.get(i));
        }
    }
}
