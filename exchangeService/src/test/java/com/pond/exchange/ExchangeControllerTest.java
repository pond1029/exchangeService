/**
  * @Package : com.pond.exchange
  * @FileName : ExchangeControllerTest.java
  * @Date : 2021. 6. 28. 
  * @Author : "LeeJaeYeon"
  * @Version :
  * @Information :
  */

package com.pond.exchange;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

/**
  * @Package : com.pond.exchange
  * @FileName : ExchangeControllerTest.java
  * @Date : 2021. 6. 28. 
  * @Author : "LeeJaeYeon"
  * @Version :
  * @Information :
  */

@SpringBootTest
@AutoConfigureMockMvc
public class ExchangeControllerTest {

	@Autowired
	private MockMvc mock;
	
	@Test
	@DisplayName("교환 화면 정상")
	public void exchangeView_OK() throws Exception {
		mock.perform(get("/exchange"))
			.andExpect(status().isOk())
			.andExpect(view().name("exchange"));
	}
	
	@Test
	@DisplayName("환 전환 조회 정상")
	public void exchangeMoney_OK() throws Exception {
		mock.perform(get("/exchangedMoney")
				.param("from","USD")
				.param("to", "KRW")
				.param("value", "100"))
		.andExpect(status().isOk());
	}

	@Test
	@DisplayName("환 전환 조회 금액 초과 실패")
	public void exchangedMoney_bad1() throws Exception {
		mock.perform(get("/exchangedMoney")
				.param("from","USD")
				.param("to", "KRW")
				.param("value", "101"))
		.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("환 전환 조회 잘못된 값 입력 실패")
	public void exchangedMoney_bad2() throws Exception {
		mock.perform(get("/exchangedMoney")
				.param("from","USD")
				.param("to", "KRW")
				.param("value", "r"))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("환율 조회 정상")
	public void exchangeRate_OK() throws Exception {
		mock.perform(get("/exchangeRate")
				.param("from","USD")
				.param("to", "KRW"))
		.andExpect(status().isOk());
	}
}
