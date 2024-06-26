package school.redrover;

import com.google.common.net.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import school.redrover.runner.ProjectUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class APIJenkins1Test {
    private String token;

    private static String getBasicAuth(String username, String password) {
        String valueToEncode = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
    }

    @BeforeMethod
    public void generateNewToken() throws IOException {
        final BasicCookieStore cookieStore = new BasicCookieStore();
        final String crumb;

        try (CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build()) {

            HttpGet request = new HttpGet(ProjectUtils.getUrl() + "crumbIssuer/api/json");

            request.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuth(ProjectUtils.getUserName(), ProjectUtils.getPassword()));

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);

                System.out.println("all cookies>>>");
                cookieStore.getCookies().forEach(System.out::println);


                crumb = EntityUtils.toString(response.getEntity()).substring(61, 125);
                System.out.println("crumb>>> " + crumb);
            }

            HttpPost httpPost = new HttpPost(
                    ProjectUtils.getUrl() + "me/descriptorByName/jenkins.security.ApiTokenProperty/generateNewToken");

            httpPost.addHeader("Jenkins-Crumb", crumb);
            httpPost.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuth(ProjectUtils.getUserName(), ProjectUtils.getPassword()));

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
                String token1 = EntityUtils.toString(response.getEntity());
                System.out.println("token>>> " + token1);
                token = token1.substring(153, 187);
                System.out.println("token>>> " + token);
            }
        }
    }

    @Test
    public void testCreateJob() throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpPost httpPost = new HttpPost(
                    ProjectUtils.getUrl() + "view/all/createItem");

            final List<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("name", "this is the job name"));
            nameValuePairs.add(new BasicNameValuePair("mode", "hudson.model.FreeStyleProject"));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            httpPost.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuth(ProjectUtils.getUserName(), token));

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                Assert.assertEquals(response.getStatusLine().getStatusCode(), 302);
            }
        }
    }

    @Test
    public void testGetJob() throws IOException {

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(ProjectUtils.getUrl() + "api/json");

            request.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuth(ProjectUtils.getUserName(), token));

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);

                String jsonString = EntityUtils.toString(response.getEntity());
                System.out.println(jsonString);
            }
        }
    }

    @AfterMethod
    public void revokeAll() throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpPost httpPost = new HttpPost(
                    ProjectUtils.getUrl() + "me/descriptorByName/jenkins.security.ApiTokenProperty/revokeAll");

            httpPost.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuth(ProjectUtils.getUserName(), token));

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
            }
        }
    }
}
