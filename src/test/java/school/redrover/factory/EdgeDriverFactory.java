package school.redrover.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class EdgeDriverFactory extends AbstractDriverFactory {

    private final EdgeOptions options;

    public EdgeDriverFactory() {
        this.options = new EdgeOptions();
    }

    public EdgeDriverFactory(EdgeOptions options) {
        this.options = options;
    }

    @Override
    protected WebDriver create() {
        return new EdgeDriver(options);
    }
}
