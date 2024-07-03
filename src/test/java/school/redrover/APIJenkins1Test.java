package school.redrover;

import com.google.common.net.HttpHeaders;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
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
import school.redrover.runner.TestUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class APIJenkins1Test extends BaseAPITest {
    private static final String JOB_NAME = "this is the job name";
    private static final String PIPELINE_NAME = "this is Pipeline name";
    private static final String VIEW_NAME = "Customized";

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
                System.out.println(jsonString);

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

    @Test
    public void testCreateView() throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            String viewXML = """
                    <hudson.model.ListView>
                        <name>""" + TestUtils.asURL(VIEW_NAME) + """
                        </name>
                        <filterExecutors>false</filterExecutors>
                        <filterQueue>false</filterQueue>
                        <properties class="hudson.model.View$PropertyList"/>
                        <jobNames>
                            <comparator class="java.lang.String$CaseInsensitiveComparator"/>
                        </jobNames>
                        <jobFilters/>
                        <columns>
                            <hudson.views.StatusColumn/>
                            <hudson.views.WeatherColumn/>
                            <hudson.views.JobColumn/>
                            <hudson.views.LastSuccessColumn/>
                            <hudson.views.LastFailureColumn/>
                            <hudson.views.LastDurationColumn/>
                            <hudson.views.BuildButtonColumn/>
                        </columns>
                        <recurse>false</recurse>
                    </hudson.model.ListView>""";

            HttpPost httpPost = new HttpPost(ProjectUtils.getUrl()
                    + "createView?name="
                    + TestUtils.asURL(VIEW_NAME));

            httpPost.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());
            httpPost.addHeader(HttpHeaders.CONTENT_TYPE, "application/xml");

            HttpEntity entity = new StringEntity(viewXML, ContentType.APPLICATION_XML);
            httpPost.setEntity(entity);

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

                Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
            }
        }
    }

    @Test
    public void testCreatePipeline() throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpPost httpPost = new HttpPost(ProjectUtils.getUrl() + "view/all/createItem/");

            final List<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("name", PIPELINE_NAME));
            nameValuePairs.add(new BasicNameValuePair("mode", "org.jenkinsci.plugins.workflow.job.WorkflowJob"));

            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            httpPost.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

                String jsonString = EntityUtils.toString(response.getEntity());
                System.out.println(jsonString);

                Assert.assertEquals(response.getStatusLine().getStatusCode(), 302);
            }
        }
    }

    @Test(dependsOnMethods = {"testCreateView", "testCreatePipeline"})
    public void testAddJobToView() throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpPost httpPost = new HttpPost(ProjectUtils.getUrl()
                    + "view/"
                    + TestUtils.asURL(VIEW_NAME)
                    + "/addJobToView?name="
                    + TestUtils.asURL(PIPELINE_NAME));

            httpPost.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

                Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);

            }
        }
    }

    @Test(dependsOnMethods = "testAddJobToView")
    private void testVerifyJobInView() throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpGet request = new HttpGet(ProjectUtils.getUrl()
                    + "view/"
                    + TestUtils.asURL(VIEW_NAME)
                    + "/api/json");

            request.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            try (CloseableHttpResponse response = httpClient.execute(request)) {

                Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);

                String jsonString = EntityUtils.toString(response.getEntity());

                Assert.assertTrue(jsonString.contains(PIPELINE_NAME),
                        "Job " + PIPELINE_NAME + " was not found in view " + VIEW_NAME);
            }
        }
    }

    @Test(dependsOnMethods = "testVerifyJobInView")
    public void testRemoveJobFromView() throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpPost httpPost = new HttpPost(ProjectUtils.getUrl()
                    + "view/" + TestUtils.asURL(VIEW_NAME)
                    + "/removeJobFromView?name="
                    + TestUtils.asURL(PIPELINE_NAME));

            httpPost.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

                Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
            }
        }
    }

    @Test(dependsOnMethods = "testRemoveJobFromView")
    public void testDeleteViewViaDoDelete() throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpPost httpPost = new HttpPost(ProjectUtils.getUrl()
                    + "view/"
                    + TestUtils.asURL(VIEW_NAME)
                    + "/doDelete/");

            httpPost.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

                Assert.assertEquals(response.getStatusLine().getStatusCode(), 302);
            }
        }
    }
}