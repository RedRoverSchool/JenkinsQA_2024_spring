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
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.HttpClientBaseTest;
import school.redrover.runner.ProjectUtils;
import school.redrover.runner.ResourceUtils;
import school.redrover.runner.TestUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Epic("API Manage Jenkins Views")

public class APIJenkinsViewTest extends HttpClientBaseTest {

    private static final String VIEW_NAME = "Customized view";
    private static final String NEW_VIEW_NAME = "New customized view";
    private static final String PIPELINE_NAME = "this is the Pipeline";

    @Test
    @Story("Create View")
    @Description("Verify that successful list view creation results in 200 status code")
    public void testCreateView() throws IOException {
        try (CloseableHttpClient httpClient = getHttpClientBuilder().build()) {

            String viewXML = ResourceUtils.payloadFromResource("/create-new-view.xml");

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
    @Story("Create the pipeline project")
    @Description("Verify that successful pipeline creation results in 302 status code")
    public void testCreatePipeline() throws IOException {
        try (CloseableHttpClient httpClient = getHttpClientBuilder().build()) {

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
    @Story("Reconfigure View")
    @Description("Verify that successful adding a job to a list view results in 200 status code")
    public void testAddJobToView() throws IOException {
        try (CloseableHttpClient httpClient = getHttpClientBuilder().build()) {

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
    @Story("Reconfigure View")
    @Description("Verify that the job is present in the view")
    private void testVerifyJobInView() throws IOException {
        try (CloseableHttpClient httpClient = getHttpClientBuilder().build()) {

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
    @Story("Reconfigure View")
    @Description("Verify that successful adding a column to view results in 200 status code")
    private void testAddColumnToView() throws IOException {
        try (CloseableHttpClient httpClient = getHttpClientBuilder().build()) {

            String viewXML = ResourceUtils.payloadFromResource("/create-new-view.xml");
            String extraGitBranchColumnXML = "<hudson.plugins.git.GitBranchSpecifierColumn plugin='git@5.2.2'/>";

            viewXML = String.format(viewXML, VIEW_NAME, PIPELINE_NAME, extraGitBranchColumnXML);

            HttpPost httpPost = new HttpPost(ProjectUtils.getUrl()
                    + "view/"
                    + TestUtils.asURL(VIEW_NAME)
                    + "/config.xml");

            httpPost.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());
            httpPost.addHeader(HttpHeaders.CONTENT_TYPE, "application/xml");

            HttpEntity entity = new StringEntity(viewXML, ContentType.APPLICATION_XML);
            httpPost.setEntity(entity);

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

                Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
            }
        }
    }

    @Test(dependsOnMethods = "testAddColumnToView")
    @Story("Reconfigure View")
    @Description("Verify that the view contains added column")
    public void testVerifyViewConfigXML() throws IOException {
        try (CloseableHttpClient httpClient = getHttpClientBuilder().build()) {
            HttpGet httpGet = new HttpGet(ProjectUtils.getUrl()
                    + "view/"
                    + TestUtils.asURL(VIEW_NAME)
                    + "/config.xml");

            httpGet.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);

                String responseXML = EntityUtils.toString(response.getEntity());

                Assert.assertTrue(responseXML.contains("GitBranchSpecifierColumn"));
                Assert.assertTrue(responseXML.contains("<string>" + PIPELINE_NAME + "</string>"));
            }
        }
    }

    @Test(dependsOnMethods = "testVerifyViewConfigXML")
    @Story("Reconfigure View")
    @Description("Verify that successful removal of job from view results in 200 status code")
    public void testRemoveJobFromView() throws IOException {
        try (CloseableHttpClient httpClient = getHttpClientBuilder().build()) {

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
    @Story("Reconfigure View")
    @Description("Verify that successful rename of view results in 302 status code")
    public void testRenameView() throws IOException {
        try (CloseableHttpClient httpClient = getHttpClientBuilder().build()) {

            HttpPost httpPost = new HttpPost(ProjectUtils.getUrl()
                    + "view/" + TestUtils.asURL(VIEW_NAME)
                    + "/configSubmit");

            httpPost.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());
            httpPost.addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");

            String parameters = "name=" + NEW_VIEW_NAME + "&json={}";

            HttpEntity entity = new StringEntity(parameters, ContentType.APPLICATION_FORM_URLENCODED);
            httpPost.setEntity(entity);

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                Assert.assertEquals(response.getStatusLine().getStatusCode(), 302);
            }
        }
    }

    @Test(dependsOnMethods = "testRenameView")
    @Story("Delete View")
    @Description("Verify that successful deletion of view results in 302 status code")
    public void testDeleteViewViaDoDelete() throws IOException {
        try (CloseableHttpClient httpClient = getHttpClientBuilder().build()) {

            HttpPost httpPost = new HttpPost(ProjectUtils.getUrl()
                    + "view/"
                    + TestUtils.asURL(NEW_VIEW_NAME)
                    + "/doDelete/");

            httpPost.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

                Assert.assertEquals(response.getStatusLine().getStatusCode(), 302);
            }
        }
    }
}

