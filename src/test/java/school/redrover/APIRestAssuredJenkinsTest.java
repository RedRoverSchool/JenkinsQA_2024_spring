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

@Epic("RestAssured Jenkins Api tests")
public class APIRestAssuredJenkinsTest extends BaseAPITest {

    private static final String JOB_NAME = "this is the rest job name";

    @Test
    @Story("Create job")
    @Description("Create a freestyle job")
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
    @Story("Read job")
    @Description("Get all jobs")
    public void testGetAllJobs() {
        RestAssured.given()
                .filter(new AllureRestAssured())
                .header(new Header("Authorization", getBasicAuthWithToken()))
                .get(ProjectUtils.getUrl() + "api/json/")
                .then()
                .statusCode(200);
    }

    @Test(dependsOnMethods = "testGetAllJobs")
    @Story("Create job")
    @Description("Create a copy of exiting job")
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

    @Test(dependsOnMethods = "testCopyJob")
    @Story("Delete job")
    @Description("Delete job by sending a http delete request")
    public void testDeleteJobViaHttpDelete() {
        RestAssured.given()
                .filter(new AllureRestAssured())
                .header(new Header("Authorization", getBasicAuthWithToken()))
                .delete(ProjectUtils.getUrl() + "job/" + JOB_NAME + "/")
                .then()
                .statusCode(204);
    }

    @Test(dependsOnMethods = "testCopyJob")
    @Story("Delete job")
    @Description("Delete job by sending a http post request to /doDelete endpoint")
    public void testDeleteJobViaDoDelete() {
        RestAssured.given()
                .filter(new AllureRestAssured())
                .header(new Header("Authorization", getBasicAuthWithToken()))
                .post(ProjectUtils.getUrl() + "job/" + JOB_NAME + "_copy/doDelete/")
                .then()
                .statusCode(302);
    }
}
