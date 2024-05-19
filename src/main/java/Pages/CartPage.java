package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class CartPage {
    WebDriver driver;
    String itemName;
    String itemPrice;
    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    //Click cart icon
    public void clickCartIcon(){
        driver.findElement(By.xpath("//*[@class='gh-eb-li-a gh-rvi-menu gh-cart-count-1']")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));// Adjust the timeout as needed
        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState == 'complete'"));
    }

    //Function to return the item name displayed in the cart
    public String getItemNameInCart(){
        String itemNameInCart = driver.findElement(By.xpath("//*[@class=\"item-title text-truncate-multiline black-link lines-2\"]")).getText();
        return itemNameInCart;
    }

    //Function to return the item price displayed in the cart
    public String getItemPriceInCart(){
        String itemPriceInCart = driver.findElement(By.xpath("//*[@class=\"item-price font-title-3\"]")).getText();
        return itemPriceInCart;
    }

    //Function to return the item quantity displayed in the cart
    public String getItemQuantityInCart(){
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement itemQuantityElement = driver.findElement(By.xpath("//*[@data-test-id=\"qty-dropdown\"]"));
        Select dropdownQuantity = new Select(itemQuantityElement);
        String itemQuantityInCart = dropdownQuantity.getFirstSelectedOption().getText();
        return itemQuantityInCart;
    }

    public void extractItemNameAndPrice(){
        itemName = getItemNameInCart();
        itemPrice = getItemPriceInCart();
    }


    public void printItemDetail(){
        System.out.println("\nDetails of the items in  the shopping cart");
        System.out.println("===========================================");
        System.out.println("Item name  : " + itemName);
        System.out.println("Item price : " +itemPrice);
    }

    public void clickCheckoutButton(){
        WebElement checkoutButtonElement = driver.findElement(By.xpath("//*[@data-test-id=\"cta-top\"]"));
        checkoutButtonElement.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));// Adjust the timeout as needed
        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState == 'complete'"));
    }

    public void clickContinueAsGuestButton(){
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement continueAsGuestButton = driver.findElement(By.xpath("//*[@id=\"gxo-btn\"]"));
        continueAsGuestButton.click();
    }
}
