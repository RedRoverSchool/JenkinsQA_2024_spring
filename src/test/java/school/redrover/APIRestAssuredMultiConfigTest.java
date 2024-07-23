package school.redrover;

import com.google.common.net.HttpHeaders;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import org.hamcrest.Matchers;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.testng.annotations.Test;
import school.redrover.runner.BaseAPITest;
import school.redrover.runner.ProjectUtils;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.testng.Assert.assertNotNull;

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
    @Story("Create the multi-configuration project")
    @Description("Verify error message for project creation with duplicate name.")
    public void testCreateProjectWithDuplicateName() {
        String responseBody = given()
                .log().ifValidationFails(LogDetail.ALL, true)
                .header(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken())
                .formParam("name", MULTI_CONFIGURATION_PROJECT_NAME)
                .formParam("mode", "hudson.matrix.MatrixProject")
                .when()
                .log().ifValidationFails(LogDetail.ALL, true)
                .post(ProjectUtils.getUrl() + "view/all/createItem/")
                .then()
                .statusCode(400)
                .extract().response()
                .getBody().asString();

        Document document = Jsoup.parse(responseBody);
        Element element = document.select("p").first();

        assertNotNull(element, "The <p> element was not found in the response.");
        assertThat(element.text(), containsString(
                "A job already exists with the name ‘" + MULTI_CONFIGURATION_PROJECT_NAME + "’"));
    }

    @Test(dependsOnMethods = "testCreateProjectWithDuplicateName")
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
