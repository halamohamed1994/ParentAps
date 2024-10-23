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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ViewEventDetails {
    WebDriver driver = new EdgeDriver();;
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    Helper helper = new Helper();

    @Given(": ParentAps website is opened")
    public void openParentApsWebsite() {
        driver.get("https://portal-staging.parent.cloud/login");
        driver.manage().window().maximize();
    }
    @And(": login with user credentials username {string} , password {string}")
    public void loginWithCredentials(String username, String password) {
        driver.findElement(By.id("txtEmail")).sendKeys(username);
        driver.findElement(By.id("txtPassword")).sendKeys(password);
        driver.findElement(By.id("submitBtn")).click();
    }
    @When(": Open event that contain name {string}")
    public void openEventDetails(String name) {
        WebElement parentElement = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@class='institution__information'][.//div[@class='institution__name' and contains(text(), '" + name + "')]]")
        ));
        helper.scrollIntoView(parentElement,driver);
        helper.clickOnView(parentElement,driver);
    }

    @Then(": Check the details of event is returned successfully")
    public void assertOnEventDetails() {
        //Assert that header title is not empty
        Assert.assertFalse(getHeaderTitle().getText().isEmpty());
        String[] titleNames = {"Children", "Staff", "Staff ratio", "Actual", "Expected"};

        //Assert the number of titles displayed from the chart
        Assert.assertEquals(titleNames.length, getChartTitles().size());
        for (int i = 0 ; i < getChartTitles().size() ; i++) {
            List<WebElement> elementsName = (List<WebElement>) ((JavascriptExecutor) driver).executeScript("return arguments[0].getElementsByTagName('tspan');", getChartTitles().get(i));
            //Assert title names
            Assert.assertEquals(titleNames[i],elementsName.getFirst().getText());
        }

        // Assert the date picker is displayed
        Assert.assertTrue(getDatePicker().isDisplayed());
        String displayedDate = getDatePicker().getAttribute("value");
        //Assert current date is displayed
        Assert.assertEquals(displayedDate,LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMM yy")));

        String[] tabNames = {"Rooms", "Groups"};
        for (int i = 0 ; i < getTabItems().size() ; i++){
            String tabName = getTabItems().get(i).findElement(By.tagName("a")).getText().trim();
            //Assert on taps name
            Assert.assertTrue(tabName.contains(tabNames[i]));
        }

        // Assert the chart is displayed
        Assert.assertTrue(getChartElement().isDisplayed());

    }

    private WebElement getHeaderTitle(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.className("institution__header__content")
        ));
    }
    private List<WebElement> getChartTitles(){
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("g.highcharts-legend-item")
        ));
    }
    private WebElement getDatePicker(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("chartDatePicker")
        ));
    }
    private List<WebElement> getTabItems(){
        return driver.findElements(By.cssSelector("ul.nav.nav-tabs li.nav-item"));
    }
    private WebElement getChartElement(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("rect.highcharts-background")
        ));
    }
    @After
    public void closeWindow(){
        driver.quit();
    }


}
