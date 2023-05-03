package com.example.board.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.board.model.*;
import com.example.board.model.tourist.TouristSpotLikes;
import com.example.board.model.tourist.TouristSpotMyList;
import com.example.board.model.tourist.Tourist_Spot;

@Mapper
public interface TouristSpotMapper {
	
	Double findTouristLat(Long tourist_Spot_id) ;
	
	Double findTouristLng(Long tourist_Spot_id) ;
		
	
	Tourist_Spot findTouristSpot(Long tourist_Spot_id);

	List<Tourist_Spot> findAllTourist();
	
	//review_place를 위해서
	List<Object> findAllTouristName();
	

	
//좋아요 기능	
	void updateTourist(Tourist_Spot updateTouristSpot);
	
	List<String> findLikesMemberId(Long tourist_Spot_id);
	
	List<Map<String,Object>> findLikesById(Long tourist_Spot_id);
	
	void saveLikes(TouristSpotLikes touristSpotLikes);
	
	void deleteLike(Object like_id);
//	

//찜 기능
	void saveMyList(TouristSpotMyList touristSpotMyList);
	
	List<Map<String,Object>> findMyListById(Long tourist_Spot_id);
	
	List<String> findMyListMemberId(Long tourist_Spot_id);
	
	void deleteMyList(Object wishboard_id);
	
//
	
//찜 목록
	List<Map<String, Object>> findMyListByMemberId(String member_id);
	
}
