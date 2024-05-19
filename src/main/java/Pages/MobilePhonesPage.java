package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert; // import from testng.jar module.

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MobilePhonesPage {
    WebDriver driver;
    //Define soft assert from SoftAssert class. Here soft asserts are used to continue the execution of the program even if one assertion fails.
    SoftAssert softAssert = new SoftAssert();
    String[] productName = new String[5];
     String[] price = new String[5];
    List<WebElement> resultItems = null;
    boolean[] isResultDetailContain = new boolean[5];

    public MobilePhonesPage(WebDriver driver)
    {
        this.driver = driver;
    }

    //Function to store result elements
    public void getSearchResult(){
        //List of Elements
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        resultItems =
                driver.findElements(By.xpath("//div[@id = 'srp-river-results']//li"));

    }

    //Function to extract result details
    public void extractResultTitleAndPrice(){
        for (int i = 0; i < Math.min(resultItems.size(), 5); i++) {
            WebElement productNameElement = resultItems.get(i).findElement(By.xpath(".//*[@class='s-item__title']"));
            productName[i] = productNameElement.getText();
            WebElement priceElement = resultItems.get(i).findElement(By.xpath(".//span[@class='s-item__price']"));
            price[i] = priceElement.getText();

        }
    }



    //Function to check first 5 result details contain word "Mobile Phone"
    public void assertResults(){
        for(int i = 0; i < 5; i++){
            isResultDetailContain[i] = productName[i].contains("Mobile Phone");
            String msg = "Word 'Mobile Phone' is not contain in result item "+ (i + 1);
            softAssert.assertTrue(isResultDetailContain[i], msg);
        }
    }

    //Assert first 5 search result contain word 'Mobile Phone'. Since it doesn't concert much it has low priority.
    public void assertAll(){
        softAssert.assertAll();
    }

    //Function to print item title and price
    public void printDetails(){
        System.out.println("First 5 results name and price");
        for (int i = 0; i < Math.min(resultItems.size(), 5); i++) {
            System.out.println(i + 1 +". Product Name: " + productName[i]);
            System.out.println("   Price       : " + price[i]);
        }
    }


    //Function to select the first search item visible on the webpage
    public void selectTheFirstProductVisible(){
        WebElement firstItem = resultItems.getFirst();
        firstItem.findElement(By.xpath(".//a[@class='s-item__link']")).click();


        //Change the chrome driver handler to the newly opened tab from the previous tab
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.getLast());

    }

    public String getFirstProductName() {
        return productName[0];
    }

    public String getFirstProductPrice() {
        return price[0];
    }
}
