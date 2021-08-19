package com.kh.happve.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.kh.happve.entity.RestaurantApi;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api")
public class ApiController {
	
	@GetMapping("/basic")
	public String basic() {
		StringBuffer result = new StringBuffer();
		try {
	        StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088"); /*URL 각팀별로 가져오려는 공공데이터 엔드포인트 주소 , 샘플-무더위쉼터 엔드포인트*/
	        urlBuilder.append("/" + "6f4a424463716c773834794d516d44"); /*Service Key 공공데이터포털에서 받은 인증키*/
	        urlBuilder.append("/" + URLEncoder.encode("json", "UTF-8")); /*호출문서 형태*/
	        urlBuilder.append("/" + URLEncoder.encode("CrtfcUpsoInfo", "UTF-8")); /*서비스명*/
	        urlBuilder.append("/" + 1); //*한 페이지 결과 수*/
	        urlBuilder.append("/" + 5+ "/"); /*페이지번호*/

	        System.out.println(urlBuilder.toString());
	        URL url = new URL(urlBuilder.toString());
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        BufferedReader rd;
	        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	        	rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
	        } else {
	        	rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	        }
	        String line;
	        while ((line = rd.readLine()) != null) {
	        	result.append(line + "\n");
	        }	
	        rd.close();
	        conn.disconnect();

			ObjectMapper objectMapper = null;
			String mappedValue = null;
			String upso_nm = null;
			List<RestaurantApi> restaurant = null;
			JsonNode jsonNode = null;


			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject)jsonParser.parse(result.toString());
			JSONObject CrtfcUpsoInfo = (JSONObject) jsonObject.get("CrtfcUpsoInfo");
			JSONArray row = (JSONArray) CrtfcUpsoInfo.get("row");
			//JSONObject row1 = (JSONObject) row.get(0);

			for(int i=0; i<row.size(); i++) {
				JSONObject row1 = (JSONObject) row.get(i);
				for (int j = 0; j < row1.size(); j++) {
					objectMapper = new ObjectMapper();
					mappedValue = objectMapper.writeValueAsString(row1);
					jsonNode = objectMapper.readTree(mappedValue);
					upso_nm = jsonNode.get("UPSO_NM").asText();

				}
					System.out.println(upso_nm);
					System.out.println(mappedValue);
			}
		/*		List<RestaurantApi> restaurantApilist = objectMapper.convertValue(jsonNode, new TypeReference<List<RestaurantApi>>() {});
				System.out.println(restaurantApilist);*/
		} catch (Exception e) {
			e.printStackTrace();
		}


/*			ObjectMapper objectMapper = new ObjectMapper();
			String mappedValue = objectMapper.writeValueAsString(result);
			RestaurantEntity restaurantEntity = objectMapper.readValue(mappedValue, RestaurantEntity.class);*/



		return result + "";
        
	}

}
