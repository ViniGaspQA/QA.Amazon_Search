package br.com.gaspar;

import pages.MenuPage;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import utils.DriverFactory;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MenuTest {
    private static WebDriver driver;
    private MenuPage menuPage;
    private static DriverFactory driverFactory;

    @BeforeAll
    public void setUp() {
        driver = driverFactory.getDriver();     
        DriverFactory.openBaseUrl();    
        menuPage = new MenuPage(driver);
    }

    @Test
    @DisplayName("Links do menu")
    void testMenuLinks() {
        menuPage.clickMaisVendidos();
        assertEquals("Mais vendidos", menuPage.textVendidos());
    }

    @Test
    @DisplayName("Retração do menu")
    public void testRetraçãoDoMenu() {
        menuPage.clickMenuButton();
        menuPage.clickOutsideMenu();
        assertFalse(menuPage.isMenuVisible());
    }

    @Test
    @DisplayName("Responsividade")
    public void testResponsividade() {
        driver.manage().window().setSize(new Dimension(1024, 768));
        menuPage.clickMenuButton();
        assertTrue(menuPage.isMenuVisible());
    }

    @Test
    @DisplayName("Submenus")
    public void testSubMenu() {
        menuPage.hoverOverCategory("Cozinha");
        menuPage.search("Concha");
        assertFalse(menuPage.isSubMenuVisible());
    }

    @Test
    @DisplayName("Cliques repetitivos no menu")
    public void testCliquesRepetitivos() {
        for (int i = 0; i < 10; i++) {
            menuPage.clickMenuButton();
            menuPage.clickOutsideMenu();
        }
        assertFalse(menuPage.isMenuVisible());
    }

    @AfterAll
    public static void tearDown() {
        DriverFactory.quitDriver();
    }
}
