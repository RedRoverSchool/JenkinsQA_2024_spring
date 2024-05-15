package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class CredentialProvidersPage extends BasePage {

    @FindBy(tagName = "h1")
    private WebElement pageHeading;

    public CredentialProvidersPage(WebDriver driver) {
        super(driver);
    }

   public String getPageHeading() {
        return pageHeading.getText();
   }
}
