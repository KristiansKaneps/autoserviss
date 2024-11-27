package lv.kristianskaneps.autoserviss.tests.unit.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FormPage extends Page {
    public FormPage(WebDriver driver) {
        super(driver);
    }

    public void enterForm(String name, String phone) {
        waitForObserverConvenience();

        WebElement shadowDomHost = accessShadowDom(driver.findElement(By.tagName("forms-page")));
        shadowDomHost.findElement(By.cssSelector(".nameentry")).sendKeys(name);
        shadowDomHost.findElement(By.cssSelector(".phoneentry")).sendKeys(phone);
        shadowDomHost.findElement(By.tagName("button")).click();

        waitForObserverConvenience();
    }
}
