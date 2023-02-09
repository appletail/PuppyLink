package com.web.puppylink.controller;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.web.puppylink.config.code.CommonCode;
import com.web.puppylink.config.code.ExceptionCode;
import com.web.puppylink.dto.BasicResponseDto;
import com.web.puppylink.model.Foundation;
import com.web.puppylink.service.FoundationServiceImpl;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { 
		@ApiResponse(code = 200, message = "[OK] 클라이언트의 요청에 대한 응답 성공", response = ResponseEntity.class),
		@ApiResponse(code = 201, message = "[Created] 데이터 생성 혹은 수정 성공", response = ResponseEntity.class),
		@ApiResponse(code = 204, message = "[No Content] 본문 없는 응답", response = ResponseEntity.class),
		@ApiResponse(code = 401, message = "[Unauthorized] 유효한 자격 증명 부재", response = ResponseEntity.class),
        @ApiResponse(code = 403, message = "[Forbidden] 서버의 클라이언트 접근 거부", response = ResponseEntity.class),
        @ApiResponse(code = 404, message = "[Not Found] 요청한 리소스를 찾을 수 없음", response = ResponseEntity.class),
        @ApiResponse(code = 500, message = "[Failure] 서버 내부 오류", response = ResponseEntity.class) })

@RestController
@RequestMapping("/foundation")
public class FoundationController {
	
	private @Value("${api.serviceKey}") String apiKey;
	private final FoundationServiceImpl foundationService;
	
	public FoundationController(FoundationServiceImpl foundationService) {
		this.foundationService = foundationService;
	}
	
	// STEP2-1.
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @GetMapping("/list")
    @ApiOperation(code = 200, value = "[단체] 재단 전체 조회 [STEP1-1]", notes = "봉사 신청 페이지에서 봉사자가 단체에 대한 설명을 보고 고를 수 있도록, 전체 단체에 대한 정보를 반환한다. ", response = ResponseEntity.class)
    public Object selectAll() {
        // return ResponseEntity.ok(foundationService.getFoundationAll());
        return new ResponseEntity<BasicResponseDto>(
            	new BasicResponseDto(
            			CommonCode.SELECT_FOUNDATION,
            			foundationService.getFoundationAll()
            	), 
            	HttpStatus.OK
            );
    }
    
    @PostMapping("/validate")
    @ApiOperation(value = "사업자등록번호 진위확인")
    @ApiResponses(value = {
            @ApiResponse(code=200, message="유효한 이메일입니다.", response = ResponseEntity.class)
        })
    public Object checkBusinessNo(@Valid @RequestBody Foundation request) {
    	JsonObject result = new JsonObject();

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
    				return new ResponseEntity<>(new BasicResponseDto<ExceptionCode>(
    	                    ExceptionCode.EXCEPTION_APIPOST, null), HttpStatus.EXPECTATION_FAILED);
    			}
                
                // 요펑 후 응답받은 데이터 받기
            	BufferedReader br;
    			try {
    				br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
    				result = parseStringToJson(br.readLine());
    			} catch (UnsupportedEncodingException e) {
    				return new ResponseEntity<>(new BasicResponseDto<ExceptionCode>(
    	                    ExceptionCode.EXCEPTION_APIGET, null), HttpStatus.EXPECTATION_FAILED);
    			}
    			
			} catch (Exception e) {
				return new ResponseEntity<>(new BasicResponseDto<ExceptionCode>(
	                    ExceptionCode.EXCEPTION_API, null), HttpStatus.EXPECTATION_FAILED);
			}
        	
            JsonArray data = (JsonArray) result.get("data");
            
            if(data.toString().contains("valid_msg")) {
            	return new ResponseEntity<>(new BasicResponseDto<CommonCode>(
                        CommonCode.FAILED_BUSINESSNO, false), HttpStatus.OK);
            }else {
            	return new ResponseEntity<>(new BasicResponseDto<CommonCode>(
                        CommonCode.SUCCESS_BUSINESSNO, true), HttpStatus.OK);
            }
    }
    
    private JsonObject parseStringToJson(String stringJson) throws ParseException{
    	JsonObject convertedObject = new Gson().fromJson(stringJson, JsonObject.class);
    	
    	return convertedObject;
    }
}
