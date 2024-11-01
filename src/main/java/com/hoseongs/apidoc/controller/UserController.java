package com.hoseongs.apidoc.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hoseongs.apidoc.model.Member;
import com.hoseongs.apidoc.model.OneRes;
import com.hoseongs.apidoc.model.Response;
import com.hoseongs.apidoc.model.Tokens;
import com.hoseongs.apidoc.model.User;
import com.hoseongs.apidoc.service.TokenService;
import com.hoseongs.apidoc.service.UserService;

import io.jsonwebtoken.ExpiredJwtException;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService uService;
	
	@Autowired
	private TokenService tService;

	// GET테스트
	@GetMapping("/users")
	public ResponseEntity<ArrayList<User>>/*ArrayList<User>*/ getUsers(/*
									 * @RequestParam String id,
									 * 
									 * @RequestParam String pwd,
									 * 
									 * @RequestHeader Map<String, String> header
									 */) {
//		System.out.println(id + "+" + pwd);
//		String paramToken = header.get("authorization");
//		System.out.println(paramToken);
		ArrayList<User> userList = uService.getUsers();
		return new ResponseEntity<>(
				userList
				, HttpStatus.OK);
//		return userList;
	}
	
	// POST테스트(user추가)
	@PostMapping("/user")
	public ResponseEntity<String> joinUser(@ModelAttribute User user) {
		System.out.println(user);
		
		User duplUser = uService.getUser(user.getId());
		System.out.println(duplUser);

		if(duplUser != null) {
			return new ResponseEntity<>(
					"중복 아이디가 존재합니다."
				, HttpStatus.BAD_REQUEST);
		} else {
			int result = uService.joinUser(user);
			System.out.println(result);
			if(result == 1) {
				return new ResponseEntity<>(
						"success"
					, HttpStatus.OK);	
			} else {
				return new ResponseEntity<>(
						"무슨 문제지??"
					, HttpStatus.UNAUTHORIZED);	
			}
		}
	}
	
	
	// GET 페이징테스트
	@GetMapping("/page/{pageNum}")
	public String pageTest(@PathVariable String pageNum) {
		System.out.println(pageNum);
		return pageNum;
	}
	
	// 테스트post
	@PostMapping("/testPost")
	public ResponseEntity<ArrayList<User>> testPost(@RequestBody Object id) {
		System.out.println(id);

		ArrayList<User> userList = uService.getUsers();
//		@RequestBody Map<String, Object> params
//		System.out.println(params.get("id"));
//		System.out.println(params.get("pwd"));
//		System.out.println(params);
		
		return new ResponseEntity<>(
				userList
				, HttpStatus.OK);
	}
	
	@PostMapping("/user/login")
	public Response login(@RequestParam("uid") String uid, @RequestParam("pwd") String pwd) throws Exception {
		System.out.println("uid=" + uid + ",pwd=" + pwd);
		Member paramUser = new Member(uid, pwd);
		Response result = null;
		
		String password = uService.getPwd(paramUser.getUid());
		System.out.println("pwd = " + password);
		if(password != null && paramUser.getPwd().equals(password)) {
			System.out.println("비밀번호가 일치합니다.");
			
//			Date now = new Date(System.currentTimeMillis()); // sql Date() 현재날짜 뽑기
			
			String refreshToken = tService.makeJwtToken(180L);
			User user = uService.login(paramUser.getUid(), refreshToken);
			String accessToken = tService.makeJwtToken(60L, user);
			
			result = new Tokens(200, "성공", accessToken, refreshToken);
		} else {
			result = new Response(400, "아이디 또는 비밀번호가 일치하지 않습니다.");
		}
		
		return result;
	}
	
	@PostMapping("/user/email-auth")
	public String emailAuth(@RequestParam("email") String email) {
		System.out.println(email);
		
		return "success";
	}
	
	@PostMapping("/user/tokenDecryption")
	public Response tokenDecryption(@RequestBody String token) {
		Response result = null;
		try {
			result = new OneRes(200, "성공", tService.parseJwtToken(token));
		} catch(ExpiredJwtException e) {
			System.out.println("ExpiredJwtException");
			result = new Response(400, "토큰 시간이 만료되었습니다.");
		}
		return result;
	}
	
	@PostMapping("/user/reToken") 
	public Response reToken(@RequestHeader Map<String, String> header) {
		Response result = null;
		
		String paramToken = header.get("authorization");
		System.out.println(paramToken);
		
		try {
			tService.parseJwtToken(paramToken);
			
			User user = uService.getUserFromToken(paramToken);
			System.out.println(user);
			if(user != null) {
				String newRToken = tService.makeJwtToken(180L);
				uService.login(user.getId(), newRToken);
				String newAToken = tService.makeJwtToken(60L, user);
				result = new Tokens(200, "성공", newAToken, newRToken);
			} else {
				result = new Response(410, "재토큰 중 오류. 유저 조회 실패");
			}
		} catch (ExpiredJwtException e) {
			System.out.println("ExpiredJwtException");
			result = new Response(400, "토큰 시간이 만료되었습니다.");
		} catch (Exception e) {
			result = new Response(410, "재토큰 중 오류.");
		}
		return result;
	}
	
	@GetMapping("/file/download")
	public ResponseEntity<byte[]> fileDownload() throws IOException {
		// 파일을 클래스패스에서 로드합니다. (여기서는 resoures 디렉토리에 있는 파일)
		ClassPathResource resource = new ClassPathResource("downloads/elemental.jpg");
		
		byte[] down = null;
		
		try(InputStream inputStream = resource.getInputStream()) {
			down = inputStream.readAllBytes();
		}
		
		return ResponseEntity
				.ok()
				.contentType(MediaType.IMAGE_JPEG)
				.header("Content-Disposition", "attachment; filename=abc" + resource.getFilename())
				.body(down);
	}
	
	@PostMapping("/file/upload")
	public Boolean fileUpload(@RequestParam("uploadFile") MultipartFile[] files) {
		if(files.length == 0) {
			System.out.println("없어 ㅠㅜ");
		} else {
			for(MultipartFile file : files) {
				System.out.println(file);
			}
		}
		return true;
	}
	
	@PostMapping("/json")
	public String jsonTest(@RequestBody Object map) {
		System.out.println(map);
		
		return "success";
	}
	
	@PostMapping("/www-form")
	public String wwwFormTest(@RequestParam Map<String, Object> map) {
		System.out.println(map);
		System.out.println(map.get("num"));
		System.out.println(map.get("str"));
		System.out.println(map.get("bool"));
		System.out.println(map.get("obj"));
		System.out.println(map.get("arr"));
		
		return "success";
	}
	
	@PostMapping("/multi-form")
	public String multiFormTest(@ModelAttribute("obj") Map<String, Object> obj) {
//		System.out.println(map);
//		System.out.println(map.get("num"));
//		System.out.println(map.get("str"));
//		System.out.println(map.get("bool"));
//		System.out.println(map.get("obj"));
//		System.out.println(map.get("arr"));
//		
		System.out.println(obj);
		
		return "success";
	}
	
}