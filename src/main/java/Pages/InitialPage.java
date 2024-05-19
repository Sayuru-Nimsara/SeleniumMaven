package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class InitialPage {

    WebDriver driver;
    public InitialPage(WebDriver driver) {
        this.driver = driver;
    }

    //Function to get the url of the navigated web page
    public String getNavigatedUrl(){
        By urlNavigated = By.xpath("//td[@class='gh-td'][1]/h1/a");
        String navigatedUrl = (driver.findElement(urlNavigated).getAttribute("href"));
        return navigatedUrl;
    }

    public String selectCellPhoneAndAccessoriesCategory(String category){

        //Select "Cell Phones & Accessories" Category
        WebElement categoryDropdown = driver.findElement(By.id("gh-cat"));
        Select select = new Select(categoryDropdown);
        select.selectByVisibleText(category);
        String selectedCategory = select.getFirstSelectedOption().getText();
        return selectedCategory;
    }

    //Type “Mobile phone” on search bar
    public void typeSearchItem(String searchItem){
        WebElement searchBox = driver.findElement(By.id("gh-ac"));
        searchBox.sendKeys(searchItem);
        //Click on Search button icon;
        searchBox.sendKeys(Keys.RETURN);
    }

}
