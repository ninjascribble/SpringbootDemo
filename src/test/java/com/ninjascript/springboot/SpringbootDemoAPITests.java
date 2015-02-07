package com.ninjascript.springboot;

import com.jayway.restassured.RestAssured;
import com.ninjascript.springboot.models.Customer;
import com.ninjascript.springboot.repositories.CustomerRepository;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringbootDemoApplication.class)
@WebIntegrationTest(randomPort = true)
public class SpringbootDemoAPITests {

    @Autowired
    EmbeddedWebApplicationContext server;
    
    @Autowired
    CustomerRepository repository;

    @Value("${local.server.port}")
    int port;
    
    @Before
    public void setUp() throws Exception
    {
        RestAssured.port = port;

        // save a couple of customers
        repository.save(new Customer("Jack", "Bauer"));
        repository.save(new Customer("Chloe", "O'Brian"));
        repository.save(new Customer("Kim", "Bauer"));
        repository.save(new Customer("David", "Palmer"));
        repository.save(new Customer("Michelle", "Dessler"));
    }
    
    @After
    public void tearDown() throws Exception
    {
        repository.deleteAll();
    }
    
	@Test
	public void testLocalServerPort() throws Exception
    {
        assertThat(port, greaterThan(0));
	}

    @Test
    public void testCustomersLookup() throws Exception
    {
        RestAssured.get("/customers?lastName=Bauer")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("customers.size()", is(2));
    }

    @Test
    public void testCustomersLookup_NoResults() throws Exception
    {
        RestAssured.get("/customers?lastName=Ziegler")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("customers.size()", is(0));
    }
}
