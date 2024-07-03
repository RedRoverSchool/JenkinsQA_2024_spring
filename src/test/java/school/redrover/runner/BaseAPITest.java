package school.redrover.runner;

import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.Level;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.util.Base64;

public class BaseAPITest {

    private record Crumb(String _class, String crumb, String crumbRequestField) {
    }

    private record Token(String tokenName, String tokenUuid, String tokenValue) {
    }

    private record Response(String status, Token data) {
    }

    private String token;

    private String getBasicAuthWithPassword() {
        String valueToEncode = ProjectUtils.getUserName() + ":" + ProjectUtils.getPassword();
        return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
    }

    protected String getBasicAuthWithToken() {
        String valueToEncode = ProjectUtils.getUserName() + ":" + token;
        return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
    }

    @BeforeClass
    public void generateNewToken() throws IOException {
        final BasicCookieStore cookieStore = new BasicCookieStore();
        final String crumb;

        try (CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build()) {

            HttpGet request = new HttpGet(ProjectUtils.getUrl() + "crumbIssuer/api/json");

            request.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithPassword());

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);

                System.out.println("all cookies>>>");
                cookieStore.getCookies().forEach(System.out::println);

                crumb = new Gson().fromJson(EntityUtils.toString(response.getEntity()), Crumb.class).crumb;
                ProjectUtils.log(Level.INFO, "Crumb: " + crumb);
            }

            HttpPost httpPost = new HttpPost(
                    ProjectUtils.getUrl() + "me/descriptorByName/jenkins.security.ApiTokenProperty/generateNewToken");

            httpPost.addHeader("Jenkins-Crumb", crumb);
            httpPost.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithPassword());

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);

                token = new Gson().fromJson(EntityUtils.toString(response.getEntity()), Response.class).data.tokenValue;
                ProjectUtils.log(Level.INFO, "Token: " + token);
            }
        }
    }

    @AfterClass
    public void revokeAllTokens() throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpPost httpPost = new HttpPost(
                    ProjectUtils.getUrl() + "me/descriptorByName/jenkins.security.ApiTokenProperty/revokeAll");

            httpPost.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
            }
        }
    }
}
