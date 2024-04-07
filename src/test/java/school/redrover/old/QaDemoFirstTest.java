package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.util.List;

@Ignore
public class QaDemoFirstTest {

    @Test
    public void testFindElementsOnMainPageAndCheck() {

        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/");

        String titleMain = driver.getTitle();
        Assert.assertEquals(titleMain, "DEMOQA");


        WebElement headerLink = driver.findElement(By.cssSelector("div#app header>a"));
        String hrefValue = headerLink.getAttribute("href");
        Assert.assertEquals(hrefValue, "https://demoqa.com/");


        WebElement headerImg = driver.findElement(By.cssSelector("div#app header>a>img"));
        String srcValue = headerImg.getAttribute("src");
        String fileName = srcValue.substring(srcValue.lastIndexOf("/") + 1);
        Assert.assertEquals(fileName,"Toolsqa.jpg");


//       div.home-banner>a>img[alt="Selenium Online Training"] проверить переход на страницу,
//       заголовок
//       div.home-body>div.category-cards как проверить длину списка и входящие элементы?
        List<WebElement> listOfCards = driver.findElements(By.cssSelector("div.card-body"));
        Assert.assertEquals(listOfCards.size(), 6);
        System.out.println(listOfCards.size());

// Как проверить имя каждого элемента без копирования текст? Сравнить 2 списка в цикле?
        Assert.assertEquals(listOfCards.get(0).getText(), "Elements");
        System.out.println(listOfCards.get(0).getText());

        Assert.assertEquals(listOfCards.get(1).getText(), "Forms");
        System.out.println(listOfCards.get(1).getText());


        WebElement firstLabelElement = driver.findElement(By.cssSelector("div.card:first-child"));
        firstLabelElement.click();

        String titleMainElements = driver.getTitle();
        Assert.assertEquals(titleMainElements, "DEMOQA");

        driver.quit();

    }

}
