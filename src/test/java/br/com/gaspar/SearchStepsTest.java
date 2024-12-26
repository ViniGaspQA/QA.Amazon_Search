package br.com.gaspar;

import org.openqa.selenium.WebDriver;

import pages.HomePage;
import pages.MenuPage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;
import org.junit.jupiter.params.provider.*;

import utils.DriverFactory;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class SearchStepsTest {
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

    private static Stream<Arguments> termosDePesquisa() {
        return Stream.of(
            Arguments.of("Cozinha", "concha"),
            Arguments.of("Pet Shop", "gato petisco"),
            Arguments.of("Eletrônicos", "som")
        );
    }

    @Test
    @DisplayName("Healthcheck Inicial")
    @Order(0)
    public void healthCheckSearchSteps() {
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
    @DisplayName("Pesquisa sugestão válida")
    @Order(1)
    void pesquisaSugestaoValida() {
        menuPage.search("material escolar");
        assertEquals("1-48 de mais de 1.000 resultados para", menuPage.textReturnSearch());
    }

    @Test
    @DisplayName("Pesquisa sugestão válida dinâmica")
    @Order(2)
    void pesquisaSugestaoValidaDinamica() {
        menuPage.search("livros romance");
        assertEquals("1-48 de mais de 100.000 resultados para", menuPage.textReturnSearch());
    }

    @ParameterizedTest
    @DisplayName("Pesquisa por departamento")
    @Order(3)
    @MethodSource("termosDePesquisa")
    public void pesquisaPorDepartamento(String departamento, String termoPesquisa) {
        menuPage.hoverOverCategory(departamento);
        menuPage.search(termoPesquisa);
        assertThat(menuPage.textReturnSearch(), containsString("1-16 de"));
        assertThat(menuPage.textReturnSearch(), containsString("resultados para"));
    }

    @Test
    @DisplayName("Pesquisa sugestão inválida com caracteres especiais")
    @Order(4)
    public void pesquisaSugestaoInvalidaComCaracteresEspeciais() {
        String pesquisa = "# $& * * fb444";
        menuPage.search(pesquisa);
        assertEquals("Nenhum resultado para", menuPage.textReturnSearchErro());
        assertEquals(pesquisa, menuPage.textReturnSearchErro2());
    }

    @Test
    @DisplayName("Pesquisa inválida")
    @Order(5)
    public void pesquisaInvalida() {
        String pesquisa = "asdfghjkHHHH";
        menuPage.search(pesquisa);
        assertEquals("Nenhum resultado para", menuPage.textReturnSearchErro());
        assertEquals(pesquisa, menuPage.textReturnSearchErro2());
    }

    @AfterAll
    public void tearDown() {
        if (healthCheckPassed) {
            driverFactory.quitDriver();
        }
    }
}
