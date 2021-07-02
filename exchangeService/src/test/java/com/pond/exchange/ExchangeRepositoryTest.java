/**
  * @Package : com.pond.exchange
  * @FileName : ExchangeRepositoryTest.java
  * @Date : 2021. 7. 1. 
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

/**
  * @Package : com.pond.exchange
  * @FileName : ExchangeRepositoryTest.java
  * @Date : 2021. 7. 1. 
  * @Author : "LeeJaeYeon"
  * @Version :
  * @Information :
  */

@SpringBootTest
public class ExchangeRepositoryTest {

	@Autowired
	ExchangeRepositoryImpl exchange;

	@Test
	@DisplayName("환율 조회 정상")
	public void findExchangeRateByNationCodeTest_Success() {
		assertThat(exchange.findExchangeRateByNationCode("USD", "KRW"))
		.isNotNull();
	}

	@Test
	@DisplayName("환율 조회 실패")
	public void findExchangeRateByNationCodeTest_Fail() {
		assertThat(exchange.findExchangeRateByNationCode("JPY", "KRW"))
		.isNull();
	}
	
	@Test
	@DisplayName("국가 코드 조회 정상")
	public void findNationInfo_Success() {
		assertThat(exchange.findNationInfo())
		.isNotNull();		
	}

	@Test
	@Disabled
	@DisplayName("데이터 업데이트 성공")
	public void updateData_Success() {
		assertThat(exchange.updateData()).isTrue();
	}
}
