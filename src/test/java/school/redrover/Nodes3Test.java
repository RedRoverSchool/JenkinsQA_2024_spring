package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

public class Nodes3Test extends BaseTest {

    @Test
    public void testCreateNewNode() {
        final String nodeName = "New Node";

        HomePage homePage = new HomePage(getDriver())
                .clickManageJenkins()
                .clickNodes()
                .clickNewNodeButton()
                .setNodeName(nodeName)
                .selectPermanentAgentRadioButton()
                .clickOkButton()
                .clickSaveButton()
                .clickLogo();

        Assert.assertTrue(homePage.getNodesList().contains(nodeName));
    }
}