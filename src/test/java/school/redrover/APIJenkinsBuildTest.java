package school.redrover;

import com.google.common.net.HttpHeaders;
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

public class APIJenkinsBuildTest extends BaseAPITest {

    private static final String MULTI_CONFIGURATION_PROJECT_NAME = "this is the MultiConfigName";
    private static final String NEW_MAX_NUMBER_OF_BUILDS_TO_KEEP = "1";

    @Test
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