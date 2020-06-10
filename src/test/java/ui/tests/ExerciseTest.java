package ui.tests;

import org.testng.annotations.Test;

import ui.base.BaseTest;
import ui.utilities.Log;

import ui.pages.ExercisePage;

public class ExerciseTest extends BaseTest{
	
	public ExercisePage exercise= new ExercisePage(driver);
	
	@Test
	public void test1() {
		Log.info("test demo");
		exercise.sumValues();
	}

}
