package school.redrover;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class ManageJenkinsTest extends BaseTest {

    private static final List<String> SYSTEM_CONFIGURATION_SECTIONS_NAMES_EXPECTED = List.of("System", "Tools", "Plugins", "Nodes", "Clouds", "Appearance");
    private static final List<String> SECURITY_SECTIONS_NAMES_EXPECTED = List.of("Security", "Credentials", "Credential Providers", "Users", "In-process Script Approval");
    private static final List<String> STATUS_INFORMATION_SECTIONS_NAMES_EXPECTED = List.of("System Information", "System Log", "Load Statistics", "About Jenkins");
    private static final List<String> TROUBLESHOOTING_SECTIONS_NAMES_EXPECTED = List.of("Manage Old Data");
    private static final List<String> TOOLS_AND_ACTIONS_SECTIONS_NAMES_EXPECTED = List.of("Reload Configuration from Disk", "Jenkins CLI", "Script Console", "Prepare for Shutdown");
    private static final List<String> BLOCK_NAMES_EXPECTED = List.of("System Configuration", "Security", "Status Information", "Troubleshooting", "Tools and Actions");
    private static final int SYSTEM_BLOCK = 1;
    private static final int SECURITY_BLOCK= 2;
    private static final int STATUS_BLOCK = 3;
    private static final int TROUBLESHOOTING_BLOCK = 4;
    private static final int TOOLS_BLOCK = 5;

    private void goToManage() {
        getDriver().findElement(By.cssSelector("[href='/manage']")).click();
    }

    private By getBlockLocator(int blockNumber) {
        return By.xpath("//section[contains(@class, 'jenkins-section')][" + blockNumber + "]//div//dt");
    }

    private List<List<String>> getSectionsNamesListExpected() {
        return List.of(SYSTEM_CONFIGURATION_SECTIONS_NAMES_EXPECTED, SECURITY_SECTIONS_NAMES_EXPECTED,
                STATUS_INFORMATION_SECTIONS_NAMES_EXPECTED, TROUBLESHOOTING_SECTIONS_NAMES_EXPECTED,
                TOOLS_AND_ACTIONS_SECTIONS_NAMES_EXPECTED);
    }

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

    @Test
    public void testSectionsNamesVisibility() {
        goToManage();

        List<WebElement> blockNamesActual = getDriver().findElements(By.cssSelector("[class$='section__title']"));

        List<List<WebElement>> sectionListNamesActual = List.of(getDriver().findElements(getBlockLocator(SYSTEM_BLOCK)),
                getDriver().findElements(getBlockLocator(SECURITY_BLOCK)),
                getDriver().findElements(getBlockLocator(STATUS_BLOCK)),
                getDriver().findElements(getBlockLocator(TROUBLESHOOTING_BLOCK)),
                getDriver().findElements(getBlockLocator(TOOLS_BLOCK)));

        for (int i = 0; i < BLOCK_NAMES_EXPECTED.size(); i++) {
            Assert.assertTrue(blockNamesActual.get(i).getText().matches(BLOCK_NAMES_EXPECTED.get(i)));
        }

        for (int i = 0; i < getSectionsNamesListExpected().size(); i++) {
            for (int j = 0; j < getSectionsNamesListExpected().get(i).size(); j++) {
                Assert.assertTrue(sectionListNamesActual.get(i).get(j).getText()
                        .matches(getSectionsNamesListExpected().get(i).get(j)));
            }
        }
    }
}
