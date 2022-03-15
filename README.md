# 카카오페이 사전과제 - 고객문의 접수 및 답변 기능 개발

## 데이터베이스 ERD
<img width="569" alt="erd" src="https://user-images.githubusercontent.com/60500720/158424616-6fa389cc-d4d8-4479-a344-d0c042c58bb2.png">

## 프로젝트 구성
* Backend
    * JDK 11
    * H2 Database
    * Gradle 7.4
    * Spring Boot 2.6.4
    * Spring Security
    * Spring Data JPA
    * Spring Validation
    * Jwt
    * Lombok
    * Mapstruct
* Frontend
    * Nuxt.js 2.15.8 (Vue 2.0)


## 실행방법
아래의 명령어로 실행 하실 수 있습니다.
### Backend
```bash
$ ./gradlew bootRun --args='--spring.profiles.active=local'
```
### Frontend
```bash
$ npm install
$ npm run dev
```

## 문제 해결 전략
### Backend (API)
#### 1. 공통 Response
모든 성공(SuccessResponseApiDto)과 실패(ErrorResponseApiDto)를 AbstractApiResponseDto을 확장하여 구현 하였습니다.
```Java
@Getter
public abstract class AbstractApiResponseDto {

    private final Integer code;
    private final String message;

    public AbstractApiResponseDto(HttpStatus status) {
        this(status.value(), status.getReasonPhrase());
    }

    public AbstractApiResponseDto(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
```
**SuccessResponse**
```Json
{ "code": 200, "message": "OK", "data": "body" }
```
**ErrorResponse**
```Json
{ "code": "?", "message":  "?", "errors":  "?" }
```

#### 2. 공통 에러 처리
모든 오류를 RestExceptionHandler에서 공통으로 처리하도록 구현 하였습니다.
```Java
public class RestExceptionHandler {

    @ExceptionHandler({
            BindException.class,
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class
    })
    protected ResponseEntity<?> handleBadRequest(Exception e) {
        BindingResult bindingResult = null;
        if (e instanceof BindException) {
            bindingResult = ((BindException) e).getBindingResult();
        }
        return errorResponse(HttpStatus.BAD_REQUEST, FieldErrorDetail.getList(bindingResult));
    }
    ...생략
}
```
- Custom Exception (KakaoPayException)을 사용하여 의도적으로 내는 오류들도 처리하고 있습니다.
- 모든 오류와 메세지는 HttpStatus 기반으로 사용하고 있습니다. (대략 내용은 아래와 같습니다.)
    * 400 : Bad Request 
        * Spring Validation 통하여 들어온 에러를 처리하고, field 오류 정보를 같이 내보내고 있습니다.
        * 데이터가 들어오지 않을때, 그리고 타입이 맞지 않을때 처리합니다.
    * 401 : Unauthorized 
        * 인증이 필요할때 처리하고 있습니다.
    * 404 : Not Found
        * 리소스가 없을때, 지원하지 않는 Http Method로 들어 왔을 경우 처리하고 있습니다
    * 409 : Conflict
        * 업데이트 할때 충돌이 나는 경우 (이미 업데이트 된 경우) 처리하고 있습니다.
    * 500 : Internal Server Error
        * 오류를 처리 할 수 없을떄 알 수 없을 때 처리하고 있습니다.
        
#### 3. Security + Jwt를 이용한 인증 및 로그인
1. 로그인을 통하여 access_token을 발급 하도록 하였습니다.
2. 인증이 필요한 페이지들은 access_token을 사용하여 Filter, Provider을 통하여 인증을 거치도록 하였습니다.
3. access_token 유효기간은 기본 30분으로 하였습니다.
```Java
public class TokenManager {

    private final Key key;
    private static final String PAYLOAD = "payload";
    private static final long DEFAULT_EXPIRATION = 1800;

    public TokenManager(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public <T extends Payload> TokenDto generateToken(T payload) {
        return generateToken(payload, DEFAULT_EXPIRATION);
    }

    public <T extends Payload> TokenDto generateToken(T payload, long expiration) {
        Date exp = new Date(System.currentTimeMillis() + (expiration * 1000));
        String accessToken = Jwts.builder()
                .setClaims(Jwts.claims(Map.of(PAYLOAD, payload)))
                .setExpiration(exp)
                .signWith(this.key)
                .compact();
        return TokenDto.builder()
                .accessToken(accessToken)
                .accessTokenExpiration(exp.getTime())
                .build();
    }
    ...생략
}
class SecurityConfig {
    @Bean
    public TokenAuthenticationProvider tokenAuthenticationProvider() {
        return new TokenAuthenticationProvider(tokenManager);
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenAuthenticationProvider());
    }
    ...생략
}
```

