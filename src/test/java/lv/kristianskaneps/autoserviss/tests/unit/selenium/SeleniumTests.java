package lv.kristianskaneps.autoserviss.tests.unit.selenium;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URI;

public class SeleniumTests {
    private static final Logger log = LoggerFactory.getLogger(SeleniumTests.class);

    private static WebDriver driver;

    protected Page homepage;

//    @BeforeEach
    public void beforeEach() throws MalformedURLException {
        if (driver != null) {
            return;
        }

        final String useWebDriver = "remote;chrome";

        if ("chrome".equalsIgnoreCase(useWebDriver)) {
            log.info("Creating ChromeDriver");
            driver = new ChromeDriver();
        } else if (useWebDriver != null && useWebDriver.startsWith("remote")) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            String[] split = useWebDriver.split(";");
            if (split.length == 2) {
                capabilities.setBrowserName(split[1]);
            }

            log.info("Creating RemoteWebDriver with capabilities: {}", capabilities);
            driver = new RemoteWebDriver(URI.create("http://localhost:4444/").toURL(), capabilities);
        } else {
            log.error("No support: {}", useWebDriver);
            throw new RuntimeException("No support for " + useWebDriver);
        }

        homepage = new Page(driver, "http://localhost:8080");
    }

//    @AfterEach
    public void afterEach() {
        log.info("Closing WebDriver session");
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

//    @Test
    public void checkHomepagePageTitle() {
        String title = homepage.getTitle();
        log.warn("Info: {} \"{}\"", driver.getCurrentUrl(), title);
        Assertions.assertTrue("Home".equals(title) || "Sākums".equals(title));
    }
}
