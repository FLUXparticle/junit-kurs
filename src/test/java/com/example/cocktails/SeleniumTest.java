package com.example.cocktails;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.safari.*;

import static org.junit.jupiter.api.Assertions.*;

public class SeleniumTest {

    @Test
    void testGoogleSearch() {
        // Konfiguriere den Webbrowser-Treiber (Pfad zum ChromeDriver anpassen)
//        System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");

        // Erstelle eine Instanz des WebDriver für Chrome
        WebDriver driver = new SafariDriver();

        // Öffne die Google-Startseite
        driver.get("https://www.google.com");

        // Finde das Suchfeld durch seinen Namen ("q")
        WebElement searchBox = driver.findElement(By.name("q"));

        // Gib "Selenium" in das Suchfeld ein
        searchBox.sendKeys("Selenium");

        // Sende das Formular (Enter drücken)
        searchBox.submit();

        // Warte darauf, dass die Suchergebnisse geladen werden (kann je nach Netzwerkgeschwindigkeit variieren)
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Überprüfe, ob die Anzahl der Suchergebnisse mindestens 10 beträgt
        WebElement resultsStats = driver.findElement(By.id("result-stats"));
        String resultsText = resultsStats.getText();
        assertTrue(resultsText.contains(" results") && resultsText.matches(".*\\d+ results.*"));

        // Schließe den WebDriver
        driver.quit();
    }

}
