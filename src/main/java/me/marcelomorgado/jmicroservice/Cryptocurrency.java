package me.marcelomorgado.jmicroservice;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Cryptocurrency {

    @Id
    private  ObjectId _id;
    private String symbol;
    private String name;


    public Cryptocurrency() {}

    public Cryptocurrency(ObjectId _id, String symbol, String name) {
        this._id = _id;
        this.symbol = symbol;
        this.name = name;
    }

    public Cryptocurrency(String symbol, String name) {
        this.symbol = symbol;
        this.name = name;
    }

    // ObjectId needs to be converted to string
    public String get_id() { return _id.toHexString(); }
    public void set_id(ObjectId _id) { this._id = _id; }

    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
