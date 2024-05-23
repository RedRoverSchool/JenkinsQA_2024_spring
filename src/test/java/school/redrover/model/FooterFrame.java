package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import school.redrover.model.base.BaseFrame;
import school.redrover.model.base.BasePage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class FooterFrame extends BaseFrame {

    public FooterFrame(WebDriver driver) {
        super(driver);
    }

    public String getVersionOnFooter() {

        return getDriver().findElement(By.cssSelector("[class$=jenkins_ver]"))
                .getText()
                .split(" ")[1];
    }

//    public <T extends BasePage> T clickVersion(T page) {
//        WebDriverWait wait5 = new WebDriverWait(page.getDriver(), Duration.ofSeconds(5));
//        wait5.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class$=jenkins_ver]"))).click();
//
//        return page;
//    }
//
//    public ApiPage clickApiLink() {
//        getDriver().findElement(By.xpath("//a[@href='api/']")).click();
//        return new ApiPage(getDriver());
//    }
//
//    public boolean isDisplayedAboutJenkinsDropdownItem(WebDriver driver) {
//
//        return driver.findElement(By.xpath(
//                "//a[@href='/manage/about']")).isDisplayed();
//    }
//
//    public boolean isDisplayedInvolvedDropdownItem(WebDriver driver) {
//
//        return driver.findElement(By.xpath(
//                "//a[@href='https://www.jenkins.io/participate/']")).isDisplayed();
//    }
//
//    public boolean isDisplayedWebsiteDropdownItem(WebDriver driver) {
//
//        return driver.findElement(By.xpath(
//                "//a[@href='https://www.jenkins.io/']")).isDisplayed();
//    }
//
//    public AboutJenkinsPage selectAboutJenkinsAndClick(WebDriver driver) {
//        driver.findElement(By.xpath("//a[@href='/manage/about']")).click();
//
//        return new AboutJenkinsPage(driver);
//    }
//
//    public List<String> getVersionDropDownElementsValues(WebDriver driver) {
//        List<WebElement> dropDownElements = driver.findElements(By.className("jenkins-dropdown__item"));
//
//        List<String> actualDropDownElementsValues = new ArrayList<>();
//
//        for (WebElement element : dropDownElements) {
//            actualDropDownElementsValues.add(element.getDomProperty("innerText"));
//        }
//
//        return actualDropDownElementsValues;
//    }

}
