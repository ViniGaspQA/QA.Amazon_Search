package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class DriverFactory {
    private WebDriver driver;
    private static final String CHROME_DRIVER_PATH = "E:\\Projetos\\amazon\\chromedriver.exe";
    public String BASE_URL = "https://www.amazon.com.br/";

    // Método para inicializar o WebDriver
    public WebDriver getDriver() {        
        try {
            if (driver == null) {
                verificarWebDriverEmExecucao();
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
        } catch (WebDriverException e) {
            System.err.println("Erro ao tentar inicializar o WebDriver.");
            e.printStackTrace();  
        } catch (Exception e) {
            System.err.println("Erro inesperado ao tentar inicializar o driver.");
            e.printStackTrace();  
        }    
        return driver;    
    }

    // Método para acessar a URL
    public void openBaseUrl() {
        try {
            if (driver == null) {
                driver = getDriver();  
            }
            driver.get(BASE_URL);  
        } catch (NullPointerException e) {
            System.err.println("Erro: O driver está nulo. Não é possível acessar a URL.");
            e.printStackTrace();  
        } catch (WebDriverException e) {
            System.err.println("Erro: Não foi possível acessar a URL devido a um problema com o WebDriver.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao tentar abrir a URL base.");
            e.printStackTrace();  
        }
    }

    //Método para atualizar tela com captura de exceção
    public void refreshPage() {        
        try {
            openBaseUrl();
        } catch (WebDriverException e) {
            System.err.println("Erro ao tentar atualizar a página.");
            e.printStackTrace();  
        } catch (Exception e) {
            System.err.println("Erro inesperado ao tentar atualizar a página.");
            e.printStackTrace();  
        }
    }
    // Método para fechar o WebDriver
    public void quitDriver() {
        try {
            if (driver != null) {
                driver.quit();  
                driver = null;  
            }
        } catch (WebDriverException e) {
            System.err.println("Erro ao tentar fechar o WebDriver.");
            e.printStackTrace();  
        } catch (Exception e) {
            System.err.println("Erro inesperado ao tentar fechar o driver.");
            e.printStackTrace();  
        }
    }

    private List<String> getRunningProcesses() throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("tasklist");
        Process process = processBuilder.start();

        // Obtendo a saída do comando tasklist
         try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            // Lendo os processos em execução e coletando em uma lista
            return reader.lines().collect(Collectors.toList());
        }        
    }

    public void verificarWebDriverEmExecucao() throws IOException {
        // Verificar se o WebDriver está em execução verificando o processo no sistema
        List<String> processos = getRunningProcesses();   
        if (processos.contains("chromedriver.exe")) {
            killProcess("chromedriver.exe");
        }    
    }

    private void killProcess(String processName) throws IOException {
        // Comando para matar o processo
        String command = "taskkill /F /IM " + processName;
        ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", command);
        processBuilder.start();
    }
}