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
import org.testng.annotations.Ignore;
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
    private static final String USERNAME = ProjectUtils.getUserName();
    private static final String PASSWORD = ProjectUtils.getPassword();
    private static final String JOBS_URL = "/api/json?pretty=true";
    private static String encodedAuth;
    private static Token token;

    @BeforeClass
    public static void beforeClass() {
        encodedAuth = Base64.getEncoder().encodeToString((USERNAME + ":" + PASSWORD).getBytes());
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            token = getToken(getCrumb(httpClient), httpClient);

        } catch (IOException e) {
            ProjectUtils.log(e.getMessage());
            throw new RuntimeException("Failed to initialize Crumb and Token", e);
        }
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

    @Test
    @Story("Test get jobs with Json ContentType")
    @Description("Verify that a jobs returned correct json format")
    public void testGetJobs() {
        String jsonResponse = getJsonJobs();
        SlimHudson slimHudson = new Gson().fromJson(jsonResponse, SlimHudson.class);
        Hudson hudson = new Gson().fromJson(jsonResponse, Hudson.class);

        Assert.assertNotNull(hudson);
        Assert.assertNotNull(slimHudson);
        Allure.step("Expected results: job entity has name and status");
        slimHudson.getJobs().stream().map(SlimJob::getName).forEach(Assert::assertNotNull);
        slimHudson.getJobs().stream().map(SlimJob::getColor).forEach(Assert::assertNotNull);
    }

    @Ignore
    @Story("Create new job with Json ContentType")
    @Description("Check the status code is returned 200")
    @Test(dataProvider = "jobDataProvider")
    public void testCreateNewJobWithJson(String jobName, String jobDescription) {
        final String url = ProjectUtils.getUrl() + "/createItem?name=" + jobName;

        JsonObject jobJson = new JsonObject();
        jobJson.addProperty("description", jobDescription);
        jobJson.add("actions", new JsonObject());
        jobJson.add("properties", new JsonObject());
        jobJson.add("builders", new JsonObject());
        jobJson.add("publishers", new JsonObject());
        jobJson.add("buildWrappers", new JsonObject());

        Allure.step("Expected results: job entity has been created");
        Assert.assertNotNull(post(url, jobJson.toString(), ContentType.APPLICATION_JSON, 200));
    }

    @Test(dataProvider = "jobDataProvider")
    @Story("Create new job with XML ContentType")
    @Description("Check the status code is returned 200")
    public void testCreateNewJob(String jobName, String jobDescription) {
        String url = ProjectUtils.getUrl() + "/createItem?name=" + jobName;
        String jobXml = "<project>\n" +
                "  <actions/>\n" +
                "  <description>" + jobDescription + "</description>\n" +
                "  <keepDependencies>false</keepDependencies>\n" +
                "  <properties/>\n" +
                "  <scm class=\"hudson.scm.NullSCM\"/>\n" +
                "  <canRoam>true</canRoam>\n" +
                "  <disabled>false</disabled>\n" +
                "  <blockBuildWhenDownstreamBuilding>false</blockBuildWhenDownstreamBuilding>\n" +
                "  <blockBuildWhenUpstreamBuilding>false</blockBuildWhenUpstreamBuilding>\n" +
                "  <triggers/>\n" +
                "  <concurrentBuild>false</concurrentBuild>\n" +
                "  <builders/>\n" +
                "  <publishers/>\n" +
                "  <buildWrappers/>\n" +
                "</project>";

        Allure.step("Expected results: job entity has been created");
        Assert.assertNotNull(post(url, jobXml, ContentType.APPLICATION_XML, 200));
    }

    @Test(dataProvider = "jobDataProvider")
    @Story("Build existed job with Json")
    @Description("Check the status of job")
    public void testRunJob(String job, String desc) {
        post(ProjectUtils.getUrl() + "/job/" + job + "/build", getJson(), ContentType.APPLICATION_JSON, 201);
        SlimHudson slimHudson = new Gson().fromJson(getJsonJobs(), SlimHudson.class);
        List<SlimJob> jobs = slimHudson.getJobs();

        Assert.assertTrue(slimHudson.isUseCrumbs());
        Assert.assertTrue(slimHudson.isUseSecurity());

        Allure.step("Expected results: job entity has been built with same name and verify status");
        Assert.assertFalse(slimHudson.getJobs().isEmpty());
        
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
        byte[] credAuth = (USERNAME + ":" + password).getBytes();

        return "Basic " + Base64.getEncoder().encodeToString(credAuth);
    }

    private static Token getToken(Crumb crumb, CloseableHttpClient httpClient) throws IOException {
        String url = ProjectUtils.getUrl() + "/me/descriptorByName/jenkins.security.ApiTokenProperty/generateNewToken";
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encodedAuth);
        httpPost.setHeader(crumb.getCrumbRequestField(), crumb.getCrumb());

        return new Gson().fromJson(getEntity(httpClient, httpPost, 200), Token.class);
    }

    private static Crumb getCrumb(CloseableHttpClient httpClient) throws IOException {
        HttpGet httpGet = new HttpGet(getCrumbUrl());
        httpGet.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encodedAuth);

        return new Gson().fromJson(getEntity(httpClient, httpGet, 200), Crumb.class);
    }

    private static String getCrumbUrl() {
        String xpath = "concat(//crumbRequestField,\":\",//crumb)";
        String query = URLEncoder.encode(xpath, StandardCharsets.UTF_8);

        return ProjectUtils.getUrl() + "/crumbIssuer/api/json?xpath=" + query;
    }

    private static String getEntity(CloseableHttpClient httpClient, HttpRequestBase request, int status) throws IOException {
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            Assert.assertEquals(response.getStatusLine().getStatusCode(), status);
            HttpEntity entity = response.getEntity();
            Assert.assertNotNull(entity);

            return EntityUtils.toString(entity);
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
        }
        return "";
    }

    private String post(String url, String body, ContentType contentType, int status) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(url);
            request.setHeader(HttpHeaders.AUTHORIZATION, getBasicAuthToken());
            request.setEntity(new StringEntity(body, contentType));

            return getEntity(httpClient, request, status);

        } catch (IOException e) {
            ProjectUtils.log(e.getMessage());
        }
        return "";
    }
}
