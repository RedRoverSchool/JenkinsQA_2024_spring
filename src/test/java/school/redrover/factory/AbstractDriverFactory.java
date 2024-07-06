package school.redrover.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;

public abstract class AbstractDriverFactory implements DriverFactory {

    public WebDriver getDriver() {
        return new EventFiringDecorator<>(
                new CustomWebDriverListener())
                .decorate(create());
    }

    protected abstract WebDriver create();
}
