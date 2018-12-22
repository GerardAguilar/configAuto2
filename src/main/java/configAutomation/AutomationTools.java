package configAutomation;

import java.awt.AWTException;
//import java.awt.GraphicsConfiguration;
//import java.awt.GraphicsEnvironment;
//import java.awt.MouseInfo;
//import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.List;
//import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
//import org.monte.media.Format;
//import org.monte.media.FormatKeys.MediaType;
//import org.monte.media.math.Rational;
//import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
//import org.openqa.selenium.Dimension;
//import org.openqa.selenium.ElementNotVisibleException;
//import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
//import static org.monte.media.AudioFormatKeys.*;
//import static org.monte.media.VideoFormatKeys.*;

import com.google.common.base.Function;
import com.sun.jna.NativeLibrary;

public class AutomationTools {
	WebDriver driver;
	public String fitnesseRootDirectory;
	public String homePage;
	String chromeBinaryLocation;
	int millisecondsToWait = 20000;
	VlcScreenRecorder recorder;
	
	public AutomationTools(String targetChrome, String targetHome) {
//		String classDirectory = new File(".").getAbsolutePath();
//		classDirectory = classDirectory.substring(0,classDirectory.length()-2);	
//		System.out.println("classDirectory: " + classDirectory);
//		
//		String pluginDirectory = classDirectory+"\\plugins";	  
//		System.out.println("pluginDirectory: "+ pluginDirectory);
//	    NativeLibrary.addSearchPath("libvlc", "C:\\Program Files\\VideoLAN\\VLC");
//	    System.setProperty("jna.library.path", pluginDirectory);
		
		String tempFitnesseDirectory = new File(".").getAbsolutePath();
		tempFitnesseDirectory = tempFitnesseDirectory.substring(0,tempFitnesseDirectory.length()-2);	
		tempFitnesseDirectory = tempFitnesseDirectory + "\\FitNesseRoot\\files\\";
		
		System.out.println("tempFitnessDirectory: " + tempFitnesseDirectory);
		
//		fitnesseRootDirectory = "C:\\eclipse-workspace\\configAutomation\\FitNesseRoot\\files\\";//TODO Gotta change this to be dynamic
		fitnesseRootDirectory = tempFitnesseDirectory;
//		chromeBinaryLocation = "C:\\GoogleChromePortable\\GoogleChromePortable.exe";//TODO Gotta change this to be dynamic
//		chromeBinaryLocation = "C:\\GoogleChromePortable\\App\\Chrome-bin\\chrome.exe";//outdated
		chromeBinaryLocation = targetChrome;
		homePage = targetHome;
		
		try {
			setupChrome(targetChrome);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("setupChrome failed");
			driver = new ChromeDriver();
			driver.manage().window().fullscreen();
			driver.get(homePage);
		}		
		
		recorder = new VlcScreenRecorder();
		recorder.setVideoSubdirectory("\\FitNesseRoot\\files\\");//TODO Gotta change this to be dynamic too
	}
//	//pairs chrome driver with chrome binary
//	private void setupChrome() throws Exception{
////		if(chromeBinaryLocation.length()>0) {
//
////			ClassLoader classLoader = getClass().getClassLoader();
////			URL chromeDriverResource;
////			chromeDriverResource = classLoader.getResource("chromedriver.exe");
////			if(chromeDriverResource==null) {
////				chromeDriverResource = classLoader.getResource("\\chromedriver.exe");
////				if(chromeDriverResource==null) {
////					chromeDriverResource = classLoader.getResource("/chromedriver.exe");
////					if(chromeDriverResource==null) {
////						chromeDriverResource = AutomationTools.class.getClassLoader().getResource("chromedriver.exe");
////						if(chromeDriverResource==null) {
////							chromeDriverResource = AutomationTools.class.getClassLoader().getResource("\\\\chromedriver.exe");
////							if(chromeDriverResource==null) {
////								chromeDriverResource = AutomationTools.class.getClassLoader().getResource("/chromedriver.exe");
////								if(chromeDriverResource==null) {
////									System.out.println("Well.....stick with copying the file onto the root folder then");
////								}
////							}
////						}
////					}
////				}	
////			}
////			
////			System.out.println("chromeDriverResource: " + chromeDriverResource.toString());
//			
////	        URL chromeDriverResource1 = classLoader.getResource("/chromedriver.exe");
////	        URL chromeDriverResource2 = AutomationTools.class.getClassLoader().getResource("chromedriver.exe");
////	        URL googleChromePortableResource = classLoader.getResource("/GoogleChromePortable/GoogleChromePortable.exe");
////	        URL googleChromePortableResource1 = classLoader.getResource("GoogleChromePortable/GoogleChromePortable.exe");
////	        URL googleChromePortableResource2 = AutomationTools.class.getClassLoader().getResource("GoogleChromePortable/GoogleChromePortable.exe");
////	        URL chromePortable = classLoader.getResource(".\\GoogleChromePortable\\GoogleChromePortable.exe");
////			File chromedriver = new File("Driver"+"\\chromedriver.exe");//this seems to have issues being created when triggered from a jar file, likely due to the location
//
////		/***
////		 * From our resources folder, copy chromedriver.exe into a Driver folder
////		 * Modify that chrome driver to attach to the chrome binary as designated in the Fitnesse table
////		 */
//		File chromedriver = new File("chromedriver.exe");
//		System.out.println("Location of chromedriver.exe is " + chromedriver.getAbsolutePath());
////            
////		why do I have to copy it if it's already in my resources? Because I can't get getResource to work
//		if (!chromedriver.exists()) {
//           	chromedriver.createNewFile();
//            FileUtils.copyURLToFile(chromeDriverResource, chromedriver);
//        }else {
//          	System.out.println("chromedriver.exe already exists");
//        }
//		String chromeDriverLocation = chromedriver.getAbsolutePath();
//
//	        
//		String chromeDriverLocation = chromeDriverResource.getPath();		
//        System.out.println("chromeDriverResource.getPath() "+ chromeDriverLocation);
//		String googleChromePortableLocation = googleChromePortableResource.getPath();
//        System.out.println("googleChromePortableResource: " + googleChromePortableLocation);
////	        System.out.println("chromeDriver's absolute path: " + chromeDriverLocation);
//
//		ChromeOptions options = new ChromeOptions();
////			options.setBinary(googleChromePortableLocation);
//		options.addArguments("disable-infobars");
////			options.addArguments("--allow-file-access-from-files");			
//		System.setProperty("webdriver.chrome.driver", chromeDriverLocation);             
////			System.setProperty("webdriver.chrome.driver", chromeDriverLocation);
//		driver = new ChromeDriver(options);
//	    driver.get("about:blank");
//		Dimension d = new Dimension(420,660);
//		driver.manage().window().setSize(d);
//		driver.get(homePage);
////		}		
//	}
	
