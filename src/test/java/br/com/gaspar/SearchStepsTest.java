package br.com.gaspar;

import org.openqa.selenium.WebDriver;
import pages.MenuPage;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import utils.DriverFactory;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SearchStepsTest {
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
    @DisplayName("Pesquisa sugestão válida")
    void pesquisaSugestaoValida() {
        menuPage.search("material escolar");
        assertEquals("1-48 de mais de 1.000 resultados para", menuPage.textReturnSearch());
    }

    @Test
    @DisplayName("Pesquisa sugestão válida dinâmica")
    void pesquisaSugestaoValidaDinamica() {
        menuPage.search("livros romance");
        assertEquals("1-48 de mais de 100.000 resultados para", menuPage.textReturnSearch());
    }

    @Test
    @DisplayName("Pesquisa por departamento")
    public void pesquisaPorDepartamento() {
        menuPage.hoverOverCategory("Cozinha");
        menuPage.search("concha");
        assertEquals("1-16 de 398 resultados para", menuPage.textReturnSearch());
    }

    @Test
    @DisplayName("Pesquisa sugestão inválida com caracteres especiais")
    public void pesquisaSugestaoInvalidaComCaracteresEspeciais() {
        String pesquisa = "# $& * * fb444";
        menuPage.search(pesquisa);
        assertEquals("Nenhum resultado para", menuPage.textReturnSearch());
        assertEquals(pesquisa, menuPage.textReturnSearch2());
    }

    @Test
    @DisplayName("Pesquisa inválida")
    public void pesquisaInvalida() {
        String pesquisa = "asdfghjkHHHH";
        menuPage.search(pesquisa);
        assertEquals("Nenhum resultado para", menuPage.textReturnSearch());
        assertEquals(pesquisa, menuPage.textReturnSearch2());
    }

    @AfterAll
    public static void tearDown() {
        DriverFactory.quitDriver();
    }
}
