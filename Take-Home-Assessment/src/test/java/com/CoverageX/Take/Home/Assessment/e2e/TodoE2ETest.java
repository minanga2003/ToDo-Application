package com.CoverageX.Take.Home.Assessment.e2e;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
class TodoE2ETest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setup() {
        // Requires Homebrew chromedriver in PATH
        ChromeOptions opts = new ChromeOptions();
        opts.addArguments("--headless", "--disable-gpu", "--window-size=1280,800");
        driver = new ChromeDriver(opts);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void createAndCompleteTask() {
        driver.get("http://localhost:3000");

        // Fill form fields and submit
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input")))
                .sendKeys("Test Task");
        driver.findElement(By.cssSelector("textarea")).sendKeys("Test Description");
        driver.findElement(By.xpath("//button[text()='Add New Task']")).click();

        // Explicit wait: task should appear
        WebElement taskCard = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h6[contains(normalize-space(), 'Test Task')]")
        ));
        assertNotNull(taskCard, "Created task should be visible");

        // Click "Mark Complete"
        driver.findElement(By.xpath("//button[text()='Mark Complete']")).click();

        // Wait until task card disappears
        boolean removed = wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//h6[contains(normalize-space(), 'Test Task')]")
        ));
        assertTrue(removed, "Task should be removed after marking complete");
    }
}