	public void setupChrome(String targetChrome) throws Exception{
		/***
		 * From our resources folder, copy chromedriver.exe into a Driver folder
		 * Modify that chrome driver to attach to the chrome binary as designated in the Fitnesse table
		 */
		ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("chromedriver.exe");//the copying works, so this URL should be valid
//		File chromedriver = new File("Driver"+"\\chromedriver.exe");//this seems to have issues being created when triggered from a jar file, likely due to the location
		File chromedriver = new File("chromedriver.exe");
//		File chromeBinary = new File(chromeBinaryLocation);
		File chromeBinary = new File(targetChrome);
		System.out.println("Location of chromedriver.exe is " + chromedriver.getAbsolutePath());
		System.out.println("resource: "+resource.toString());
		System.out.println("chrome binary location: " + chromeBinary.getAbsolutePath());
        if (!chromedriver.exists()) {
        	chromedriver.createNewFile();
            FileUtils.copyURLToFile(resource, chromedriver);
        }else {
        	System.out.println("chromedriver.exe already exists");
        }
		String chromeDriverLocation = chromedriver.getAbsolutePath();
        
		ChromeOptions options = new ChromeOptions();
		//this might be the issue with the chrome setup failing
//		options.addArguments("--headless");
		options.setBinary(chromeBinary.getAbsolutePath());
//		options.addArguments("disable-infobars");
//		options.addArguments("--allow-file-access-from-files");
//		options.addArguments("--disable-dev-shm-usage"); 

		System.setProperty("webdriver.chrome.driver", chromeDriverLocation);              
		driver = new ChromeDriver(options);
	    driver.get("about:blank");
		Dimension d = new Dimension(1920,1080);
		driver.manage().window().setSize(d);
		driver.manage().window().maximize();
		driver.get(homePage);
	}
	
