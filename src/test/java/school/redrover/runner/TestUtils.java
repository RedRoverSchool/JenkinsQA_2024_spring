package school.redrover.runner;

import io.qameta.allure.Step;
import school.redrover.model.HomePage;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

public final class TestUtils {

    public static class Item {
        public static final String FREESTYLE_PROJECT = "hudson_model_FreeStyleProject";
        public static final String PIPELINE = "org_jenkinsci_plugins_workflow_job_WorkflowJob";
        public static final String MULTI_CONFIGURATION_PROJECT = "hudson_matrix_MatrixProject";
        public static final String FOLDER = "com_cloudbees_hudson_plugins_folder_Folder";
        public static final String MULTI_BRANCH_PIPELINE = "org_jenkinsci_plugins_workflow_multibranch_WorkflowMultiBranchProject";
        public static final String ORGANIZATION_FOLDER = "jenkins_branch_OrganizationFolder";
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

    public static String randomString() {
        return UUID.randomUUID().toString();
    }

    public static void resetJenkinsTheme(BaseTest baseTest) {
        new HomePage(baseTest.getDriver())
                .clickManageJenkins()
                .clickAppearanceButton()
                .switchToDefaultTheme()
                .clickLogo();
    }

    public static List<String> getJobsBeginningFromThisFirstLetters(BaseTest baseTest, String firstLetters) {
        return new HomePage(baseTest.getDriver())
                .getItemList()
                .stream()
                .filter(el -> el.substring(0, firstLetters.length()).equalsIgnoreCase(firstLetters))
                .toList();
    }
}
