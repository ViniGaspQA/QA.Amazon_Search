package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {

    WebDriver driver;

    By searchBar = By.id("twotabsearchtextbox");
    By navBar = By.id("navbar-main");
    By pageContent = By.id("pageContent");
    By suggestionList = By.id("sac-autocomplete-results-container");
    By departmentDropdown = By.id("searchDropdownDescription");
    By screnCaptcha = By.cssSelector("body > div > div.a-row.a-spacing-double-large > div.a-section > div > div");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getSearchBar() {
        try {
            return driver.findElement(searchBar);
        } catch (Exception e) {
            System.out.println("Erro genérico ao tentar acessar o elemento: " + e.getMessage());
            throw e;
        }
    }

    public WebElement getNavBar() {
        try {
            return driver.findElement(navBar);
        } catch (Exception e) {
            System.out.println("Erro genérico ao tentar acessar o elemento: " + e.getMessage());
            throw e;
        }
    }

    public WebElement getPageContent() {
        try {
            return driver.findElement(pageContent);
        } catch (Exception e) {
            System.out.println("Erro genérico ao tentar acessar o elemento: " + e.getMessage());
            throw e;
        }
    }

    public WebElement getSuggestionList() {
        try {
            return driver.findElement(suggestionList);
        } catch (Exception e) {
            System.out.println("Erro genérico ao tentar acessar o elemento: " + e.getMessage());
            throw e;
        }
    }

    public boolean getScrenCaptcha() {
        try {
            if(driver.findElement(screnCaptcha).isDisplayed()){                   
                return true;        
            }else{
                return false;
            }            
        } catch (Exception e) {
            return false;
        }
    }

    public WebElement getDepartmentDropdown() {
        try {
            return driver.findElement(departmentDropdown);
        } catch (Exception e) {
            System.out.println("Erro genérico ao tentar acessar o elemento: " + e.getMessage());
            throw e;
        }
    }

    public void searchFor(String query) {
        try {
            getSearchBar().sendKeys(query);
        } catch (Exception e) {
            System.out.println("Erro genérico ao tentar acessar o elemento: " + e.getMessage());
            throw e;
        }
    }

    public void selectDepartment(String department) {
        try {
            getDepartmentDropdown().sendKeys(department);
        } catch (Exception e) {
            System.out.println("Erro genérico ao tentar acessar o elemento: " + e.getMessage());
            throw e;
        }
    }
}
