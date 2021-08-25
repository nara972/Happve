package com.kh.happve.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.kh.happve.entity.Review;


@Transactional(readOnly=true)
public interface ReviewRepository extends JpaRepository<Review, Integer>{
	
	List<Review> findByCrtfcUpsoMgtSno(Integer crtfcUpsoMgtSno);
	
	@Query(value="select count(r) from Review r")
	Integer findByReviewId();
	
	@Query(value="select count(r) from Review r where r.crtfcUpsoMgtSno=:crtfcUpsoMgtSno group by r.rating order by rating asc")
	List<Integer> findByRatingAndCrtfcUpsoMgtSno(@Param("crtfcUpsoMgtSno") Integer crtfcUpsoMgtSno);

	@Query(value = "select r from Review r order by regdate desc")
	List<Review> findReviewsOrderByRegDateDesc();

}