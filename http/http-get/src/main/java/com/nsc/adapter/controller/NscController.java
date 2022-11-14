package com.nsc.adapter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nsc.adapter.Utils.AdapterGetData;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class NscController {

	@Autowired
	AdapterGetData adapterGetData;

	@GetMapping("/getNscData")
	public ResponseEntity<?> getResponse() {
		try {
			String resp = adapterGetData.getApiData();
			log.info("*** resp from service = {}",resp);
			return new ResponseEntity<String>(resp, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("failed", HttpStatus.GONE);
	}
}
