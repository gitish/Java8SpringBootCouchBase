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
import com.shl.pojo.Product;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
//Running test cases in order of method names in ascending order
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductControllerTest {
	@Autowired
	ProductController productController;
	
	@Test
	public void testAddPrice(){
		Product p=new Product();
		p.setPartNumber(4);
		p.setTitle("tab");
		p.setDescription("tab for multiple use");
		p.setPrimaryImage("tab.jpg");
		p.setCategory("electronics");
		p.setColor("white");
		p.setSize("NA");
		String result=productController.create(p);
		Assert.assertEquals("Success",result);
	}
	
}
