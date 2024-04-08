package school.redrover.old;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by User: Andrey Sereda
 */
public class MailRuTest {

        @Test
        public void testMailRu() throws InterruptedException {
            WebDriver driver = new ChromeDriver();
            driver.get("https://mail.ru//");

            WebElement buttonAccept = driver.findElement(By.xpath("//a[@class='cmpboxbtn cmpboxbtnyes cmptxt_btn_yes']"));
            buttonAccept.click();

            WebElement textBox = driver.findElement(By.xpath("//a[@class='resplash-btn resplash-btn_outlined-themed resplash-btn_mailbox-big cea-fefe-ehxscg']"));
            textBox.sendKeys("kino");

            //  нажимаем и закрываем окно
            WebElement buttonFind = driver.findElement(By.xpath("//button[contains(text(),'Найти')]"));
            buttonFind.click();


            WebElement link = driver.findElement(By.xpath("//div[contains(text(),'92,7')]"));
            link.click();

            WebElement linkOK = driver.findElement(By.xpath("//a[contains(text(),'ОК')]"));
            linkOK.click();

            driver.quit();
        }

}
