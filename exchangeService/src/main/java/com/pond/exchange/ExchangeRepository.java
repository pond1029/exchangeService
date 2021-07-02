/**
  * @Package : com.pond.exchange
  * @FileName : ExchangeRepository.java
  * @Date : 2021. 6. 29. 
  * @Author : "LeeJaeYeon"
  * @Version :
  * @Information :
  */

package com.pond.exchange;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
  * @Package : com.pond.exchange
  * @FileName : ExchangeRepository.java
  * @Date : 2021. 6. 29. 
  * @Author : "LeeJaeYeon"
  * @Version :
  * @Information :
  */

@Repository
public interface ExchangeRepository {
	/**
	 * 
	  * @Method Name : findRateByNationCode
	  * @Date : 2021. 7. 1.
	  * @Author : "LeeJaeYeon"
	  * @Version : 
	  * @Information : 환율 검색
	  * @param from 교환 전 화폐 코드
	  * @param to 교환 후 화폐 코드
	  * @return
	 */
	BigDecimal findExchangeRateByNationCode(String from, String to);
	
	/**
	 * 
	  * @Method Name : findNationInfo
	  * @Date : 2021. 7. 1.
	  * @Author : "LeeJaeYeon"
	  * @Version : 
	  * @Information : 화폐 국가 코드 검색
	  * @return
	 */
	List<Map> findNationInfo();
	
	/**
	 * 
	  * @Method Name : save
	  * @Date : 2021. 7. 1.
	  * @Author : "LeeJaeYeon"
	  * @Version : 
	  * @Information : 환율 정보 저장
	  * @param from
	  * @param to
	  * @param rate
	 */
	void save(String from, String to, BigDecimal rate);
}
