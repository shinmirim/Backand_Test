package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.UserProfile;

//사용자api를 처리하는 컨트롤러 


@RestController //컨트롤러라고 인식 
public class UserProfileController {

	//임의의 Map 을 통한 데이터 처리 
	private Map<String, UserProfile> userMap;
	
	@PostConstruct
	public void init() {
		userMap =new HashMap<String, UserProfile>();
		userMap.put("1", new UserProfile("1","신미림","111-1111","서울시중구"));
		userMap.put("2", new UserProfile("2","신미림","111-1111","서울시중구"));//키 , value 값 
		userMap.put("3", new UserProfile("3","신미림","111-1111","서울시중구"));	
	}
	
	@GetMapping("/user/{id}")//유저파일 리턴 //id 를 받아 다시 호출해준다.
	public UserProfile getUserProfile(@PathVariable("id")String id) {
	      return userMap.get(id);//요청한 아이디에 해당하는 유저프로파일을 가지고  리턴해준다.
	      //이 객체를 자동으로 json형태로 매핑해 클라이언트에 전달한다.
	}
	
	//리스트 전체를 전달하는 API
	@GetMapping("/user/all")
	public List<UserProfile> getUserProfileList(){
		//userfile 의 리스트 형태를 가져올 것 
	    return new ArrayList<UserProfile>(userMap.values()); //기존에 가지고 있는 Map을 리스트로 바꿔 전달할 것 
	}

	@PutMapping("/user/{id}")//데이터를 전송하는 것 일반적으로 RequestParam를 이용해 http 프로토콜에 파라미터형태 전달하는 것이 일반적이다.
	//추가할 아이디,이름, 전화번호, 주소를 파라미터로 전달을 받고 
	public void putUserProfile(@PathVariable("id") String id, @RequestParam("name")String name,@RequestParam("phone")String phone,@RequestParam("address") String address ) {
		UserProfile userProfile = new UserProfile(id,name,phone,address);//새로받은 정보를 가지고 userfile객체를 만들고
		userMap.put(id, userProfile);//userMap에 id를 키로하는 userprofile객체 추가
	}
	
	//수정하기 
	@PostMapping("/user/{id}")//pathvariable 로id 전달 
	public void postUserProfile(@PathVariable("id") String id, @RequestParam("name")String name,@RequestParam("phone")String phone,@RequestParam("address") String address) {
		//파라미터로 이름, 주소, 번호를 전달 받는다. 
		UserProfile userProfile = userMap.get(id);//기존에 가지고 있던 정보에 전달된 id 에 해당되 사용자 정의 객체를 찾아
		userProfile.setName(name);//이름을 바꾸고 
		userProfile.setPhone(phone);//전화번호 바꾸고 
		userProfile.setAddress(address);//주소를 바꾼다. 
	}
	
	//삭제하기 
	@DeleteMapping("/user/{id}")//id를  pathvariable 로 받고 
	public void deleteUserProfile(@PathVariable("id") String id) {
		userMap.remove(id);//userMap 에 전달된 id에 해당하는 객체를 Map 에서 삭제
		
	}
	
	
}