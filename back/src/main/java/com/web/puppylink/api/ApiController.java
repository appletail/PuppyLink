package com.web.puppylink.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.validation.Valid;

import org.apache.tomcat.util.json.ParseException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.web.puppylink.dto.BasicResponseDto;
import com.web.puppylink.model.Foundation;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponseDto.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponseDto.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponseDto.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponseDto.class) })

@CrossOrigin(origins = { "http://localhost:8081" })
@RestController
public class ApiController {
    private @Value("${api.serviceKey}") String apiKey;
    
    @PostMapping("/foundation/validate")
    @ApiOperation(value = "사업자등록번호 진위확인")
    public String checkBusinessNo(@Valid @RequestBody Foundation request) {
    	JsonObject result = new JsonObject();
    	String msg = "";

        String apiUrl = "https://api.odcloud.kr/api/nts-businessman/v1/validate?" +
        					"serviceKey=" + apiKey +
        					"&returnType=JSON";
        
        	JSONObject body = new JSONObject();
        	JSONArray body2 = new JSONArray();
        	JSONObject outerBody = new JSONObject();
        	
            body.put("b_no", request.getBusinessNo());
            body.put("start_dt", request.getStartDate());
            body.put("p_nm", request.getPresidentName());
            body.put("p_nm2", "");
            body.put("b_nm", "");
            body.put("corp_no", "");
            body.put("b_sector", "");
            body.put("b_type", "");
            body2.put(body);
            outerBody.put("businesses", body2);
        	
            // 확인
            String ParamData = outerBody.toString();
            
            try {
            	URL url = new URL(apiUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection(); 
            	urlConnection.setRequestMethod("POST"); 
            	urlConnection.setRequestProperty("Content-type", "application/json"); 
            	
            	//OutputStream을 사용해서 post body 데이터 전송
            	urlConnection.setDoOutput(true); 	
            	try (OutputStream os = urlConnection.getOutputStream()){
    				byte request_data[] = ParamData.getBytes("utf-8");
    				os.write(request_data);
    				os.close();
    			}
    			catch(Exception e) {
    				e.printStackTrace();
    			}
                
                // 요펑 후 응답받은 데이터 받기
            	BufferedReader br;
    			try {
    				br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
    				result = parseStringToJson(br.readLine());
    				
    			} catch (UnsupportedEncodingException e) {
    				e.printStackTrace();
    			} catch (IOException e) {
    				e.printStackTrace();
    			} 
            	
			} catch (Exception e) {
				e.printStackTrace();
			}
        	
            JsonArray data = (JsonArray) result.get("data");
            
//            JsonElement receiveMsg = data.getAsJsonObject().get("valid_msg");
//            System.out.println("\nreceiveMsg=: " + receiveMsg.toString());
            
            if(data.toString().contains("valid_msg")) {
            	msg = "사업자등록번호를 확인할 수 없습니다.";
            }else {
            	msg = "사업자인증이 완료되었습니다. 나머지 정보도 입력해주세요.";
            }

            return msg;
    }
    
    private JsonObject parseStringToJson(String stringJson) throws ParseException{
    	JsonObject convertedObject = new Gson().fromJson(stringJson, JsonObject.class);
    	
    	return convertedObject;
    }
    
}