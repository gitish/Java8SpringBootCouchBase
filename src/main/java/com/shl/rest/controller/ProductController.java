package com.shl.rest.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.view.ViewQuery;
import com.couchbase.client.java.view.ViewResult;
import com.google.gson.Gson;
import com.shl.constant.ProductCons;
import com.shl.pojo.Product;

@RestController
public class ProductController {

	@Autowired
	CouchbaseClient client;

	@Autowired
	Bucket bucket;
	
	@RequestMapping("/prodInfo")
	public List<Product> getDetails(){
		ViewResult result = bucket.query(ViewQuery
				.from("dev_price", "allProduct"));
		Gson g = new Gson();
		return result.allRows().stream()
				.map(row -> g.fromJson(row.value().toString(), Product.class))
				.collect(Collectors.toList());
		
	}
	
	/**
	 * This service is used to add new price to couchbase
	 * @return Success/Failure
	 */
	@RequestMapping(value = "prodInfo/add", method = RequestMethod.POST)
	@ResponseBody
	public String create(@RequestBody Product product) {
		Gson g = new Gson();
		try {
			return client.add(ProductCons.PRODINFO_PREFIX+product.getPartNumber(), g.toJson(product))
					.get() ? "Success" : "Failure";
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return "Failure";
	}
	
}
