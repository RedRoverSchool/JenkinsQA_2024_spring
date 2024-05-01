package school.redrover;

import org.checkerframework.checker.signature.qual.DotSeparatedIdentifiersOrPrimitiveType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class ManageJenkinsTest extends BaseTest {

    @Test
    public void testRedirectionToSecurityPage() {
        getDriver().findElement(By.cssSelector("[href='/manage']")).click();
        getDriver().findElement(By.cssSelector("[href='configureSecurity']")).click();

        String pageTitle = getDriver().getTitle().split(" ")[0];
        Assert.assertEquals(pageTitle, "Security");
    }

    @Test
    public void testSectionNamesOfSecurityBlock() {
        final List <String> sectionsNamesExpected = List.of("Security", "Manage Credentials", "Configure Credential Providers",
                "Users", "In-process Script Approval");

        getDriver().findElement(By.xpath("//a[@href = '/manage']")).click();

        List <WebElement> securityBlockElements = getDriver().findElements(By
                .xpath("//section[contains(@class, 'jenkins-section')][2]//div//dt"));
        List <String> sectionsNamesActual = new ArrayList<>();

        for (WebElement element : securityBlockElements) {
            sectionsNamesActual.add(getWait2().until(ExpectedConditions.visibilityOf(element)).getText());
        }

        Assert.assertEquals(sectionsNamesActual,sectionsNamesExpected);
    }
}
