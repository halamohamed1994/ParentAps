package Integration;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CreateEvent {
    WebDriver driver = new EdgeDriver();
    Helper helper = new Helper();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    @Given(": ParentAps website already opened")
    public void openParentApsWebsite() {
        driver.get("https://portal-staging.parent.cloud/login");
        driver.manage().window().maximize();
    }

    @And(": login with user credentials username {string} and password {string}")
    public void loginWithCredentials(String username, String password) {
        driver.findElement(By.id("txtEmail")).sendKeys(username);
        driver.findElement(By.id("txtPassword")).sendKeys(password);
        driver.findElement(By.id("submitBtn")).click();
    }

    @And(": User opened event that contain name {string}")
    public void openEventDetails(String name) {
        WebElement parentElement = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@class='institution__information'][.//div[@class='institution__name' and contains(text(), '" + name + "')]]")
        ));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", parentElement);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", parentElement);
    }

    @When(": Click on calendar icon from the top of page")
    public void openCalendarPage() {
        WebElement calendarIcon = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("i.picon-new_calendar")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", calendarIcon);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", calendarIcon);
    }

    @And(": Click on create event")
    public void clickOnCreateEvent() {
        WebElement createEventButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("createEventBtn")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", createEventButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", createEventButton);
    }

    @After
    public void closeWindow(){
        driver.close();
    }

    @Then(": Check all fields of creation are displayed successfully")
    public void assertOnCreationFields() {
        // Assert that the header is displayed
        Assert.assertTrue(helper.getElementByCssSelector("span.header-title",driver).isDisplayed());
        // Assert that the title is displayed
        Assert.assertTrue(helper.getElementByCssSelector("input#eventTitle",driver).isDisplayed());
        // Assert that the description is displayed
        Assert.assertTrue(helper.getElementByCssSelector("textarea[placeholder='Description']",driver).isDisplayed());
        // Assert that the Recipients dropdown is displayed
        Assert.assertTrue(helper.getElementByCssSelector("div[role='combobox'] input[type='text']",driver).isDisplayed());
        // Assert that the date picker is displayed
        Assert.assertTrue(helper.getElementByCssSelector("div.form-group input[placeholder='Select a date']",driver).isDisplayed());
        // Assert start date field is displayed
        Assert.assertTrue(helper.getElementByCssSelector("input#timepickerStartTime",driver).isDisplayed());
        // Assert end date field is displayed
        Assert.assertTrue(helper.getElementByCssSelector("input#timepickerEndTime",driver).isDisplayed());
        // Assert upload file field is displayed
        Assert.assertTrue(helper.getElementByCssSelector("#uploadFilesBtn",driver).isDisplayed());
        // Assert Reminder a day before event switcher is displayed
        Assert.assertTrue(helper.getElementByXpath("//span[text()='Reminder a day before event']/..",driver).isDisplayed());
        // Assert Reservation needed switcher is displayed
        Assert.assertTrue(helper.getElementByXpath("//span[text()='Reservation needed']/..",driver).isDisplayed());
        // Assert Reservation needed switcher is displayed
        Assert.assertTrue(helper.getElementByXpath("//span[text()='Reservation needed']/..",driver).isDisplayed());
        // Assert cancel button is displayed
        Assert.assertTrue(helper.getElementByCssSelector("#cancelCreateEventBtn",driver).isDisplayed());
        // Assert save button is displayed
        Assert.assertTrue(helper.getElementByCssSelector("#submitCreateEventBtn",driver).isDisplayed());
    }

    @Then(": Fill all fields with valid data")
    public void fillCreationFields() {
    }
}
