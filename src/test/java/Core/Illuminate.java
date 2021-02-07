package Core;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;
import java.io.FileInputStream;
import java.util.Properties;

public class Illuminate {

    public static AndroidDriver<WebElement> driver;
    static Properties xpath = new Properties();
    static Properties id = new Properties();
    static Properties access = new Properties();
    static Properties automator = new Properties();

    @Test(priority = 1, testName = "illuminate")
    public void illuminate() throws InterruptedException {
        Common.ClickWhenVisible(driver, By.xpath("//*[@text = 'Осветить']"));
        Thread.sleep(5000);
        Common.CheckElementOnDisplaying(driver, By.xpath("//*[@text = 'Вода освещена\n" +
                "Наслаждайтесь']"));
    }

    @BeforeTest
    public void setUp() throws Exception {
        driver = Common.getDriver();
        xpath.load(new FileInputStream("./locators/xpath.properties"));
        id.load(new FileInputStream("./locators/id.properties"));
        access.load(new FileInputStream("./locators/Accessibility.properties"));
        automator.load(new FileInputStream("./locators/Automator.properties"));

    }

    @AfterTest
    public void tearDown() throws Exception {
        driver.quit();
    }
}
