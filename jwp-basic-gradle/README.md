# jwp-basic
자바 웹 프로그래밍 기본 실습

### 7.2. DAO 리팩토링 요구사항
- JDBC 공통 라이브러리 제작
  - 개발자가 SQL 쿼리, 쿼리에 전달할 인자, SELECT 구문의 경우 조회한 데이터를 추출
  - SQLException을 Runtime Exception으로 변환하여 try/catch 문을 사용하지 않도록 수정
- 요구사항 힌트
  - insert/update 쿼리는 비슷하기 때문에 먼저 insert/update 쿼리를 가지는 메소드의 중복 제거 작업을 진행한다.
  - 분리한 메소드 중에서 변화가 발생하지 않는 부분 (즉, 공통 라이브러리로 구현할 코드)를 새로운 클래스로 추가한후 이동한다.
    - 공통라이브러리 : Connection, Statement, ResultSet, Parameter Setting, Transaction 관리
  - https://www.youtube.com/watch?v=ylrMBeakVnk&feature=youtu.be&ab_channel=%EB%B0%95%EC%9E%AC%EC%84%B1
    * DAO 중복 코드에 대한 이슈 제기
    * 라이브러리 코드와 개발자가 구현해야 하는 코드 분리
    * abstract 키워드를 활용해 추상 클래스 구현
    * 익명 클래스 사용
    * insert, update, delete 문에 대한 중복 제거