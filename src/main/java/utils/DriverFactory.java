package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class DriverFactory {
    private static WebDriver driver;
    private static final String CHROME_DRIVER_PATH = "E:\\Projetos\\amazon\\chromedriver.exe";
    private static final String BASE_URL = "https://www.amazon.com.br/";

    // Método para inicializar o WebDriver
    public static WebDriver getDriver() {
        if (driver == null) {
            System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
            
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--start-maximized");
            chromeOptions.addArguments("--disable-notifications");
            chromeOptions.addArguments("--start-maximized");
            chromeOptions.addArguments("--incognito");
            //chromeOptions.addArguments("--headless");

            driver = new ChromeDriver(chromeOptions);

            // Configurar tempo de espera implícito
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
        return driver;
    }

    // Método para acessar a URL
    public static void openBaseUrl() {
        if (driver == null) {
            getDriver();
        }
        driver.get(BASE_URL);
    }

    // Método para fechar o WebDriver
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    // public static void main(String[] args) {
    //     try {
    //         // Iniciar o WebDriver e acessar a URL
    //         openBaseUrl();

    //         // Aqui você pode adicionar mais comandos de teste
    //         System.out.println("Página carregada: " + driver.getTitle());
    //     } finally {
    //         // Fechar o WebDriver
    //         quitDriver();
    //     }
    // }
}