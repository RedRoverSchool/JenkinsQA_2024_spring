package school.redrover.fillFormTest.pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import static school.redrover.fillFormTest.tests.BaseTest.driver;

public class CalendarComponent {
    public void setDate(String year, String mouth, String day) {
        WebElement selectYear = driver.findElement(By.className("react-datepicker__year-select"));
        WebElement selectMonth = driver.findElement(By.className("react-datepicker__month-select"));
        new Select(selectYear).selectByVisibleText(year);
        new Select(selectMonth).selectByVisibleText(mouth);
        String daySelector = String.format(".react-datepicker__day--0%s:not(.react-datepicker__day--outside-month)", day);
        WebElement selectDayElement = driver.findElement(By.cssSelector(daySelector));
        selectDayElement.click();
    }
}