	public void closeBrowser() {
//		driver.close();		
		driver.quit();
	}
	
	public void waitForPresence(final String customAttributeIdPair) {
		
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
			    .withTimeout(Duration.ofMillis(millisecondsToWait))
			    .pollingEvery(Duration.ofMillis(500))
			    .ignoring(NoSuchElementException.class);
				
		//Check for element being present
		wait.until(new Function<WebDriver, Boolean>(){
			public Boolean apply(WebDriver driverCopy) {
				Boolean elementIsPresent = false;
				try {
					WebElement tempElement = driver.findElement(By.cssSelector("*["+customAttributeIdPair+"]"));	
					if(tempElement!=null) {
						elementIsPresent = true;
					}else {
						elementIsPresent = false;
					}
				}catch(org.openqa.selenium.StaleElementReferenceException ex) {
					WebElement tempElement = driver.findElement(By.cssSelector("*["+customAttributeIdPair+"]"));		
					if(tempElement!=null) {
						elementIsPresent = true;
					}else {
						elementIsPresent = false;
					}
				}catch(org.openqa.selenium.ElementNotVisibleException ex) {
					WebElement tempElement = driver.findElement(By.cssSelector("*["+customAttributeIdPair+"]"));			
					if(tempElement!=null) {
						elementIsPresent = true;
					}else {
						elementIsPresent = false;
					}
				}
				return elementIsPresent;						
			}
		});
	}
	
	public void waitForVisibility(final String customAttributeIdPair) {
		
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
			    .withTimeout(Duration.ofMillis(millisecondsToWait))
			    .pollingEvery(Duration.ofMillis(500))
			    .ignoring(NoSuchElementException.class);
				
		//Check for element being present
		wait.until(new Function<WebDriver, Boolean>(){
			public Boolean apply(WebDriver driverCopy) {
				Boolean elementIsVisible = false;
				try {
					WebElement tempElement = driver.findElement(By.cssSelector("*["+customAttributeIdPair+"]"));	
					if(tempElement.isDisplayed()) {
						elementIsVisible = true;
					}else {
						elementIsVisible = false;
					}
				}catch(org.openqa.selenium.StaleElementReferenceException ex) {
					WebElement tempElement = driver.findElement(By.cssSelector("*["+customAttributeIdPair+"]"));		
					if(tempElement.isDisplayed()) {
						elementIsVisible = true;
					}else {
						elementIsVisible = false;
					}
				}catch(org.openqa.selenium.ElementNotVisibleException ex) {
					WebElement tempElement = driver.findElement(By.cssSelector("*["+customAttributeIdPair+"]"));			
					if(tempElement.isDisplayed()) {
						elementIsVisible = true;
					}else {
						elementIsVisible = false;
					}
				}
				return elementIsVisible;						
			}
		});
	}
	
