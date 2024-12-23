package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class MenuPage {
    private WebDriver driver;

    private By menuButtonSearch = By.id("searchDropdownBox");
    private By menuButton = By.xpath("//a[@id='nav-hamburger-menu']/span");
    private By menuButtonOutside = By.id("hmenu-canvas-background");
    private By maisVendidosLink = By.linkText("Mais Vendidos");
    private By menuItems = By.id("hmenu-customer-profile-right");
    private By searchText = By.id("twotabsearchtextbox");
    private By textMenu = By.id("zg_banner_text");
    private By textReturnSearch = By.xpath("//div[@id='search']/span/div[2]/h1/div/div/div/h2/span");
    private By textReturnSearch2 = By.xpath("//div[@id='search']/span/div[2]/h1/div/div/div/h2/span[2]");
    
    public MenuPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickMaisVendidos() {
        driver.findElement(maisVendidosLink).click();
    }

    public String textVendidos() {
        return driver.findElement(textMenu).getText();
    }

    public String textReturnSearch() {
        return driver.findElement(textReturnSearch).getText();
    }

    public String textReturnSearch2() {
        return driver.findElement(textReturnSearch2).getText();
    }

    public void clickMenuButton() {
        driver.findElement(menuButton).click();
    }

    public void clickOutsideMenu() {
        driver.findElement(menuButtonOutside).click();
    }

    public boolean isMenuVisible() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return driver.findElement(menuItems).isDisplayed();
    }

    public boolean isSubMenuVisible() {        
        return driver.findElement(menuButtonSearch).isDisplayed();
    }

    public void hoverOverCategory(String category) {
        driver.findElement(menuButtonSearch).click();
        new Select(driver.findElement(menuButtonSearch)).selectByVisibleText(category);
    }  
    
    public void search(String search) {
        driver.findElement(searchText).click();
        driver.findElement(searchText).clear();
        driver.findElement(searchText).sendKeys(search);
        driver.findElement(By.id("nav-search-bar-form")).submit();
    } 
}
