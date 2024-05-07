package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.model.base.BasePage;
import java.util.ArrayList;
import java.util.List;

public class PipelineSyntaxPage extends BasePage {

    public List<String> getActualListOfDocumentation(){
        List<String> actualList = new ArrayList<>();
        for (int i=2; i<=9; i++){
            String xPath = "//*[@id=\"tasks\"]/div["+ i + "]/span/a/span[2]";
            actualList.add(getDriver().findElement(By.xpath(xPath)).getText());
        }
        return actualList;
    }

    public static final List<String> listOfDocumentation = List.of("Snippet Generator", "Declarative Directive Generator",
            "Declarative Online Documentation", "Steps Reference",
            "Global Variables Reference", "Online Documentation", "Examples Reference",
            "IntelliJ IDEA GDSL");

    public PipelineSyntaxPage(WebDriver driver) {
        super(driver);
    }
}
