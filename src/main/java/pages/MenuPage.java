package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

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
    private By textReturnSearchErro = By.xpath("//*[@id=\"search\"]/div[1]/div[1]/div/span[1]/div[1]/div[1]/div/div/div/div[1]/span[1]");
    private By textReturnSearchErro2 = By.xpath("//*[@id=\"search\"]/div[1]/div[1]/div/span[1]/div[1]/div[1]/div/div/div/div[1]/span[2]");
    private By navBar = By.id("nav-search-bar-form");
    
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

    public String textReturnSearchErro() {
        return driver.findElement(textReturnSearchErro).getText();
    }

    public String textReturnSearchErro2() {
        return driver.findElement(textReturnSearchErro2).getText();
    }

    public void clickMenuButton() {
        driver.findElement(menuButton).click();
    }

    public void clickOutsideMenu() {
        driver.findElement(menuButtonOutside).click();
    }

    public boolean isMenuVisible() {
        try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
                WebElement menuElement = wait.until(ExpectedConditions.visibilityOfElementLocated(menuItems));
                return menuElement.isDisplayed();
            } catch (TimeoutException e) {
                System.err.println("O menu não está visível após 30 segundos.");
                return false;
            } catch (NoSuchElementException e) {
                System.err.println("O elemento do menu não foi encontrado no DOM.");
                return false;
            }
    }

    public boolean isSubMenuVisible() {        
        try {
            return driver.findElement(menuButtonSearch).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }        
    }

    public void hoverOverCategory(String category) {
        try {
            driver.findElement(menuButtonSearch).click();   
            new Select(driver.findElement(menuButtonSearch)).selectByVisibleText(category);
        } catch (NoSuchElementException e) {
            System.out.println("Erro ao encontrar o elemento de busca ou menu: " + e.getMessage());
            throw e;
        } catch (ElementNotInteractableException e) {
            System.out.println("O elemento não está visível para interação: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.out.println("Erro inesperado ao tentar interagir com o menu: " + e.getMessage());
            throw e;
        }
    }  
    
    public void search(String search) {
        driver.findElement(searchText).click();
        driver.findElement(searchText).clear();
        driver.findElement(searchText).sendKeys(search);
        driver.findElement(navBar).submit();
    } 
}
