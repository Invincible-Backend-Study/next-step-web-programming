# 실습을 위한 개발 환경 세팅
* https://github.com/slipp/web-application-server 프로젝트를 자신의 계정으로 Fork한다. Github 우측 상단의 Fork 버튼을 클릭하면 자신의 계정으로 Fork된다.
* Fork한 프로젝트를 eclipse 또는 터미널에서 clone 한다.
* Fork한 프로젝트를 eclipse로 import한 후에 Maven 빌드 도구를 활용해 eclipse 프로젝트로 변환한다.(mvn eclipse:clean eclipse:eclipse)
* 빌드가 성공하면 반드시 refresh(fn + f5)를 실행해야 한다.

# 웹 서버 시작 및 테스트
* webserver.WebServer 는 사용자의 요청을 받아 RequestHandler에 작업을 위임하는 클래스이다.
* 사용자 요청에 대한 모든 처리는 RequestHandler 클래스의 run() 메서드가 담당한다.
* WebServer를 실행한 후 브라우저에서 http://localhost:8080으로 접속해 "Hello World" 메시지가 출력되는지 확인한다.

# 각 요구사항별 학습 내용 정리
* 구현 단계에서는 각 요구사항을 구현하는데 집중한다.
* 구현을 완료한 후 구현 과정에서 새롭게 알게된 내용, 궁금한 내용을 기록한다.
* 각 요구사항을 구현하는 것이 중요한 것이 아니라 구현 과정을 통해 학습한 내용을 인식하는 것이 배움에 중요하다.

### 요구사항 1 - http://localhost:8080/index.html로 접속시 응답
- [x]  Request Line을 기준으로 Http Method, Request URI, 전송방식을 구분해야 한다.
- [x]  Http Method는 상태값을 가진다.
- [x]  Request URI를 이용해 webapp에 존재하는 index.html 정적 리소스를 요청해야 한다.
- [x]  전송 방식은 기본이 HTTP/1.1이다.

### 요구사항 2 - get 방식으로 회원가입
- [x] 모든 요청은 Request Line, Request Header, Message body로 나뉜다.
- [X] GET 쿼리스트링으로 값이 들어온다면, URI와 쿼리스트링을 분리할 수 있어야한다.
  - [X] 각 쿼리스트링 값은 &로 구분되며, key=value 타입이다.
- [x] 입력받은 내용을 가지고 회원가입을 해야한다.
- [x] 회원가입을 하게되면 해당정보로 User 클래스를 생성한다.

### 요구사항 3 - post 방식으로 회원가입
- [x] POST로 값이 들어왔을떄 request header와 쿼리스트링 값을 분리할 수 있어야 한다.
- [x] 입력받은 내용을 가지고 회원가입을 해야한다.
- [x] 회원가입을 하게되면 해당 정보로 User 클래스의 인스턴스를 생성한다.

### 요구사항 4 - redirect 방식으로 이동
- [x] 회원가입을 완료하면 /index.html 페이지로 이동해야 한다.
  - [x] Response Http Status를 302로 변경해야 한다.
  - [x] Location header에 /index.html을 입력해야 한다.

### 요구사항 5 - 로그인 하기 with cookie
- [x] 사용자가 회원가입을 하게 되면 database에 해당 user 정보를 저장한다.
- [x] 사용자의 로그인 요청시 입력한 값과 DB의 user와 일치한다면 로그인 성공이다.
  - [x] 일치하지 않으면 실패
- [x] 로그인 성공시 response header에 cookie값으로 logined=true를 전달해야 한다.
- [x] 로그인을 성공하게 되면 index.html로 redirect 한다.

### 요구사항 6 - 사용자 목록 출력
- [x] request header에 존재하는 쿠키값을 파싱해야 한다.
- [x] logined=true라면 사용자의 목록을 보여준다.
- [x] logined=false라면 로그인 페이지(login.html)로 이동한다.
- [x] 사용자의 목록은 데이터베이스에서 가져온다.

### 요구사항 7 - CSS 지원하기
- [X] Content-Type또는 Accept를 활용해 CSS를 적용해야 한다.

### heroku 서버에 배포 후
* 