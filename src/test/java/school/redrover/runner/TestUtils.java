package school.redrover.runner;

import io.qameta.allure.Step;
import school.redrover.model.HomePage;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public final class TestUtils {

    private TestUtils() {
        throw new UnsupportedOperationException();
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

    @Step("Create the Freestyle project: '{name}'")
    public static void createFreestyleProject(BaseTest baseTest, String name) {
        new HomePage(baseTest.getDriver())
                .clickNewItem()
                .typeItemName(name.trim())
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo();
    }

    @Step("Create the Pipeline project: '{name}'")
    public static void createPipelineProject(BaseTest baseTest, String name) {
        new HomePage(baseTest.getDriver())
                .clickNewItem()
                .typeItemName(name.trim())
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .clickLogo();
    }

    @Step("Create the Multi-configuration project: '{name}'")
    public static void createMultiConfigurationProject(BaseTest baseTest, String name) {
        new HomePage(baseTest.getDriver())
                .clickNewItem()
                .typeItemName(name.trim())
                .selectMultiConfigurationAndClickOk()
                .clickSaveButton()
                .clickLogo();
    }

    @Step("Create the Folder: '{name}'")
    public static void createFolderProject(BaseTest baseTest, String name) {
        new HomePage(baseTest.getDriver())
                .clickNewItem()
                .typeItemName(name.trim())
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickLogo();
    }

    @Step("Create the Multibranch Pipeline: '{name}'")
    public static void createMultibranchProject(BaseTest baseTest, String name) {
        new HomePage(baseTest.getDriver())
                .clickNewItem()
                .typeItemName(name.trim())
                .selectMultibranchPipelineAndClickOk()
                .clickSaveButton()
                .clickLogo();
    }

    @Step("Create the Organization Folder: '{name}'")
    public static void createOrganizationFolderProject(BaseTest baseTest, String name) {
        new HomePage(baseTest.getDriver())
                .clickNewItem()
                .typeItemName(name.trim())
                .selectOrganizationFolderAndClickOk()
                .clickSaveButton()
                .clickLogo();
    }

    public static void resetJenkinsTheme(BaseTest baseTest) {
        new HomePage(baseTest.getDriver())
                .clickManageJenkins()
                .clickAppearanceLink()
                .switchToDefaultTheme()
                .clickLogo();
    }

    @Step("Set insensitiveSearch = {flag} user setting")
    public static void setInsensitiveSearchUserSetting(BaseTest baseTest, boolean flag) {
        new HomePage(baseTest.getDriver())
                .getHeader().goToAdminConfigurePage()
                .turnInsensitiveSearch(flag)
                .clickApplyButton()
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
