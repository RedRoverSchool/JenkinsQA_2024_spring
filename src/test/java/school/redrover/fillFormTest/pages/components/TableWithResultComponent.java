package school.redrover.fillFormTest.pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static school.redrover.fillFormTest.tests.BaseTest.wait;

public class TableWithResultComponent {
    private final By tableWithResult = By.cssSelector(".table-responsive");

    public void checkTable(String key, String value) {
        WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(tableWithResult));

        String xpathExpression = String.format("//td[contains(text(), '%s')]/following-sibling::td", key);
        WebElement cellWithValue = table.findElement(By.xpath(xpathExpression));

        if (!cellWithValue.getText().equals(value)) {
            throw new AssertionError(String.format("Значение '%s' не соответствует ожидаемому '%s'", cellWithValue.getText(), value));
        }
    }
}
