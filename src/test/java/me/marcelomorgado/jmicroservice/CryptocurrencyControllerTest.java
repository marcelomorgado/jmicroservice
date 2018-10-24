package me.marcelomorgado.jmicroservice;

import org.json.JSONArray;
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
    private void createWithNoData() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/cryptocurrencies/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
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
    private JSONArray getAll(String expected) throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/cryptocurrencies/getAll")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expected)).andReturn();

        return new JSONArray(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void getAll() throws Exception {
        getAll("[]");
        create(ethJson);
        getAll("["+ethJson+"]");
    }

    @Test
    public void create() throws Exception {
        createWithNoData();
        create(ethJson);
    }




}