#### 4. Entity 공통 처리
1. 기본키가 되는 id와, createdDate 생성시간, modifiedDate 수정시간을 공통적으로 넣을 수 있도록 BaseEntity를 구현했습니다.
2. 강제적으로 Dto를 리턴하는 메소드를 구현 하도록 했습니다.
```Java
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @CreatedDate
    private LocalDateTime createdDate;

    @Column
    @LastModifiedDate
    private LocalDateTime modifiedDate;

    public abstract BaseResponseDto toDto();
}
```

#### 5. MapStruct을 사용하여 Entity와 Dto 자동 매핑
1. 얼마 되지는 않지만, 자동적으로 매핑 할 수 있도록 구현 하였습니다. 아래의 코드는 공통 interface 입니다.
2. 기본 원칙으로 BaseEntity, BaseRequestDto, BaseResponseDto 상속 받아야 사용 할 수 있도록 하였습니다.
```Java
public interface BaseMapStruct<E extends BaseEntity, REQ extends BaseRequestDto, RES extends BaseResponseDto> {
    E toEntity(REQ dto);
    RES toDto(E entity);
    List<RES> toDtoList(List<E> entityList);
}
```

#### 7. API
API는 총 6개로 구성 되어 있습니다.
##### 1. 고객 문의 내용 API
```
GET /api/v1/inquiries/{id} HTTP 1.1
Host: localhost:8888
```
**Parameter**

|파라미터|타입|설명|필수|
|----------|-------|----------|----|
|id|Long| 문의 ID| O|

**Response**

|파라미터|타입|설명|필수|
|----------|-------|----------|----|
|id|Long| 문의 ID|O|
|title|String|제목|O|
|content|String|내용|O|
|reply|Json|답변|X|
|createdDate|String|등록일자|O|

##### 2. 고객 문의 등록 API
```
POST /api/v1/inquiries HTTP 1.1
Host: localhost:8888
```

**Parameter**

|파라미터|타입|설명|필수|
|----------|-------|----------|----|
|customerId|String|사용자 식별 아이디|O|
|title|String|제목|O|
|content|String|내용|O|

**Response**

|파라미터|타입|설명|필수|
|----------|-------|----------|----|
|id|Long| 문의 ID|O|
|title|String|제목|O|
|content|String|내용|O|
|reply|Json|답변|X|
|createdDate|String|등록일자|O|

##### 3. 고객 아이디로 리스트 가져오기
```
GET /inquiries/customers/{id} HTTP 1.1
Host: localhost:8888
```

**Parameter**

|파라미터|타입|설명|필수|
|--------------|-------|----------|----|
|id|String|고객 식별 아이디|O|

**Response**

|파라미터|타입|설명|필수|
|-----------|------------|------------|----|
|elements|List< inquiry >|고객 문의 리스트|O|

##### 4. 로그인 및 토큰 발급
```
POST /api/v1/auth/authorize HTTP 1.1
Host: localhost:8888
```

**Parameter**

|파라미터|타입|설명|필수|
|----------|-------|----------|----|
|username|String|아이디|O|
|password|String|비밀번호|O|

**Response**

|파라미터|타입|설명|필수|
|-------------------------|-------|----------|----|
|access_token|String|토큰|O|
|accessTokenExpiration|Long|토큰만료시간|O|

##### 5. 문의 상담사 지정 API
```
PATCH /api/v1/inquiries/{id}/counselors HTTP 1.1
Host: localhost:8888
Authorization: Bearer ${ACCESS_TOKEN}
```

**Parameter**

|파라미터|타입|설명|필수|
|--------------|-------|----------|----|
|id|Long| 문의 ID| O|
|access_token|String|토큰|O|

**Response**

|파라미터|타입|설명|필수|
|----------|-------|----------|----|
|id|Long| 문의 ID|O|
|title|String|제목|O|
|content|String|내용|O|
|reply|Json|답변|X|
|createdDate|String|등록일자|O|

##### 6. 미답변 문의 내용 전체 가져오기
```
GET /api/v1/inquiries/no-counselors HTTP 1.1
Host: localhost:8888
Authorization: Bearer ${ACCESS_TOKEN}
```

**Parameter**

|파라미터|타입|설명|필수|
|--------------|-------|----------|----|
|access_token|String|토큰|O|

**Response**

|파라미터|타입|설명|필수|
|-----------|------------|------------|----|
|elements|List< inquiry >|미답변 문의 리스트|O|

##### 7. 고객 문의 답변 등록 
```
POST /api/v1/inquiries/replies HTTP 1.1
Host: localhost:8888
Authorization: Bearer ${ACCESS_TOKEN}
```
**Parameter**

