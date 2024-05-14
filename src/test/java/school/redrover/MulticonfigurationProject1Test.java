package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.Random;


public class MulticonfigurationProject1Test extends BaseTest {
    final String PROJECT_NAME = generateRandomText(20);
    final String FOLDER_NAME = generateRandomText(10);

    private void createMulticonfigurationProject(){
        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//*[@class='jenkins-input']")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.xpath("//*[@class='hudson_matrix_MatrixProject']")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    private void createFolder(){
        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(FOLDER_NAME);
        getDriver().findElement(By.xpath("//*[@class='com_cloudbees_hudson_plugins_folder_Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    private String generateRandomNumber(){
        Random r = new Random();
        int randomNumber = r.nextInt(100) + 1;
        return String.valueOf(randomNumber);
    }

    private String generateRandomText(int length){
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();
    }

    @Test
    public void testMoveProjectToFolderFromDashboardPage(){
        createFolder();
        TestUtils.returnToDashBoard(this);

        createMulticonfigurationProject();
        TestUtils.returnToDashBoard(this);

        getDriver().findElement(By.xpath("//*[@href='job/" + PROJECT_NAME + "/']")).click();
        getDriver().findElement(By.linkText("Move")).click();

        final WebElement selectFolder = getDriver().findElement(By.xpath("//*[@class='select setting-input']"));
        Select dropDown = new Select(selectFolder);
        dropDown.selectByValue("/" + FOLDER_NAME);
        getDriver().findElement(By.name("Submit")).click();

        TestUtils.returnToDashBoard(this);
        getDriver().findElement(By.xpath("//*[@href='job/" + FOLDER_NAME + "/']/span")).click();

        Assert.assertTrue(getDriver().findElement(By.id("job_" + PROJECT_NAME)).isDisplayed());
    }
}
