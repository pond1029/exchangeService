/**
  * @Package : com.pond.exchange
  * @FileName : ExchangeController.java
  * @Date : 2021. 6. 28. 
  * @Author : "LeeJaeYeon"
  * @Version :
  * @Information :
  */

package com.pond.exchange;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
  * @Package : com.pond.exchange
  * @FileName : ExchangeController.java
  * @Date : 2021. 6. 29. 
  * @Author : "LeeJaeYeon"
  * @Version :
  * @Information : 환전 컨트롤러
  */

@Controller
public class ExchangeController {

	@Autowired
	private ExchangeService exchange;

	@GetMapping("/exchange")
	public String exchange(Model model) {		
		model.addAttribute("nationCodeList", exchange.getNationInfoList());
		return "exchange";
	}

	@GetMapping("/exchangedMoney")
	public ResponseEntity<Map> exchangedMoney(String from, String to, Long value) {	
		Map<String, Object> responseMessage = new HashMap();

		//validate
		if(from == null || to == null) {
			responseMessage.put("error","국가 코드를 입력하세요.");
			return ResponseEntity.ok().body(responseMessage);
		}
		
		if(value < 0 || value > 100) {
			responseMessage.put("error", "환전은 1 ~ 100 "+ from + "까지만 가능합니다.");
			return ResponseEntity.badRequest().body(responseMessage);
		}
				
		BigDecimal exchangedMoney = exchange.exchangeMoney(from, to, value);		
		if(exchangedMoney == null) {
			responseMessage.put("error","데이터가 없습니다.");
			return ResponseEntity.badRequest().body(responseMessage);
		}
		
		responseMessage.put("exchangedMoney", exchangedMoney.setScale(2, RoundingMode.DOWN).doubleValue());		
		return ResponseEntity.ok().body(responseMessage);
	}
	
	@GetMapping("/exchangeRate")
	public ResponseEntity<Map> exchangeRate(String from, String to) {
		BigDecimal exchangeRate = exchange.getExchangeRate(from, to);
		
		Map<String, Object> responseMessage = new HashMap();
		if(exchangeRate == null) {
			responseMessage.put("exchangeRate", null);
			return ResponseEntity.ok().body(responseMessage);
		}
		
		responseMessage.put("exchangeRate", exchange.getExchangeRate(from, to).setScale(2, RoundingMode.DOWN).doubleValue());
		return ResponseEntity.ok().body(responseMessage);
	}
	
}
