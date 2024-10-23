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

import java.io.File;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
        helper.scrollIntoView(parentElement,driver);
        helper.clickOnView(parentElement,driver);
    }

    @When(": Click on calendar icon from the top of page")
    public void openCalendarPage() {
        WebElement calendarIcon = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("i.picon-new_calendar")));
        helper.scrollIntoView(calendarIcon,driver);
        helper.clickOnView(calendarIcon,driver);
    }

    @And(": Click on create event")
    public void clickOnCreateEvent() {
        WebElement createEventButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("createEventBtn")));
        helper.scrollIntoView(createEventButton,driver);
        helper.clickOnView(createEventButton,driver);
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
        WebElement uploadButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(., 'Upload or take a photo')]")));
        uploadButton.click();
        WebElement fileInput = driver.findElement(By.id("imageInput"));
        String filePath = new File("src/test/resources/test_file.png").getAbsolutePath();
        fileInput.sendKeys(filePath);

        driver.findElement(By.id("eventTitle")).sendKeys("test title");
        driver.findElement(By.id("eventDescription")).sendKeys("test description");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yy");
        String formattedDate = LocalDate.now().format(formatter);
        WebElement dateInput = driver.findElement(By.xpath("//input[@placeholder='Select a date']"));
        helper.scrollIntoView(dateInput,driver);
        helper.clickOnView(dateInput,driver);
        dateInput.sendKeys(formattedDate);

        WebElement startDate = driver.findElement(By.id("timepickerStartTime"));
        helper.scrollIntoView(startDate,driver);
        helper.clickOnView(startDate,driver);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("ui-timepicker-list")));
        WebElement firstDate = driver.findElement(By.xpath("//ul[contains(@class, 'ui-timepicker-list')]/li[1]"));
        helper.scrollIntoView(firstDate,driver);
        helper.clickOnView(firstDate,driver);

        WebElement endDate = driver.findElement(By.id("timepickerEndTime")); // Adjust if necessary
        helper.scrollIntoView(endDate,driver);
        helper.clickOnView(endDate,driver);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("ui-timepicker-list")));
        WebElement secondDate = driver.findElement(By.xpath("//ul[contains(@class, 'ui-timepicker-list')]/li[3]"));
        helper.scrollIntoView(secondDate,driver);
        helper.clickOnView(secondDate,driver);


        driver.findElement(By.id("submitCreateEventBtn")).click();

    }
}
