package ui.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import ui.base.BasePage;
import ui.utilities.CommonUtilities;
import ui.utilities.Log;

public class ExercisePage extends BasePage{

	By byColumnHeaderList = By.xpath("//tbody[tr[th]]//th");
	By byColumnList;
	
	String str1 = "//table/tbody/tr/td[count(//table/thead/tr/th/preceding-sibling::th)+";
	String str2 = "]";
	
	List<WebElement> listHdr;
	List<WebElement> list;
	
	public ExercisePage(WebDriver driver) {
		super(driver);
	}
	
	public void sumValues() {
		int count = 1;
		
		listHdr = getListPresenceOfElementsLocated(byColumnHeaderList, 3);
		
		 for(WebElement e : listHdr){
			int sum = 0;
			byColumnList = By.xpath(str1 + count + str2);
			list = getListPresenceOfElementsLocated(byColumnList, 10);
			
			for(WebElement ele : list) {
				Log.info(ele.getText());
				sum += Integer.parseInt(ele.getText());
			}
			
			Log.info("The sum of column " + e.getText() + " is " + sum);
			count++;
		}
		
		CommonUtilities.sleepByNSeconds(3);
	}
	
}
