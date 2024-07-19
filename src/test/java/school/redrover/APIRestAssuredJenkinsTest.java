package school.redrover;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.Header;
import org.testng.annotations.Test;
import school.redrover.runner.BaseAPITest;
import school.redrover.runner.ProjectUtils;

@Epic("ApiRestAssuredTest")
public class APIRestAssuredJenkinsTest extends BaseAPITest {

    private static final String JOB_NAME = "this is the rest job name";

    @Test
    @Story("Create a freestyle job")
    @Description("")
    public void testCreateJob() {
        RestAssured.given()
                .filter(new AllureRestAssured())
                .log().ifValidationFails(LogDetail.ALL, true)
                .header(new Header("Authorization", getBasicAuthWithToken()))
                .contentType("multipart/form-data")
                .multiPart("name", JOB_NAME)
                .multiPart("mode", "hudson.model.FreeStyleProject")
                .post(ProjectUtils.getUrl() + "view/all/createItem/")
                .then()
                .log().ifValidationFails(LogDetail.ALL, true)
                .statusCode(302);
    }

    @Test(dependsOnMethods = "testCreateJob")
    @Story("Get all jobs")
    @Description("")
    public void testGetAllJobs() {
        RestAssured.given()
                .filter(new AllureRestAssured())
                .header(new Header("Authorization", getBasicAuthWithToken()))
                .get(ProjectUtils.getUrl() + "api/json/")
                .then()
                .statusCode(200);
    }

    @Story("Create a copy of exiting job")
    @Description("")
    @Test(dependsOnMethods = "testGetAllJobs")
    public void testCopyJob() {
        RestAssured.given()
                .filter(new AllureRestAssured())
                .log().ifValidationFails(LogDetail.ALL, true)
                .queryParam("name", JOB_NAME + "_copy")
                .queryParam("mode", "copy")
                .queryParam("from", JOB_NAME)
                .header(new Header("Authorization", getBasicAuthWithToken()))
                .post(ProjectUtils.getUrl() + "createItem")
                .then()
                .log().ifValidationFails(LogDetail.ALL, true)
                .statusCode(302);
    }

    @Story("Delete job by sending a http delete request")
    @Description("")
    @Test(dependsOnMethods = "testCopyJob")
    public void testDeleteJobViaHttpDelete() {
        RestAssured.given()
                .filter(new AllureRestAssured())
                .header(new Header("Authorization", getBasicAuthWithToken()))
                .delete(ProjectUtils.getUrl() + "job/" + JOB_NAME + "/")
                .then()
                .statusCode(204);
    }

    @Test(dependsOnMethods = "testCopyJob")
    @Story("Delete job by sending a http post request to /doDelete endpoint")
    @Description("")
    public void testDeleteJobViaDoDelete() {
        RestAssured.given()
                .filter(new AllureRestAssured())
                .header(new Header("Authorization", getBasicAuthWithToken()))
                .post(ProjectUtils.getUrl() + "job/" + JOB_NAME + "_copy/doDelete/")
                .then()
                .statusCode(302);
    }
}
