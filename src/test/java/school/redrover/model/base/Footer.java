package school.redrover.model.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import school.redrover.model.AboutJenkinsPage;
import school.redrover.model.ApiPage;

import java.util.ArrayList;
import java.util.List;

public interface Footer {

    default String getVersionOnFooter(WebDriver driver) {

        return driver.findElement(By.cssSelector("[class$=jenkins_ver]"))
                .getText()
                .split(" ")[1];
    }

    default <T extends BasePage> T clickVersion(T page) {
        page.getDriver().findElement(By.cssSelector("[class$=jenkins_ver]")).click();

        return page;
    }

    default ApiPage clickApiLink(WebDriver driver) {
        driver.findElement(By.xpath("//a[@href='api/']")).click();
        return new ApiPage(driver);
    }

    default boolean isDisplayedAboutJenkinsDropdownItem(WebDriver driver) {

        return driver.findElement(By.xpath(
                "//a[@href='/manage/about']")).isDisplayed();
    }

    default boolean isDisplayedInvolvedDropdownItem(WebDriver driver) {

        return driver.findElement(By.xpath(
                "//a[@href='https://www.jenkins.io/participate/']")).isDisplayed();
    }

    default boolean isDisplayedWebsiteDropdownItem(WebDriver driver) {

        return driver.findElement(By.xpath(
                "//a[@href='https://www.jenkins.io/']")).isDisplayed();
    }

    default AboutJenkinsPage selectAboutJenkinsAndClick(WebDriver driver) {
        driver.findElement(By.xpath("//a[@href='/manage/about']")).click();

        return new AboutJenkinsPage(driver);
    }

    default List<String> getVersionDropDownElementsValues(WebDriver driver) {
        List<WebElement> dropDownElements = driver.findElements(By.className("jenkins-dropdown__item"));

        List<String> actualDropDownElementsValues = new ArrayList<>();

        for (WebElement element : dropDownElements) {
            actualDropDownElementsValues.add(element.getDomProperty("innerText"));
        }

        return actualDropDownElementsValues;
    }

}
