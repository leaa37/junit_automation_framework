package helpers;

import static org.junit.Assert.fail;

import java.util.Calendar;

import com.relevantcodes.extentreports.LogStatus;

import automation.MainTest;

public class LogHelper {

	public LogHelper() {
	}

	public void logInfo(String text) {
		log(text, false);
	}

	public void logError(String text) {
		log(text, true);
		fail(text);
	}

	public void log(String text, Boolean error) {
		Calendar calendar = Calendar.getInstance();
		String log = calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR) + " " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + "> " + text;
		System.out.println(log);
		if (MainTest.extentTest != null) {
			if (error) {
				MainTest.extentTest.log(LogStatus.ERROR, log);
			} else {
				MainTest.extentTest.log(LogStatus.INFO, log);
			}
			
		}
	}
}
