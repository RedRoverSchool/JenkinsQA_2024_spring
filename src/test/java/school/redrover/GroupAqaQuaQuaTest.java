package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
public class GroupAqaQuaQuaTest extends BaseTest {

    @Test
    public void testyAddingBookToCart() {
        getDriver().get("https://demowebshop.tricentis.com/");


        WebElement books = getDriver().findElement(By.xpath("//ul[@class='top-menu']//a[@href='/books']"));
        books.click();

        WebElement myBook = getDriver().findElement(By.xpath("//div[@class = 'details']//a[@href= '/computing-and-internet']"));
        myBook.click();

        WebElement addMyBook = getDriver().findElement(By.id("add-to-cart-button-13"));
        addMyBook.click();

        WebElement shoppingCart = getDriver().findElement(By.className("cart-label"));
        shoppingCart.click();


        Assert.assertEquals(getDriver().findElement(By.className("product-name")).getText(), "Computing and Internet");

    }

    @Test
    public void testDropDownMenuGiftCards() {
        getDriver().get("https://demowebshop.tricentis.com/");

        WebElement elementDropDownMenu = getDriver().findElement(
                By.xpath("//a[@href='/gift-cards']"));
        elementDropDownMenu.click();

        Assert.assertEquals(getDriver().getTitle(), "Demo Web Shop. Gift Cards");

        WebElement menuOnPageGiftCards = getDriver().findElement((
                By.cssSelector("div.page.category-page>div.page-title>h1")
                ));
        Assert.assertEquals(menuOnPageGiftCards.getText(), "Gift Cards");
    }
    @Test
    public void testLoginTheInternet() {
        getDriver().get("https://the-internet.herokuapp.com/");

        Assert.assertEquals(getDriver().getTitle(), "The Internet");

        getDriver().findElement(By.linkText("Form Authentication")).click();

        getDriver().findElement(By.id("username")).sendKeys("tomsmith");
        getDriver().findElement(By.id("password")).sendKeys("SuperSecretPassword!");
        getDriver().findElement(By.cssSelector("button")).click();

        Assert.assertEquals(getDriver().findElement(By.tagName("h2")).getText(), "Secure Area");
    }
    @Test
    public void testRegistration() {
        getDriver().get("https://demowebshop.tricentis.com/");

        Assert.assertEquals(getDriver().getTitle(), "Demo Web Shop");

        getDriver().findElement(By.className("ico-register")).click();

        Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(), "Register");

        getDriver().findElement(By.id("gender-male")).click();
        getDriver().findElement(By.id("FirstName")).sendKeys("Topper");
        getDriver().findElement(By.id("LastName")).sendKeys("Harley");
        getDriver().findElement(By.id("Email")).sendKeys("topperharley@hotmail.com");
        getDriver().findElement(By.id("Password")).sendKeys("HotShots");
        getDriver().findElement(By.id("ConfirmPassword")).sendKeys("HotShots");
        getDriver().findElement(By.id("register-button")).click();

        Assert.assertEquals(getDriver().findElement(By.className("validation-summary-errors")).getText(),
                "The specified email already exists");
    }
    @Test
    public void testLoginPositive() {
        getDriver().get("https://demowebshop.tricentis.com/");

        Assert.assertEquals(getDriver().getTitle(), "Demo Web Shop");

        getDriver().findElement(By.className("ico-login")).click();
        getDriver().findElement(By.id("Email")).sendKeys("topperharley@hotmail.com");
        getDriver().findElement(By.id("Password")).sendKeys("HotShots");
        getDriver().findElement(By.id("RememberMe")).click();
        getDriver().findElement(By.className("login-button")).click();

        Assert.assertEquals(getDriver().findElement(By.className("account")).getText(),
                "topperharley@hotmail.com");
    }
    @Test
    public void testCameraSortByLowPrise() {
        getDriver().get("https://demowebshop.tricentis.com/camera-photo");

        getDriver().findElement(By.name("products-orderby")).click();
        Select sortBy = new Select(getDriver().findElement(By.name("products-orderby")));
        sortBy.selectByVisibleText("Price: Low to High");

        getDriver().findElement(By.id("products-viewmode")).click();
        Select viewAs = new Select(getDriver().findElement(By.id("products-viewmode")));
        viewAs.selectByVisibleText("List");

        Assert.assertEquals(getDriver().findElement(By.cssSelector("span.actual-price")).getText(), "349.00");
    }

    @Test
    public void testAddGiftCardToCart() {
        getDriver().get("https://demowebshop.tricentis.com/");

        WebElement selectSortBy = getDriver().findElement(
                By.cssSelector("div.header-menu>ul.top-menu>li>a[href='/gift-cards']"));
        selectSortBy.click();

        getDriver().findElement(
                        By.cssSelector("div.product-grid>div.item-box>div.product-item[data-productid='1']>div.picture"))
                .click();
        getDriver().findElement(
                By.cssSelector("div.breadcrumb>ul>li>strong.current-item"));

        Assert.assertEquals(getDriver().findElement(
                By.cssSelector("div.breadcrumb>ul>li>strong.current-item")).getText(), "$5 VIRTUAL GIFT CARD");

        getDriver().findElement(
                By.cssSelector("input#giftcard_1_RecipientName")).sendKeys("Ippolit");
        getDriver().findElement(
                By.cssSelector("input#giftcard_1_RecipientEmail")).sendKeys("ippolit@mail.ru");
        getDriver().findElement(
                By.cssSelector("input#giftcard_1_SenderName")).sendKeys("Barbara");
        getDriver().findElement(
                By.cssSelector("input#giftcard_1_SenderEmail")).sendKeys("barbara@mail.com");
        getDriver().findElement(
                By.cssSelector("textarea#giftcard_1_Message")).sendKeys("Тебе от меня! :)");
        getDriver().findElement(
                By.cssSelector("input[type='button']#add-to-cart-button-1")).click();
        getDriver().findElement(
                By.cssSelector("div.header-links>ul>li#topcartlink")).click();

        Assert.assertEquals(getDriver().getCurrentUrl(), "https://demowebshop.tricentis.com/cart");
        Assert.assertEquals(getDriver().findElement(
                        By.cssSelector("div.page-title>h1")).getText(), "Shopping cart");
        Assert.assertEquals(getDriver().findElement(
                        By.cssSelector("td.product>a.product-name")).getText(), "$5 Virtual Gift Card");
        // как подцепить эл.адреса?
        Assert.assertEquals(getDriver().findElement(
                        By.cssSelector("td.unit-price.nobr>span.product-unit-price")).getText(), "5.00");
        Assert.assertEquals(getDriver().findElement(
                        By.cssSelector("td.qty.nobr>input.qty-input")).getAttribute("value"), "1");
        Assert.assertEquals(getDriver().findElement(
                        By.cssSelector("td.subtotal.nobr.end>span.product-subtotal")).getText(), "5.00");
        Assert.assertEquals(getDriver().findElement(
                        By.cssSelector("td.cart-total-right>span.nobr>span.product-price")).getText(), "5.00");
        Assert.assertEquals(getDriver().findElement(
                                By.cssSelector("td.cart-total-right>span.nobr>span.product-price.order-total>strong"))
                        .getText(), "5.00");

        getDriver().findElement(By.id("termsofservice")).click();
        getDriver().findElement(By.id("checkout")).click();

        Assert.assertEquals(getDriver().findElement(
                By.cssSelector("div.page-title>h1")).getText(), "Welcome, Please Sign In!");
    }
}