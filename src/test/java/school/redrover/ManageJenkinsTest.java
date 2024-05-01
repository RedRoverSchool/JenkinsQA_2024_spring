package school.redrover;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import org.testng.Assert;
import school.redrover.runner.BaseTest;
import java.util.List;

public class ManageJenkinsTest extends BaseTest {

    private boolean areElementsEnabled(List<WebElement> elements) {
        for (WebElement element : elements) {
            return element.isEnabled();
        }
        return false;
    }

    @Test
    public void testRedirectionToSecurityPage() {
        getDriver().findElement(By.cssSelector("[href='/manage']")).click();
        getDriver().findElement(By.cssSelector("[href='configureSecurity']")).click();

        String pageTitle = getDriver().getTitle().split(" ")[0];
        Assert.assertEquals(pageTitle, "Security");
    }

    @Test
    public void testSectionNamesOfSecurityBlock() {
        final List <String> sectionsNamesExpected = List.of("Security", "Credentials", "Credential Providers",
                "Users");

        getDriver().findElement(By.xpath("//a[@href = '/manage']")).click();

        List <WebElement> securityBlockElements = getDriver().findElements(By
                .xpath("//section[contains(@class, 'jenkins-section')][2]//div//dt"));

        for (int i = 0; i < securityBlockElements.size(); i++) {
            Assert.assertTrue(securityBlockElements.get(i).getText().matches(sectionsNamesExpected.get(i)));
        }
    }

    @Test
    public void testSectionDescriptionOfSecurityBlock() {
        final List <String> expectedDescription = List
                .of("Secure Jenkins; define who is allowed to access/use the system.", "Configure credentials",
                        "Configure the credential providers and types",
                        "Create/delete/modify users that can log in to this Jenkins.");

        getDriver().findElement(By.xpath("//a[@href = '/manage']")).click();

        List<WebElement> actualDescription = getDriver().findElements(By
                .xpath("//section[contains(@class, 'jenkins-section')][2]//div//dd[. !='']"));

        for (int i = 0; i < actualDescription.size(); i++) {
            Assert.assertTrue(actualDescription.get(i).getText().matches(expectedDescription.get(i)));
        }
    }
  
    @Test
    public void testSecurityBlockSectionsClickable(){
        getDriver().findElement(By.xpath("//a[@href = '/manage']")).click();

        List <WebElement> securityBlockSections = getDriver().findElements(By
                .xpath("(//div[contains(@class, 'section__items')])[2]/div"));

        for (WebElement element : securityBlockSections) {
            new Actions(getDriver()).moveToElement(element).perform();
            Assert.assertTrue(element.isEnabled());
        }
    }

        @Test
        public void testToolsAndActionsBlockSectionsEnabled() {
            getDriver().findElement(By.cssSelector("[href='/manage']")).click();

            List<WebElement> toolsAndActionsSections = getDriver().findElements(
                    By.xpath("(//div[@class='jenkins-section__items'])[5]/div[contains(@class, 'item')]"));

            Assert.assertTrue(areElementsEnabled(toolsAndActionsSections),
                    "'Tools and Actions' sections are not clickable");
        }
    }
