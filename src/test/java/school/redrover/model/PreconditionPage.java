package school.redrover.model;

import org.openqa.selenium.WebDriver;
import school.redrover.model.base.BasePage;

public class PreconditionPage extends BasePage {

    public PreconditionPage(WebDriver driver) {
        super(driver);
    }

    final HomePage homePage = new HomePage(getDriver());
    final CreateNewItemPage createNewItemPage = new CreateNewItemPage(getDriver());

    public void createFolder(String folderName) {
        homePage.clickNewItemSideMenu();
        createNewItemPage.inputItemName(folderName);
        createNewItemPage.clickFolderRadioButton();
        createNewItemPage.clickOkButton();
    }

}
