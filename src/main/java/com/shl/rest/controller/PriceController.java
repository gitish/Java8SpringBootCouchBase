package com.shl.rest.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.couchbase.client.CouchbaseClient;
import com.google.gson.Gson;
import com.shl.pojo.Price;

@RestController
public class PriceController {

	@Autowired
	CouchbaseClient client;

	@RequestMapping("/price/{partNumber}")
	public Price getPrice(@PathVariable int partNumber) {
		Gson g= new Gson();
		Price p= g.fromJson((String) client.get(partNumber+""),Price.class);
		return p;
	}

	@RequestMapping(value = "price/add", method = RequestMethod.POST)
	@ResponseBody
	public String create(@RequestBody Price price) {
		Gson g= new Gson();
		try {
			return client.add(price.getPartNumber()+"",g.toJson(price)).get()?"Success":"Failure";
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return "Failure";
	}

}
