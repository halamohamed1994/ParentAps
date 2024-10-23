package Integration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
public class Helper {
    public WebElement getElementByCssSelector(String elementName , WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(elementName)
        ));
    }
    public WebElement getElementByXpath(String elementName , WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(elementName) // Select the parent label of the span with the specific text
        ));
    }
    public void scrollIntoView(WebElement element , WebDriver driver){
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }
    public void clickOnView(WebElement element , WebDriver driver){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
    public void sendKeyBySelectId(String elementName , String elementValue , WebDriver driver){
        driver.findElement(By.id(elementName)).sendKeys(elementValue);
    }
}
