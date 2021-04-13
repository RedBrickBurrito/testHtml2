package mx.tec.lab;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

@SpringBootTest
public class TestHtml2ApplicationTests {
	
	private static WebDriver driver;
	
	@BeforeAll
	public static void setUp() {
		System.setProperty("webdriver.chrome.driver", "./chromedriver");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	
	@AfterAll
	public static void tearDown() {
		driver.quit();
	}

	@Test
	public void givenAClient_whenEnteringAutomationPractice_thenPageTitleIsCorrect() throws Exception {
		driver.get("http://automationpractice.com/index.php");
		String title = driver.getTitle();
		
		assertEquals("My Store", title);
	}
	
	@Test
	public void givenAClient_whenEnterLoginCredentials_thenAccountPageIsDisplayed() throws Exception {
		driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
		WebElement emailField = driver.findElement(By.id("email"));
		emailField.sendKeys("a01568010@gmail.com");
		WebElement passwordField = driver.findElement(By.id("passwd"));
		passwordField.sendKeys("Holamundo1234");
		WebElement submitButton = driver.findElement(By.id("SubmitLogin"));
		submitButton.click();
		String title = driver.getTitle();
		
		assertEquals("My account - My Store", title);
	}
	
	@Test
	public void givenAClient_whenEnteringWrongLoginCredentials_thenAuthenticationPageIsDisplayed() 
		throws Exception {
		driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
		WebElement emailField = driver.findElement(By.id("email"));
		emailField.sendKeys("a01568010@gmail.com");
		WebElement passwordField = driver.findElement(By.id("passwd"));
		passwordField.sendKeys("Holamundo12345");
		WebElement submitButton = driver.findElement(By.id("SubmitLogin"));
		submitButton.click();
		String title = driver.getTitle();
		
		assertEquals("Login - My Store", title);
	}
	
	@Test
	public void givenAClient_whenEnteringWrongLoginCredentials_thenErrorMessageIsDisplayed() 
		throws Exception {
		driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
		WebElement emailField = driver.findElement(By.id("email"));
		emailField.sendKeys("a01568010@gmail.com");
		WebElement passwordField = driver.findElement(By.id("passwd"));
		passwordField.sendKeys("Holamundo12345");
		WebElement submitButton = driver.findElement(By.id("SubmitLogin"));
		submitButton.click();
	
		assertNotNull(driver.findElements(By.xpath("//div[@class='alert alert-danger']/ol/li")));
	}
	
	@Test
	public void givenAClient_whenSearchingNotExisitingProduct_thenNoResultsDisplayed() 
		throws Exception {
		driver.get("http://automationpractice.com/index.php");
		WebElement searchField = driver.findElement(By.id("search_query_top"));
		searchField.sendKeys("dfasfawe");
		WebElement submitButton = driver.findElement(By.name("submit_search"));
		submitButton.click();
		
		WebElement warning= driver.findElement(By.xpath("//p[@class='alert alert-warning']")); 
		assertEquals("No results were found for your search \"dfasfawe\"", warning.getText().trim());
		
	}
	
	@Test
	public void givenAClient_whenSearchingEmptyString_thenPleaseEnterDisplayed() 
		throws Exception {
		driver.get("http://automationpractice.com/index.php");
		WebElement searchField = driver.findElement(By.id("search_query_top"));
		searchField.sendKeys("");
		WebElement submitButton = driver.findElement(By.name("submit_search"));
		submitButton.click();
		
		WebElement warning= driver.findElement(By.xpath("//p[@class='alert alert-warning']")); 
		assertEquals("Please enter a search keyword", warning.getText().trim());
		
	}
	
	@Test
	public void givenAClient_whenSigningOut_thenAuthenticationPageIsDisplayed() 
		throws Exception {
		driver.get("http://automationpractice.com/index.php?controller=my-account");
		WebElement logoutButton = driver.findElement(By.className("logout"));
		logoutButton.click();
		String title = driver.getTitle();
		
		assertEquals("Login - My Store", title);
		
	}
}
