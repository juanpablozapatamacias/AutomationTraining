package ui.base;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import ui.utilities.CommonUtilities;
import ui.utilities.Log;

public class BaseTest {

	protected String projectPath = System.getProperty("user.dir");
	
	protected Properties propi = CommonUtilities.loadProperties(projectPath + "/src/main/resources/sample.properties");
	protected String pathScreenshots = projectPath + "/screenshots/";
	
	protected WebDriver driver = getDriver();
	
	public WebDriver getDriver() {
		if (driver==null)
			driver = connectDriver();
		return driver;
	}
		
	public WebDriver connectDriver() {
		String propBrowser = propi.getProperty("webapp.browser").toLowerCase();
		
		Properties propSystem = System.getProperties();
		Enumeration e = propSystem.propertyNames();
		
		Map<String,String> propMap = new HashMap<String, String>();
		propMap = CommonUtilities.putSysProps(e, propSystem);
		projectPath = propMap.get("user.dir");
		String os = propMap.get("os.name").trim().toLowerCase();
				 
		if (propBrowser.isEmpty() || propBrowser == null){
			throw new IllegalArgumentException("Missing parameter value for browser");
		}
		else {
			String http_proxy = System.getProperty("http_proxy", "");
			String https_proxy = System.getProperty("https_proxy", "");
			
			if(os.indexOf("win")>=0) {
				
				if(propBrowser.contentEquals("chrome")) {
					
					ChromeOptions op = new ChromeOptions();
					
					System.setProperty("webdriver.chrome.driver", 
							projectPath + "\\drivers\\chromedriver\\chromedriver.exe");

					if(propi.getProperty("webapp.headlessExecution").equals("true")){
						op.addArguments("--headless");
					}
					
					if(propi.getProperty("webapp.incognito").equals("true")) {
						op.addArguments("--incognito");
					}

					op.setExperimentalOption("useAutomationExtension",false);
					op.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
					
					op.setPageLoadStrategy(PageLoadStrategy.NONE);
                    op.addArguments("start-maximized");
                    op.addArguments("enable-automation");
                    op.addArguments("--disable-gpu");
                    op.addArguments("--disable-extensions");
                    op.addArguments("--no-sandbox");
                    op.addArguments("--disable-dev-shm-usage");
                    
					driver = new ChromeDriver(op);	
				}
				else if(propBrowser.contentEquals("ff") || 
						propBrowser.contentEquals("firefox")) {
					FirefoxOptions dc = new FirefoxOptions();

					if(propi.getProperty("webapp.headlessExecution").equals("true")){
						dc.setHeadless(true);
					}

					FirefoxProfile profile = new FirefoxProfile();
					
					profile.setAcceptUntrustedCertificates(true);
					profile.setAssumeUntrustedCertificateIssuer(false);
					
					profile.setPreference("app.update.enabled", false);
					profile.setPreference("browser.download.folderList", 2);
					profile.setPreference("browser.helperApps.alwaysAsk.force", false);
					
					if(propi.getProperty("webapp.incognito").equals("true")) {
						profile.setPreference("browser.private.browsing.autostart", true);
					}
					
					dc.setCapability(FirefoxDriver.PROFILE, profile);
					dc.setCapability("marionette", true);
					
					System.setProperty("webdriver.gecko.driver", 
							projectPath + "\\drivers\\firefox\\geckodriver.exe");
					driver = new FirefoxDriver(dc);
				}
				else {
					throw new IllegalArgumentException("Browser name is not correct or is not valid...");
				}	
			}
			else if((os.indexOf("nux")>=0) || (os.indexOf("nix")>=0)) {
				
				if(propBrowser.contentEquals("chrome")) {
					ChromeOptions op = new ChromeOptions();
					
					System.setProperty("webdriver.chrome.driver", 
							projectPath + "/drivers/chromedriver/chromedriver");
					
					if(propi.getProperty("webapp.headlessExecution").equals("true")){
						op.addArguments("--headless");
					}
					
					if(propi.getProperty("webapp.incognito").equals("true")) {
						op.addArguments("--incognito");
					}
					op.setExperimentalOption("useAutomationExtension",false);
					op.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
					
					op.setPageLoadStrategy(PageLoadStrategy.NONE);
                    op.addArguments("start-maximized");
                    op.addArguments("enable-automation");
                    op.addArguments("--disable-gpu");
                    op.addArguments("--disable-extensions");
                    op.addArguments("--no-sandbox");
                    op.addArguments("--disable-dev-shm-usage");
					
					driver = new ChromeDriver(op);	
				}
				else if(propBrowser.contentEquals("ff") || 
						propBrowser.contentEquals("firefox")) {
					FirefoxOptions dc = new FirefoxOptions();
					
					
					if(propi.getProperty("webapp.headlessExecution").equals("true")){
						dc.setHeadless(true);
					}
					
					FirefoxProfile profile = new FirefoxProfile();
					
					profile.setAcceptUntrustedCertificates(true);
					profile.setAssumeUntrustedCertificateIssuer(false);
					
					profile.setPreference("app.update.enabled", false);
					profile.setPreference("browser.download.folderLisy", 2);
					profile.setPreference("browser.helperApps.alwaysAsk.force", false);
					
					if(propi.getProperty("webapp.incognito").equals("true")) {
						profile.setPreference("browser.private.browsing.autostart", true);
					}
					
					dc.setCapability(FirefoxDriver.PROFILE, profile);
					dc.setCapability("marionette", true);
					
					System.setProperty("webdriver.gecko.driver", 
							projectPath + "/drivers/firefox/geckodriver");
					driver = new FirefoxDriver(dc);
				}
				else {
					throw new IllegalArgumentException("Browser name is not correct or is not valid...");
				}
			}
			else if((os.indexOf("mac")>=0) || (os.indexOf("darwin")>=0)) {
				
				if(propBrowser.trim().contentEquals("chrome")) {
					ChromeOptions op = new ChromeOptions();
					
					System.setProperty("webdriver.chrome.driver", 
							projectPath + "/drivers/chromedriver/chromedriver-77");
					
					if(propi.getProperty("webapp.headlessExecution").equals("true")){
						op.addArguments("--headless");
					}
					
					if(propi.getProperty("webapp.incognito").equals("true")) {
						op.addArguments("--incognito");
					}
					
					op.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
					
					op.setPageLoadStrategy(PageLoadStrategy.NONE);
                    op.addArguments("start-maximized");
                    op.addArguments("enable-automation");
                    op.addArguments("--disable-gpu");
                    op.addArguments("--disable-extensions");
                    op.addArguments("--no-sandbox");
                    op.addArguments("--disable-dev-shm-usage");
                    
					driver = new ChromeDriver(op);	
				}
				else if(propBrowser.contentEquals("ff") || 
						propBrowser.contentEquals("firefox")) {
					FirefoxOptions dc = new FirefoxOptions();

					if(propi.getProperty("webapp.headlessExecution").equals("true")){
						dc.setHeadless(true);
					}
					
					FirefoxProfile profile = new FirefoxProfile();
					
					profile.setAcceptUntrustedCertificates(true);
					profile.setAssumeUntrustedCertificateIssuer(false);
					
					profile.setPreference("browser.download.folderList", 2);
					profile.setPreference("browser.helperApps.alwaysAsk.force", false);
					
					if(propi.getProperty("webapp.incognito").equals("true")) {
						profile.setPreference("browser.private.browsing.autostart", true);
					}

					
					dc.setCapability(FirefoxDriver.PROFILE, profile);
					dc.setCapability("marionette", true);
					
					System.setProperty("webdriver.gecko.driver", 
							projectPath + "/drivers/firefox/geckodriver");
					driver = new FirefoxDriver(dc);
				}
				else {
					throw new IllegalArgumentException("Browser name is not correct or is not valid...");
				}
			}
			else {
				throw new IllegalArgumentException("OS out of scope ...");
			}
		}
		return driver;
	}
	
