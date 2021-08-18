package com.kh.happve.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="restaurant")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="restaurant_id")
    private Integer restaurantId; //식당 고유 id

    @NotBlank
    private String title;  //식당 이름

    @Column(nullable=true)
    private String category; // 업태명 (ex 한식, 경양식, 카페 등)

    @Column(nullable=true)
    private String address; // 도로명 상세주소

    @NotBlank
    private String roadAddress; //도로명주소

    private String tel;  //업체 전화번호

    private String menu; //업소 인증메뉴

    @Column(nullable=true)
    private String imageLink; //이미지 링크


/*  @Column(nullable=true)
    private String homePageLink; //홈페이지 링크 */

/*  private boolean isVisit;  //방문 일시

    private LocalDateTime lastVisitDate;  // 마지막 방문일시

    private boolean isReview;  // 리뷰 여부 */

}
