package school.redrover;

import com.google.common.net.HttpHeaders;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import school.redrover.runner.BaseAPITest;
import school.redrover.runner.ProjectUtils;

@Epic("ApiRestAssuredTest2")

public class APIRestAssuredMultiConfigTest extends BaseAPITest {

    private static final String MULTI_CONFIGURATION_PROJECT_NAME = "this is MultiConfig Project name";
    private static final String PROJECT_DESCRIPTION = "this is description";

    @Test
    @Story("Create the multi-configuration project")
    @Description("Verify that successful multi-configuration creation results in 302 status code")
    public void testCreateProject() {
        RestAssured.given()
                .header(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken())
                .formParam("name", MULTI_CONFIGURATION_PROJECT_NAME)
                .formParam("mode", "hudson.matrix.MatrixProject")
                .when()
                .post(ProjectUtils.getUrl() + "view/all/createItem/")
                .then()
                .statusCode(302);

    }

    @Test(dependsOnMethods = "testCreateProject")
    @Story("Search the project by name")
    @Description("Verify that a project can be found using the search box")
    public void testSearchProjectName() {
        RestAssured.given()
                .header(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken())
                .queryParam("q", MULTI_CONFIGURATION_PROJECT_NAME)
                .when()
                .get(ProjectUtils.getUrl() + "search/")
                .then()
                .statusCode(200)
                .body(Matchers.containsString(MULTI_CONFIGURATION_PROJECT_NAME));
    }

    @Test(dependsOnMethods = "testSearchProjectName")
    @Story("Add description")
    @Description("Verify that successful adding description to the project results in 302 status code")
    public void testAddDescription() {
        RestAssured.given()
                .header(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken())
                .formParam("description", PROJECT_DESCRIPTION)
                .when()
                .post(ProjectUtils.getUrl() + "job/"
                        + MULTI_CONFIGURATION_PROJECT_NAME + "/submitDescription")
                .then()
                .statusCode(302);
    }

    @Test(dependsOnMethods = "testAddDescription")
    @Story("Add description")
    @Description("Verify that the description of the project is successfully added")
    public void testVerifyDescriptionIsAdded() {
        RestAssured.given()
                .header(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken())
                .when()
                .get(ProjectUtils.getUrl() + "job/" + MULTI_CONFIGURATION_PROJECT_NAME + "/api/json")
                .then()
                .statusCode(200)
                .body("description", Matchers.equalTo(PROJECT_DESCRIPTION));
    }
}
