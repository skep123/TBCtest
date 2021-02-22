package AllureReports;

import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Testtbc {
	
	WebDriver drv;
	
	String mainPath = "http://the-internet.herokuapp.com/";
	String filePath = "C:\\Users\\MyPC\\Desktop\\testUpload.jpg";
	String fileName = "testUpload.jpg";
	
	@BeforeClass
	public void openBrowser() {
		WebDriverManager.chromedriver().setup();
		drv = new ChromeDriver();
		drv.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		drv.get(mainPath);
	}
	
	@AfterClass
	public void closeBrowser() {
		drv.close();
	}
	
	@Test
	public void uploadTest() {
		drv.navigate().to("http://the-internet.herokuapp.com/");
		drv.findElement(By.xpath("//*[@id=\"content\"]/ul/li[18]/a")).click();
		drv.findElement(By.xpath("//*[@id=\"file-upload\"]")).sendKeys(filePath);
		drv.findElement(By.xpath("//*[@id=\"file-submit\"]")).click();
		drv.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		assertTrue(drv.findElement(By.xpath("//*[@id=\"content\"]/div/h3")).getText().equals("File Uploaded!"));
	}
	
	/* 
	 * Test at first requires to check "Home" button but there's no such button so this test just checks the headings of the pages.
	 * button check would be following:
	 * 
	 * Dimension initDim = drv.findElement(By.tagName("body")).getSize();
	 * drv.findElement(By.linkText("Home")).click();
	 * Dimension finDim = drv.findElement(By.tagName("body")).getSize();
	 * iassertTrue(if(initDim == finDim));
	 * 
	 */
	@Test
	public void shiftTest() {
		String oldStr, newStr;
		drv.navigate().to("http://the-internet.herokuapp.com/");
		oldStr = drv.findElement(By.xpath("//*[@id=\"content\"]/h2")).getText();
		drv.findElement(By.xpath("//*[@id=\"content\"]/ul/li[39]/a")).click();
		newStr = drv.findElement(By.xpath("//*[@id=\"content\"]/div/h3")).getText();
		boolean result = false;
		if(!oldStr.equals(newStr)) {
			drv.findElement(By.xpath("//*[@id=\"content\"]/div/a[2]")).click();
			Point pointA = drv.findElement(By.xpath("//*[@id=\"content\"]/div/img")).getLocation();
			drv.findElement(By.xpath("//*[@id=\"content\"]/div/p[3]/a")).click();
			Point pointB = drv.findElement(By.xpath("//*[@id=\"content\"]/div/img")).getLocation();
			if(!pointA.equals(pointB)) {
				result = true;
			}
		}
		assertTrue(result);
	}
	
	@Test
	public void domTest() {
		drv.navigate().to("http://the-internet.herokuapp.com/");
		drv.findElement(By.xpath("//*[@id=\"content\"]/ul/li[5]/a")).click();
		WebElement column = drv.findElement(By.xpath("//*[@id=\"content\"]/div/div/div/div[2]/table/tbody/tr[1]"));
		boolean result = true;
		for(int i = 1; i <=6; i++) {
			String curElement = column.findElement(By.xpath("//*[@id=\"content\"]/div/div/div/div[2]/table/tbody/tr[1]/td[" + i + "]")).getText();
			if((curElement.charAt(curElement.length() - 1) - '0')!=0) {
				result = false;
				break;
			}
		}
		assertTrue(result);
	}

}
