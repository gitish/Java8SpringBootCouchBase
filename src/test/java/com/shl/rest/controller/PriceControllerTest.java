package com.shl.rest.controller;

import java.util.List;





import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.shl.Application;
import com.shl.pojo.Price;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
//Running test cases in order of method names in ascending order
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PriceControllerTest {
	@Autowired
	PriceController priceController;
	
	@Test
	public void testAddPrice(){
		Price price=new Price();
		price.setPartNumber(999);
		price.setMarketPrice(200.00);
		price.setSalePrice(149.00);
		String result=priceController.create(price);
		Assert.assertEquals("Success",result);
	}
	
	@Test
	public void testGetAllPriceResponse(){
		List<Price> prices= priceController.getAllPrice();
		Assert.assertNotNull(prices);
		Assert.assertNotEquals(prices.size(), 0);
	}
	
	@Test
	public void testGetPriceForGivenPartNumber(){
		Price price = priceController.getPrice(123456);
		Assert.assertEquals(price.getPartNumber(), 123456);
	}
	
	@Test
	public void testDeletePrice(){
		Price price=new Price();
		price.setPartNumber(999);
		String result=priceController.delete(price);
		Assert.assertEquals("Success",result);
	}
}
