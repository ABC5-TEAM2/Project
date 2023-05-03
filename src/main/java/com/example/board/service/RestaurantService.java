package com.example.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.example.board.model.AttachedImg;
import com.example.board.model.restaurant.Restaurant;
import com.example.board.model.review.Review;
import com.example.board.repository.RestaurantMapper;
import com.example.board.repository.ReviewMapper;
import com.example.board.util.FileService;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.activation.FileDataSource;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class RestaurantService {

    private final RestaurantMapper restaurnatMapper;
    private final FileService fileService;

    @Value("${file.upload.path}")
    private String uploadPath;

    
    
    public Restaurant findRestaurant(Long restaurant_id) {
        return restaurnatMapper.findRestaurant(restaurant_id);
    }
    
//    public List<AttachedImg> findFilesByReviewId(Long review_id) {
//        return reviewMapper.findFilesByReviewId(review_id);
//    }
//
//    public AttachedImg findFileByAttachedFileId(Long img_id) {
//        return reviewMapper.findFileByAttachedFileId(img_id);
//    }
//
//    public int getTotal(String searchText) {
//        return reviewMapper.getTotal(searchText);
//    }
}
