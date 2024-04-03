package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DoradoMainHw15Test {

    @Test
    public void testAskomdchFindJeans() {

        // Start the session
        WebDriver driver = new ChromeDriver();

        // Take action on browser
        driver.get("https://askomdch.com/");
        driver.findElement(By.id("menu-item-1229")).click(); // "Women" menu link

        // Find an element and take action on element
        WebElement searchField = driver.findElement(By.id("woocommerce-product-search-field-0"));
        searchField.sendKeys("jeans");

        // Take action on element
        searchField.sendKeys(Keys.RETURN); // Press the Enter key

        // Request element information. Check the search result - the title text of the search results page
        WebElement SearchResults = driver.findElement(By.className("woocommerce-products-header"));
        String value = SearchResults.getText();
        Assert.assertEquals(value, "Search results: “jeans”");

        // End the session
        driver.quit();
    }

    @Test
    public void testAskomdchLogin() {

        // Start the session
        WebDriver driver = new ChromeDriver();

        // Take action on browser
        driver.get("https://askomdch.com/");
        driver.findElement(By.id("menu-item-1237")).click(); // "Account" menu link

        // Find an element and take action on element
        WebElement usernameOrEmailAddressInput = driver.findElement(By.id("username"));
        usernameOrEmailAddressInput.click();
        usernameOrEmailAddressInput.sendKeys("dorado");

        WebElement passwordInput = driver.findElement(By.id("password"));
        passwordInput.click();
        passwordInput.sendKeys("dorado13");

        WebElement rememberMeCheckbox = driver.findElement(By.id("rememberme"));
        rememberMeCheckbox.click();

        WebElement loginButton = driver.findElement(By.name("login"));
        loginButton.click();

        // Request element information. Check the search result - the title text of the search results page
        WebElement AccountPage = driver.findElement(By.className("has-text-align-center"));
        String findAccountPage = AccountPage.getText();
        Assert.assertEquals(findAccountPage, "Account");

        // End the session
        driver.quit();
    }

}
