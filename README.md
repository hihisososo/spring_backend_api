# spring_backend_api
[**URL 파싱 후 데이터 가공 하기**] API 스터디 Repository 입니다.

### 개발 환경
- IDE : IntelliJ
- Java : open jdk 11
- Build Tool : gradle
- Lib, Framework : spring boot 2.4.2, swagger 2.9.2, mockwebserver 4.0.1, okhttp 4.0.1

### 실행 방법(택1)
1.  IntelliJ 상에서 해당 저장소 clone 후 실행
2.  project root 에 있는 spring_backend_api.jar 다운로드 후 실행. (java 11 이상)
<pre>
#java -jar spring_backend_api.jar

http://localhost:8080/swagger-ui.html 접속
</pre>

### 구현 방법 및 특이사항
* 컨트롤러 : 기본적인 @RestController 어노테이션 및 @RequestMapping 통해 구현하였습니다.
* 서비스
  * 클래스(HttpResponseDividerServiceImpl) 내부에 Http 요청을 추상화(ResponseBodyReader)하여 단위 테스트가 가능하도록 구현하였습니다.
  * 출력은 정렬된 알파벳뭉치, 숫자뭉치를 따로 구하여 합쳐서 제작하였습니다
  * 정렬은 숫자 -> 기본 정렬, 알파벳 -> 대,소문자를 특정 숫자에 매핑하여 정렬하였습니다
* 테스트
  * 서비스 : http 요청 부분은 Mock 객체를 통해 단위테스트로 구현하였습니다
  * 컨트롤러 : MockWebServer 를 통해 임시 Http 서버를 올려서 테스트하였습니다.   