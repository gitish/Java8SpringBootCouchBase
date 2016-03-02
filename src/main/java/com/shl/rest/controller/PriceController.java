package com.shl.rest.controller;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
import com.google.gson.Gson;
import com.shl.pojo.Price;

/**
 * PriceController is a class which provides all service on price i.e: add,
 * view, edit etc.
 * 
 * @author sshail
 */
@RestController
public class PriceController {

	@Autowired
	CouchbaseClient client;

	@Autowired
	Bucket bucket;
	
	/**
	 * This service is used to return entire list of prices
	 * @return List<Price>
	 */
	@RequestMapping("/price")
	public List<Price> getAllPrice() {
		ViewResult result = bucket.query(ViewQuery
				.from("dev_price", "allPrice"));
		Gson g = new Gson();
		return result.allRows().stream()
				.map(row -> g.fromJson(row.value().toString(), Price.class))
				.collect(Collectors.toList());
	}
	

	/**
	 * This method is used to return the price between given range
	 * @param min
	 * @param max
	 * @return List<Price>
	 */
	@RequestMapping("/priceRange")
	public List<Price> getPriceBetween(@Param(value = "min") Double min,@Param(value="max") Double max){
		Optional<Double> optMin = Optional.ofNullable(min);
		Optional<Double> optMax = Optional.ofNullable(max);
		Predicate<Price> gtatherThanMin = p -> p.getSalePrice() > min; 
		Predicate<Price> lessThanMax = p -> p.getSalePrice() < max;
		if(!optMin.isPresent()){
			return getAllPrice().stream()
					.filter(lessThanMax)
					.collect(Collectors.toList());
		}else if(!optMax.isPresent()){
			return getAllPrice().stream()
					.filter(gtatherThanMin)
					.collect(Collectors.toList());
		}else{
			return getAllPrice().stream()
					.filter(gtatherThanMin)
					.filter(lessThanMax)
					.collect(Collectors.toList());
		}
	}
	
	/**
	 * This service is used to return a speicfic prices associate with given partNumber
	 * @return Price
	 */
	@RequestMapping("/price/{partNumber}")
	public Price getPrice(@PathVariable int partNumber) {
		Gson g = new Gson();
		Price p = g.fromJson((String) client.get(partNumber + ""), Price.class);
		return p;
	}

	/**
	 * This service is used to add new price to couchbase
	 * @return Success/Failure
	 */
	@RequestMapping(value = "price/add", method = RequestMethod.POST)
	@ResponseBody
	public String create(@RequestBody Price price) {
		Gson g = new Gson();
		try {
			return client.add(price.getPartNumber() + "", g.toJson(price))
					.get() ? "Success" : "Failure";
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return "Failure";
	}

	/**
	 * This service is used to delete price with given partNumber
	 * @return Success/Failure
	 */
	@RequestMapping(value = "price/delete", method = RequestMethod.POST)
	@ResponseBody
	public String delete(@RequestBody Price price) {
		try {
			return client.delete(price.getPartNumber() + "").get() ? "Success"
					: "Failure";
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return "Failure";
	}
}
