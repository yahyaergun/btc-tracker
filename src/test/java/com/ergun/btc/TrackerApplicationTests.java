package com.ergun.btc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrackerApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void formatterTest() throws ParseException {
		NumberFormat formatter = NumberFormat.getInstance();

		Number parse = formatter.parse("8,980.00");
		System.out.println(parse.doubleValue());

	}

}
