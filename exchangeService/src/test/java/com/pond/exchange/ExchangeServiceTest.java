/**
  * @Package : com.pond.exchange
  * @FileName : ExchangeServiceTest.java
  * @Date : 2021. 6. 26. 
  * @Author : "LeeJaeYeon"
  * @Version :
  * @Information :
  */

package com.pond.exchange;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
  * @Package : com.pond.exchange
  * @FileName : ExchangeServiceTest.java
  * @Date : 2021. 6. 29. 
  * @Author : "LeeJaeYeon"
  * @Version :
  * @Information :
  */

@SpringBootTest
public class ExchangeServiceTest {

	@Autowired
	ExchangeService exchange;
	
	@Test
	@DisplayName("교환 정상 작동")
	public void exchangeMoney_OK() {
		assertThat(exchange.exchangeMoney("USD", "KRW", 100l))
		.isNotNull();
	}

	@Test
	@DisplayName("환율 정상 작동")
	public void exchangeRate() {
		assertThat(exchange.getExchangeRate("USD", "KRW"))
		.isNotNull();
	}
}
