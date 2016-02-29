package com.shl.rest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.view.ViewQuery;
import com.couchbase.client.java.view.ViewResult;
import com.couchbase.client.java.view.ViewRow;
import com.google.gson.Gson;
import com.shl.pojo.Price;

/**
 * PriceController is a class which provides all service on price 
 * i.e:
 * add, view, edit etc.
 * @author sshail
 */
@RestController
public class PriceController {

	@Autowired
	CouchbaseClient client;
	
	@Autowired
	Bucket bucket;

	@RequestMapping("/price")
	public List<Price> getAllPrice() {
		ViewResult result = bucket.query(ViewQuery.from("dev_price", "allPrice"));
		List<Price> prices=new ArrayList<Price>();
		for (ViewRow row : result) {
		    Gson g= new Gson();
			Price p= g.fromJson(row.value().toString(),Price.class);
			prices.add(p);
		}
		return prices;
		
	}
	
	
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
