package com.example.board.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.board.model.festival.Festival;
import com.example.board.model.festival.FestivalLikes;
import com.example.board.model.festival.FestivalMyList;
import com.example.board.model.tourist.TouristSpotMyList;


@Mapper
public interface FestivalMapper {
	
	Double findFestivalLat(Long festival_id) ;
	
	Double findFestivalLng(Long festival_id) ;
		
	Festival findFestival(Long festival_id);

	List<Festival> findAllFestival();
	
	//review_place를 위해서
	List<Object> findAllFestivalName();

//좋아요 기능	
	void updateFestival(Festival updateFestival);
	
	List<String> findLikesMemberId(Long festival_id);
	
	List<Map<String,Object>> findLikesById(Long festival_id);
	
	void saveLikes(FestivalLikes festivalLikes);
	
	void deleteLike(Object like_id);
//	
	//찜하기
	
	void saveMyList(FestivalMyList festivalMyList);
	
	List<Map<String,Object>> findMyListById(Long festival_id);
	
	List<String> findMyListMemberId(Long festival_id);
	
	//테이블 다른걸로!
	void deleteMyList(Object wishboard_id);
	
//
	
	
	
	//
	List<Map<String, Object>> findMyListByMemberId(String member_id);

}

