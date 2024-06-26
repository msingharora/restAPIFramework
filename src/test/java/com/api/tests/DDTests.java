package com.api.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.api.endpoints.UserEndPoints;
import com.api.payload.User;
import com.api.utilities.DataProviders;

import io.restassured.response.Response;

public class DDTests {
	
	
	@Test(priority=1, dataProvider="Data", dataProviderClass=DataProviders.class)
	public void testPostUser(String userID, String userName, String fname, String lname, String email, String pwd, String ph) {
		
		User userPayLoad=new User();
		userPayLoad.setId(Integer.parseInt(userID));
		userPayLoad.setUsername(userName);
		userPayLoad.setFirstname(fname);
		userPayLoad.setLastname(lname);
		userPayLoad.setEmail(email);
		userPayLoad.setPassword(pwd);
		userPayLoad.setPhone(ph);
		
		Response res=UserEndPoints.createUser(userPayLoad);
		Assert.assertEquals(res.getStatusCode(), 200);
		
	}
	
	@Test(priority=2, dataProvider="UserNames", dataProviderClass=DataProviders.class)
	public void testDeleteUser(String userName) {
		Response res=UserEndPoints.deleteUser(userName);
		Assert.assertEquals(res.getStatusCode(), 200);
	}

}
