package school.redrover;

import com.google.common.net.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
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

public class APIJenkins1Test extends BaseAPITest {
    private static final String JOB_NAME = "this is the job name";

    @Test
    public void testCreateJob() throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

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
    public void testGetAllJobs() throws IOException {

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
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
    public void testCopyJob() throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
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
    public void testDeleteJobViaHttpDelete() throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpDelete httpDelete = new HttpDelete(ProjectUtils.getUrl() + "job/" + TestUtils.asURL(JOB_NAME) + "/");

            httpDelete.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            try (CloseableHttpResponse response = httpClient.execute(httpDelete)) {
                Assert.assertEquals(response.getStatusLine().getStatusCode(), 204);
            }
        }
    }


    @Test(dependsOnMethods = "testCopyJob")
    public void testDeleteJobViaDoDelete() throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

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