|파라미터|타입|설명|필수|
|--------------|-------|----------|----|
|inquiryId|Long|문의 ID|O|
|content|String|답변내용|O|
|access_token|String|토큰|O|

**Response**

|파라미터|타입|설명|필수|
|-----------|------------|------------|----|
|id|Long|답변 ID|O|
|cotent|String|답변 내용|O|
|createdDate|String|등록일자|O|


### Frontend
#### 1. 공통 스크립트 구현
1. 유틸리티성 라이브러리 사용 불가로 인하여 직접 구현하여 사용하였습니다. (쿠키, HTTP 통신)
2. http 통신 후 기본 오류 처리를 공통으로 하였습니다. 
    * 추가적으로 isErrorHandler 옵션을 주게 되면 직접 페이지에서 구현해서 사용 할 수 있도록 하였습니다.
3. SSR(서버 사이드), CSR(클라이언트 사이드)에서 전부 사용 가능하도록 구현 하였습니다.
```javascript
export default ({ app, req, res, redirect, route }, inject) => {

  // kakao common script
  const kakao = {};

  kakao.cookie = (function() {
    return {
      set(name, value, time) {
        const date = new Date(time);
        const cookie = `${name}=${value};expires=${date.toUTCString()};path=/`;
        if (res) {
          res.setHeader('Set-Cookie', cookie);
        } else {
          document.cookie = cookie;
        }
      },
      get(name) {
        const key = `${name}=`;
        const cookies = (req?.headers || document).cookie?.split(';');
        for (let i = 0, n = cookies?.length; i < n; ++i) {
          let cookie = cookies[i];
          while (cookie.charAt(0) === ' ') {
            cookie = cookie.substring(1);
          }
          if (cookie.indexOf(key) === 0) {
            return cookie.substring(key.length);
          }
        }
        return null;
      },
      delete(name) {
        this.set(name, '', new Date().getTime() - 1);
      },
    }
  })();

  kakao.fetch = (function() {
    const defaultOptions = {
      headers: {
        'Content-Type': 'application/json'
      }
    };
    const excludeErrorCode = [401];
    const errorHandlerObject = {
      400(responseJson, { fieldErrorMessage }) {
        // bad request
        //responseJson?.errors?.forEach(({ field, message }) => fieldErrorMessage[field] = message)
      },
      401() {
        if (process.client) {
          alert('세션이 만료되어 로그인 페이지로 이동합니다.');
        }
        kakao.cookie.delete('access_token');
        redirect('/login');
      },
      default({code}) {
        redirect(`/error?code=${code}`);
      }
    };
    async function request(url, {isErrorHandler, ...restOptions}) {
      const mergeOptions = {
        ...defaultOptions,
        ...restOptions,
      };
      const accessToken = kakao.cookie.get('access_token');
      if (accessToken) {
        mergeOptions.headers['Authorization'] = `Bearer ${accessToken}`;
      }
      const response = await fetch(`${process.env.API_URL}${url}`, mergeOptions);
      const responseJson = await response.json();
      if (!response.ok) {
        const { code } = responseJson;
        if (isErrorHandler && !excludeErrorCode.includes(code)) {
          return Promise.reject(responseJson);
        }
        (errorHandlerObject[code] || errorHandlerObject.default)(responseJson);
        return Promise.reject();
      }
      return responseJson;
    }
    return {
      async get(url, options) {
        return await request(url, {
          ...options,
          method: 'GET'
        });
      },
      async post(url, data, options) {
        return await request(url, {
          ...options,
          method: 'POST',
          body: JSON.stringify(data)
        });
      },
      async patch(url, data, options) {
        return await request(url, {
          ...options,
          method: 'PATCH',
          body: JSON.stringify(data)
        });
      },
      // TODO : put, delete
    }
  })();

  inject('kakao', kakao);
}
```

#### 2. middleware, layout을 사용하여 페이지 구분
상담사, 사용자 페이지를 구분 두기 위해 사용하여 구현하였습니다.

#### 3. ErrorPage 공통으로 구현
에러를 공통적으로 처리하기 위해 ErrorPage를 컴포넌트로 구현하였습니다.

#### 4. 페이지
1. 고객 문의 등록 페이지 : /customer/inquiry/register
2. 고객 문의 확인 페이지 : /customer/inquiry/_id
3. 고객 아이디로 문의 확인 페이지 : /customer/inquiry/list?id=
4. 상담사 로그인 페이지 : /login
5. 미 답변 문의 리스트 페이지 : /counselor/inquiry/list
    * setInterval을 사용하여 페이지를 10초에 한번씩 polling 하도록 하였습니다.
6. 문의 답변 등록 페이지 : /counselor/inquiry/reply