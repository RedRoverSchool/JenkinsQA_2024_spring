package school.redrover.runner;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public final class TestUtils {

    public static final String FREESTYLE_PROJECT = "Freestyle project";
    public static final String PIPELINE = "Pipeline";
    public static final String MULTI_CONFIGURATION_PROJECT = "Multi-configuration project";
    public static final String FOLDER = "Folder";
    public static final String MULTIBRANCH_PIPELINE = "Multibranch Pipeline";
    public static final String ORGANIZATION_FOLDER = "Organization Folder";

    public static final By DROPDOWN_DELETE = By.cssSelector("button[href $= '/doDelete']");
    public static final By DROPDOWN_RENAME = By.cssSelector("a[href $= '/confirm-rename']");

    public static final By DIALOG_DEFAULT_BUTTON = By.cssSelector("dialog .jenkins-button--primary");
    public static final By EMPTY_STATE_BLOCK = By.cssSelector("div.empty-state-block");
    public static final String JOB_XPATH = "//*[text()='%s']";

    public static String getUserID(WebDriver driver) {
        return driver.findElement(By.xpath("//a[contains(@href, 'user')]")).getText();
    }

    public static void createItem(String type, String name, WebDriver driver) {
        driver.findElement(By.linkText("New Item")).click();
        driver.findElement(By.id("name")).sendKeys(name);
        driver.findElement(By.xpath("//span[text()='" + type + "']")).click();
        driver.findElement(By.id("ok-button")).click();
        driver.findElement(By.xpath("//button[contains(text(), 'Save')]")).click();
    }

    public static void goToMainPage(WebDriver driver) {
        driver.findElement(By.id("jenkins-name-icon")).click();
    }

    public static class Item {
        public static final String FREESTYLE_PROJECT = "hudson_model_FreeStyleProject";
        public static final String PIPELINE = "org_jenkinsci_plugins_workflow_job_WorkflowJob";
        public static final String MULTI_CONFIGURATION_PROJECT = "hudson_matrix_MatrixProject";
        public static final String FOLDER = "com_cloudbees_hudson_plugins_folder_Folder";
        public static final String MULTI_BRANCH_PIPELINE = "org_jenkinsci_plugins_workflow_multibranch_WorkflowMultiBranchProject";
        public static final String ORGANIZATION_FOLDER = "jenkins_branch_OrganizationFolder";
    }

    public static void sleep(BaseTest baseTest, long seconds) {
        new Actions(baseTest.getDriver()).pause(seconds * 1000).perform();
    }

    public static WebDriverWait getWait15(BaseTest baseTest) {
        return new WebDriverWait(baseTest.getDriver(), Duration.ofSeconds(15));
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

    public static void createNewItem(BaseTest baseTest, String name, String itemClassName) {
        baseTest.getDriver().findElement(By.cssSelector("#side-panel > div > div")).click();
        getWait15(baseTest).until(ExpectedConditions.visibilityOfElementLocated(By.id("name"))).sendKeys(name);
        baseTest.getDriver().findElement(By.className(itemClassName)).click();
        baseTest.getDriver().findElement(By.id("ok-button")).click();
    }

    public static void returnToDashBoard(BaseTest baseTest) {
        baseTest.getDriver().findElement(By.cssSelector("a[href = '/']")).click();
    }

    public static void createNewItemAndReturnToDashboard(BaseTest baseTest, String name, String itemClassName) {
        createNewItem(baseTest, name, itemClassName);
        returnToDashBoard(baseTest);
    }

    public static void openElementDropdown(BaseTest baseTest, WebElement element) {
        WebElement chevron = element.findElement(By.className("jenkins-menu-dropdown-chevron"));

        ((JavascriptExecutor) baseTest.getDriver()).executeScript("arguments[0].dispatchEvent(new Event('mouseenter'));", chevron);
        ((JavascriptExecutor) baseTest.getDriver()).executeScript("arguments[0].dispatchEvent(new Event('click'));", chevron);
    }

    public static void deleteUsingDropdown(BaseTest baseTest, String name) {
        openElementDropdown(baseTest, baseTest.getDriver().findElement(
                By.cssSelector(String.format("td a[href = 'job/%s/']", asURL(name)))));

        baseTest.getDriver().findElement(DROPDOWN_DELETE).click();
        getWait15(baseTest).until(ExpectedConditions.elementToBeClickable(DIALOG_DEFAULT_BUTTON)).click();
    }

    public static void addProjectDescription(BaseTest baseTest, String projectName, String description) {
        baseTest.getDriver().findElement(By.cssSelector(String.format("[href = 'job/%s/']", projectName))).click();
        getWait15(baseTest).until(ExpectedConditions.visibilityOfElementLocated(By.id("description-link"))).click();
        baseTest.getDriver().findElement(By.name("description")).sendKeys(description);
        baseTest.getDriver().findElement(By.name("Submit")).click();
    }

    public static List<String> getTexts(List<WebElement> elementList) {
        return elementList.stream().map(WebElement::getText).toList();
    }

    public static void goToJobPageAndEnterJobName(BaseTest baseTest, String jobName) {
        baseTest.getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        baseTest.getDriver().findElement(By.id("name")).sendKeys(jobName);
    }

    public static void createJob(BaseTest baseTest, Job job, String jobName) {
        goToJobPageAndEnterJobName(baseTest, jobName);
        baseTest.getDriver().findElement(By.xpath(JOB_XPATH.formatted(job))).click();
        baseTest.getDriver().findElement(By.id("ok-button")).click();
    }
    public static void createNewJob(BaseTest baseTest, Job job, String jobName) {
        goToJobPageAndEnterJobName(baseTest, jobName);
        baseTest.getDriver().findElement(By.xpath(JOB_XPATH.formatted(job))).click();
        baseTest.getDriver().findElement(By.id("ok-button")).click();
        baseTest.getDriver().findElement(By.id("jenkins-home-link")).click();

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

    public enum Job {
        FREESTYLE("Freestyle project"),
        PIPELINE("Pipeline"),
        MULTI_CONFIGURATION("Multi-configuration project"),
        FOLDER("Folder"),
        MULTI_BRUNCH_PIPELINE("Multibranch Pipeline"),
        ORGANIZATION_FOLDER("Organization Folder");

        private final String jobName;

        Job(String jobName) {
            this.jobName = jobName;
        }

        @Override
        public String toString() {
            return jobName;
        }
    }
}