	public void select(final String customAttributeIdPair, String value) {
		waitForPresence(customAttributeIdPair);
		waitForVisibility(customAttributeIdPair);

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
			    .withTimeout(Duration.ofMillis(millisecondsToWait))
			    .pollingEvery(Duration.ofMillis(500))
			    .ignoring(NoSuchElementException.class);
		
		wait.until(new Function<WebDriver, Boolean>() 
		{
			public Boolean apply(WebDriver driverCopy) {
				Select select = new Select(driver.findElement(By.cssSelector("*["+customAttributeIdPair+"]")));
				int count = select.getOptions().size();
				boolean selectHasOptions = count>1;
	            System.out.println("count: " + count);
	            return selectHasOptions;
			}
		});		
		
		System.out.println("select: "+customAttributeIdPair + "|"+value);
		WebElement mySelectElement = driver.findElement(By.cssSelector("*["+customAttributeIdPair+"]"));
		Select dropdown = new Select(mySelectElement);

		List<WebElement> webElementList = dropdown.getOptions();
		for(int i=0; i<webElementList.size(); i++) {
			System.out.println("Dropdown " + i + ": "+ webElementList.get(i).getText());
		}
//		dropdown.selectByValue(value);
		dropdown.selectByVisibleText(value);
	}
	
	public void click(String customAttributeIdPair, String value) {
		waitForPresence(customAttributeIdPair);
		waitForVisibility(customAttributeIdPair);
		WebElement element = driver.findElement(By.cssSelector("*["+customAttributeIdPair+"]"));		
		element.click();
	}
	
	//TODO: Mouse manipulation that focuses on the interaction of the canvas, may need to be on a different class
	public void mouse(String actionType, String selection) {
		int xOffset=0;
		int yOffset=0;
		if(!selection.equals("")) {
			try {
				xOffset = Integer.parseInt(selection.split(",")[0]);
				yOffset = Integer.parseInt(selection.split(",")[1]);			
			}catch(NumberFormatException e) {
				System.out.println(selection + " couldn't be parsed into two separate Integers");
				xOffset = 0;
				yOffset = 0;
			}
			if(actionType.equals("move")) {
				mouseMove(xOffset, yOffset);				
			}else if(actionType.equals("drag")) {
				mouseDrag(xOffset, yOffset);				
			}			
		}
		else {
			if(actionType.equals("click")) {
				mouseManualClick();
			}else if(actionType.equals("hold")) {
				mouseHold();
			}else if(actionType.equals("release")) {
				mouseRelease();
			}
		}
	}
	
	public void mouseManualClick(){
		mouseHold();
		mouseRelease();
	}
	public void mouseHold() {
		try {
			Robot robot = new Robot();
			robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		} catch (AWTException e) {
			e.printStackTrace();
		}		
	}
	public void mouseRelease() {
		try {
			Robot robot = new Robot();
			robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		} catch (AWTException e) {
			e.printStackTrace();
		}		
	}
	public void mouseMove(int xOffset, int yOffset) {
			try {			
				Robot robot = new Robot();
				robot.mouseMove(xOffset, yOffset);
			} catch (AWTException e) {
				e.printStackTrace();
			}			
	}
	
	public void mouseDrag(int xOffset, int yOffset) {
		WebElement target = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/canvas[1]"));
		Actions builder = (new Actions(driver)).dragAndDropBy(target, xOffset, yOffset);
		builder.perform();
	}		

	@SuppressWarnings("unused")
	public String screenshot(String sheetName, String configName, int optionCount) {
		File screenshotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		Long timestamp = (new Timestamp(System.currentTimeMillis())).getTime();
		String currentFilename = fitnesseRootDirectory+""+sheetName+"_"+configName+".png";
		
		try {
			FileUtils.copyFile(screenshotFile, new File(currentFilename));					
		} catch (IOException e) {
			e.printStackTrace();
		}				
		return image(sheetName, configName, optionCount);
	}
	
	public String image(String sheetName, String configName, int optionCount) {
		optionCount--;
		String imageString ="<img src='http://localhost/files/"+sheetName+"_"+configName+".png' width='600'>";		
		return imageString;
	}	
	
	//http://www.experimentalqa.com/2017/11/record-selenium-test-video-in-mp4.html
	public void startRecording(String testName) {
		recorder.startRecording(testName);
	}
	public void stopRecording() {
		recorder.stopRecording();
//		recorder.releaseRecordingResources();
	}


	

}
