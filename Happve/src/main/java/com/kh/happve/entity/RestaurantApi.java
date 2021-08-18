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
public class RestaurantApi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="restaurant_api")
    private Integer restaurantId;  //식당 고유 id

    @NotBlank
    private String title;  //식당 이름

    @Column(nullable=true)
    private String category; // 업태명 (한식, 경양식, 카페 등)

    @NotBlank
    private String areaCode;  //자치구 코드

    @NotBlank
    private String areaName;  //자치구 명

    @NotBlank
    private String roadAddress;  //도로명주소

    @NotBlank
    private Integer mapY; //지도 y좌표

    @NotBlank
    private Integer mapX; // 지도 x좌표

    private String tel;  //업체 전화번호

    private String menu; //업소 인증메뉴

}
