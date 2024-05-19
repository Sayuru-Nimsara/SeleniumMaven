package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class ProductPage {

    WebDriver driver;
    String nameOfPhone;
    String priceOfPhone;
    String approximatePrice;
    String colorOfPhone;
    String plugOption;
    boolean isApproximatePriceAvailable;
    boolean isColorDropdownAvailable;
    boolean isPlugDropdownAvailable;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    //Extract the name and the price of the mobile phone
    public void extractNameAndPrice(){
        WebElement nameElement =  driver.findElement(By.xpath("//*[@data-testid ='x-item-title']"));
        nameOfPhone = nameElement.getText();
        WebElement priceElement =  driver.findElement(By.xpath("//*[@class='x-price-primary']"));
        priceOfPhone = priceElement.getText();
        setApproximatePrice();

    }

    public void printNameAndPrice(){
        System.out.println("\nClicked item Details");
        System.out.println("========================");
        System.out.println("Product name : " +nameOfPhone);
        System.out.println("Product price: " +priceOfPhone);
        if(isApproximatePriceAvailable){
            System.out.println("Product approximate price : " +approximatePrice);
        }
    }

    //check approximate price available and set it
    public void setApproximatePrice() {
        WebElement approximatePriceElement = null;
        try {
            approximatePriceElement = driver.findElement(By.xpath("//*[@class=\"x-price-approx__price\"]"));
            approximatePrice= approximatePriceElement.getText();
            isApproximatePriceAvailable = true;
        }
        // Element not found, handle the exception
        catch (Exception ignored) {
            isApproximatePriceAvailable = false;
        }
    }



    //Select color
    public void setColor(){
        try {
            WebElement colorElement = driver.findElement(By.xpath("//*[@id='x-msku__select-box-1000']"));
            Select colorDropdown = new Select(colorElement);
            colorDropdown.selectByIndex(2);
            colorOfPhone = colorDropdown.getFirstSelectedOption().getText();
            isColorDropdownAvailable = true;
        }
        catch(Exception ignored){
            isColorDropdownAvailable = false;
        }
    }

    //Select the plug
    public void setPlug(){
        try {
            WebElement plugElement = driver.findElement(By.xpath("//*[@id='x-msku__select-box-1001']"));
            Select plugDropdown = new Select(plugElement);
            plugDropdown.selectByIndex(1);
            plugOption = plugDropdown.getFirstSelectedOption().getText();
            isPlugDropdownAvailable = true;
        }
        catch(Exception ignored){
            isPlugDropdownAvailable = false;
        }
    }

    //Set quantity
    public void setQuantity(String quantity){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement quantityBox =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("qtyTextBox")));
        quantityBox.clear();
        quantityBox.sendKeys(quantity);

    }

    public void addToCart(){
        driver.findElement(By.xpath("//*[@data-testid='x-atc-action']")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));// Adjust the timeout as needed
        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState == 'complete'"));
    }


    public String getNameOfPhone() {
        return nameOfPhone;
    }

    public String getPriceOfPhone() {
        return priceOfPhone;
    }

    public String getApproximatePrice() {
        return approximatePrice;
    }

    public String getColorOfPhone() {
        return colorOfPhone;
    }

    public String getPlugOption() {
        return plugOption;
    }

    public boolean isApproximatePriceAvailable() {
        return isApproximatePriceAvailable;
    }


    public boolean isColorDropdownAvailable() {
        return isColorDropdownAvailable;
    }

    public boolean isPlugDropdownAvailable() {
        return isPlugDropdownAvailable;
    }
}
