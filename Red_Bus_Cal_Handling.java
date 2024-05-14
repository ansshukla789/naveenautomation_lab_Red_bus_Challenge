package introduction;

import java.text.DateFormatSymbols;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Red_Bus_Cal_Handling {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		String ls = null;
		List<String> list1 = new ArrayList<String>();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications"); // To Stop Notification
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\Anshul\\Documents\\chromedriver-win64\\chromedriver.exe");// Setting Location of Web Driver
		WebDriver driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.get("https://www.redbus.in/");
		// Method Calling "Please Enter Proper Month and year"
		// example:- Apr 2024,Dec 2025,Feb 2024 as per as calendar Standard etc.
		try {
			month(driver, "Jun 2024");
		} catch (IllegalArgumentException e) {
			System.out.println("Please Enter Proper Month and Year");
		}

	}

	// block the previous month if user enter previous month its show error message
	static void month(WebDriver driver, String your_month) throws InterruptedException {
		String ls = null;
		List<String> list1 = new ArrayList<String>();
		String month_name[] = your_month.split(" ");
		int user_input_month_no = monthAbbrToNumber(month_name[0]);

		String year = month_name[1];

		int user_input_year_no = Integer.parseInt(year);

		driver.findElement(By.cssSelector("div[class='labelCalendarContainer']")).click();

		String month = driver
				.findElement(By.cssSelector("div div div[class='DayNavigator__IconBlock-qj8jdz-2 iZpveD']:nth-child(2"))
				.getText();

		String month1[] = month.split(" ");
		int calendar_month_no = monthAbbrToNumber(month1[0]);

		String year1[] = month1[1].split("\\R"); // Split on the basis of New Line

		int calendar_year_no = Integer.parseInt(year1[0]);

		if ((month.contains(your_month))) {
			System.out.println(month);
			List<WebElement> dates = driver
					.findElements(By.cssSelector("span[class='DayTiles__CalendarDaysSpan-sc-1xum02u-1 bwoYtA']"));

			for (int i = 0; i < driver
					.findElements(By.cssSelector("span[class='DayTiles__CalendarDaysSpan-sc-1xum02u-1 bwoYtA']"))
					.size(); i++) {
				ls = (dates.get(i).getText());

				list1.add(ls);

			}
			// converting List of String to List of Integer
			List<Integer> result = list1.stream().map(Integer::valueOf).collect(Collectors.toList());

			String date = driver
					.findElement(By.cssSelector("span[class='DayTiles__CalendarDaysSpan-sc-1xum02u-1 fgdqFw']"))
					.getText();

			// Current date in Integer Format
			int mon_date = Integer.parseInt(date);
			// Get the current day in String Format
			String dayNames[] = new DateFormatSymbols().getWeekdays();
			Calendar date11 = Calendar.getInstance();
			String Current_day = dayNames[date11.get(Calendar.DAY_OF_WEEK)];

			if (Current_day == "Saturday" || Current_day == "Sunday") {
				result.add(mon_date);
				List<Integer> weekends = result.stream().sorted().collect(Collectors.toList());
				System.out.println("The Weekends Dates Are :-" + weekends);
			} else {
				if (result.isEmpty()) {
					System.out.println("Sorry No Weekends Available !!!!!!");
				} else {
					System.out.println("The Weekends Dates Are :-" + result);
				}
			}

		} else {

			int year_diff = user_input_year_no - calendar_year_no;

			if (user_input_year_no >= calendar_year_no)

			{

				int month_diff = calendar_month_no - user_input_month_no;

				if ((month_diff < 1) || (year_diff > 0))

				{
					System.out.println(month);
					while (!(month.contains(your_month)))

					{
						driver.findElement(By.cssSelector(
								"div div div[class='DayNavigator__IconBlock-qj8jdz-2 iZpveD']:nth-child(3)")).click();
						month = driver
								.findElement(By.cssSelector(
										"div div div[class='DayNavigator__IconBlock-qj8jdz-2 iZpveD']:nth-child(2"))
								.getText();
						System.out.println(month);
					}

					List<WebElement> dates = driver.findElements(
							By.cssSelector("span[class='DayTiles__CalendarDaysSpan-sc-1xum02u-1 bwoYtA']"));

					for (int i = 0; i < driver
							.findElements(
									By.cssSelector("span[class='DayTiles__CalendarDaysSpan-sc-1xum02u-1 bwoYtA']"))
							.size(); i++) {
						ls = (dates.get(i).getText());

						list1.add(ls);

					}
					System.out.println("The Weekends Dates Are :-" + list1);
				}
			} else {
				System.out.println("please enter right month");
			}
		}
	}

	public static int monthAbbrToNumber(String abbreviation) {

		java.util.Optional<Month> monthOptional = Arrays.stream(Month.values())
				.filter(month -> month.name().substring(0, 3).equalsIgnoreCase(abbreviation)).findFirst();

		return monthOptional.orElseThrow(IllegalArgumentException::new).getValue();
	}
}
