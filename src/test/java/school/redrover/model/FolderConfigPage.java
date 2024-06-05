package school.redrover.model;

import org.openqa.selenium.WebDriver;
import school.redrover.model.base.BaseConfigPage;

public class FolderConfigPage extends BaseConfigPage<FolderProjectPage, FolderConfigPage> {

    public FolderConfigPage(WebDriver driver) {
        super(driver, new FolderProjectPage(driver));
    }
}
