package school.redrover;

import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
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
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.domain.auth.Crumb;
import school.redrover.domain.Hudson;
import school.redrover.domain.SlimHudson;
import school.redrover.domain.auth.Token;
import school.redrover.runner.ProjectUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class APIJenkinsJobsTest {

    private static final String username = ProjectUtils.getUserName();
    private static final String password = ProjectUtils.getPassword();

    @Test
    public void testCrumbAndToken() {
        String auth = username + ":" + password;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            Crumb crumb = getCrumb(encodedAuth, httpClient);
            Token token = getToken(encodedAuth, crumb, httpClient);
            Assert.assertNotNull(token);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private Token getToken(String encodedAuth, Crumb crumb, CloseableHttpClient httpClient) throws IOException {
        String url = ProjectUtils.getUrl() + "/me/descriptorByName/jenkins.security.ApiTokenProperty/generateNewToken";
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encodedAuth);
        httpPost.setHeader(crumb.getCrumbRequestField(), crumb.getCrumb());
        String entity = getEntity(httpClient, httpPost);
        return new Gson().fromJson(entity, Token.class);
    }

    private Crumb getCrumb(String encodedAuth, CloseableHttpClient httpClient) throws IOException {
        String xpath = "concat(//crumbRequestField,\":\",//crumb)";
        String query = URLEncoder.encode(xpath, StandardCharsets.UTF_8);
        String crumbUrl = ProjectUtils.getUrl() + "/crumbIssuer/api/json?xpath=" + query;
        HttpGet httpGet = new HttpGet(crumbUrl);
        httpGet.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encodedAuth);
        String entity = getEntity(httpClient, httpGet);
        return new Gson().fromJson(entity, Crumb.class);
    }

    private String getEntity(CloseableHttpClient httpClient, HttpRequestBase request) throws IOException {
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
            HttpEntity entity = response.getEntity();
            Assert.assertNotNull(entity);
            return EntityUtils.toString(entity);
        }
    }

    @Test
    public void testGetJobs() {
        String url = ProjectUtils.getUrl() + "/api/json?pretty=true";
        String jsonResponse = get(url);
        SlimHudson hudsonModel = new Gson().fromJson(jsonResponse, SlimHudson.class);
        Hudson hudson = new Gson().fromJson(jsonResponse, Hudson.class);
        Assert.assertNotNull(hudson);
        Assert.assertNotNull(hudsonModel);
    }

    @Test
    @Ignore
    public void testBuild() {
//        String jenkinsUrl = "http://localhost:8080/job/Joba/buildWithParameters";
        String url = ProjectUtils.getUrl() + "/job/NEW_JOB/build";

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String auth = username + ":" + password;
            String encodedAuth = java.util.Base64.getEncoder().encodeToString(auth.getBytes());

            Crumb crumb = getCrumb(encodedAuth, httpClient);
            Token token = getToken(encodedAuth, crumb, httpClient);
            String credAuth = username + ":" + token.getData().getTokenValue();
            String authToken = Base64.getEncoder().encodeToString(credAuth.getBytes());

            String jsonData = "{\"parameter\": [{\"name\":\"id\", \"value\":\"123\"}, {\"name\":\"verbosity\", \"value\":\"high\"}]}";
            StringEntity params = new StringEntity("json=" + jsonData, ContentType.APPLICATION_FORM_URLENCODED);
            HttpPost request = new HttpPost(url);
            request.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + authToken);

            request.setEntity(params);

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
                Assert.assertEquals( statusCode, 201);
                HttpEntity entity = response.getEntity();
                Assert.assertNotNull(entity);
                String responseString = EntityUtils.toString(entity);
                Assert.assertNotNull(responseString);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void testCreateNewJob() {
        String url = "http://localhost:8080/createItem?name=NEW_JOB";
        String jobXml = "<project>\n" +
                "  <actions/>\n" +
                "  <description>My new job description</description>\n" +
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

        post(url, jobXml);
    }

    private String get(String url) {
        String auth = username + ":" + password;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");
            request.addHeader(HttpHeaders.AUTHORIZATION, "Basic " + encodedAuth);

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
                Assert.assertEquals(statusCode, 200);
                return EntityUtils.toString(response.getEntity());
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return "";
    }

    private String post(String url, String body) {
        String auth = username + ":" + password;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(url);

            Crumb crumb = getCrumb(encodedAuth, httpClient);
            Token token = getToken(encodedAuth, crumb, httpClient);
            String credAuth = username + ":" + token.getData().getTokenValue();
            String authToken = Base64.getEncoder().encodeToString(credAuth.getBytes());
            request.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + authToken);
            request.setEntity(new StringEntity(body, ContentType.APPLICATION_XML));

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
                Assert.assertEquals(statusCode, 200);
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    return EntityUtils.toString(entity);
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return "";
    }
}
