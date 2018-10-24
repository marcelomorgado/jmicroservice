package me.marcelomorgado.jmicroservice;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URL;

import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CryptocurrencyControllerIT {

    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/create");
    }

    @Test
    public void noname() throws Exception {
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();


        ResponseEntity<String> response = template.postForEntity(base.toString(), parts, String.class);

        JSONObject expectedJson = new JSONObject("{ id: 1, name: 'Bitcoin' }");
        JSONObject responseJson = new JSONObject(response.getBody());

        Assert.assertThat(responseJson.toString(), equalTo(expectedJson.toString()));
    }

    @Test
    public void withname() throws Exception {
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
        parts.add("name", "Ethereum");

        ResponseEntity<String> response = template.postForEntity(base.toString(), parts, String.class);

        JSONObject expectedJson = new JSONObject("{ id: 2, name: 'Ethereum' }");
        JSONObject responseJson = new JSONObject(response.getBody());

        Assert.assertThat(responseJson.toString(), equalTo(expectedJson.toString()));
    }
}