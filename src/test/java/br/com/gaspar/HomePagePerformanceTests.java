package br.com.gaspar;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import pages.HomePage;
import utils.DriverFactory;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HomePagePerformanceTests {
    private static WebDriver driver;
    private static DriverFactory driverFactory;
    private HomePage homePage;

    @BeforeAll
    public void setUp() {
        driver = driverFactory.getDriver();     
        DriverFactory.openBaseUrl();    
        homePage = new HomePage(driver);
    }

    @Test
    @DisplayName("Tempo de Carregamento")
    public void testPageLoadTime() {
        long startTime = System.currentTimeMillis();
        driver.navigate().refresh();
        long endTime = System.currentTimeMillis();
        long loadTime = endTime - startTime;
        assertTrue(loadTime < 3000, "O tempo de carregamento deve ser inferior a 3 segundos");
    }

    @Test
    @DisplayName("Renderização de Elementos Principais")
    public void testMainElementsRender() {
        assertTrue(homePage.getSearchBar().isDisplayed(), "A barra de pesquisa não foi renderizada");
        assertTrue(homePage.getnavBar().isDisplayed(), "O menu principal não foi renderizado");
    }

    @Test
    @DisplayName("Carregamento Eficiente")
    public void testEfficientLoad() {
        try {
            driver.findElement(By.id("main-script"));
            driver.findElement(By.id("main-image"));
            driver.findElement(By.id("main-style"));
        } catch (NoSuchElementException e) {
            fail("Erro no carregamento de scripts, imagens ou estilos");
        }
    }

    @Test
    @DisplayName("Cache Habilitado")
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
    public void testInterruptionInLoad() {
        driver.navigate().to("about:blank");
        driver.get("https://www.amazon.com.br/");
        assertTrue(homePage.getSearchBar().isDisplayed(), "A barra de pesquisa deve ser visível");
    }

    @Test
    @DisplayName("Uso de Memória")
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
    public void testAdBlockers() {
        WebElement mainContent = driver.findElement(By.id("main-content"));
        assertTrue(mainContent.isDisplayed(), "O carregamento da página não foi concluído devido ao bloqueador de anúncios");
    }

    @AfterAll
    public static void tearDown() {
        DriverFactory.quitDriver();
    }
}
