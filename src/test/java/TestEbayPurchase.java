import Pages.*;
import Utills.Configuration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.time.Duration;

public class TestEbayPurchase {

    //Initiating an object from Configurations
    Configuration conf = new Configuration();
    //Define a driver from WebDriver
    WebDriver driver;
    //Define the initialPage from the InitialPage class
    InitialPage initialPage = null;
    //Define the mobilePhonesPage from the MobilePhonesPage class
    MobilePhonesPage mobilePhonesPage = null;
    ProductPage productPage = null;
    CartPage cartPage = null;


    @BeforeTest
    @Parameters({"url","browser"})
    //Navigate to the web page
    public void setUp(String url, String browser){
        if(browser.equalsIgnoreCase("chrome")) {
            conf.openChromeBrowser();
            conf.navigateToURL(url);
            driver = Configuration.getDriver();
        } else if(browser.equalsIgnoreCase("firefox")) {
            conf. openFirefoxBrowser();
            conf.navigateToURL(url);
            driver = Configuration.getDriver();
        }

    }

    @Test(priority = 0)
    @Parameters("url")
    //Verify correct page has loaded
    public void urlNavigation(String url){
        //Assert the navigated URL and the entered URL initially
        initialPage = new InitialPage(driver);
        String navigatedUrl = initialPage.getNavigatedUrl();
        Assert.assertEquals(navigatedUrl,url, "The navigated url test failed");
    }

    @Test(priority = 1)
    @Parameters({"searchItem", "category"})
    //Verify the correct category selected
    public void selectCorrectCategory(String searchItem, String category){
        String selectedCategory = initialPage.selectCellPhoneAndAccessoriesCategory(category);
        Assert.assertEquals(selectedCategory,"Cell Phones & Accessories","Selected does not match to 'Cell Phones & Accessories'");
        initialPage.typeSearchItem(searchItem);

    }

    @Test(priority = 2)
    //Extract and print search result and choose first result
    public void chooseFirstResult(){
        mobilePhonesPage = new MobilePhonesPage(driver);
        mobilePhonesPage.getSearchResult();
        mobilePhonesPage.extractResultTitleAndPrice();
        mobilePhonesPage.assertResults();
        mobilePhonesPage.printDetails();
        mobilePhonesPage.selectTheFirstProductVisible();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));// Adjust the timeout as needed
        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState == 'complete'"));
        mobilePhonesPage.assertAll();
    }

    @Test(priority = 3)
    @Parameters({"quantity"})
    //Assert selected item details and set color, plug and quantity
    public void retrieveAndDisplayItemDetails(String quantity){
        productPage = new ProductPage(driver);
        productPage.extractNameAndPrice();
        productPage.printNameAndPrice();
        Assert.assertEquals(productPage.getNameOfPhone(),mobilePhonesPage.getFirstProductName(),"Selected product name doesn't match with previously extracted name");
        if(productPage.isApproximatePriceAvailable()){
            Assert.assertEquals(productPage.getApproximatePrice(),"US " + mobilePhonesPage.getFirstProductPrice(),"Selected product price doesn't match with previously extracted price");
        }
        else{
            Assert.assertEquals(productPage.getPriceOfPhone(),"US " + mobilePhonesPage.getFirstProductPrice(),"Selected product price doesn't match with previously extracted price");
        }
        productPage.setColor();
        if(!productPage.isColorDropdownAvailable()){
            System.out.println("\nColor dropdown not available");
        }
        productPage.setPlug();
        if(!productPage.isPlugDropdownAvailable()){
            System.out.println("\nPlug dropdown not available");
        }
        productPage.setQuantity(quantity);
        productPage.addToCart();
    }

    @Test(priority = 4)
    @Parameters({"quantity"})
    //Assert cart item details
    public void verifyShoppingCartItemDetails(String quantity){
        cartPage = new CartPage(driver);
        cartPage.clickCartIcon();
        Assert.assertEquals(cartPage.getItemNameInCart(),productPage.getNameOfPhone(),"Item name does not match those previously received");
        Assert.assertEquals(cartPage.getItemPriceInCart(),productPage.getPriceOfPhone(),"Item price does not match those previously received");
        Assert.assertEquals(cartPage.getItemQuantityInCart(),quantity,"Item quantity does not match those previously received");
    }

    @Test(priority = 5)
    @Parameters({"quantity"})
    //Assert cart item details and go to checkout page
    public void proceedToCheckout(String quantity){
        cartPage.extractItemNameAndPrice();
        cartPage.printItemDetail();
        Assert.assertEquals(cartPage.getItemNameInCart(),productPage.getNameOfPhone(),"Item name does not match those previously received");
        Assert.assertEquals(cartPage.getItemPriceInCart(),productPage.getPriceOfPhone(),"Item price does not match those previously received");
        Assert.assertEquals(cartPage.getItemQuantityInCart(),quantity,"Item quantity does not match those previously received");
        cartPage.clickCheckoutButton();
        cartPage.clickContinueAsGuestButton();
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        Assert.assertEquals(checkoutPage.getPageTitle(),"Checkout | eBay", "Checkout page loading failed");
    }
/*
    @Test(priority = 6)
    //Assert first 5 search result contain word 'Mobile Phone'. Since it doesn't concern much it has low priority.
    public void assertFirst5SearchResult(){
        mobilePhonesPage.assertAll();
    }
*/
    @AfterTest
//    Close the browser
    public void closePage(){

        try {
            Thread.sleep(5000);
        } catch (Exception ignored) {
        }
        driver.quit();
    }

}
