package com.example.board.model.review;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ReviewWriteForm {
    @NotBlank
    private String title;
    @NotBlank
    private String contents;
    @NotBlank
    private String review_place;
    
    public static Review toReview(ReviewWriteForm reviewWriteForm) {
    	Review review = new Review();
    	review.setTitle(reviewWriteForm.getTitle());
    	review.setContents(reviewWriteForm.getContents());
    	review.setReview_place(reviewWriteForm.getReview_place());
        return review;
    }
}	
