package school.redrover.runner;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.HomePage;

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

    public static class Item {
        public static final String FREESTYLE_PROJECT = "hudson_model_FreeStyleProject";
        public static final String PIPELINE = "org_jenkinsci_plugins_workflow_job_WorkflowJob";
        public static final String MULTI_CONFIGURATION_PROJECT = "hudson_matrix_MatrixProject";
        public static final String FOLDER = "com_cloudbees_hudson_plugins_folder_Folder";
        public static final String MULTI_BRANCH_PIPELINE = "org_jenkinsci_plugins_workflow_multibranch_WorkflowMultiBranchProject";
        public static final String ORGANIZATION_FOLDER = "jenkins_branch_OrganizationFolder";
    }

    public static final String PIPELINE = "Pipeline";

    public static final By DROPDOWN_DELETE = By.cssSelector("button[href $= '/doDelete']");
    public static final By EMPTY_STATE_BLOCK = By.cssSelector("div.empty-state-block");

    public static final String JOB_XPATH = "//*[text()='%s']";

    public static void createItem(String type, String name, BaseTest baseTest) {
        baseTest.getDriver().findElement(By.linkText("New Item")).click();
        baseTest.getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.id("name"))).sendKeys(name);
        baseTest.getDriver().findElement(By.xpath("//span[text()='" + type + "']")).click();
        baseTest.getDriver().findElement(By.id("ok-button")).click();
        baseTest.getDriver().findElement(By.xpath("//button[contains(text(), 'Save')]")).click();
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

    public static HomePage createNewItem(BaseTest baseTest, String projectName, String itemClassName) {
        switch (itemClassName) {
            case Item.FREESTYLE_PROJECT -> {
                return createFreestyleProject(baseTest, projectName);
            }
            case Item.PIPELINE -> {
                return createPipelineProject(baseTest, projectName);
            }
            case Item.MULTI_CONFIGURATION_PROJECT -> {
                return createMultiConfigurationProject(baseTest, projectName);
            }
            case Item.FOLDER -> {
                return createFolderProject(baseTest, projectName);
            }
            case Item.MULTI_BRANCH_PIPELINE -> {
                return createMultibranchProject(baseTest, projectName);
            }
            case Item.ORGANIZATION_FOLDER -> {
                return createOrganizationFolderProject(baseTest, projectName);
            }
            default -> throw new IllegalArgumentException("Project type name incorrect");
        }
    }

    public static HomePage createFreestyleProject(BaseTest baseTest, String name) {
        return new HomePage(baseTest.getDriver())
                .clickNewItem()
                .setItemName(name.trim())
                .selectFreestyleAndClickOk()
                .clickLogo();
    }

    public static HomePage createPipelineProject(BaseTest baseTest, String name) {
        return new HomePage(baseTest.getDriver())
                .clickNewItem()
                .setItemName(name.trim())
                .selectPipelineAndClickOk()
                .clickLogo();
    }

    public static HomePage createMultiConfigurationProject(BaseTest baseTest, String name) {
        return new HomePage(baseTest.getDriver())
                .clickNewItem()
                .setItemName(name.trim())
                .selectMultiConfigurationAndClickOk()
                .clickLogo();
    }

    public static HomePage createFolderProject(BaseTest baseTest, String name) {
        return new HomePage(baseTest.getDriver())
                .clickNewItem()
                .setItemName(name.trim())
                .selectFolderAndClickOk()
                .clickLogo();
    }

    public static HomePage createMultibranchProject(BaseTest baseTest, String name) {
        return new HomePage(baseTest.getDriver())
                .clickNewItem()
                .setItemName(name.trim())
                .selectMultibranchPipelineAndClickOk()
                .clickLogo();
    }

    public static HomePage createOrganizationFolderProject(BaseTest baseTest, String name) {
        return new HomePage(baseTest.getDriver())
                .clickNewItem()
                .setItemName(name.trim())
                .selectOrganizationFolderAndClickOk()
                .clickLogo();
    }

    public static void returnToDashBoard(BaseTest baseTest) {
        baseTest.getDriver().findElement(By.cssSelector("a#jenkins-home-link")).click();
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
        createJob(baseTest, job, jobName);
        goToMainPage(baseTest.getDriver());
    }

    public static boolean checkIfProjectIsOnTheBoard(WebDriver driver, String projectName){
        goToMainPage(driver);
        List<WebElement> displayedProjects = driver.findElements(
                By.xpath("//table[@id='projectstatus']//button/preceding-sibling::span"));

        return displayedProjects.stream()
                .anyMatch(el -> el.getText().equals(projectName));
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
