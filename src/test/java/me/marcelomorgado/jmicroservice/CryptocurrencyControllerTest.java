package me.marcelomorgado.jmicroservice;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CryptocurrencyControllerTest {

    @Autowired
    private CryptocurrenciesRepository repository;

    @Autowired
    private MockMvc mvc;

    private String ethJson = "{ \"symbol\": \"ETH\", \"name\": \"Ethereum\" }";

    @Before
    public void before() {
        repository.deleteAll();
    }

    @Ignore
    private String create(String json) throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/cryptocurrencies/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(json)).andReturn();

        return new JSONObject(mvcResult.getResponse().getContentAsString()).getString("_id");
    }

    @Ignore
    private void getAll(String expected) throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/cryptocurrencies/getAll")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    public void listEmpty() throws Exception {
        getAll("[]");
    }


    @Test
    public void emptyCreate() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/cryptocurrencies/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }



    @Test
    public void create() throws Exception {
        create(ethJson);
    }



    @Test
    public void listNotEmpty() throws Exception {

        create(ethJson);


        mvc.perform(MockMvcRequestBuilders.get("/cryptocurrencies/getAll")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("["+ethJson+"]"));
    }
}