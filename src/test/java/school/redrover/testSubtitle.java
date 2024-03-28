package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class testSubtitle {
    @Test
    public void testSubtitleSubmitNewLang(){
        WebDriver driver = new ChromeDriver();
        driver.get("http://99-bottles-of-beer.net/");
        WebElement menuSubmitNewLang = driver.findElement(By
                .xpath("//body/div[@id='wrap']/div[@id='navigation']"
                        +"/ul[@id='menu']"
                        +"/li/a[@href='/submitnewlanguage.html']"));
        menuSubmitNewLang.click();

        WebElement subtitleSubmitNewLang = driver.findElement(By
                .xpath("//body/div[@id='wrap']/div[@id='navigation']/"
                        + "ul[@id='submenu']/li/a[@href='./submitnewlanguage.html']"));
        String actualResult = subtitleSubmitNewLang.getText();
        String expectedResult = "Submit New Language";

        driver.quit();
    }
}
