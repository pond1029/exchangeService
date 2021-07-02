/**
  * @Package : com.pond.exchange
  * @FileName : ExchangeRepositoryImpl.java
  * @Date : 2021. 6. 29. 
  * @Author : "LeeJaeYeon"
  * @Version :
  * @Information :
  */

package com.pond.exchange;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
  * @Package : com.pond.exchange
  * @FileName : ExchangeRepositoryImpl.java
  * @Date : 2021. 6. 29. 
  * @Author : "LeeJaeYeon"
  * @Version :
  * @Information :
  */

@Repository
public class ExchangeRepositoryImpl implements ExchangeRepository{

    @Value("${api.currencylayer.key}")
	private String key;

    @Value("${api.currencylayer.url}")
	private String url;
    
	private Map<String, BigDecimal> exchangeRateMap = new HashMap();
	private Map<String, String> krNameMap = new HashMap();
	
	{
		krNameMap.put("KRW", "한국");
		krNameMap.put("JPY", "일본");
		krNameMap.put("PHP","필리핀");
	}
	
	@PostConstruct
	public void postConstruct() {		
		updateData();
	}	  
	
	@Override
	public BigDecimal findExchangeRateByNationCode(String from, String to) {
		return exchangeRateMap.getOrDefault(from + to, null);
	}

	@Override
	public void save(String from, String to, BigDecimal rate) {
		exchangeRateMap.put(from + to, rate);
	}

	@Override
	public List<Map> findNationInfo() {
		List<Map> nationInfoList = new ArrayList();
		for(String nationCode : krNameMap.keySet()) {
			Map<String, String> nationInfo = new HashMap();
			nationInfo.put("nationCode", nationCode);
			nationInfo.put("krName", krNameMap.get(nationCode));
			nationInfoList.add(nationInfo);
		}		
		return nationInfoList;
	}
	
	/**
	  * @Method Name : retrieve
	  * @Date : 2021. 6. 29.
	  * @Author : "LeeJaeYeon"
	  * @Version : 
	  * @Information : api 조회
	  * @return
	  */
	
	private String retrieve() {
		RestTemplate rest = new RestTemplate();
		HttpHeaders header = new HttpHeaders();
		header.add("Accept-Charset", "application/x-www-form-urlencoded; charset=UTF-8");
		header.add("Accept", MediaType.APPLICATION_JSON_VALUE);		
		HttpEntity request = new HttpEntity(header);
		UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(url);
		uri.queryParam("access_key", key);
		try {
			ResponseEntity<String> response = rest.exchange(
					uri.build().toUri(), 
					HttpMethod.GET, 
					request,
					String.class);
			return response.getBody();
		} catch (RestClientException e) {
			// TODO: handle exception
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	/**
	 * 
	  * @Method Name : updateData
	  * @Date : 2021. 7. 1.
	  * @Author : "LeeJaeYeon"
	  * @Version : 
	  * @Information : 데이터 업데이트
	  * @return
	 */
	public boolean updateData(){
		String response = retrieve();
		if(response == null) return false;
		
		ObjectMapper mapper = new ObjectMapper();		
		Map<String, Object> responseMap;
		try {
			responseMap = mapper.readValue(response, Map.class);
			Map<String, Object> quotes = (Map<String, Object>) responseMap.get("quotes");
			
			for(String key : quotes.keySet()) {		
				Object rate = quotes.get(key);
				
				if(rate instanceof Long)
					save(key.substring(0, 3), key.substring(3), BigDecimal.valueOf((Long) rate));
				
				if(rate instanceof Double)
					save(key.substring(0, 3), key.substring(3), BigDecimal.valueOf((Double) rate));
			}
			return true;
		} catch (JsonProcessingException e) {
			// TODO: handle exception
		}
		
		return false;
	}

}
