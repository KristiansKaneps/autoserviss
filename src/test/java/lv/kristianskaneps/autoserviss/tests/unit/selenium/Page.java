package lv.kristianskaneps.autoserviss.tests.unit.selenium;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Page {
    private static final Logger log = LoggerFactory.getLogger(Page.class);

    public static final long SHORT_WAIT_PERIOD_FOR_OBSERVER_CONVENIENCE = 500;

    protected WebDriver driver;

    public Page(WebDriver driver) {
        this.driver = driver;
    }

    public Page(WebDriver driver, String url) {
        this.driver = driver;
        this.driver.get(url);
    }

    public void waitForObserverConvenience() {
        sleep(SHORT_WAIT_PERIOD_FOR_OBSERVER_CONVENIENCE);
    }

    public void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            log.error("Thread interrupted", e);
        }
    }

    /**
     * @see <a href="https://www.seleniumeasy.com/selenium-tutorials/accessing-shadow-dom-elements-with-webdriver">accessing-shadow-dom-elements-with-webdriver</a>
     */
    public WebElement accessShadowDom(WebElement shadowDomHost) {
        return (WebElement) ((JavascriptExecutor) driver).executeScript("return arguments[0].shadowRoot", shadowDomHost);
    }

    public String getTitle() {
        return driver.getTitle();
    }
}
