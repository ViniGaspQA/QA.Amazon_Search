package br.com.gaspar;

import pages.HomePage;
import pages.MenuPage;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import static org.junit.jupiter.api.Assertions.*;
import utils.DriverFactory;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class MenuTest {
    private static WebDriver driver;
    private MenuPage menuPage;
    private HomePage homePage;
    private static DriverFactory driverFactory;
    private static boolean healthCheckPassed = true;

    @BeforeAll
    public void setUp() {
        driverFactory = new DriverFactory();     
        driver = driverFactory.getDriver();    
        driverFactory.openBaseUrl();  
        menuPage = new MenuPage(driver);
        homePage = new HomePage(driver);

        if(homePage.getScrenCaptcha()){
            driverFactory.refreshPage();            
        }
    }

    @Test
    @DisplayName("Healthcheck Inicial")
    @Order(0)
    public void healthCheckMenu() {
        try {
            assertNotNull(driver, "O WebDriver não foi inicializado corretamente");
            assertDoesNotThrow(() -> driver.get(driverFactory.BASE_URL), "Não foi possível acessar a página inicial");
            assertTrue(homePage.getSearchBar().isDisplayed(), "A barra de pesquisa não está visível");
            assertTrue(homePage.getNavBar().isDisplayed(), "O menu principal não está visível");
        } catch (AssertionError | Exception e) {
            healthCheckPassed = false;
            fail("Healthcheck falhou: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Links do menu")
    @Order(1)
    void testMenuLinks() {
        menuPage.clickMaisVendidos();
        assertEquals("Mais vendidos", menuPage.textVendidos(), "Texto incorreto");
    }

    @Test
    @DisplayName("Retração do menu")
    @Order(2)
    public void testRetraçãoDoMenu() {
        menuPage.clickMenuButton();
        menuPage.clickOutsideMenu();
        assertFalse(menuPage.isMenuVisible(), "Elemento principal não está visível");
    }

    @Test
    @DisplayName("Cliques repetitivos no menu")
    @Order(3)
    public void testCliquesRepetitivos() {
        for (int i = 0; i < 10; i++) {
            menuPage.clickMenuButton();
            menuPage.clickOutsideMenu();
        }
        assertFalse(menuPage.isMenuVisible(), "Elemento principal não está visível");
    }

    @Test
    @DisplayName("Submenus")
    @Order(4)
    public void testSubMenu() {
        menuPage.hoverOverCategory("Cozinha");
        menuPage.search("Concha");
        assertFalse(menuPage.isSubMenuVisible(), "Elemento principal não está visível");
    }

    @Test
    @DisplayName("Responsividade")
    @Order(5)
    public void testResponsividade() {
        driver.manage().window().setSize(new Dimension(1024, 768));
        menuPage.clickMenuButton();
        assertTrue(menuPage.isMenuVisible(), "Elemento principal não está visível");
    }

    @AfterAll
    public void tearDown() {
        if (healthCheckPassed) {
            driverFactory.quitDriver();
        }
    }
}
