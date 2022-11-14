package org.javacodegeeks.webservices.accounts.controllers;

import java.util.ArrayList;
import java.util.List;

import org.javacodegeeks.webservices.accounts.domain.Income;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
