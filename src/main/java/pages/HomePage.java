package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {

    WebDriver driver;

    By searchBar = By.id("twotabsearchtextbox");
    By navBar = By.id("nav-bar");
    By suggestionList = By.id("sac-autocomplete-results-container");
    By departmentDropdown = By.id("searchDropdownDescription");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getSearchBar() {
        return driver.findElement(searchBar);
    }

    public WebElement getnavBar() {
        return driver.findElement(navBar);
    }

    public WebElement getSuggestionList() {
        return driver.findElement(suggestionList);
    }

    public WebElement getDepartmentDropdown() {
        return driver.findElement(departmentDropdown);
    }

    public void searchFor(String query) {
        getSearchBar().sendKeys(query);
    }

    public void selectDepartment(String department) {
        getDepartmentDropdown().sendKeys(department);
    }
}