	@BeforeSuite
	public void init() {
		System.out.println("Before Suite");
		Log.startLog("Test Suite is starting");
		
		//Log.info("Initialize WebDriver instance");
		//driver= getDriver();
		
		Log.info("Deleting Cookies");
		driver.manage().deleteAllCookies();
		
		Log.info("Maximizing the browser window");
		driver.manage().window().maximize();
		
		Log.info("Applying the implicit wait during 10 seconds");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
	}
	
	@AfterSuite
	public void end() {
		try {
			System.out.println("After Suite");
			Log.endLog("Test Suite is ending");
		}
		catch(WebDriverException we) {
			Log.fatal(we.getMessage());
		}
		finally {
			if(driver !=null) {
				propi = null;
				driver = null;
			}
		}
		
	}
	
	@BeforeTest
	public void setup() {
		
		Log.info("Open the web site to test");
		driver.get("file:///" + projectPath + "/ejercicio/exercise.html");
		//CommonUtilities.sleepByNSeconds(5);

	}
	
	@AfterTest
	public void teardown() {
		try {
			driver.close();
		}
		catch(Exception e) {
			
		}
		
	}
	
	@AfterMethod
	public void takeScreenshotOnFailure(ITestResult res) {
		if(res.getStatus() == ITestResult.FAILURE) {
			Log.fatal("Taking screenshot of the failure with status as " + res.getStatus());
			CommonUtilities.takeScreenshot(driver, this.pathScreenshots, "FAILURE"+res.getName());
		}
	}
	
}
