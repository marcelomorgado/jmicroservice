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
    private String btcJson = "{ \"symbol\": \"BTC\", \"name\": \"Bitcoin\" }";


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
    private void updateById(String _id, String json) throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/cryptocurrencies/"+_id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Ignore
    private void deleteById(String _id) throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/cryptocurrencies/"+_id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Ignore
    private JSONArray getAll(String expected) throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/cryptocurrencies/getAll")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expected)).andReturn();

        return new JSONArray(mvcResult.getResponse().getContentAsString());
    }

    @Ignore
    private JSONObject getById(String _id, String expected) throws Exception {

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/cryptocurrencies/"+_id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expected)).andReturn();

        return new JSONObject(mvcResult.getResponse().getContentAsString());
    }

    @Ignore
    private void getByInvalidId() throws Exception {

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/cryptocurrencies/5bd0afeb9effd61e13673aaa")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""))
        .andReturn();


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

    @Test
    public void get() throws Exception {
        String _id = create(ethJson);
        getById(_id, ethJson);
        getByInvalidId();
    }

    @Test
    public void update() throws Exception {
        String _id = create(ethJson);
        getById(_id, ethJson);
        updateById(_id, btcJson);
        getById(_id, btcJson);
    }

    @Test
    public void delete() throws Exception {
        String _id = create(ethJson);
        getAll("["+ethJson+"]");
        deleteById(_id);
        getAll("[]");
    }




}