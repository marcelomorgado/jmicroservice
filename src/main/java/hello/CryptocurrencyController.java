package hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
class CryptocurrencyController {

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Cryptocurrency create(@RequestParam(value="name", defaultValue="Bitcoin") String name) {
        return new Cryptocurrency(counter.incrementAndGet(), name);
    }

}