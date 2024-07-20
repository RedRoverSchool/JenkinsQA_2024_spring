package school.redrover;

import com.google.common.net.HttpHeaders;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseAPITest;
import school.redrover.runner.ProjectUtils;
import school.redrover.runner.ResourceUtils;
import school.redrover.runner.TestUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Epic("API Manage Jenkins builds")

public class APIJenkinsBuildTest extends BaseAPITest {

    private static final String MULTI_CONFIGURATION_PROJECT_NAME = "this is the MultiConfigName";
    private static final String NEW_MAX_NUMBER_OF_BUILDS_TO_KEEP = "1";

    @Test
    @Story("Create the multi-configuration project")
    @Description("Verify that successful multi-configuration creation results in 302 status code")
    public void testCreateProject() throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpPost httpPost = new HttpPost(ProjectUtils.getUrl() + "view/all/createItem/");

            final List<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("name", MULTI_CONFIGURATION_PROJECT_NAME));
            nameValuePairs.add(new BasicNameValuePair("mode", "hudson.matrix.MatrixProject"));

            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            httpPost.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

                Assert.assertEquals(response.getStatusLine().getStatusCode(), 302);
            }
        }
    }

    @Test(dependsOnMethods = "testCreateProject")
    @Story("Search the project by name")
    @Description("Verify that the project can be found using the search box")
    public void testSearchProjectName() throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpGet httpGet = new HttpGet(ProjectUtils.getUrl() + "search/?q="
                    + TestUtils.asURL(MULTI_CONFIGURATION_PROJECT_NAME));

            httpGet.addHeader("Authorization", getBasicAuthWithToken());

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);

                String responseBody = EntityUtils.toString(response.getEntity());
                System.out.println("Response: " + responseBody);

                Assert.assertTrue(responseBody.contains(MULTI_CONFIGURATION_PROJECT_NAME));
            }
        }
    }

    @Test(dependsOnMethods = "testSearchProjectName")
    @Story("Disable the project")
    @Description("Verify that successful disabling of the project results in 302 status code")
    public void testDisableProject() throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpPost httpPost = new HttpPost(ProjectUtils.getUrl()
                    + "job/"
                    + TestUtils.asURL(MULTI_CONFIGURATION_PROJECT_NAME)
                    + "/disable");

            httpPost.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

                Assert.assertEquals(response.getStatusLine().getStatusCode(), 302);
            }
        }
    }

    @Test(dependsOnMethods = "testDisableProject")
    @Story("Disable the project")
    @Description("Verify that project is not buildable")
    public void testVerifyProjectIsDisabled() throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpGet httpGet = new HttpGet(ProjectUtils.getUrl()
                    + "job/"
                    + TestUtils.asURL(MULTI_CONFIGURATION_PROJECT_NAME)
                    + "/api/json");

            httpGet.addHeader("Authorization", getBasicAuthWithToken());

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);

                HttpEntity entity = response.getEntity();

                String jsonString = EntityUtils.toString(entity);
                System.out.println(jsonString);

                Assert.assertTrue(jsonString.contains("\"buildable\":false"));
            }
        }
    }

    @Test(dependsOnMethods = "testVerifyProjectIsDisabled")
    @Story("Enable the project")
    @Description("Verify that successful enabling of the project back results in 302 status code")
    public void testEnableProject() throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpPost httpPost = new HttpPost(ProjectUtils.getUrl()
                    + "job/"
                    + TestUtils.asURL(MULTI_CONFIGURATION_PROJECT_NAME)
                    + "/enable");

            httpPost.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

                Assert.assertEquals(response.getStatusLine().getStatusCode(), 302);
            }
        }
    }

    @Test(dependsOnMethods = "testEnableProject")
    @Story("Enable the project")
    @Description("Verify that enabled back project is buildable")
    public void testVerifyProjectIsEnabled() throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpGet httpGet = new HttpGet(ProjectUtils.getUrl()
                    + "job/"
                    + TestUtils.asURL(MULTI_CONFIGURATION_PROJECT_NAME)
                    + "/api/json");

            httpGet.addHeader("Authorization", getBasicAuthWithToken());

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);

                HttpEntity entity = response.getEntity();

                String jsonString = EntityUtils.toString(entity);
                System.out.println(jsonString);

                Assert.assertTrue(jsonString.contains("\"buildable\":true"));
            }
        }
    }

    @Test(dependsOnMethods = "testVerifyProjectIsEnabled")
    @Story("Build the project")
    @Description("Verify that successful project build results in 201 status code")
    public void testPerformBuild() throws IOException, InterruptedException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpPost httpPost = new HttpPost(ProjectUtils.getUrl()
                    + "job/"
                    + TestUtils.asURL(MULTI_CONFIGURATION_PROJECT_NAME)
                    + "/build?delay=0sec");

            httpPost.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

                Assert.assertEquals(response.getStatusLine().getStatusCode(), 201);
                System.out.println(response);

                String locationHeader = response.getFirstHeader("Location").getValue();
                System.out.println("Queue location: " + locationHeader);

                String queueApiUrl = locationHeader + "api/json";

                boolean buildStarted = false;
                int intervalSeconds = 2;
                int maxRetries = 5;

                for (int i = 0; i < maxRetries && !buildStarted; i++) {
                    HttpGet httpGet = new HttpGet(queueApiUrl);
                    httpGet.addHeader("Authorization", getBasicAuthWithToken());

                    try (CloseableHttpResponse queueResponse = httpClient.execute(httpGet)) {
                        Assert.assertEquals(queueResponse.getStatusLine().getStatusCode(), 200);

                        HttpEntity entity = queueResponse.getEntity();
                        String jsonString = EntityUtils.toString(entity);
                        System.out.println("queue" + jsonString);

                        if (jsonString.contains("executable")) {
                            buildStarted = true;
                            System.out.println("Build started: " + jsonString);
                        } else if (jsonString.contains("cancelled")) {
                            System.out.println("Build was cancelled");
                            break;
                        } else {
                            System.out.println("Build not started yet");
                        }
                    }

                    TimeUnit.SECONDS.sleep(intervalSeconds);
                }

                Assert.assertTrue(buildStarted);
            }
        }
    }

    @Test(dependsOnMethods = "testPerformBuild")
    @Story("Build the project")
    @Description("Verify that the latest build for project is successful")
    public void testVerifyJobStatusAfterBuild() throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpGet httpGet = new HttpGet(ProjectUtils.getUrl()
                    + "job/"
                    + TestUtils.asURL(MULTI_CONFIGURATION_PROJECT_NAME)
                    + "/api/json");

            httpGet.addHeader("Authorization", getBasicAuthWithToken());

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                Assert.assertEquals(200, response.getStatusLine().getStatusCode());

                HttpEntity entity = response.getEntity();
                String jsonString = EntityUtils.toString(entity);
                System.out.println(jsonString);

                Assert.assertTrue(jsonString.contains("Build stability: No recent builds failed."));
            }
        }
    }

    @Test(dependsOnMethods = "testVerifyJobStatusAfterBuild")
    @Story("Reconfigure project builds")
    @Description("Verify that successful change of max number of builds to keep results in 302 status code")
    public void testSetMaxNumberBuildsToKeep() throws IOException {
        String json = ResourceUtils.payloadFromResource("/configSubmit.json");
        json = json.replaceAll("\"numToKeepStr\":\\s*\"2\"",
                "\"numToKeepStr\":\"" + NEW_MAX_NUMBER_OF_BUILDS_TO_KEEP + "\"");

        String encodedJson = URLEncoder.encode(json, StandardCharsets.UTF_8);
        String parameters = "json=" + encodedJson;

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            String url = ProjectUtils.getUrl()
                    + "job/"
                    + TestUtils.asURL(MULTI_CONFIGURATION_PROJECT_NAME)
                    + "/configSubmit";

            HttpPost httpPost = new HttpPost(url);

            httpPost.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());
            httpPost.addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");

            HttpEntity entity = new StringEntity(parameters, ContentType.APPLICATION_FORM_URLENCODED);
            httpPost.setEntity(entity);

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                Assert.assertEquals(response.getStatusLine().getStatusCode(), 302);
            }
        }
    }

    @Test(dependsOnMethods = "testSetMaxNumberBuildsToKeep")
    @Story("Reconfigure project builds")
    @Description("Verify that max number of builds to keep is set successfully")
    public void testVerifyMaxNumberBuildsToKeep() throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpGet request = new HttpGet(ProjectUtils.getUrl()
                    + "job/"
                    + TestUtils.asURL(MULTI_CONFIGURATION_PROJECT_NAME)
                    + "/config.xml");

            request.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            try (CloseableHttpResponse response = httpClient.execute(request)) {

                Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);

                String responseXML = EntityUtils.toString(response.getEntity());
                System.out.println("XML for project with changed MAX number builds to keep: " + responseXML);

                Assert.assertTrue(
                        responseXML.contains("<numToKeep>" + NEW_MAX_NUMBER_OF_BUILDS_TO_KEEP + "</numToKeep>"));
            }
        }
    }
}