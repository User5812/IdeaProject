package com.example.springboot;

import com.example.springboot.utils.SnowFlakeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

@SpringBootTest
class ApplicationTests {

	@Test
	void contextLoads() {
		System.out.println(SnowFlakeUtil.getNextId());
	}
	

}
