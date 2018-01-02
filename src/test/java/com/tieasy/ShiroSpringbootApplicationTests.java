package com.tieasy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tieasy.util.HttpUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShiroSpringbootApplicationTests {

	String baseUrl = "http://localhost:8080";
	
	@Test
	public void contextLoads() {
		HttpUtil.sendGet(baseUrl+"", "");
	}

}
