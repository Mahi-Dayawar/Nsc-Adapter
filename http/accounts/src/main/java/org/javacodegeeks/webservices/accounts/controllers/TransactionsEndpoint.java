package org.javacodegeeks.webservices.accounts.controllers;

import java.util.ArrayList;
import java.util.List;

import org.javacodegeeks.webservices.accounts.domain.Income;
import org.json.simple.JSONArray;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class TransactionsEndpoint {

	static List<Income> incomeList = new ArrayList<Income>();

	static {
		incomeList.add(new Income("First Income", 100000.0));
	}

	@GetMapping("/incomes")
	public List<Income> getAllIncomes(String msg) {
		log.info("** req data  = {}",msg);
		log.info("** req for data ***");
		return incomeList;
	}
	
	@GetMapping(path = "/getInput", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getInputMessage() {
		try {

			Object inputJSON = JsonUtils.classpathToObject("/input.json");
			JSONArray inputJsonArray = new JSONArray();
			inputJsonArray.add(inputJSON);
			String resp = JsonUtils.toPrettyJsonString(inputJsonArray);
			log.info("** formated json from acc = {}", resp);
			return resp;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
		
}
