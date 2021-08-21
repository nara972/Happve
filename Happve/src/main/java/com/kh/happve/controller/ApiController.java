package com.kh.happve.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.happve.entity.Restaurant;
import com.kh.happve.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiController {

	private final RestaurantService restaurantService;

	@GetMapping("/basic")
	public Restaurant basic() {
		StringBuffer result = new StringBuffer();
		Restaurant ra = null;
		try {
			StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088"); /*URL 각팀별로 가져오려는 공공데이터 엔드포인트 주소 , 샘플-무더위쉼터 엔드포인트*/
			urlBuilder.append("/" + "6f4a424463716c773834794d516d44"); /*Service Key 공공데이터포털에서 받은 인증키*/
			urlBuilder.append("/" + URLEncoder.encode("json", "UTF-8")); /*호출문서 형태*/
			urlBuilder.append("/" + URLEncoder.encode("CrtfcUpsoInfo", "UTF-8")); /*서비스명*/
			urlBuilder.append("/" + 1); //*한 페이지 결과 수*/
			urlBuilder.append("/" + 5); /*페이지번호*/
			urlBuilder.append("/" + 9657+ "/"); /*페이지번호*/

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
			JsonNode jsonNode = null;


			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject)jsonParser.parse(result.toString());
			JSONObject CrtfcUpsoInfo = (JSONObject) jsonObject.get("CrtfcUpsoInfo");
			JSONArray row = (JSONArray) CrtfcUpsoInfo.get("row");

			for(int i=0; i<row.size(); i++) {
				JSONObject row1 = (JSONObject) row.get(i);
				for (int j = 0; j < row1.size(); j++) {
					objectMapper = new ObjectMapper();
					mappedValue = objectMapper.writeValueAsString(row1);
					ra = objectMapper.readValue(mappedValue, Restaurant.class);
					jsonNode = objectMapper.readTree(mappedValue);
				}


/*				int crtfc_upso_mgt_sno = jsonNode.get("CRTFC_UPSO_MGT_SNO").asInt();
				String upso_sno = jsonNode.get("UPSO_SNO").asText();
				String upso_nm = jsonNode.get("UPSO_NM").asText();
				String cgg_code = jsonNode.get("CGG_CODE").asText();
				String cgg_code_nm = jsonNode.get("CGG_CODE_NM").asText();
				String cob_code_nm = jsonNode.get("COB_CODE_NM").asText();
				String bizcnd_code_nm = jsonNode.get("BIZCND_CODE_NM").asText();
				String owner_nm = jsonNode.get("OWNER_NM").asText();
				String crtfc_gbn = jsonNode.get("CRTFC_GBN").asText();
				String crtfc_gbn_nm = jsonNode.get("CRTFC_GBN_NM").asText();
				String crtfc_chr_nm = jsonNode.get("CRTFC_CHR_NM").asText();
				String crtfc_chr_id = jsonNode.get("CRTFC_CHR_ID").asText();
				String crtfc_ymd = jsonNode.get("CRTFC_YMD").asText();
				String use_yn = jsonNode.get("USE_YN").asText();
				String map_indict_yn = jsonNode.get("MAP_INDICT_YN").asText();
				String crtfc_class = jsonNode.get("CRTFC_CLASS").asText();
				String y_dnts = jsonNode.get("Y_DNTS").asText();
				String x_cnts = jsonNode.get("X_CNTS").asText();
				String tel_no = jsonNode.get("TEL_NO").asText();
				String rdn_detail_addr = jsonNode.get("RDN_DETAIL_ADDR").asText();
				String rdn_addr_code = jsonNode.get("RDN_ADDR_CODE").asText();
				String rdn_code_nm = jsonNode.get("RDN_CODE_NM").asText();
				String bizcnd_code = jsonNode.get("BIZCND_CODE").asText();
				String cob_code = jsonNode.get("COB_CODE").asText();
				String crtfc_sno = jsonNode.get("CRTFC_SNO").asText();
				String crt_time = jsonNode.get("CRT_TIME").asText();
				String crt_usr = jsonNode.get("CRT_USR").asText();
				String upd_time = jsonNode.get("UPD_TIME").asText();
				String food_menu = jsonNode.get("FOOD_MENU").asText();
				String gnt_no = jsonNode.get("GNT_NO").asText();
				String crtfc_yn = jsonNode.get("CRTFC_YN").asText();

				ra.setCrtfc_upso_mgt_sno(crtfc_upso_mgt_sno);
				ra.setUpso_sno(upso_sno);
				ra.setUpso_nm(upso_nm);
				ra.setCgg_code(cgg_code);
				ra.setCgg_code_nm(cgg_code_nm);
				ra.setCob_code_nm(cob_code_nm);
				ra.setBizcnd_code_nm(bizcnd_code_nm);
				ra.setCrtfc_gbn(crtfc_gbn);
				ra.setCrtfc_gbn_nm(crtfc_gbn_nm);
				ra.setCrtfc_chr_id(crtfc_chr_id);
				ra.setCrtfc_ymd(crtfc_ymd);
				ra.setUse_yn(use_yn);
				ra.setMap_indict_yn(map_indict_yn);
				ra.setCrtfc_class(crtfc_class);
				ra.setY_dnts(y_dnts);
				ra.setX_cnts(x_cnts);
				ra.setTel_no(tel_no);
				ra.setRdn_detail_addr(rdn_detail_addr);
				ra.setRdn_addr_code(rdn_addr_code);
				ra.setRdn_code_nm(rdn_code_nm);
				ra.setBizcnd_code(bizcnd_code);
				ra.setCob_code(cob_code);
				ra.setCrtfc_sno(crtfc_sno);
				ra.setCrt_time(crt_time);
				ra.setCrt_usr(crt_usr);
				ra.setUpd_time(upd_time);*/
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return restaurantService.save(ra);

	}

}
