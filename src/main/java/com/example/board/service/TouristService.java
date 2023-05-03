package com.example.board.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.board.model.review.Review;
import com.example.board.repository.TouristSpotMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class TouristService {

    private final TouristSpotMapper reviewMapper;
    
    public void like(Review review, MultipartFile file,MultipartFile file2) {

        }
    
    
}