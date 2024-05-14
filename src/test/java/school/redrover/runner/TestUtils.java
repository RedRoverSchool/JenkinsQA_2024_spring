package school.redrover.runner;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.HomePage;
import school.redrover.model.base.BaseConfigPage;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public final class TestUtils {

    public enum ProjectType {
        FREESTYLE_PROJECT("Freestyle project"),
        PIPELINE("Pipeline"),
        MULTI_CONFIGURATION_PROJECT("Multi-configuration project"),
        FOLDER("Folder"),
        MULTIBRANCH_PIPELINE("Multibranch Pipeline"),
        ORGANIZATION_FOLDER("Organization Folder");

        private final String projectTypeName;
        public String getProjectTypeName() {
            return projectTypeName;
        }
        ProjectType(String projectTypeName) {
            this.projectTypeName = projectTypeName;
        }
    }

    public static final By DROPDOWN_DELETE = By.cssSelector("button[href $= '/doDelete']");
    public static final By EMPTY_STATE_BLOCK = By.cssSelector("div.empty-state-block");
    public static final String JOB_XPATH = "//*[text()='%s']";

    public static String getUserID(WebDriver driver) {
        return driver.findElement(By.xpath("//a[contains(@href, 'user')]")).getText();
    }

    public static void createProjectItem(TestUtils.ProjectType projectType, BaseTest baseTest, BaseConfigPage<?> projectConfigPage, String name) {
        WebDriver driver = baseTest.getDriver();

        new HomePage(driver)
                .clickNewItem()
                .setItemName(name)
                .selectProjectTypeAndClickOk(projectType, projectConfigPage)
                .clickSaveButton()
                .clickLogo();
    }

    public static void goToMainPage(WebDriver driver) {
        driver.findElement(By.id("jenkins-name-icon")).click();
    }

    public static String getUniqueName(String value) {
        return value + new SimpleDateFormat("HHmmssSS").format(new Date());
    }

    public static String asURL(String str) {
        return URLEncoder.encode(str.trim(), StandardCharsets.UTF_8)
                .replaceAll("\\+", "%20")
                .replaceAll("%21", "!")
                .replaceAll("%27", "'")
                .replaceAll("%28", "(")
                .replaceAll("%29", ")")
                .replaceAll("%7E", "~");
    }

    public static void returnToDashBoard(BaseTest baseTest) {
        baseTest.getDriver().findElement(By.cssSelector("a#jenkins-home-link")).click();
    }

    public static WebElement getViewItemElement(BaseTest baseTest, String name) {
        return baseTest.getDriver().findElement(By.cssSelector(String.format("td>a[href = 'job/%s/']", asURL(name))));
    }

    public static void clickAtBeginOfElement(BaseTest baseTest, WebElement element) {
        Point itemPoint = baseTest.getWait10().until(ExpectedConditions.elementToBeClickable(element)).getLocation();
        new Actions(baseTest.getDriver())
                .moveToLocation(itemPoint.getX(), itemPoint.getY())
                .click()
                .perform();
    }

    public static void openElementDropdown(BaseTest baseTest, WebElement element) {
        WebElement chevron = element.findElement(By.cssSelector("[class $= 'chevron']"));

        ((JavascriptExecutor) baseTest.getDriver()).executeScript("arguments[0].dispatchEvent(new Event('mouseenter'));", chevron);
        ((JavascriptExecutor) baseTest.getDriver()).executeScript("arguments[0].dispatchEvent(new Event('click'));", chevron);
    }

    public static String randomString() {
        return UUID.randomUUID().toString();
    }

    public static void openJobDropdown(BaseTest baseTest, String jobName) {
        By dropdownChevron = By.xpath("//table//button[@class='jenkins-menu-dropdown-chevron']");

        Actions action = new Actions(baseTest.getDriver());
        baseTest.getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table//a[@href='job/" + jobName + "/']")));
        action.moveToElement(baseTest.getDriver().findElement(
                By.xpath("//table//a[@href='job/" + jobName + "/']"))).perform();

        action.moveToElement(baseTest.getDriver().findElement(dropdownChevron)).perform();
        baseTest.getWait5().until(ExpectedConditions.elementToBeClickable(dropdownChevron));
        int chevronHeight = baseTest.getDriver().findElement(dropdownChevron).getSize().getHeight();
        int chevronWidth = baseTest.getDriver().findElement(dropdownChevron).getSize().getWidth();
        action.moveToElement(baseTest.getDriver().findElement(dropdownChevron), chevronWidth, chevronHeight).click()
                .perform();
    }

    public static void deleteJobViaDropdown(BaseTest baseTest, String jobName) {
        openJobDropdown(baseTest, jobName);

        baseTest.getWait5().until(ExpectedConditions.elementToBeClickable(DROPDOWN_DELETE)).click();

        baseTest.getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();
    }

    public static void addProjectDescription(BaseTest baseTest, String projectName, String description) {
        baseTest.getDriver().findElement(By.cssSelector(String.format("[href = 'job/%s/']", projectName))).click();
        baseTest.getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.id("description-link"))).click();
        baseTest.getDriver().findElement(By.name("description")).sendKeys(description);
        baseTest.getDriver().findElement(By.name("Submit")).click();
    }

    public static List<String> getTexts(List<WebElement> elementList) {
        return elementList.stream().map(WebElement::getText).toList();
    }

    public static void renameItem(BaseTest baseTest, String currentName, String newName) {
        Actions action = new Actions(baseTest.getDriver());
        baseTest.getDriver().findElement(By.linkText(currentName)).click();
        baseTest.getDriver().findElement(By.xpath("//a[contains(., 'Rename')]")).click();
        action.doubleClick(baseTest.getDriver().findElement(By.name("newName"))).perform();
        baseTest.getDriver().findElement(By.name("newName")).sendKeys(newName);
        baseTest.getDriver().findElement(By.name("Submit")).click();
    }

    public static void deleteItem(BaseTest baseTest, String itemName) {
        baseTest.getDriver().findElement(By.linkText(itemName)).click();
        baseTest.getDriver().findElement(By.xpath("//a[contains(., 'Delete')]")).click();
        baseTest.getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();
    }

    public static boolean checkIfProjectIsOnTheBoard(WebDriver driver, String projectName){
        goToMainPage(driver);
        List<WebElement> displayedProjects = driver.findElements(
                By.xpath("//table[@id='projectstatus']//button/preceding-sibling::span"));

        return displayedProjects.stream()
                .anyMatch(el -> el.getText().equals(projectName));
    }

    public static String getFooterVersionText(BaseTest baseTest) {
        return baseTest.getDriver().findElement(By.xpath("//button[@type='button']")).getText();
    }
    
    public static void openPageInNewTab(BaseTest baseTest, String url) {
        baseTest.getDriver().switchTo().newWindow(WindowType.TAB).navigate().to(url);
    }

    public static String getBaseUrl() {
        return ProjectUtils.getUrl();
    }

    public static void resetJenkinsTheme(BaseTest baseTest) {
        new HomePage(baseTest.getDriver())
                .clickManageJenkins()
                .clickAppearanceButton()
                .switchToDefaultTheme()
                .clickLogo();
    }
}
