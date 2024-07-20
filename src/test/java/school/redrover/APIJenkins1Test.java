package school.redrover;

import com.google.common.net.HttpHeaders;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.httpclient.AllureHttpClientRequest;
import io.qameta.allure.httpclient.AllureHttpClientResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseAPITest;
import school.redrover.runner.ProjectUtils;
import school.redrover.runner.TestUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Epic("Apache http client Jenkins Api tests")
public class APIJenkins1Test extends BaseAPITest {
    private static final String JOB_NAME = "this is the job name";

    @Test
    @Story("Create job")
    @Description("Create a freestyle job")
    public void testCreateJob() throws IOException {
        final HttpClientBuilder builder = HttpClientBuilder.create()
                .addInterceptorFirst(new AllureHttpClientRequest())
                .addInterceptorLast(new AllureHttpClientResponse());

        try (CloseableHttpClient httpClient = builder.build()) {
            HttpPost httpPost = new HttpPost(ProjectUtils.getUrl() + "view/all/createItem/");

            final List<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("name", JOB_NAME));
            nameValuePairs.add(new BasicNameValuePair("mode", "hudson.model.FreeStyleProject"));

            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            httpPost.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

                String jsonString = EntityUtils.toString(response.getEntity());
                System.out.println(jsonString);

                Assert.assertEquals(response.getStatusLine().getStatusCode(), 302);
            }
        }
    }

    @Test(dependsOnMethods = "testCreateJob")
    @Story("Read job")
    @Description("Get all jobs")
    public void testGetAllJobs() throws IOException {
        final HttpClientBuilder builder = HttpClientBuilder.create()
                .addInterceptorFirst(new AllureHttpClientRequest())
                .addInterceptorLast(new AllureHttpClientResponse());

        try (CloseableHttpClient httpClient = builder.build()) {
            HttpGet request = new HttpGet(ProjectUtils.getUrl() + "api/json/");

            request.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            try (CloseableHttpResponse response = httpClient.execute(request)) {

                String jsonString = EntityUtils.toString(response.getEntity());

                Assert.assertTrue(jsonString.contains(JOB_NAME));
                Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
            }
        }
    }

    @Test(dependsOnMethods = "testGetAllJobs")
    @Story("Create job")
    @Description("Create a copy of exiting job")
    public void testCopyJob() throws IOException {
        final HttpClientBuilder builder = HttpClientBuilder.create()
                .addInterceptorFirst(new AllureHttpClientRequest())
                .addInterceptorLast(new AllureHttpClientResponse());

        try (CloseableHttpClient httpClient = builder.build()) {

            HttpPost post = new HttpPost(ProjectUtils.getUrl()
                    + "createItem?name="
                    + TestUtils.asURL(JOB_NAME)
                    + "_copy&mode=copy&from="
                    + TestUtils.asURL(JOB_NAME));

            post.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            try (CloseableHttpResponse response = httpClient.execute(post)) {

                Assert.assertEquals(response.getStatusLine().getStatusCode(), 302);
            }
        }
    }

    @Test(dependsOnMethods = "testCopyJob")
    @Story("Delete job")
    @Description("Delete job by sending a http delete request")
    public void testDeleteJobViaHttpDelete() throws IOException {
        final HttpClientBuilder builder = HttpClientBuilder.create()
                .addInterceptorFirst(new AllureHttpClientRequest())
                .addInterceptorLast(new AllureHttpClientResponse());

        try (CloseableHttpClient httpClient = builder.build()) {
            HttpDelete httpDelete = new HttpDelete(ProjectUtils.getUrl() + "job/" + TestUtils.asURL(JOB_NAME) + "/");

            httpDelete.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            try (CloseableHttpResponse response = httpClient.execute(httpDelete)) {
                Assert.assertEquals(response.getStatusLine().getStatusCode(), 204);
            }
        }
    }


    @Test(dependsOnMethods = "testCopyJob")
    @Story("Delete job")
    @Description("Delete job by sending a http post request to /doDelete endpoint")
    public void testDeleteJobViaDoDelete() throws IOException {
        final HttpClientBuilder builder = HttpClientBuilder.create()
                .addInterceptorFirst(new AllureHttpClientRequest())
                .addInterceptorLast(new AllureHttpClientResponse());

        try (CloseableHttpClient httpClient = builder.build()) {
            HttpPost httpPost = new HttpPost(ProjectUtils.getUrl()
                    + "job/"
                    + TestUtils.asURL(JOB_NAME)
                    + "_copy/doDelete/");

            httpPost.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                Assert.assertEquals(response.getStatusLine().getStatusCode(), 302);
            }
        }
    }
}
