package school.redrover;

import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.domain.Crumb;
import school.redrover.domain.SlimHudson;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Base64;

public class APIJenkinsJobsTest {

    private static final String username = "admin";
    private static final String apiToken = "admin";
    private static final String jenkinsUrl = "http://localhost:8080";

    @Test
    public void testName() throws UnsupportedEncodingException {
        String url = jenkinsUrl + "/crumbIssuer/api/json?xpath=" +
                URLEncoder.encode("concat(//crumbRequestField,\":\",//crumb)", "UTF-8");
        Gson gson = new Gson();
        String response = get(url);
        Crumb crumb = gson.fromJson(get(url), Crumb.class);
        Assert.assertNotNull(response);
        String tokenUrl = "http://localhost:8080/me/descriptorByName/jenkins.security.ApiTokenProperty/generateNewToken?newTokenName=Name";
        String responseString = post(tokenUrl, "", crumb);
        Assert.assertNotNull(responseString);
    }

    @Test
    public void testGetSlimJobs() {
        String url = jenkinsUrl + "/api/json?pretty=true&tree=jobs[name,color,url]";
        String jsonResponse = get(url);
        SlimHudson hudsonModel = new Gson().fromJson(jsonResponse, SlimHudson.class);
        Assert.assertNotNull(hudsonModel);
    }

    @Test
    public void testGetJobs() {
        String url = "http://localhost:8080/api/json?pretty=true";
        String jsonResponse = get(url);
        SlimHudson hudsonModel = new Gson().fromJson(jsonResponse, SlimHudson.class);
        Assert.assertNotNull(hudsonModel);
    }

    @Test
    public void testBuild() {
//        String jenkinsUrl = "http://localhost:8080/job/Joba/buildWithParameters";
        String url = jenkinsUrl +  "/job/Freestyle%20Project%20Name/build";

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(url);

            String auth = username + ":" + apiToken;
            String encodedAuth = java.util.Base64.getEncoder().encodeToString(auth.getBytes());
            request.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encodedAuth);

            String jsonData = "{\"parameter\": [{\"name\":\"id\", \"value\":\"123\"}, {\"name\":\"verbosity\", \"value\":\"high\"}]}";
            StringEntity params = new StringEntity("json=" + jsonData, ContentType.APPLICATION_FORM_URLENCODED);
            request.setEntity(params);

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
                System.out.println("Response status code: " + statusCode);

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String responseString = EntityUtils.toString(entity);
                    Assert.assertNotNull(responseString);
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void testCreateNewJob() {
        String url = "http://localhost:8080/createItem?name=NEW_JOB_NAME";
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

        post(url, jobXml, null);
    }

    String get(String url) {
        String auth = username + ":" + apiToken;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");
            request.addHeader(HttpHeaders.AUTHORIZATION, "Basic " + encodedAuth);

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
                Assert.assertEquals(200, statusCode);
                return EntityUtils.toString(response.getEntity());
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return "";
    }


    private String post(String url, String body, Crumb crumb) {
        String auth = username + ":" + apiToken;

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
            HttpPost request = new HttpPost(url);
            request.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encodedAuth);
            request.addHeader(crumb.getCrumbRequestField(), crumb.getCrumb());
            request.setEntity(new StringEntity(body, ContentType.APPLICATION_XML));

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
                Assert.assertEquals(200, statusCode);
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
