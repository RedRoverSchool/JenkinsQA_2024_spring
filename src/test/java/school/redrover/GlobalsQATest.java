package school.redrover;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class GlobalsQATest extends BaseTest {

    private static final String GLOBALS_QA_LOGIN_LINKS = "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login";
    private static final String GLOBALS_QA_STRING_VALUE = "testPV";

    @Test
    public void testCreateUser() {
        WebDriver driver = getDriver();
        driver.get(GLOBALS_QA_LOGIN_LINKS);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        WebElement managerButton = driver.findElement(By.xpath("//button[@ng-click='manager()']"));
        managerButton.click();

        WebElement addCustomerButton = driver.findElement(By.xpath("//button[@ng-click='addCust()']"));
        addCustomerButton.click();

        WebElement boxFNText = driver.findElement(By.xpath("//input[@ng-model='fName']"));
        boxFNText.sendKeys(GLOBALS_QA_STRING_VALUE);

        WebElement boxLNText = driver.findElement(By.xpath("//input[@ng-model='lName']"));
        boxLNText.sendKeys(GLOBALS_QA_STRING_VALUE);

        WebElement boxPCText = driver.findElement(By.xpath("//input[@ng-model='postCd']"));
        boxPCText.sendKeys(GLOBALS_QA_STRING_VALUE);

        WebElement addCustomerSubmitButton = driver.findElement(By.xpath("//button[@type='submit']"));
        addCustomerSubmitButton.click();

        driver.switchTo().alert().accept();

        WebElement goCustomersButton = driver.findElement(By.xpath("//button[@ng-click='showCust()']"));
        goCustomersButton.click();

        WebElement searchInput = driver.findElement(By.xpath("//input[@ng-model='searchCustomer']"));
        searchInput.sendKeys(GLOBALS_QA_STRING_VALUE);

        WebElement searchResultsTable = driver.findElement(By.xpath("//tbody/tr[last()]/td[1]"));
        Assert.assertEquals(searchResultsTable.getText(), GLOBALS_QA_STRING_VALUE);
    }
}