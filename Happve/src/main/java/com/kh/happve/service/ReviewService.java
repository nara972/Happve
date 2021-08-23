package com.kh.happve.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.happve.dto.ReviewForm;
import com.kh.happve.entity.Member;
import com.kh.happve.entity.Review;
import com.kh.happve.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

	private final ReviewRepository reviewRepository;

	public void saveReview(ReviewForm reviewForm, Member member, Integer crtfcUpsoMgtSno) throws Exception {
		Review review = Review.builder()
				              .content(reviewForm.getContent())
				              .rating(reviewForm.getRating()).member(member)
				              .crtfcUpsoMgtSno(crtfcUpsoMgtSno).build();

		reviewRepository.save(review);
	}

	public int replyCnt() {
		return reviewRepository.findByReviewId();
	}

	public List<Integer> replycntByRatingandCrtfc(Integer crtfcUpsoMgtSno) {
		return reviewRepository.findByRatingAndCrtfcUpsoMgtSno(crtfcUpsoMgtSno);
	}

	public List<Review> findByRestaurantId(Integer crtfcUpsoMgtSno) {
		List<Review> reviews = reviewRepository.findByCrtfcUpsoMgtSno(crtfcUpsoMgtSno);
		return reviews;
	}

}
