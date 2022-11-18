package com.nsc.adapter.controller;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;
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
	
	@GetMapping(path = "/processInputOutputDate", produces = MediaType.APPLICATION_JSON_VALUE)
	public String processInputOutputData() {

		try {

			List<Object> specs = JsonUtils.classpathToList("/spec.json");
			
			Chainr chainr = Chainr.fromSpec(specs);
			log.info("specs config = {}",JsonUtils.toPrettyJsonString(specs));

			/*Object inputJSON = JsonUtils.classpathToObject("/input.json");
			JSONArray inputJsonArray = new JSONArray();
			inputJsonArray.add(inputJSON);*/
			
			String inputJsonArray = adapterGetData.getApiDataAcc();
			
			JSONParser parser = new JSONParser();
			JSONArray json = (JSONArray) parser.parse(inputJsonArray);
			
			Object transformedOutput = chainr.transform(json);
			String resp = JsonUtils.toPrettyJsonString(transformedOutput);
			log.info("** formated json = {}", resp);
			return resp;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
