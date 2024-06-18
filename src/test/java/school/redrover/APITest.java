package school.redrover;


import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Objects;


public class APITest {

    private static final class Pokemon {
        private String name;
        private String url;

        Pokemon(String name, String url) {
            this.name = name;
            this.url = url;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Pokemon pokemon = (Pokemon) o;
            return Objects.equals(name, pokemon.name) && Objects.equals(url, pokemon.url);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, url);
        }
    }

    private static final class Pokemons {
        private int count;
        private String previous;
        private String next;
        private List<Pokemon> results;
    }

    @Test
    public void httpTest() throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet("https://pokeapi.co/api/v2/pokemon");

            request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);

                HttpEntity entity = response.getEntity();
                Assert.assertNotNull(entity);

                // simple check
                String jsonString = EntityUtils.toString(entity);
                Assert.assertTrue(jsonString.startsWith("{\"count\":1302"));

                // regular check
                Pokemons pokemons = new Gson().fromJson(jsonString, Pokemons.class);
                Assert.assertEquals(pokemons.count, 1302);
                Assert.assertNull(pokemons.previous);
                Assert.assertEquals(pokemons.next, "https://pokeapi.co/api/v2/pokemon?offset=20&limit=20");
                Assert.assertEquals(pokemons.results.size(), 20);
                Assert.assertEquals(
                        pokemons.results.get(0),
                        new Pokemon("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/"));
            }
        }
    }

    @Test
    public void restAssuredTest() {
        RestAssured.when().get("https://pokeapi.co/api/v2/pokemon")
                .then()
                .statusCode(200)
                .body("count", Matchers.equalTo(1302),
                        "results.name", Matchers.hasItems("bulbasaur", "ivysaur"));
    }
}
