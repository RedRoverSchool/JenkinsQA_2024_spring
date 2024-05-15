package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.model.OrganizationFolderProjectPage;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class OrganizationFolderTest extends BaseTest {

    private static final String ORGANIZATION_FOLDER_NAME = "OrganizationFolder";

    private static final String ORGANIZATION_FOLDER_DESCRIPTION = "Some description of the organization folder.";

    private List<String> getActualList(){
        List<String> actualList = new ArrayList<>();
        for (int i=1; i<=9; i++){
            String xPath = "//*[@id=\"tasks\"]/div["+ i + "]/span/a/span[2]";
            actualList.add(getDriver().findElement(By.xpath(xPath)).getText());
        }
        return actualList;
    }

    @Test
    public void testCreateViaNewItem() {
        String getItemPageHeading = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(ORGANIZATION_FOLDER_NAME)
                .selectOrganizationFolderAndClickOk()
                .clickSaveButton()
                .getProjectName();

        Assert.assertEquals(getItemPageHeading, ORGANIZATION_FOLDER_NAME);
    }

    @Test
    public void testCreateWithDefaultIcon() {
        String organizationFolderIcon = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(ORGANIZATION_FOLDER_NAME)
                .selectOrganizationFolderAndClickOk()
                .selectDefaultIcon()
                .clickSaveButton()
                .getOrganizationFolderIcon();

        Assert.assertEquals(organizationFolderIcon, "Folder");
    }

    @Test(dependsOnMethods = "testCreateViaNewItem")
    public void testAddDescription(){

        String textInDescription = new OrganizationFolderProjectPage(getDriver())
                .clickAddOrEditDescription()
                .setDescription(ORGANIZATION_FOLDER_DESCRIPTION)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(textInDescription, ORGANIZATION_FOLDER_DESCRIPTION);
    }

    @Test
    public void testPipelineSyntaxDocumentationAccess() {
        String pageTitle = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(ORGANIZATION_FOLDER_NAME)
                .selectOrganizationFolderAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .chooseOrganizationFolder(ORGANIZATION_FOLDER_NAME)
                .clickPipelineSyntax()
                .clickOnlineDocumentation()
                .getPipelineSyntaxTitle();

        Assert.assertTrue(getDriver().getCurrentUrl().contains("/pipeline/"));
        Assert.assertEquals(pageTitle, "Pipeline Syntax");
    }

    @Test
    public void testPipelineSyntaxExamplesAccess() {
        String pageTitle = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(ORGANIZATION_FOLDER_NAME)
                .selectOrganizationFolderAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .chooseOrganizationFolder(ORGANIZATION_FOLDER_NAME)
                .clickPipelineSyntax()
                .clickExamplesReference()
                .getPipelineExamplesTitle();

        Assert.assertTrue(getDriver().getCurrentUrl().contains("/examples/"));
        Assert.assertEquals(pageTitle, "Pipeline Examples");
    }

    @Test(dependsOnMethods = "testCreateViaNewItem")
    public void testCatchErrorStepTooltipsViaDashboardDropdown() {
        final List<String> expectedTooltipList = List.of(
                "Help for feature: catchError",
                "Help for feature: Message",
                "Help for feature: Build result on error",
                "Help for feature: Stage result on error",
                "Help for feature: Catch Pipeline interruptions");

        List<String> actualTooltipList = new HomePage(getDriver())
                .openItemDropdownWithSelenium(ORGANIZATION_FOLDER_NAME)
                .openItemPipelineSyntaxFromDropdown()
                .selectCatchError()
                .getCatchErrorTooltipList();

        Assert.assertEquals(actualTooltipList, expectedTooltipList);
    }

    @Test(dependsOnMethods = "testCreateViaNewItem")
    public void testSidebarMenuVisibility() {
        boolean isSidebarVisible = new HomePage(getDriver())
                .chooseOrganizationFolder(ORGANIZATION_FOLDER_NAME)
                .clickConfigure()
                .isSidebarVisible();

        Assert.assertTrue(isSidebarVisible);
    }

    @Test
    public void testRenameOrganizationFolder() {
        final String newOrganizationFolderName = "New Organization Folder";

        List<String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(ORGANIZATION_FOLDER_NAME)
                .selectOrganizationFolderAndClickOk()
                .clickSaveButton()
                .clickOnRenameButton()
                .setNewName(newOrganizationFolderName)
                .clickRename()
                .clickLogo()
                .getItemList();

        Assert.assertTrue(itemList.contains(newOrganizationFolderName));
    }

    @Test(dependsOnMethods = "testCreateViaNewItem")
    public void testScanOrganizationFolder(){
        getDriver().findElement(By.xpath(
                "//*[@href='job/" + ORGANIZATION_FOLDER_NAME + "/']/span")).click();
        getDriver().findElement(By.xpath("//*[contains(@href,'console')]")).click();

        Assert.assertNotNull(getDriver().findElements(By.xpath(
                "//*[contains(@class,'lds-ellipsis')]/div[1]")));
    }

    @Test(dependsOnMethods = "testCreateViaNewItem")
    public void testFindOrganizationFolderOnDashboard(){
        HomePage homePage = new HomePage(getDriver());

        Assert.assertListContainsObject(homePage.getItemList(), ORGANIZATION_FOLDER_NAME,
                ORGANIZATION_FOLDER_NAME + "is not on the dashboard");
    }

    @Test(dependsOnMethods = "testCreateViaNewItem")
    public void testPipelineSyntaxMenuList(){
        final List<String> getExpectedList = List.of("Back", "Snippet Generator", "Declarative Directive Generator",
                "Declarative Online Documentation", "Steps Reference",
                "Global Variables Reference", "Online Documentation", "Examples Reference",
                "IntelliJ IDEA GDSL");

        WebElement currentOrganizationFolder = getDriver().
                findElement(By.xpath("//span[text()='" + ORGANIZATION_FOLDER_NAME + "']/..")) ;
        new Actions(getDriver()).moveToElement(currentOrganizationFolder).perform();

        WebElement menuForCurrentOrganizationFolder = getDriver().
                findElement(By.xpath("//*[@id='job_"+ ORGANIZATION_FOLDER_NAME + "']/td[3]/a"));
        menuForCurrentOrganizationFolder.click();

        WebElement pipelineSyntaxMenu = getDriver().
                findElement(By.xpath("//*[@href='/job/"+ ORGANIZATION_FOLDER_NAME +"/pipeline-syntax']"));
        pipelineSyntaxMenu.click();

        for (int i=0; i<getActualList().size(); i++){
            Assert.assertEquals(getActualList().get(i), getExpectedList.get(i));
        }
    }

    @Test(dependsOnMethods = "testCreateViaNewItem")
    public void testViewEmptyOrganizationFolderEvents() {

        getDriver().findElement(By.linkText(ORGANIZATION_FOLDER_NAME)).click();
        getDriver().findElement(By.linkText("Organization Folder Events")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//*[@id='out']/div")).getText()
                .matches("No events as of.+waiting for events\\.{3}"), "Messages not equals!");
    }
}
