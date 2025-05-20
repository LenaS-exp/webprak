package ru.msu.cmc.webprak;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.openqa.selenium.support.ui.ExpectedConditions.not;
import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElement;

import ru.msu.cmc.webprak.model.dao.FlightsDAO;
import ru.msu.cmc.webprak.model.dao.PassangersDAO;
import ru.msu.cmc.webprak.model.dao.impl.FlightsDAOImpl;
import ru.msu.cmc.webprak.model.entity.Flights;
import ru.msu.cmc.webprak.model.dao.impl.PassangersDAOImpl;
import ru.msu.cmc.webprak.model.entity.Passangers;

import java.time.Duration;
import java.util.Collection;
import java.util.List;

public class WebprakSeleniumTests {

    String BASE_URL = "http://localhost:8080";
    WebDriver driver;
    WebDriverWait wait;




    @BeforeClass
    public void settings() {
        final String ffDriver = "/usr/local/bin";
        System.setProperty("webdriver.firefox.marionette", ffDriver);
        FirefoxOptions options = new FirefoxOptions();
        driver = new FirefoxDriver(options);
        driver.manage().window().setSize(new Dimension(1200, 1000));

        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver,30);
    }

    @Test
    public void test1_SearchFlights() {
        System.out.println("\n=== Тест 1: Поиск рейсов по направлениям и датам ===");
        driver.get(BASE_URL + "/flights");

        WebElement departure = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("departureCity")));
        departure.sendKeys("Москва");

        WebElement arrival = driver.findElement(By.name("arrivalCity"));
        arrival.sendKeys("Санкт-Петербург");

        WebElement dateFrom = driver.findElement(By.name("departureTimeFrom"));
        dateFrom.sendKeys("2025-05-22");

        WebElement dateTo = driver.findElement(By.name("departureTimeTo"));
        dateTo.sendKeys("2025-05-23");

        WebElement seats = driver.findElement(By.name("minSeats"));
        seats.sendKeys("2");

        WebElement searchBtn = driver.findElement(By.cssSelector("button[type='submit']"));
        searchBtn.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("flight-card")));
        List<WebElement> flights = driver.findElements(By.className("flight-card"));
        assert flights.size() > 0 : "Не найдено рейсов по заданным критериям";
        System.out.println("Найдено " + flights.size() + " рейсов");
    }

    @Test
    public void test2_ViewTicketPrices() {
        System.out.println("\n=== Тест 2: Просмотр информации о ценах на билеты ===");

        try {
            driver.get(BASE_URL + "/flights");
            wait.until(ExpectedConditions.titleContains("Авиарейсы"));

            WebElement firstFlight = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector(".flight-card")));

            WebElement bookButton = firstFlight.findElement(
                    By.cssSelector("a.btn-book[href^='/flights/book/']"));
            String href = bookButton.getAttribute("href");
            String flightId = href.substring(href.lastIndexOf('/') + 1);

            System.out.println("Найден рейс с ID: " + flightId);
            bookButton.click();

            wait.until(ExpectedConditions.urlContains("/flights/book/" + flightId));
            wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//h2[contains(text(), 'Бронирование билета')]")));


            WebElement priceSummary = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector(".price-summary")));

            WebElement priceElement = priceSummary.findElement(
                    By.xpath(".//p[contains(., 'Цена билета:')]/span"));

            String priceText = priceElement.getText();
            System.out.println("Цена билета: " + priceText);

            try {
                double price = Double.parseDouble(priceText.replaceAll("[^\\d.]", ""));
                assert price > 0 : "Цена должна быть больше 0";
            } catch (NumberFormatException e) {
                throw new AssertionError("Не удалось преобразовать цену в число: " + priceText);
            }
            WebElement finalPriceElement = priceSummary.findElement(By.id("finalPrice"));
            String finalPriceText = finalPriceElement.getText();
            assert finalPriceText.equals(priceText) :
                    "Итоговая цена " + finalPriceText + " не совпадает с ценой билета " + priceText;

        } catch (Exception e) {
            System.err.println("Ошибка в тесте 2: " + e.getMessage());

            throw e;
        }
    }

    @Test
    public void test3_SearchClientsByFlightAndFilters() {
        System.out.println("\n=== Тест 3: Поиск клиентов по рейсу, авиакомпании и статусу билета ===");

        try {

            driver.get(BASE_URL + "/clients");
            wait.until(ExpectedConditions.titleContains("Клиенты"));

            WebElement flightNumberInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.id("flightNumber")));
            flightNumberInput.sendKeys("8");

            WebElement airlineSelect = driver.findElement(By.id("airlineId"));
            Select airlineDropdown = new Select(airlineSelect);
            airlineDropdown.selectByVisibleText("S7 Airlines");

            WebElement ticketStatusSelect = driver.findElement(By.id("ticketStatus"));
            Select ticketStatusDropdown = new Select(ticketStatusSelect);
            ticketStatusDropdown.selectByVisibleText("Не оплачен");

            WebElement searchBtn = driver.findElement(By.xpath("//button[contains(., 'Поиск')]"));
            searchBtn.click();

            wait.until(ExpectedConditions.urlContains("flightNumber=8"));
            wait.until(ExpectedConditions.urlContains("ticketStatus=UNPAID"));

            WebElement totalCount = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector(".total-count span")));
            System.out.println("Результат поиска: " + totalCount.getText());

            List<WebElement> clients = driver.findElements(By.cssSelector(".client-card"));
            if (clients.isEmpty()) {
                WebElement emptyMessage = driver.findElement(By.cssSelector(".alert-info"));
                System.out.println(emptyMessage.getText());
                assert emptyMessage.getText().contains("Клиенты не найдены");
            } else {
                System.out.println("Найдено клиентов: " + clients.size());

                WebElement firstClient = clients.get(0);

                WebElement clientName = firstClient.findElement(By.cssSelector("h5 a"));
                System.out.println("Клиент: " + clientName.getText());

                WebElement clientEmail = firstClient.findElement(By.xpath(".//p[contains(., 'Email:')]/span"));
                System.out.println("Email: " + clientEmail.getText());

                WebElement clientPhone = firstClient.findElement(By.xpath(".//p[contains(., 'Телефон:')]/span"));
                System.out.println("Телефон: " + clientPhone.getText());
                WebElement editBtn = firstClient.findElement(By.xpath(".//a[contains(., 'Редактировать')]"));
                WebElement deleteBtn = firstClient.findElement(By.xpath(".//button[contains(., 'Удалить')]"));
                assert editBtn.isDisplayed() && deleteBtn.isDisplayed();
            }

            WebElement resetBtn = driver.findElement(By.xpath("//a[contains(., 'Сбросить')]"));
            resetBtn.click();

            wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("flightNumber=")));
            wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("ticketStatus=")));

        } catch (Exception e) {
            System.err.println("Ошибка в тесте 3: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void test4_RegisterNewClient() {
        System.out.println("\n=== Тест 4: Регистрация нового клиента ===");
        try {
            driver.get(BASE_URL + "/register");
            wait.until(ExpectedConditions.titleContains("Регистрация"));

            WebElement firstName = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("firstName")));
            firstName.sendKeys("Иван");

            WebElement lastName = driver.findElement(By.name("lastName"));
            lastName.sendKeys("Петров");

            String email = "test" + System.currentTimeMillis() + "@example.com";
            WebElement emailField = driver.findElement(By.name("email"));
            emailField.sendKeys(email);

            WebElement phone = driver.findElement(By.name("phone"));
            phone.sendKeys("+79991234567");

            WebElement password = driver.findElement(By.name("password"));
            password.sendKeys("SecurePass123!");

            WebElement registerButton = driver.findElement(By.cssSelector("button[type='submit']"));
            wait.until(ExpectedConditions.elementToBeClickable(registerButton)).click();
            wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/register")));
            System.out.println("Новый клиент успешно зарегистрирован: " + email);


        } catch (Exception e) {
            System.err.println("Ошибка в тесте 4: " + e.getMessage());
            throw e;
        }

    }

    @Test
    public void test5_EditClientInfo() {
        System.out.println("\n=== Тест 5: Редактирование информации о клиенте ===");

        try {
            driver.get(BASE_URL + "/clients");
            WebElement firstClient = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("client-card")));
            WebElement editBtn = firstClient.findElement(By.xpath(".//a[contains(text(), 'Редактировать')]"));
            editBtn.click();

            WebElement phone = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("phoneNumber")));
            phone.clear();
            String newPhone = "+7 (999) 888-77-66";
            phone.sendKeys(newPhone);

            WebElement saveBtn = driver.findElement(By.xpath("//button[contains(text(), 'Сохранить')]"));
            saveBtn.click();

            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("info-label")));
            WebElement phoneElement = driver.findElement(By.xpath("//span[@class='info-label'][contains(text(),'Телефон:')]/following-sibling::span"));

            assert phoneElement.isDisplayed();
            String actualPhone = phoneElement.getText();
            Assert.assertEquals(actualPhone, newPhone, "Номер телефона не соответствует ожидаемому");
            System.out.println("Телефон клиента успешно изменен на " + newPhone);
        } catch (Exception e) {
            System.err.println("Ошибка в тесте 5: " + e.getMessage());
            throw e;
        }

    }
    @Test
    public void test6_AddFlight() {

        try {
            driver.get(BASE_URL + "/flights/add");

            Select depAirportSelect = new Select(driver.findElement(By.name("depAirportId")));
            depAirportSelect.selectByIndex(1);

            Select arrAirportSelect = new Select(driver.findElement(By.name("arrAirportId")));
            arrAirportSelect.selectByIndex(2);

            Select airlineSelect = new Select(driver.findElement(By.name("airlineId")));
            airlineSelect.selectByIndex(1);

            Select aircraftSelect = new Select(driver.findElement(By.name("aircraftId")));
            aircraftSelect.selectByIndex(1);

            WebElement timeDepInput = driver.findElement(By.id("timeDepart"));
            timeDepInput.sendKeys("2025-02-20T02:20");

            WebElement timeArrInput = driver.findElement(By.id("timeArrive"));
            timeArrInput.sendKeys("2025-02-20T02:22");

            WebElement seatNumInput = driver.findElement(By.name("seatNum"));
            seatNumInput.sendKeys("150");

            WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));
            submitButton.click();
            wait.until(ExpectedConditions.titleIs("Авиарейсы"));
        } catch (Exception e) {
            System.err.println("Ошибка в тесте 6: " + e.getMessage());
            throw e;
        }

    }

    @Test
    public void test7_EditFlightInfo() {
        System.out.println("\n=== Тест 7: Редактирование информации о рейсе ===");
        try {
            driver.get(BASE_URL + "/flights");

            WebElement firstFlight = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("flight-card")));
            WebElement editBtn = firstFlight.findElement(By.xpath(".//a[contains(@class, 'btn-warning')]"));
            editBtn.click();

            WebElement seats = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("availableSeatNum")));
            seats.clear();
            String newSeats = "10";
            seats.sendKeys(newSeats);

            WebElement timearr = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("timeArr")));
            WebElement timedep = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("timeDep")));
            timedep.sendKeys("2025-05-22T00:00");
            timearr.sendKeys("2025-05-22T05:00");

            WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));
            submitButton.click();

            wait.until(ExpectedConditions.urlContains("/flights"));
            WebElement seatsElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//div[@class='flight-seats']//div[contains(., 'Свободно: 10')]")));
            assert seatsElement.isDisplayed();
            System.out.println("Количество мест успешно изменено на " + newSeats);
        }
        catch (Exception e) {
            System.err.println("Ошибка в тесте 7: " + e.getMessage());
            throw e;
        }

    }

    @Test
    public void test8_DeleteFlight() {
        System.out.println("\n=== Тест 8: Удаление рейса ===");
        driver.get(BASE_URL + "/flights");
        WebElement firstFlight = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("flight-card")));
        String flightId = firstFlight.getAttribute("data-flight-id");
        WebElement deleteBtn = firstFlight.findElement(By.xpath(".//a[contains(@class, 'btn-danger')]"));
        deleteBtn.click();

        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        alert.accept();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@data-flight-id='" + flightId + "']")));
        System.out.println("Рейс " + flightId + " успешно удален");
    }


    @AfterClass
    public void finish() {
        driver.quit();
    }
}