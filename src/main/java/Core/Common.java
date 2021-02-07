package Core;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Common {

    public static AndroidDriver<WebElement> driver;

    public static AndroidDriver<WebElement> getDriver() throws IOException, InterruptedException {
        File app = new File("./WaterIllumination.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "android");
        capabilities.setCapability("fullReset", true);
        capabilities.setCapability("app", app.getAbsolutePath());
        AndroidDriver<WebElement> driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.findElement(By.id("button")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("button")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("button")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("button")).click();
        Thread.sleep(2000);
        return driver;
    }

    public static void ClickWhenVisible(AndroidDriver driver, By by) {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 40);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by)).click();
    }

    public static void ClickWhenVisibleByIndex(AndroidDriver driver, By by, int i) {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        List<WebElement> ListOfElements = driver.findElements(by);
        WebDriverWait wait = new WebDriverWait(driver, 40);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
        if (!ListOfElements.isEmpty()) {
            ListOfElements.get(i).click();
        }
    }

    public static boolean CheckElementOnDisplaying(AndroidDriver driver, By by) {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 40);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        if (driver.findElement(by).isDisplayed()) {
            return true;
        } else return false;
    }

    static void inputValue(AndroidDriver driver, By by, String value) {
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 40);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        if (!driver.findElements(by).isEmpty() && driver.findElement(by).isDisplayed())
            driver.findElement(by).click();
        driver.findElement(by).sendKeys(value);
    }

    static String getText(AndroidDriver driver, By by) {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        String str = driver.findElement(by).getText();
        return str;
    }

    static void scroll(AndroidDriver driver, int fromX, int fromY, int toX, int toY) {

        TouchAction action = new TouchAction(driver);
        action.press(PointOption.point(fromX, fromY))
                .waitAction(new WaitOptions().withDuration(Duration.ofMillis(3000)))    // you can change wait durations as
                .moveTo(PointOption.point(toX, toY)).release().perform();                // per your requirement
    }

    public static void scrollToText(AndroidDriver driver, String text) {
        driver.findElementByAndroidUIAutomator("new UiScrollable("
                + "new UiSelector().scrollable(true)).scrollIntoView(" + "new UiSelector().text(\"" + text + "\"));");
    }

    public static void scrollToObject(AndroidDriver driver, int y, int y2, By objectby, long timeout) throws InterruptedException {
        boolean isElementFound = driver.findElements(objectby).size() > 0;
        long t = System.currentTimeMillis();
        long end = t + timeout;
        while (!isElementFound) {
            Common.scroll(driver, 0, y, 0, y2);
            Thread.sleep(2000);
            isElementFound = driver.findElements(objectby).size() > 0;
            if (System.currentTimeMillis() > end) {
                throw new AssertionError("scroll out of time");
            }
        }
    }
}
