# 단계별 요구사항

> 시작하기전 처음 개발할때 너무 리팩터링과 테스트코드에 집중하지 말고 단계별로 기능의 목적을 정확히 파악하고 필요이상을 개발하지 않기

## 1단계 요구사항

> `http://localhost:8080/index.html`로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.
>

```java

19:50:15.242[INFO][main][webserver.WebServer]-Web Application Server started 8080port.
        19:50:33.800[DEBUG][Thread-0][webserver.RequestHandler]-New Client Connect!Connected IP:/0:0:0:0:0:0:0:1,Port:53028
        19:50:33.801[INFO][Thread-0][webserver.RequestHandler]-GET/index.html HTTP/1.1
        19:50:33.980[DEBUG][Thread-1][webserver.RequestHandler]-New Client Connect!Connected IP:/0:0:0:0:0:0:0:1,Port:53033
        19:50:33.981[DEBUG][Thread-2][webserver.RequestHandler]-New Client Connect!Connected IP:/0:0:0:0:0:0:0:1,Port:53034
        19:50:34.561[INFO][Thread-1][webserver.RequestHandler]-GET/favicon.ico HTTP/1.1

```

첫번째 응답을 받았을때 /index.html에 대한 요청과 /favicoon에 대한 정보를 요청받음

```

var splitStr=startLine.split(" ");
var url=splitStr[1]; // 첫줄의 1번째 인덱스에는 Url이 위치함
var body=Files.readAllBytes(new File("./webapp"+url).toPath());
// TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.

DataOutputStream dos=new DataOutputStream(out);

response200Header(dos,body.length);
responseBody(dos,body);
```

해당 방법으로 `start line`에서 요청 url을 뽑아낼 수 있게 됨

## 2단계 요구사항>

> GET 요청을 통해서 회원가입을 할 수 있어야 함

```java
class Runner {
    void run() {
        ...
        var requestPath = url;
        var paramString = "";
        if (delimiterIndex != -1) {
            requestPath = url.substring(0, delimiterIndex);
            paramString = url.substring(delimiterIndex + 1);
        }

        var queryParams = HttpRequestUtils.parseQueryString(paramString);
        ...
    }
}
```

해당 방법으로 정상적으로 쿼리스트링이 파싱되었으며 다음 요구사항은 회원가입을 하는 것입니다.

```java 
class Runner {
    void run() {
        //...
        if (requestPath.equals("/user/create")) {
            var user = User.builder()
                    .userId(queryParams.get("userId"))
                    .password(queryParams.get("password"))
                    .email(queryParams.get("email"))
                    .name(queryParams.get("name"))
                    .build();
            DataBase.addUser(user);
            log.info(DataBase.findUserById(queryParams.get("userId")).toString());
        }
        //...
    }
}
```

이제 회원가입을 하기 위한 url로 접근할 경우 정상적으로 인메모리 데이터베이스에 저장되는 것을 확인했다.

## 요구사항3

> Get방식에서 Post로 회원가입하기