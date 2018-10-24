package me.marcelomorgado.jmicroservice;


import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

// https://www.codementor.io/gtommee97/rest-api-java-spring-boot-and-mongodb-j7nluip8d
@RestController
@RequestMapping("/cryptocurrencies")
class CryptocurrencyController {

    @Autowired
    private CryptocurrenciesRepository repository;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<Cryptocurrency> getAll() {
        return repository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Cryptocurrency get(@PathVariable("id") ObjectId id) {
        return repository.findBy_id(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable("id") ObjectId id, @Valid @RequestBody Cryptocurrency cryptocurrency) {
        cryptocurrency.set_id(id);
        repository.save(cryptocurrency);
    }


    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Cryptocurrency create(@Valid @RequestBody Cryptocurrency cryptocurrency) {
        cryptocurrency.set_id(ObjectId.get());
        repository.save(cryptocurrency);
        return cryptocurrency;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable ObjectId id) {
        repository.delete(repository.findBy_id(id));
    }

}