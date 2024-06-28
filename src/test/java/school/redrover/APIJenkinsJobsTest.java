package school.redrover;

import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.domain.Hudson;
import school.redrover.domain.SlimHudson;
import school.redrover.domain.SlimJob;
import school.redrover.domain.auth.Crumb;
import school.redrover.domain.auth.Token;
import school.redrover.runner.ProjectUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Epic("Jenkins API")
public class APIJenkinsJobsTest {
    private static final String JOBS_URL = "/api/json?pretty=true";
    private static String encodedAuth;
    private static Token token;

    @BeforeClass
    public void beforeClass() {
        encodedAuth = Base64.getEncoder().encodeToString(
                (ProjectUtils.getUserName() + ":" + ProjectUtils.getPassword()).getBytes());
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            token = getToken(getCrumb(httpClient), httpClient);

        } catch (IOException e) {
            ProjectUtils.log(e.getMessage());
            throw new RuntimeException("Failed to initialize Crumb and Token", e);
        }
    }

    @Test(dataProvider = "jobDataProvider", priority = 5)
    public void testDelete(String jobName, String jobDescription) {
        String url = ProjectUtils.getUrl() + "/job/" + jobName;
        delete(url);
    }

    @DataProvider(name = "jobDataProvider")
    public Object[][] jobDataProvider() {
        return new Object[][]{
                {"NEW_JOB", "Description for job"},
                {"NEW_JOB_1", "Description for job 1"},
                {"NEW_JOB_2", "Description for job 2"}
        };
    }

    @Test
    @Story("Check to build a project")
    @Description("Verify that a token and crumb created and check status 200")
    public void testToken() {
        Allure.step("Expected results: token is valid for using");
        Assert.assertNotNull(token);
    }

    @Test(dependsOnMethods = "testCreateNewJob")
    @Story("Test get jobs with Json ContentType")
    @Description("Verify that a jobs returned correct json format")
    public void testGetJobs() {
        String jsonResponse = getJsonJobs();
        SlimHudson slimHudson = new Gson().fromJson(jsonResponse, SlimHudson.class);
        Hudson hudson = new Gson().fromJson(jsonResponse, Hudson.class);

        Allure.step("Expected results: job entity should not be null");
        Assert.assertNotNull(hudson, "Hudson should not be null");
        Allure.step("Expected results: job entity should not be null");
        Assert.assertNotNull(slimHudson, "SlimHudson should not be null");
        Allure.step("Expected results: job entity has name and status");
        slimHudson.getJobs().stream().map(SlimJob::getName).forEach(Assert::assertNotNull);
    }

    @Test(dataProvider = "jobDataProvider")
    @Story("Create new job with XML ContentType")
    @Description("Check the status code is returned 200 after jobs is created")
    public void testCreateNewJob(String jobName, String jobDescription) {
        String url = ProjectUtils.getUrl() + "/createItem?name=" + jobName;
        String jobXml = """
                <project>
                    <actions/>
                    <description>""" + jobDescription + """
                    </description>
                    <keepDependencies>false</keepDependencies>
                    <properties/>
                    <scm class="hudson.scm.NullSCM"/>
                    <canRoam>true</canRoam>
                    <disabled>false</disabled>
                    <blockBuildWhenDownstreamBuilding>false</blockBuildWhenDownstreamBuilding>
                    <blockBuildWhenUpstreamBuilding>false</blockBuildWhenUpstreamBuilding>
                    <triggers/>
                    <concurrentBuild>false</concurrentBuild>
                    <builders/>
                    <publishers/>
                    <buildWrappers/>
                </project>""";

        Allure.step("Expected results: job entity has been created");
        Assert.assertNotNull(post(url, jobXml, ContentType.APPLICATION_XML, 200));
    }

    @Test(dataProvider = "jobDataProvider")
    @Story("Build existed job with Json")
    @Description("Check the status of job after Run")
    public void testRunJob(String job, String desc) {
        post(ProjectUtils.getUrl() + "/job/" + job + "/build", getJson(), ContentType.APPLICATION_JSON, 201);
        SlimHudson slimHudson = new Gson().fromJson(getJsonJobs(), SlimHudson.class);
        List<SlimJob> jobs = slimHudson.getJobs();

        Allure.step("Checking if slimHudson is using crumbs");
        Assert.assertTrue(slimHudson.isUseCrumbs());
        Allure.step("Checking if slimHudson is using security");
        Assert.assertTrue(slimHudson.isUseSecurity());

        Allure.step("Expected results: job entity has been built with same name and verify status");
        Assert.assertFalse(slimHudson.getJobs().isEmpty());

        Allure.step("Expected results: Job name should match the provided job");
        jobs.stream().filter(slimJob -> slimJob.getName().equals(job)).forEach(slimJob ->
                Assert.assertEquals(slimJob.getName(), job));
    }

    private String getJsonJobs() {
        return get(ProjectUtils.getUrl() + JOBS_URL);
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

    private static String getBasicAuthToken() {
        String password = token.getData().getTokenValue();
        byte[] credAuth = (ProjectUtils.getUserName() + ":" + password).getBytes();

        return "Basic " + Base64.getEncoder().encodeToString(credAuth);
    }

    private Token getToken(Crumb crumb, CloseableHttpClient httpClient) throws IOException {
        String url = ProjectUtils.getUrl() + "/me/descriptorByName/jenkins.security.ApiTokenProperty/generateNewToken";
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encodedAuth);
        httpPost.setHeader(crumb.getCrumbRequestField(), crumb.getCrumb());

        return new Gson().fromJson(getEntity(httpClient, httpPost, 200), Token.class);
    }

    private Crumb getCrumb(CloseableHttpClient httpClient) throws IOException {
        HttpGet httpGet = new HttpGet(getCrumbUrl());
        httpGet.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encodedAuth);

        return new Gson().fromJson(getEntity(httpClient, httpGet, 200), Crumb.class);
    }

    private String getCrumbUrl() {
        String xpath = "concat(//crumbRequestField,\":\",//crumb)";
        String query = URLEncoder.encode(xpath, StandardCharsets.UTF_8);

        return ProjectUtils.getUrl() + "/crumbIssuer/api/json?xpath=" + query;
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
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpDelete request = new HttpDelete(url);
            request.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthToken());

            getEntity(httpClient, request, 302);

        } catch (IOException e) {
            ProjectUtils.log(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    private String get(String url) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");
            request.addHeader(HttpHeaders.AUTHORIZATION, "Basic " + encodedAuth);

            return getEntity(httpClient, request, 200);

        } catch (IOException e) {
            ProjectUtils.log(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    private String post(String url, String body, ContentType contentType, int status) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(url);
            request.setHeader(HttpHeaders.AUTHORIZATION, getBasicAuthToken());
            request.setEntity(new StringEntity(body, contentType));

            return getEntity(httpClient, request, status);

        } catch (IOException e) {
            ProjectUtils.log(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
