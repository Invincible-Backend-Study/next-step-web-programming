# jwp-basic
자바 웹 프로그래밍 기본 실습

### 7.2. DAO 리팩토링 요구사항
- JDBC 공통 라이브러리 제작
  - 개발자가 SQL 쿼리, 쿼리에 전달할 인자, SELECT 구문의 경우 조회한 데이터를 추출
  - SQLException을 Runtime Exception으로 변환하여 try/catch 문을 사용하지 않도록 수정
- 요구사항 힌트
  - 분리한 메소드 중에서 변화가 발생하지 않는 부분 (즉, 공통 라이브러리로 구현할 코드)를 새로운 클래스로 추가한후 이동한다.
    - 공통라이브러리 : Connection, Statement, ResultSet, Parameter Setting, Transaction 관리
  - insert, update, delete 중복 제거
    - https://www.youtube.com/watch?v=ylrMBeakVnk&feature=youtu.be&ab_channel=%EB%B0%95%EC%9E%AC%EC%84%B1
    * DAO 중복 코드에 대한 이슈 제기
      - insert/update 쿼리는 비슷하기 때문에 먼저 insert/update 쿼리를 가지는 메소드의 중복 제거 작업을 진행한다.
    * 라이브러리 코드와 개발자가 구현해야 하는 코드 분리
    * abstract 키워드를 활용해 추상 클래스 구현
    * 익명 클래스 사용
    * insert, update, delete 문에 대한 중복 제거
  * select 중복 제거
    * https://www.youtube.com/watch?v=zfXAZkqPH44&ab_channel=%EB%B0%95%EC%9E%AC%EC%84%B1
    * select 쿼리에 대한 중복 코드 제거
    * getConnection 메서드 중복 제거 및 테스트 코드 수정
    * Template Method 패턴
  * Template 통합
    * https://www.youtube.com/watch?v=yEHUB97B62I&ab_channel=%EB%B0%95%EC%9E%AC%EC%84%B1
    * Template Method 패턴을 활용해 JdbcTemplate과 SelectJdbcTemplate 통합
    * Template Method 패턴을 활용할 때의 문제점
    * 각 Method를 interface로 분리해 JdbcTemplate과 SelectJdbcTemplate 통합
  * 라이브러리 리팩토링 및 목록 기능 추가
    * https://www.youtube.com/watch?v=nkepkHJi7e8&ab_channel=%EB%B0%95%EC%9E%AC%EC%84%B1
    * 자바 Generic을 활용해 캐스팅을 하지 않도록 라이브러리 구현
    * 가변 인자를 활용해 개발자 편의성 개선
    * 여러 건의 데이터를 조회할 수 있는 기능 추가
    * 라이브러리 코드에서 중복을 제거하는 과정에 대한 설명
  * SQLException을 런타임 에러로 래핑
    * https://www.youtube.com/watch?v=lFTyw7Uipyo&ab_channel=%EB%B0%95%EC%9E%AC%EC%84%B1
    * Checked Exception과 Unchecked Exception
    * SQLException을 Unchecked Exception으로 변환 처리
  * 람다 표현식을 사용하여 리팩토링
    * https://www.youtube.com/watch?v=0ax9jxfW9x4&ab_channel=%EB%B0%95%EC%9E%AC%EC%84%B1
    * RowMapper에 FunctionalInterface 어노테이션 설정
    * RowMapper를 사용할 때 람도 표현식을 사용하도록 리팩토링

## AJAX를 활용해 새로고침 없이 데이터 갱신하기
### 8.1 질문 답변 게시판 구현
- 질문 답변 관련 객체 및 DAO 구현
- 질문
  - GET - Read 질문 리스트
  - GET - Read 질문
  - GET - Read 질문 작성 Form
  - POST - Create 질문 생성
  - PUT - Update 질문 수정
  - DELETE - Delete 질문 삭제
- 답변
  - GET - Read 답변 리스트
  - GET - Read 답변
  - GET - Read 답변 작성 Form
  - POST - Create 답변 생성
  - PUT - Update 답변 수정
  - DELETE - Delete 답변 삭제
