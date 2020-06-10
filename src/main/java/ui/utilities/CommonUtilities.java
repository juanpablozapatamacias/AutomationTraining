package ui.utilities;

import java.awt.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

import org.monte.media.Format;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;

import static org.monte.media.AudioFormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class CommonUtilities {
	
	private static ScreenRecorder screenRecorder;
	
	public static void sleepByNSeconds(int sec) {
		try {
			Thread.sleep(sec * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Map<String,String> putSysProps(Enumeration e, Properties p){
		Map<String,String> hash = new HashMap<String,String>();
		
		while(e.hasMoreElements()) {
			String key = (String) e.nextElement();
			String value = p.getProperty(key);
			hash.put(key, value);
		}
		
		return hash;
	}
	
	public static void takeScreenshot(WebDriver driver, String filePath, String scenario){
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(Calendar.getInstance().getTime());
		try {
			TakesScreenshot scrShot = ((TakesScreenshot)driver);

			File fin = new File(filePath 
					+ "Screenshot_" 
					+ scenario 
					+ "_" +timeStamp 
					+ ".png");
			
			File srcFile = scrShot.getScreenshotAs(OutputType.FILE);
			
			File destFile = fin;
			
			FileUtils.copyFile(srcFile, destFile);
		}
		catch(IOException ioe) {}
		catch(Exception e) {}
	}
	
	public static void startRecording(String path){
		try {
			File file = new File(path);
			
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	        int width = screenSize.width;
	        int height = screenSize.height;
	        
	        Rectangle captureSize = new Rectangle(0,0,width,height);
	        
	        GraphicsConfiguration gc = GraphicsEnvironment
	                .getLocalGraphicsEnvironment()
	                .getDefaultScreenDevice()
	                .getDefaultConfiguration();
	        
	        screenRecorder = new ScreenRecorder(gc, captureSize,
	                new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
	                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
	                     CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
	                     DepthKey, 24, FrameRateKey, Rational.valueOf(15),
	                     QualityKey, 1.0f,
	                     KeyFrameIntervalKey, 15 * 60),
	                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black",
	                     FrameRateKey, Rational.valueOf(30)),
	                null, file);
	        screenRecorder.start();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void stopRecording() {
		try {
			screenRecorder.stop();
		}
		catch(Exception e) {}
	}
	
	public static Properties loadProperties(String path) {
		try (InputStream input = new FileInputStream(path)){
			Properties prop = new Properties();
			prop.load(input);
			
			return prop;
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return null;
	}
}