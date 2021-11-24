# Happve (Happy Vegan, 채식 식당 추천 사이트)

Happve를 통해 주변의 비건 식당을 찾아 시도해보고 지구를 살리는 한걸음에 동참해 보세요.

<br>

## ⏰개발 기간 
2021년 08월 09일 ~ 2021년 8월 30일

<br>

## 🛠기술 스택
**`Back-end`**
- Java 16
- SpringBoot 2.5.4
- Spring Security
- JPA
- QueryDSL
- MySQL 8.0

**`Front-end`**
- HTML, CSS3
- JavaScript, jQuery

**`DevOps`**
- AWS RDS (MySQL 8.0)

<br>

## 📃기능 설명
<br>

⌨️회원가입

@Valid 어노테이션을 이용하여 값의 길이, 필수값을 검증  
Validator 인터페이스를 이용하여 중복 이메일, 닉네임 여부 에 대한 유효성 검사를 진행  
모든 검증을 마치고 회원가입이 완료 되었을 경우 로그인 상태를 유지시키고 메인페이지로 리다다이렉트

<br>

🖱️로그인

닉네임 또는 이메일, 비밀번호를 입력 받아 동작  
유효하지 않을 경우 메시지를 출력     
로그인 상태 유지 기능을 사용하기 위해 스프링 시큐리티 remember me를 이용하여 구현

<br>

🔍검색

검색 키워드를 식당과 음식으로 나누어 적용  
유저가 드롭박스에서 선택한 키워드 타입과 입력한 키워드를 Controller Class 에서 get 방식으로 받아 동작  
Repository에 만들어둔 쿼리를 실행시켜 해당값을 db에서 가져오고 값을 addAttribute로 검색결과창으로 넘겨줌  
addAttribute로 넘어온 검색결과를 Thymeleaf 반복문 each로 출력  

<br>

📄상세페이지

서울시 공공데이터 오픈 API를 이용해 데이터 연동  
카카오 지도API 연동  
JSONParser와 Jackson 라이브러리(ObjectMapper)를 이용해 Json 데이터 자바 객체로 parsing   
-트리구조로 되어있는 JSON데이터 노드를 JSONObject와 JSONArray 형변환을 통해 꺼내어 RestaurantAPI 자바 객체로 매핑

<br>

⭐리뷰 및 별점

식당에 관한 리뷰,별점,이미지를 입력받아 동작  
이미지의 경우 MultipartFIle을 사용하여 이미지 업로드, 최대 3개까지만 작성되도록 자바스크립트를 통해 구현  
식당별 별점은 쿼리문을 이용하여 적용

<br>

📌즐겨찾기

RestApi와 Ajax를 이용하여 즐겨찾기 등록,취소 기능 구현   
즐겨찾기한 목록을 ArrayList형태로 반환하여 모두 화면에 출력

<br>

📢건의하기

스프링 전용 validation @Validated를 사용해 사용자가 입력한 이메일과 건의내용을 검증  
사용자가 건의내용이 전달되었는지 알 수 있도록 RedirectAttributes로 상태 값을 true로 전달  
View에서 status가 true면 “건의 내용이 전달되었습니다”라는 메세지가 출력

