package com.hoseongs.apidoc.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.hoseongs.apidoc.model.Test;
import com.hoseongs.apidoc.model.User;

@Controller
@RequestMapping("/test")
public class TestController {

//	@RequestParam String id,
//	@RequestHeader Map<String, String> header
//	@ModelAttribute User user
//	@PathVariable String pageNum
//	@RequestBody Object id
//	@RequestParam("uploadFile") MultipartFile[] files
	
	@GetMapping("/headerTest")
	public String headerTest(@RequestHeader("test") String test /*1212321 */) {
		System.out.println(test);
		return test;
	}
	@GetMapping("/headerTest2")
	public Map<String, String> headerTest2(@RequestHeader Map<String, String> headers) {
	    // 모든 헤더 출력
	    headers.forEach((key, value) -> {
	        System.out.println(key + ": " + value);
	    });
		return headers;
	}
	@GetMapping("/headerTest3")
	public String headerTest3(@RequestHeader("test") String test /*1212321 */) {
		System.out.println(test);
		return test;
	}
	@PostMapping("/headerTest-post")
	public String headerTestPost(@RequestHeader("test") String test) {
		System.out.println(test);
		return test;
	}
	@GetMapping("/input-get")
	public String inputGet(@RequestParam("id") String id, @RequestParam("nickName") String nickName) {
		System.out.println(id + " " + nickName);
		return "success";

	}
	@GetMapping("/input-get-multi")
	public String inputGetMulti(@ModelAttribute User user) {
		System.out.println(user);
		return "success";
	}
	@GetMapping("/input-get-multi2")
	public String inputGetMulti2(@RequestParam Map<String, String> map) {
		// 모든 파라미터 출력
		map.forEach((key, value) -> {
	        System.out.println(key + ": " + value);
	    });
		return "success";
	}
	@GetMapping("/input-get-path/{value}")
	public String inputGetPath(@PathVariable String value) {
		System.out.println(value);
		return "success";
	}
	@PostMapping("/input-wwwform")
	public String inputWwwPost(@ModelAttribute Test testObj) {
//		System.out.println(id + " " + nickName);
//		System.out.println(user);
		// 모든 파라미터 출력
//		map.forEach((key, value) -> {
//	        System.out.println(key + ": " + value);
//	    });
//		for(int i = 0; i < list.size(); i++) {
//			System.out.println(list.get(i));
//		}
		System.out.println(testObj);
		
		return "success";
	}
	
	@PostMapping("/input-form")
	public String inputForm(@ModelAttribute Test testObj, @RequestParam(value="file", required=false) MultipartFile file, 
			@RequestParam("id") String id, @RequestParam(value="fileList", required=false) List<MultipartFile> fileList) {
//		System.out.println(id + " " + nickName);
//		System.out.println(user);
		// 모든 파라미터 출력
//		map.forEach((key, value) -> {
//	        System.out.println(key + ": " + value);
//	    });
//		for(int i = 0; i < list.size(); i++) {
//			System.out.println(list.get(i));
//		}
	
		System.out.print(file + " ");
		System.out.println(fileList);
		System.out.println(testObj);
		if(file != null) {
			System.out.println(file.getName());
			System.out.println(file.getOriginalFilename());
		}
		System.out.println(id);
		
		if(fileList != null) {
			for(int i = 0; i < fileList.size(); i++) {
				System.out.println(fileList.get(i).getOriginalFilename());
			}
		}
		
		
		return "success";
	}
	
	@PostMapping("/input-json")
	public String inputJson(/* @RequestBody User user */@RequestParam("id") String id,
			@RequestParam("nickName") String nickName) {
		System.out.println(id + " " + nickName);
		
//		System.out.println(id + " " + nickName);
//		System.out.println(user);
		// 모든 파라미터 출력
//		map.forEach((key, value) -> {
//	        System.out.println(key + ": " + value);
//	    });
//		for(int i = 0; i < list.size(); i++) {
//			System.out.println(list.get(i));
//		}
	
		return "success";
	}
		
	@GetMapping("/response-test")
	public ResponseEntity<Map<String, Object>> responseTest() {
		Map<String, Object> map = new HashMap<String, Object>();
		Test test = new Test("아이디", "닉네임");
//		map.put("test", test);
		
//		OneRes res = new OneRes(200, "성공", map);
		map.put("code", 200);
		map.put("msg", "성공");
		map.put("test", test);
		
		return ResponseEntity.ok(map);
	}
	
	@GetMapping("/error-test")
	public ResponseEntity<Map<String, Object>> errorTest() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", 403);
		map.put("msg", "실패했습니다.");
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(map);
	}
	
	@GetMapping("/file-download")
	public ResponseEntity<byte[]> fileDownload() throws IOException {
		// 파일을 클래스패스에서 로드합니다. (여기서는 resoures 디렉토리에 있는 파일)
		ClassPathResource resource = new ClassPathResource("downloads/elemental.jpg");
		
		byte[] down = null;
		
		try(InputStream inputStream = resource.getInputStream()) {
			down = inputStream.readAllBytes();
		}
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.IMAGE_JPEG); // 다운로드 파일 type에 맞춰야함
	    headers.setContentDispositionFormData("attachment", "testfile"+resource.getFilename());
	    
	    return new ResponseEntity<>(down, headers, HttpStatus.OK);
	}
	
}
