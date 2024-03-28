package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordsTest {

    @Test
    public void testTest(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://randomwordgenerator.com/");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        WebElement qty = driver.findElement(By.name("qty"));
        qty.clear();
        qty.sendKeys("35");

        WebElement btn = driver.findElement(By.name("submit"));
        btn.click();

        WebElement ulElement = driver.findElement(By.id("result"));
        List<WebElement> liElements = ulElement.findElements(By.tagName("li"));
        System.out.println();

        List<String> str = new ArrayList<>();

        for (WebElement li : liElements) {
            str.add(li.getText());
        }

        Map<Character, String> longestWords = new HashMap<>();

        for (String word : str) {
            char firstChar = word.charAt(0);
            if (!longestWords.containsKey(firstChar) || word.length() > longestWords.get(firstChar).length()) {
                longestWords.put(firstChar, word);
            }
        }

        System.out.println(longestWords.values());

    }
}
