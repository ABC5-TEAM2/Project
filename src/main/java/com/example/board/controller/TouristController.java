package com.example.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.board.model.member.Member;
import com.example.board.model.review.Review;
import com.example.board.model.tourist.TouristSpotLikes;
import com.example.board.model.tourist.TouristSpotMyList;
import com.example.board.model.tourist.Tourist_Spot;
import com.example.board.repository.TouristSpotMapper;
import com.example.board.service.TouristService;
import com.example.board.util.PageNavigator;

@Slf4j
@RequestMapping("tourist_Spot")
@Controller
public class TouristController {

	@Autowired
	private TouristSpotMapper touristMapper;
	private TouristService touristService;
	
	 // 게시판 관련 상수 값
    final int countPerPage = 10;    // 페이지 당 글 수
    final int pagePerGroup = 5;     // 페이지 이동 그룹 당 표시할 페이지 수
    
	@PostMapping("/{tourist_Spot_id}")
	public ResponseEntity<List<Tourist_Spot>> findTourist(Long tourist_Spot_id, Model model) {

		log.info("명소 실행");
		List<Tourist_Spot> findAllTourist = touristMapper.findAllTourist();
		log.info(" findAllTourist:{}", findAllTourist);
		return ResponseEntity.ok(findAllTourist);
	}

	@GetMapping("list")
	public String list(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "searchText", defaultValue = "") String searchText, Model model) {
		
		log.info("searchText: {}", searchText);
		//int total = reviewService.getTotal(searchText);
		//PageNavigator navi = new PageNavigator(countPerPage, pagePerGroup, page, total);
		// 데이터베이스에 저장된 모든 Board 객체를 리스트 형태로 받는다.
		//List<Review> reviews = reviewService.findReviews(searchText, navi.getStartRecord(), navi.getCountPerPage());
		
		List<Tourist_Spot> findAllTourist = touristMapper.findAllTourist();
		// Board 리스트를 model 에 저장한다.
		model.addAttribute("findAllTourist", findAllTourist);
		// PageNavigation 객체를 model 에 저장한다.
		//model.addAttribute("navi", navi);
		model.addAttribute("searchText", searchText);

		// board/list.html 를 찾아서 리턴한다.
		return "tourist/TourList";
	}
	
	
	// 게시글 읽기
	@GetMapping("/TouristInfo")
	public String read(@RequestParam("tourist_Spot_id") Long tourist_Spot_id,@SessionAttribute(value = "loginMember", required = false) Member loginMember,
			 Model model) {

		// board_id 에 해당하는 게시글을 데이터베이스에서 찾는다.
		Tourist_Spot tourist_Spot= touristMapper.findTouristSpot(tourist_Spot_id);
		// board_id에 해당하는 게시글이 없으면 리스트로 리다이렉트 시킨다.
		if (tourist_Spot == null) {
			log.info("명소 없음");
			return "redirect:/tourist_Spot/list";
		}
		// 모델에 restaurant 객체를 저장한다.
		model.addAttribute("tourist_Spot", tourist_Spot);
		log.info("tourist_Spot:{}",tourist_Spot);
		List<String> findTouristSpotLikes = touristMapper.findLikesMemberId(tourist_Spot_id);
		model.addAttribute("findTouristSpotLikes", findTouristSpotLikes);
		
		List<String> findTouristSpotMyList = touristMapper.findMyListMemberId(tourist_Spot_id);
		model.addAttribute("findTouristSpotMyList", findTouristSpotMyList);

		model.addAttribute("member_id", loginMember.getMember_id());
		// board/read.html 를 찾아서 리턴한다.
		return "tourist/TouristSpotInfo";
		}

		  
	@PostMapping("/like")
	public ResponseEntity<Tourist_Spot> likeTouristSpot(@RequestParam("touristSpotId") Long tourist_Spot_id
														,@SessionAttribute(value = "loginMember", required = false) Member loginMember
														) {

		List<String> findTouristSpotLikes = touristMapper.findLikesMemberId(tourist_Spot_id);
		List<Map<String, Object>> findLikesById = touristMapper.findLikesById(tourist_Spot_id);
		
		Tourist_Spot touristSpot= touristMapper.findTouristSpot(tourist_Spot_id);
		TouristSpotLikes touristSpotLike = new TouristSpotLikes();
		String member_id = loginMember.getMember_id();
		log.info("findLikesById:{}",findLikesById);
		
		Object like_id = null;
		for (int i = 0; i < findLikesById.size(); i++) {
		    Map<String, Object> map = findLikesById.get(i);
		    if (member_id.equals((String)map.get("MEMBER_ID"))) {
		        like_id =map.get("LIKE_ID");
		        break;
		    }
		}
		if (touristSpot != null) {
			if(!findTouristSpotLikes.contains(member_id)) {
				touristSpot.addPlace_like();
				touristSpotLike.setMember_id(member_id);
				touristSpotLike.setTourist_Spot_id(tourist_Spot_id);
				touristMapper.saveLikes(touristSpotLike);
				touristSpot.setLiked(true);
			}
			else {
				touristSpot.removePlace_like();
				touristMapper.deleteLike(like_id);
				touristSpot.setLiked(false);
		    }
		    touristMapper.updateTourist(touristSpot);
		    log.info("touristSpot:{}",touristSpot);
	    return ResponseEntity.ok(touristSpot);
	  } else {
	    // 관광지 정보가 없는 경우, 오류 응답을 반환합니다.
	    return ResponseEntity.badRequest().build();
	  }
	}
	
	@PostMapping("/myList")
	public ResponseEntity<Tourist_Spot> myTouristSpot(@RequestParam("touristSpotId") Long tourist_Spot_id
														,@SessionAttribute(value = "loginMember", required = false) Member loginMember
														) {

		List<String> findTouristSpotMyList = touristMapper.findMyListMemberId(tourist_Spot_id);
		List<Map<String, Object>> findMyListById = touristMapper.findMyListById(tourist_Spot_id);
		Tourist_Spot touristSpot= touristMapper.findTouristSpot(tourist_Spot_id);
		
		
		TouristSpotMyList touristSpotMyList = new TouristSpotMyList();
		String member_id = loginMember.getMember_id();
		
		log.info("findMyListById:{}",findMyListById);
		
		Object wishboard_id = null;
		for (int i = 0; i < findMyListById.size(); i++) {
		    Map<String, Object> map = findMyListById.get(i);
		    if (member_id.equals((String)map.get("MEMBER_ID"))) {
		    	wishboard_id =map.get("WISHBOARD_ID");
		        break;
		    }
		}
		if (touristSpot != null) {
			if(!findTouristSpotMyList.contains(member_id)) {
				touristSpotMyList.setMember_id(member_id);
				touristSpotMyList.setTourist_Spot_id(tourist_Spot_id);
				touristMapper.saveMyList(touristSpotMyList);
				touristSpot.setJjim(true);
			}
			else {
				touristMapper.deleteMyList(wishboard_id);
				touristSpot.setJjim(false);
		    }
		    
		    log.info("touristSpot:{}",touristSpot);
		    
	    return ResponseEntity.ok(touristSpot);
	  } else {
	    // 관광지 정보가 없는 경우, 오류 응답을 반환합니다.
	    return ResponseEntity.badRequest().build();
	  }
	}
	  
}	
