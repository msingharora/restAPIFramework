package com.api.tests;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.api.endpoints.UserEndPoints;
import com.api.payload.User;
import com.github.javafaker.Faker;

import io.restassured.response.Response;


public class UserTests {

	
	
	Faker faker;
	User userPayload;
	public Logger logger;//creating logger
	// https://github.com/msingharora/restAPIFramework.git
	@Test
	public void setUpData()
	{
		faker=new Faker();
		userPayload=new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstname(faker.name().firstName());
		userPayload.setLastname(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5,10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		//logs
		logger=LogManager.getLogger(this.getClass());//instantiate logger
	}
	
	@Test(priority=1)
	public void testPostUser() {
		
		logger.info("***************Creating User********************");
		Response response=UserEndPoints.createUser(userPayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("*****************User Created******************");
	}
	
	//@Test(priority=2)
	public void testGetUserByName() 
	{
		Response response=UserEndPoints.readUser(this.userPayload.getUsername());
		//System.out.println("username is " +this.userPayload.getUsername());
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		
	}
	
	@Test(priority=3)
	public void testUpdateUser()
	{
		logger.info("*****************Updating User******************");
		userPayload.setFirstname(faker.name().firstName());
		userPayload.setLastname(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		
		
		Response response=UserEndPoints.updateUser(this.userPayload.getUsername(), userPayload);
		response.then().log().body();		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("*****************User Updated******************");
		
		//checking after update
		Response responseafterupdate=UserEndPoints.readUser(this.userPayload.getUsername());
		Assert.assertEquals(responseafterupdate.getStatusCode(), 200);
		
	}
	
	@Test(priority=4)
	public void testDeleteUser()
	{
		logger.info("*****************Deleting User ******************");
		Response response=UserEndPoints.deleteUser(this.userPayload.getUsername());
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("*****************User Deleted******************");
	}
	
}
