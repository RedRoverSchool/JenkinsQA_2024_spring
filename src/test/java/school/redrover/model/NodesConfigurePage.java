package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

import java.util.ArrayList;
import java.util.List;

public class NodesConfigurePage extends BasePage<NodesConfigurePage> {

    @FindBy(css = "[tooltip^='Help for feature']")
    private List<WebElement> tooltipsConfigureNodePage;

    public NodesConfigurePage(WebDriver driver) { super(driver); }

    public List<String> getTooltipsConfigureNodePage() {
        List<String> actualList = new ArrayList<>();
        for (WebElement e : tooltipsConfigureNodePage) {
            ((JavascriptExecutor) getDriver()).executeScript("return arguments[0].scrollIntoView({block:'center'});", e);

            new Actions(getDriver())
                    .pause(300)
                    .moveToElement(e)
                    .pause(300)
                    .perform();

            actualList.add(getDriver().findElement(By.cssSelector("[class='tippy-content']")).getText());
        }
        return actualList;
    }
}
