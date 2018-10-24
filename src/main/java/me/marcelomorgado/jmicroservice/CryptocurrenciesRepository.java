package me.marcelomorgado.jmicroservice;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CryptocurrenciesRepository extends MongoRepository<Cryptocurrency, String> {
    Cryptocurrency findBy_id(ObjectId _id);
}

