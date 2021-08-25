package com.kh.happve.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.happve.entity.Restaurant;
import com.kh.happve.service.ImageService;
import com.kh.happve.service.ReviewService;

import lombok.RequiredArgsConstructor;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiController {
	
	private final ReviewService reviewService;
	private final ImageService imageService;

	
	@RequestMapping("/{crtfc_upso_mgt_sno}")
	public String basic(@PathVariable("crtfc_upso_mgt_sno") Integer crtfc_upso_mgt_sno,
						Model model) {

		StringBuffer result = new StringBuffer();
		Restaurant ra = new Restaurant();
		JSONArray rowrow = null;

		try {
			StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088"); /*URL 각팀별로 가져오려는 공공데이터 엔드포인트 주소 , 샘플-무더위쉼터 엔드포인트*/
			urlBuilder.append("/" + "6f4a424463716c773834794d516d44"); /*Service Key 공공데이터포털에서 받은 인증키*/
			urlBuilder.append("/" + URLEncoder.encode("json", "UTF-8")); /*호출문서 형태*/
			urlBuilder.append("/" + URLEncoder.encode("CrtfcUpsoInfo", "UTF-8")); /*서비스명*/
			urlBuilder.append("/" + 1); //*한 페이지 결과 수*/
			urlBuilder.append("/" + 5 ); /*페이지번호*/
			urlBuilder.append("/" + crtfc_upso_mgt_sno); /*페이지번호*/
			urlBuilder.append("/" );

			URL url = new URL(urlBuilder.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			BufferedReader rd;
			if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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

			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(result.toString());
			JSONObject CrtfcUpsoInfo = (JSONObject) jsonObject.get("CrtfcUpsoInfo");
			rowrow = (JSONArray) CrtfcUpsoInfo.get("row");

			JSONObject arrayToJson = null;
			ObjectMapper mapper = new ObjectMapper();

			/* Restaurant getOne */

			for(int i =0; i< rowrow.size(); i++) {
				arrayToJson = (JSONObject) rowrow.get(i);
				System.out.println("arrayToJson ===> " + arrayToJson);
				ra = mapper.readValue(arrayToJson.toString(), Restaurant.class);
			}
			//전체 리뷰 수
			model.addAttribute("replycnt",reviewService.replyCnt());
			
			//식당에 대한 각 별점 평가 수
			model.addAttribute("oneRating",reviewService.replycntByRatingandCrtfc(crtfc_upso_mgt_sno).get(0));
			model.addAttribute("twoRating",reviewService.replycntByRatingandCrtfc(crtfc_upso_mgt_sno).get(1));
			model.addAttribute("threeRating",reviewService.replycntByRatingandCrtfc(crtfc_upso_mgt_sno).get(2));
			model.addAttribute("fourRating",reviewService.replycntByRatingandCrtfc(crtfc_upso_mgt_sno).get(3));
			model.addAttribute("fiveRating",reviewService.replycntByRatingandCrtfc(crtfc_upso_mgt_sno).get(4));

		} catch (Exception e) {
			e.printStackTrace();
		}

		/*int x = Integer.parseInt(ra.getX_cnts());
		int y = Integer.parseInt(ra.getY_dnts());
		ra.setX_cnts(x);
		*/

		//식당 정보 getOne
		model.addAttribute("ra",ra);
		
		//리뷰 리스트
		model.addAttribute("reviewlist",reviewService.findByRestaurantId(crtfc_upso_mgt_sno));
		
		//이미지 리스트
		model.addAttribute("imagelist",imageService.findRestaurantId(crtfc_upso_mgt_sno));

		return "detail";

	}

	/* Restaurant getList */
	@GetMapping("/restaurants")
	private List<Restaurant> restaurants(JSONArray jsonArray) {
		List<Restaurant> list = new ArrayList<>();
		Restaurant restaurant = null;
		JSONObject arrayToJson = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			for(int i =0; i< jsonArray.size(); i++){
				arrayToJson = (JSONObject) jsonArray.get(i);
				System.out.println("arrayToJson ===> " + arrayToJson);
				restaurant = mapper.readValue(arrayToJson.toString(), Restaurant.class);
				list.add(restaurant);
				System.out.println("===== getCrtfc_upso_mgt_sno() ===> " + restaurant.getCrtfc_upso_mgt_sno());
			}

			System.out.println("list data ===> " + list);

		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return list;
	}
}