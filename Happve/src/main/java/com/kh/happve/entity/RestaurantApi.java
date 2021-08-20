package com.kh.happve.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "restaurant_api")
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestaurantApi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer crtfc_upso_mgt_sno;  //식당 고유 id ( 식품 인증 업소 관리 일련 번호)

    private String upso_sno; //업소 번호

    @NotBlank
    private String upso_nm;  //업소명

    @NotBlank
    private String cgg_code;  //자치구 코드

    @NotBlank
    private String cgg_code_nm;  //자치구 명

    @JsonIgnoreProperties(ignoreUnknown = true)
    private String cob_code_nm; //업종명

    @Column(nullable=true)
    private String bizcnd_code_nm; // 업태명 (한식, 경양식, 카페 등)

    private String owner_nm;  //업주명

    private String crtfc_gbn; // 식품인증구분

    private String crtfc_gbn_nm; //식품인증구분명

    private String crtfc_chr_nm; //담당자 이름

    private String crtfc_chr_id; //담당자 id

    private String crtfc_ymd; //인증일자

    private String use_yn; //사용여부

    private String map_indict_yn; //지도 표시 여부

    private String crtfc_class; //식품인증업소 등급

    @NotBlank
    private String y_dnts; //지도 y좌표

    @NotBlank
    private String x_cnts; // 지도 x좌표

    @NotBlank
    private String tel_no; //전화번호

    private String rdn_detail_addr; //도로명 상세 주소

    private String rdn_addr_code; //도로명 주소 코드

    @NotBlank
    private String rdn_code_nm;  //도로명주소

    @Nullable
    private String bizcnd_code; // 업태코드

    private String cob_code; // 업종코드

    private String crtfc_sno; //허가번호

    private String crt_time; // 최초 작성일자

    private String crt_usr; // 최초 작성자

    private String upd_time; //최초 수정일자

    @NotBlank
    private String food_menu; //업소 인증 메뉴

    private String gnt_no; //교부번호

    private String crtfc_yn; //인증여부
}