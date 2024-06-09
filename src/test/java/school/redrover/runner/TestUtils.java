package school.redrover.runner;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import school.redrover.model.HomePage;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Collections;
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

    @Step("Create the Freestyle project")
    public static HomePage createFreestyleProject(BaseTest baseTest, String name) {
        return new HomePage(baseTest.getDriver())
                .clickNewItem()
                .setItemName(name.trim())
                .selectFreestyleAndClickOk()
                .clickLogo();
    }

    @Step("Create the Pipeline")
    public static HomePage createPipelineProject(BaseTest baseTest, String name) {
        return new HomePage(baseTest.getDriver())
                .clickNewItem()
                .setItemName(name.trim())
                .selectPipelineAndClickOk()
                .clickLogo();
    }

    @Step("Create the Multi-configuration project")
    public static HomePage createMultiConfigurationProject(BaseTest baseTest, String name) {
        return new HomePage(baseTest.getDriver())
                .clickNewItem()
                .setItemName(name.trim())
                .selectMultiConfigurationAndClickOk()
                .clickLogo();
    }

    @Step("Create the Folder")
    public static HomePage createFolderProject(BaseTest baseTest, String name) {
        return new HomePage(baseTest.getDriver())
                .clickNewItem()
                .setItemName(name.trim())
                .selectFolderAndClickOk()
                .clickLogo();
    }

    @Step("Create the Multibranch Pipeline")
    public static HomePage createMultibranchProject(BaseTest baseTest, String name) {
        return new HomePage(baseTest.getDriver())
                .clickNewItem()
                .setItemName(name.trim())
                .selectMultibranchPipelineAndClickOk()
                .clickLogo();
    }

    @Step("Create the Organization Folder")
    public static HomePage createOrganizationFolderProject(BaseTest baseTest, String name) {
        return new HomePage(baseTest.getDriver())
                .clickNewItem()
                .setItemName(name.trim())
                .selectOrganizationFolderAndClickOk()
                .clickLogo();
    }

    public static void openElementDropdown(BaseTest baseTest, WebElement element) {
        WebElement chevron = element.findElement(By.cssSelector("[class $= 'chevron']"));

        ((JavascriptExecutor) baseTest.getDriver()).executeScript("arguments[0].dispatchEvent(new Event('mouseenter'));", chevron);
        ((JavascriptExecutor) baseTest.getDriver()).executeScript("arguments[0].dispatchEvent(new Event('click'));", chevron);
    }

    public static String randomString() {
        return UUID.randomUUID().toString();
    }

    public static List<String> getTexts(List<WebElement> elementList) {
        return elementList.stream().map(WebElement::getText).toList();
    }

    public static void resetJenkinsTheme(BaseTest baseTest) {
        new HomePage(baseTest.getDriver())
                .clickManageJenkins()
                .clickAppearanceButton()
                .switchToDefaultTheme()
                .clickLogo();
    }

    public static List<String> getJobsBeginningFromThisFirstLetters(BaseTest baseTest, String firstLetters) {

        return new HomePage(baseTest.getDriver()).getItemList()
                .stream()
                .filter(el -> el.substring(0, firstLetters.length()).equalsIgnoreCase(firstLetters))
                .toList();
    }

    public static void assertEqualsLists(List<String> actualList, List<String> expectedList) {
        try {
            Assert.assertEquals(actualList, expectedList);
        } catch (AssertionError e) {
            Collections.sort(expectedList);
            Assert.assertEquals(actualList, expectedList,
                    "Actual list is different after sorting expected values alphabetically");
        }
    }

}
