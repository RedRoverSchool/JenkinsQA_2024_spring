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
import school.redrover.runner.TestUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class APIJenkinsViewTest extends BaseAPITest {

    private static final String VIEW_NAME = "Customized view";
    private static final String NEW_VIEW_NAME = "New customized view";
    private static final String PIPELINE_NAME = "this is the Pipeline";

    private String toStringAndClose(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        }
    }

    public String payloadFromResource(String resource) {
        try {
            InputStream inputStream = getClass().getResourceAsStream(resource);
            if (inputStream == null) {
                throw new IllegalArgumentException("Resource not found: " + resource);
            }
            return toStringAndClose(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read resource: " + resource, e);
        }
    }

    @Test
    public void testCreateView() throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            String viewXML = payloadFromResource("/create-new-view.xml");
            viewXML = viewXML.replace("${VIEW_NAME}", VIEW_NAME);

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
    private void testAddColumnToView() throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            String viewXML = payloadFromResource("/create-new-view.xml");
            viewXML = viewXML.replace("${VIEW_NAME}", VIEW_NAME);
            viewXML = viewXML.replace("${JOB_NAME}", PIPELINE_NAME);
            viewXML = viewXML.replace("${EXTRA_COLUMN}",
                    "<hudson.plugins.git.GitBranchSpecifierColumn plugin='git@5.2.2'/>");

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
    public void testVerifyViewConfigXML() throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
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
    public void testRenameView() throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

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
    public void testDeleteViewViaDoDelete() throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

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

