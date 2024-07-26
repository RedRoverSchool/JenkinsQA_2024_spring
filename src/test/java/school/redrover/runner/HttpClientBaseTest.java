package school.redrover.runner;

import io.qameta.allure.httpclient.AllureHttpClientRequest;
import io.qameta.allure.httpclient.AllureHttpClientResponse;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.annotations.BeforeClass;

public class HttpClientBaseTest extends BaseAPITest{

    protected HttpClientBuilder httpClientBuilder;

    @BeforeClass
    public void setupHttpClient() {
        httpClientBuilder = HttpClientBuilder.create()
                .addInterceptorFirst(new AllureHttpClientRequest())
                .addInterceptorLast(new AllureHttpClientResponse());
    }
}

