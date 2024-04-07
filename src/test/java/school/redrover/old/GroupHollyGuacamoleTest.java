package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.*;

@Ignore
public class GroupHollyGuacamoleTest extends BaseTest {
    private static final String BASE_URL = "https://www.saucedemo.com/";
    private static final String STANDARD_USER = "standard_user";
    private static final String LOCKED_OUT_USER = "locked_out_user";
    private static final String PROBLEM_USER = "problem_user";
    private static final String ERROR_USER = "error_user";
    private static final String VISUAL_USER = "visual_user";
    private static final String PASSWORD = "secret_sauce";
    private static final String CART_PAGE = "//a[@class='shopping_cart_link']";
    private static final String BACKPACK_REMOVE_BUTTON = "//button[@id='remove-sauce-labs-backpack']";

    public void login(String login, String password){
        getDriver().get(BASE_URL);
        getDriver().findElement(By.xpath("//input[@id='user-name']")).sendKeys(login);
        getDriver().findElement(By.xpath("//input[@id='password']")).sendKeys(password);
        getDriver().findElement(By.xpath("//input[@id='login-button']")).click();
    }

    @Test
    public void testLoginSuccess(){
        String expectedResult = BASE_URL + "inventory.html";
        login(STANDARD_USER, PASSWORD);
        String actualResult = getDriver().getCurrentUrl();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testCheckAmountItems() throws InterruptedException {
        int expectedResult = 6;
        login(STANDARD_USER, PASSWORD);
        Thread.sleep(1000);
        List<WebElement> items = getDriver().findElements(By.xpath("//div[@class='inventory_item']"));
        int actualResult = items.size();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testAddItemToTheCard(){
        boolean expectedResult = true;
        login(STANDARD_USER, PASSWORD);
        getDriver().findElement(By.xpath("//button[@id='add-to-cart-sauce-labs-bike-light']")).click();
        getDriver().findElement(By.xpath(CART_PAGE)).click();
        boolean actualResult = getDriver().findElement(By.linkText("Sauce Labs Bike Light")).isDisplayed();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testRemoveItemFromTheCart(){
        login(STANDARD_USER, PASSWORD);
        getDriver().findElement(By.xpath("//button[@id='add-to-cart-sauce-labs-backpack']")).click();
        getDriver().findElement(By.xpath(CART_PAGE)).click();
        getDriver().findElement(By.linkText("Sauce Labs Backpack")).isDisplayed();
        getDriver().findElement(By.xpath(BACKPACK_REMOVE_BUTTON)).click();
        boolean yestCho;
        yestCho = getDriver().findElements(By.xpath(BACKPACK_REMOVE_BUTTON)).isEmpty();

        Assert.assertTrue(yestCho);
    }

    @Test
    public void testLoginSaucedemo () {
        getDriver().get(BASE_URL);

        getDriver().findElement(By.xpath("//input[@data-test='username']")).sendKeys("standard_user");
        getDriver().findElement(By.xpath("//input[@data-test='password']")).sendKeys("secret_sauce");
        getDriver().findElement(By.xpath("//input[@data-test='login-button']")).click();

        String actual = getDriver().findElement(By.xpath("//span[@class='title']")).getText();

        Assert.assertEquals(actual, "Products");
    }

    @Test
    public void testLockedOutUser() {
        String expectedResult = "Epic sadface: Sorry, this user has been locked out.";

        login(LOCKED_OUT_USER, PASSWORD);

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@class='error-message-container error']")).getAttribute("textContent"), expectedResult);
    }

    @Test
    public void testProblemUser() {
        login(PROBLEM_USER, PASSWORD);

        WebElement imageSourceList = getDriver().findElement(By.xpath("//div[@class='inventory_list']/div/div/a/img"));
        String actualResult = imageSourceList.getAttribute("src");

        getDriver().findElement(By.xpath("//div[text()='Sauce Labs Backpack']")).click();
        WebElement imageSourceItem = getDriver().findElement(By.xpath("//img[@alt='Sauce Labs Fleece Jacket']"));
        String expectedResult = imageSourceItem.getAttribute("src");

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testErrorUser() throws InterruptedException {
        login(ERROR_USER, PASSWORD);

        getDriver().findElement(By.xpath("//button[@name='add-to-cart-sauce-labs-backpack']")).click();
        WebElement removeButton = getDriver().findElement(By.xpath("//button[@name='remove-sauce-labs-backpack']"));
        removeButton.getText();

        Thread.sleep(1000);
        removeButton.click();

        Assert.assertEquals(removeButton.getText(), "Add to cart");
    }

    @Test
    public void testVisualUser() {
        String expectedRes = "10px";
        login(VISUAL_USER, PASSWORD);

        WebElement cssAttribute = getDriver().findElement(By.xpath("//div[@id='shopping_cart_container']"));
        String actualRes = cssAttribute.getCssValue("top");

        Assert.assertEquals(actualRes, expectedRes);
    }

    @Test
    public void testProductPrice() {
        getDriver().get("https://academybugs.com/find-bugs/");
        List<WebElement> allProducts = getDriver().findElements(By.xpath("//li[@class='ec_product_li']"));
        for (int i = 0; i < allProducts.size(); i++) {
            String id = allProducts.get(i).getAttribute("id");
            boolean isVisible = allProducts.get(i).findElement(By.xpath("//li[@id='"+ id + "']//span[@class='ec_price_type1']")).isDisplayed();

            Assert.assertTrue(isVisible);
        }
    }

    @Test
    public void testSortZtoA() {
        login(STANDARD_USER, PASSWORD);

        List<WebElement> productsNamesElements = getDriver().findElements(By.xpath("//div[@data-test='inventory-item-name']"));
        List<String> namesText = new ArrayList<>();
        for (int i = 0; i < productsNamesElements.size(); i++) {
            namesText.add(productsNamesElements.get(i).getText());
        }
        Collections.sort(namesText, Collections.reverseOrder());
        List<String> expected = new ArrayList<>(namesText);

        getDriver().findElement(By.xpath("//option[@value='za']")).click();

        productsNamesElements = getDriver().findElements(By.xpath("//div[@data-test='inventory-item-name']"));
        List<String> actualResult = new ArrayList<>();
        for (int i = 0; i < productsNamesElements.size(); i++) {
            actualResult.add(productsNamesElements.get(i).getText());
        }

        Assert.assertEquals(actualResult, expected);
    }

    @Test
    public void testSortPriceLowtoHigh() {
        login(STANDARD_USER, PASSWORD);

        List<WebElement> productsPriceElements = getDriver().findElements(By.xpath("//div[@data-test='inventory-item-price']"));
        List<Double> pricesText = new ArrayList<>();
        for (WebElement priceElement : productsPriceElements) {
            String temp = priceElement.getText().substring(1);
            pricesText.add(Double.parseDouble(temp));
        }
        Collections.sort(pricesText);

        List<String> expected = new ArrayList<>(pricesText.size());
        for (Double element: pricesText ) {
            String temp = "$" + element;
            expected.add(temp);
        }

        getDriver().findElement(By.xpath("//option[@value='lohi']")).click();

        productsPriceElements = getDriver().findElements(By.xpath("//div[@data-test='inventory-item-price']"));
        List<String> actualResult = new ArrayList<>();
        for (int i = 0; i < productsPriceElements.size(); i++) {
            actualResult.add(productsPriceElements.get(i).getText());
        }

        Assert.assertEquals(actualResult, expected);
    }

    @Test
    public void testSortPriceHigToLow() {
        login(STANDARD_USER, PASSWORD);

        List<WebElement> productsPriceElements = getDriver().findElements(By.xpath("//div[@data-test='inventory-item-price']"));
        List<Double> pricesText = new ArrayList<>();
        for (WebElement priceElement : productsPriceElements) {
            String temp = priceElement.getText().substring(1);
            pricesText.add(Double.parseDouble(temp));
        }
        pricesText.sort(Collections.reverseOrder());

        List<String> expected = new ArrayList<>(pricesText.size());
        for (Double element: pricesText ) {
            String temp = "$" + element;
            expected.add(temp);
        }

        getDriver().findElement(By.xpath("//option[@value='hilo']")).click();

        productsPriceElements = getDriver().findElements(By.xpath("//div[@data-test='inventory-item-price']"));
        List<String> actualResult = new ArrayList<>();
        for (int i = 0; i < productsPriceElements.size(); i++) {
            actualResult.add(productsPriceElements.get(i).getText());
        }

        Assert.assertEquals(actualResult, expected);
    }
}
