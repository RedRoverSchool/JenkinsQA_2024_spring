package school.redrover.runner;

import io.qameta.allure.httpclient.AllureHttpClientRequest;
import io.qameta.allure.httpclient.AllureHttpClientResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public class HttpClientBaseTest extends BaseAPITest {

    private HttpClientBuilder httpClientBuilder;

    public CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    private CloseableHttpClient httpClient;

    @BeforeClass
    public void setHttpClientBuilder() {
        httpClientBuilder = HttpClientBuilder.create()
                .addInterceptorFirst(new AllureHttpClientRequest())
                .addInterceptorLast(new AllureHttpClientResponse());
    }

    @BeforeMethod
    public void setupHttpClient() {
        httpClient = httpClientBuilder.build();
    }
}

