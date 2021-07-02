/**
  * @Package : com.pond.exchange
  * @FileName : ExchangeService.java
  * @Date : 2021. 6. 26. 
  * @Author : "LeeJaeYeon"
  * @Version :
  * @Information :
  */

package com.pond.exchange;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
  * @Package : com.pond.exchange
  * @FileName : ExchangeService.java
  * @Date : 2021. 6. 29. 
  * @Author : "LeeJaeYeon"
  * @Version :
  * @Information : 환전 서비스
  */

@Service
public class ExchangeService {

	@Autowired
	private ExchangeRepositoryImpl exchange;
		
	/**
	  * @Method Name : exchangeMoney
	  * @Date : 2021. 6. 28.
	  * @Author : "LeeJaeYeon"
	  * @Version : 
	  * @Information : 환전
	  * @param from 교환 전 화폐 코드
	  * @param to 교환 후 화폐 코드
	  * @param value 
	  * @return
	  */
	
	public BigDecimal exchangeMoney(String from, String to, Long value) {
		BigDecimal exchangeRate = getExchangeRate(from, to);		
		if(exchangeRate == null) return null;
		
		BigDecimal exchangedMoney = new BigDecimal(value);
		exchangedMoney = exchangedMoney.multiply(exchangeRate);
		return exchangedMoney;
	}

	/**
	  * @Method Name : getExchangeRate
	  * @Date : 2021. 6. 29.
	  * @Author : "LeeJaeYeon"
	  * @Version : 
	  * @Information : 환율 정보
	  * @return
	  */
	
	public BigDecimal getExchangeRate(String from, String to) {		
		return exchange.findExchangeRateByNationCode(from, to);
	}
	
	/**
	  * @Method Name : getNationCodeAndKorName
	  * @Date : 2021. 6. 29.
	  * @Author : "LeeJaeYeon"
	  * @Version : 
	  * @Information : 화폐 국가 코드, 국가명
	  * @return
	  */
	
	public List<Map> getNationInfoList() {		
		return exchange.findNationInfo();
	}

}
