package br.com.gaspar;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import pages.HomePage;
import utils.DriverFactory;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class HomePagePerformanceTests {
    private WebDriver driver;
    private DriverFactory driverFactory;
    private HomePage homePage;
    private static boolean healthCheckPassed = true;

    @BeforeAll
    public void setUp() {   
        driverFactory = new DriverFactory();     
        driver = driverFactory.getDriver();    
        driverFactory.openBaseUrl();  
        homePage = new HomePage(driver);
    }

    @Test
    @DisplayName("Healthcheck Inicial")
    @Order(0)
    public void healthCheckHomePagePerformance() {
        try {
            assertNotNull(driver, "O WebDriver não foi inicializado corretamente");
            assertDoesNotThrow(() -> driver.get("https://www.amazon.com.br/"), "Não foi possível acessar a página inicial");
            assertTrue(homePage.getSearchBar().isDisplayed(), "A barra de pesquisa não está visível");
            assertTrue(homePage.getNavBar().isDisplayed(), "O menu principal não está visível");
        } catch (AssertionError | Exception e) {
            healthCheckPassed = false;
            fail("Healthcheck falhou: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Tempo de Carregamento")
    @Order(1)
    public void testPageLoadTime() {
        long startTime = System.currentTimeMillis();
        driver.navigate().refresh();
        long endTime = System.currentTimeMillis();
        long loadTime = endTime - startTime;
        assertTrue(loadTime < 3000, "O tempo de carregamento deve ser inferior a 3 segundos");
    }

    @Test
    @DisplayName("Renderização de Elementos Principais")
    @Order(2)
    public void testMainElementsRender() {
        assertTrue(homePage.getSearchBar().isDisplayed(), "A barra de pesquisa não foi renderizada");
        assertTrue(homePage.getNavBar().isDisplayed(), "O menu principal não foi renderizado");
    }

    @Test
    @DisplayName("Carregamento Eficiente")
    @Order(3)
    public void testEfficientLoad() {
        try {
            driver.findElement(By.xpath("//*[@id=\"a-page\"]/script[5]"));
            driver.findElement(By.xpath("//*[@id=\"a-page\"]/script[6]"));
            driver.findElement(By.xpath("//*[@id=\"navbar-main\"]"));
        } catch (NoSuchElementException e) {
            fail("Erro no carregamento de scripts, imagens ou estilos");
        }
    }

    @Test
    @DisplayName("Cache Habilitado")
    @Order(4)
    public void testCacheEnabled() {
        driver.navigate().refresh();  // Tenta recarregar a página usando o cache
        long startTime = System.currentTimeMillis();
        driver.navigate().refresh();
        long endTime = System.currentTimeMillis();
        long loadTime = endTime - startTime;
        assertTrue(loadTime < 2000, "O tempo de carregamento deve ser reduzido com o cache");
    }

    @Test
    @DisplayName("Interrupção no Carregamento")
    @Order(5)
    public void testInterruptionInLoad() {
        driver.navigate().to("about:blank");
        driver.get("https://www.amazon.com.br/");
        assertTrue(homePage.getSearchBar().isDisplayed(), "A barra de pesquisa deve ser visível");
    }

    @Test
    @DisplayName("Uso de Memória")
    @Order(6)
    public void testMemoryUsage() {
        long initialMemory = Runtime.getRuntime().freeMemory();
        // Navega por 15 minutos (simulando comportamento de usuário)
        try {
            Thread.sleep(900000); // 15 minutos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long finalMemory = Runtime.getRuntime().freeMemory();
        assertTrue(finalMemory >= initialMemory, "O uso de memória não deve aumentar progressivamente");
    }

    @Test
    @DisplayName("Bloqueadores de Anúncio")
    @Order(7)
    public void testAdBlockers() {
        WebElement mainContent = driver.findElement(By.id("main-content"));
        assertTrue(mainContent.isDisplayed(), "O carregamento da página não foi concluído devido ao bloqueador de anúncios");
    }

    @AfterAll
    public void tearDown() {
        if (healthCheckPassed) {
            driverFactory.quitDriver();
        }
    }
}
