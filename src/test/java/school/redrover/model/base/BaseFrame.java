package school.redrover.model.base;

import org.openqa.selenium.WebDriver;

public abstract class BaseFrame<T extends BasePage<T>> extends BaseModel {

    private final T returnPage;

    public BaseFrame(WebDriver driver, T returnPage) {
        super(driver);
        this.returnPage = returnPage;
    }

    public T getReturnPage() {
        return returnPage;
    }
}
