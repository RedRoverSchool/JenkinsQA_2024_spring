package school.redrover;

import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.runner.BaseAPITest;
import school.redrover.runner.ProjectUtils;
import school.redrover.runner.ResourceUtils;

import java.io.IOException;
import java.util.List;

@Epic("Jenkins API")
public class APIJenkinsJobsTest extends BaseAPITest {

    private record SlimJob(@SerializedName("_class") String clazz, String name, String color) {
    }

    private record SlimHudson(@SerializedName("_class") String clazz, String url,
                              boolean useCrumbs, boolean useSecurity, List<SlimJob> jobs) {
    }

    @Test(dataProvider = "jobDataProvider", priority = 5, dependsOnMethods = "testCreateNewJob")
    public void testDelete(String jobName, String jobDescription) {
        String url = String.format("%s/job/%s", ProjectUtils.getUrl(), jobName);
        delete(url);
    }

    @Test(dataProvider = "jobDataProvider", priority = 6, dependsOnMethods = "testDelete")
    public void testJobNotExists(String jobName, String jobDescription) {
        String jsonResponse = getJsonJobs();
        SlimHudson slimHudson = new Gson().fromJson(jsonResponse, SlimHudson.class);
        boolean isJobExists = slimHudson.jobs().stream().anyMatch(job -> job.name().equals(jobName));
        Assert.assertFalse(isJobExists);
    }

    @DataProvider(name = "jobDataProvider")
    public Object[][] jobDataProvider() {
        return new Object[][]{
                {"NEW_JOB", "Description for job"},
                {"NEW_JOB_1", "Description for job 1"},
                {"NEW_JOB_2", "Description for job 2"}
        };
    }

    @Test(dependsOnMethods = "testCreateNewJob")
    @Story("Test get jobs with Json ContentType")
    @Description("Verify that a jobs returned correct json format")
    public void testGetJobs() {
        String jsonResponse = getJsonJobs();
        SlimHudson slimHudson = new Gson().fromJson(jsonResponse, SlimHudson.class);

        Allure.step("Expected results: job entity should not be null");
        Assert.assertNotNull(slimHudson, "SlimHudson should not be null");
        Allure.step("Expected results: job entity has name and status");
        slimHudson.jobs().stream().map(SlimJob::name).forEach(Assert::assertNotNull);
    }

    @Test(dataProvider = "jobDataProvider")
    @Story("Create new job with XML ContentType")
    @Description("Check the status code is returned 200 after jobs is created")
    public void testCreateNewJob(String jobName, String jobDescription) {
        final String url = String.format("%s/createItem?name=%s", ProjectUtils.getUrl(), jobName);
        String jobXml = String.format(ResourceUtils.payloadFromResource("/create-new-job.xml"), jobDescription);

        Allure.step("Expected results: job entity has been created");
        String post = post(url, jobXml, ContentType.APPLICATION_XML, 200);

        Assert.assertNotNull(post);
    }

    @Test(dataProvider = "jobDataProvider")
    @Story("Build existed job with Json")
    @Description("Check the status of job after Run")
    public void testRunJob(String job, String desc) {
        final String url = String.format("%s/job/%s/build", ProjectUtils.getUrl(), job);
        post(url, getJson(), ContentType.APPLICATION_JSON, 201);
        SlimHudson slimHudson = new Gson().fromJson(getJsonJobs(), SlimHudson.class);
        List<SlimJob> jobs = slimHudson.jobs();

        Allure.step("Checking if slimHudson is using crumbs");
        Assert.assertTrue(slimHudson.useCrumbs());
        Allure.step("Checking if slimHudson is using security");
        Assert.assertTrue(slimHudson.useSecurity());
        Allure.step("Expected results: job entity has been built with same name and verify status");
        Assert.assertFalse(slimHudson.jobs().isEmpty());

        Allure.step("Expected results: Job name should match the provided job");
        jobs.stream().filter(slimJob -> slimJob.name().equals(job)).forEach(slimJob ->
                Assert.assertEquals(slimJob.name(), job));
    }

    private String getJsonJobs() {
        return get(ProjectUtils.getUrl() + "/api/json?pretty=true");
    }

    private String getJson() {
        JsonObject jsonData = new JsonObject();
        JsonArray parameters = new JsonArray();
        parameters.add(getParams("id", "123"));
        parameters.add(getParams("verbosity", "high"));
        jsonData.add("parameter", parameters);

        return jsonData.toString();
    }

    private JsonObject getParams(String name, String value) {
        JsonObject param = new JsonObject();
        param.addProperty("name", name);
        param.addProperty("value", value);

        return param;
    }

    private String getEntity(CloseableHttpClient httpClient,
                             HttpRequestBase request,
                             int status) throws IOException {
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            Assert.assertEquals(response.getStatusLine().getStatusCode(), status);
            HttpEntity entity = response.getEntity();
            Assert.assertNotNull(entity);

            return EntityUtils.toString(entity);
        }
    }

    private void delete(String url) {
        try (CloseableHttpClient httpClient = HttpClients.custom().disableRedirectHandling().build()) {
            HttpDelete request = new HttpDelete(url);
            request.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 302) {
                    Header locationHeader = response.getFirstHeader("Location");
                    if (locationHeader != null) {
                        delete(locationHeader.getValue());
                    } else {
                        throw new RuntimeException("Redirect without Location header");
                    }
                } else if (statusCode != 200 && statusCode != 204) {
                    throw new RuntimeException("Failed to delete job. HTTP error code: " + statusCode);
                }
            }
        } catch (IOException e) {
            ProjectUtils.log(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    private String get(String url) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            request.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            return getEntity(httpClient, request, 200);

        } catch (IOException e) {
            ProjectUtils.log(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    private String post(String url, String body, ContentType contentType, int status) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(url);
            request.setHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());
            request.setEntity(new StringEntity(body, contentType));

            return getEntity(httpClient, request, status);

        } catch (IOException e) {
            ProjectUtils.log(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
