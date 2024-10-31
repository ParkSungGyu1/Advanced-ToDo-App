# API 명세서

| API 명칭   | 메서드    | 요청 URL                                 | 요청 헤더                          | 요청 본문                                                                                  | 응답 코드 | 설명          | 응답 본문                                                                                                                  |
|----------|--------|----------------------------------------|--------------------------------|----------------------------------------------------------------------------------------|-------|-------------|------------------------------------------------------------------------------------------------------------------------|
| 일정 생성    | POST   | /api/todo                              | Content-Type: application/json | ```json {"username":"testuser","title":"할 일 제목", "description":"이 할 일에 대한 설명입니다."} ``` | 201   | 일정 생성 성공    | ```json {"id":1,"username":"testuser","title":"할 일 제목", "description":"이 할 일에 대한 설명입니다.","createdAt":"2024-10-03"} ``` |
| 전체 일정 조회 | GET    | /api/todo                              | Content-Type: application/json | N/A                                                                                    | 200   | 전체 일정 조회 성공 | ```json [ { "id": 1, "username": "testuser", "email": "test@example.com" } ... ] ```                                   |
| 선택 일정 조회 | GET    | /api/todo/{todoId}                     | Content-Type: application/json | N/A                                                                                    | 200   | 선택 일정 조회 성공 | ```json {"id":1,"username":"testuser","title":"할 일 제목", "description":"이 할 일에 대한 설명입니다.","createdAt":"2024-10-03"} ``` |
| 일정 수정    | PUT    | /api/todo/{todoId}                     | Content-Type: application/json | ```json {"username":"수정된 유저", "description":"수정된 값"}```                                | 204   | 선택 일정 수정 성공 | N/A                                                                                                                    |
| 일정 삭제    | DELETE | /api/todo/{todoId}                     | Content-Type: application/json | N/A                                                                                    | 204   | 선택 일정 삭제 성공 | N/A                                                                                                                    |
| 댓글 생성    | POST   | /api/todo/{todoId}/comment             | Content-Type: application/json | ```json {"username":"testuser","comment":"댓글 내용"} ```                                  | 201   | 댓글 생성 성공    | ```json {"username":"testuser","comment":"댓글 내용",  "createdAt":"2024-10-03"} ```                                       |
| 댓글 수정    | PUT    | /api/todo/{todoId}/comment/{commentId} | Content-Type: application/json | ```json {"username":"testuser","comment":"댓글 내용"}```                                   | 204   | 선택 댓글 수정 성공 | N/A                                                                                                                    |
| 댓글 삭제    | DELETE | /api/todo/{todoId}/comment/{commentId} | Content-Type: application/json | N/A                                                                                    | 204   | 선택 댓글 삭제 성공 | N/A                                                                                                                    |
| 유저 생성    | POST   | /api/user                              | Content-Type: application/json | ```json {"username":"testuser","email":"email@email.com"} ```                          | 201   | 일정 유저 성공    | ```json {"username":"testuser","email":"할 일 제목", "createdAt":"2024-10-03"} ```                                         |
| 유저 조회    | GET    | /api/user/{userId}                     | Content-Type: application/json | N/A                                                                                    | 200   | 유저 조회 성공    | ```json {"username":"testuser","email":"할 일 제목", "createdAt":"2024-10-03", "updatedAt": "2024-10-03"} ```              |
| 유저 수정    | PUT    | /api/user/{userId}                     | Content-Type: application/json | ```json {"username":"수정된 유저", "email":"email@email.com"}```                            | 204   | 유저 수정 성공    | N/A                                                                                                                    |
| 유저 삭제    | DELETE | /api/user/{userId}                     | Content-Type: application/json | N/A                                                                                    | 204   | 유저 삭제 성공    | N/A                                                                                                                    |


# 3-Layered Architecture 에서 유효성 검사가 어떤 계층의 책임인가 <br>

## 유효성 검사는 구체화와 같은 것이다. <br>
예를들어서 client는 JSON data로 요청해야한다 → JSON data가 아닌 요청은 거부한다이다.<br> 
즉 구체화와 유효성은 함께 가는 것이다.

## Controller Layer : shapes and types <br>
client가 보낸 data의 형식이 예상했던 것과 같은지 검사해야한다. 예를들어 아래와 같은 data를 기대했다면 <br>
```
json  {
    "currency": "EUR",
    "amount": 1000,
    "options": {
        "localed": "nl_NL"
    }
}
```

1) payload에는 유효한 JSON data형식이어야한다.
2) currency는 string이어야한다.
3) amount는 integer 이어야한다.
4) option은 object이며 그 안에 localed은 string이어야한다.

따라서 아래와 같은 유효성 검사가 이뤄져야합니다. <br>

## Service Layer : from types to concepts
Domain Layer에 갈 수 있는 상태로 만들어주어야 합니다. <br>
예를 들어 PaymentMethod soqn validMethods 변수는 여러 string값을 갖을 수 있었지만 <br>
PaymentMethod는 비즈니스 적으로 'ideal', 'creditcard', 'banktransfer' 중에 하나여야 한다면 Service Layer에서 잡아줘야합니다.<br> 
validation은 아래와 같이 할 수 있습니다.

```
public class PaymentMethod {

    public static PaymentMethod fromString(String name) {
        List<String> validMethods = Arrays.asList("ideal", "creditcard", "banktransfer");

        if (!validMethods.contains(name)) {
            throw new PaymentMethodIsNotValid(name);
        }

        return new PaymentMethod(name);
    }

    public String getName() {
        return name;
    }
}

class PaymentMethodIsNotValid extends RuntimeException {
    public SorryPaymentMethodIsNotValid(String name) {
        super("Sorry, payment method is not valid: " + name);
    }
}
```
## Domain Layer : guarding invariants

여기서는 invariants(항상 사실이어야하는 것)를 체크해줘야합니다. <br>
예를 들어 은행시스템에서 자신이 가지고 있는 돈보다 더 많이 인출하는 것은 불가능하겠죠? <br>
해당 상황은 아래와 같이 체크해볼 수 있습니다.

```

public class BankAccount {
    private double balance; // 잔액 필드

    public BankAccount(double initialBalance) {
        this.balance = initialBalance; // 초기 잔액 설정
    }

    public boolean spend(Amount amount) {
        // guard first
        guardAgainstOverspending(amount);

        // 잔액 변경
        this.balance -= amount.getValue();
        return true; // 지출이 성공적으로 완료되면 true 반환
    }

    private void guardAgainstOverspending(Amount amount) {
        if (this.balance < amount.getValue()) {
            throw new SorryNotEnoughBalanceForSpending(amount);
        }
    }

    public double getBalance() {
        return balance; // 현재 잔액 반환
    }
}
```
## ‘내’가 정의한 문제와 해결 과정
### ❌ 안 좋은 예: RESTful API를 기반으로 상품의 좋아요와 장바구니 CRUD API를 작성했습니다.
### ✅ 좋은 예: 현재 서비스 내에서 A 대상군의 실 결제 전환율 개선을 위한 기능으로 상품의 즐겨찾기와 좋아요 기능 구현.
'즐겨찾기'는 구현에 큰 어려움이 없었으나, '좋아요' 같은 경우에는 상품에도 좋아요 수를 업데이트해야 하는 작업이 있어 상품 테이블에 대해 데드락 이슈가 간간히 발생 (근거자료 혹은 발생으로인한 에러로그 등) <br> 좋아요를 할 때마다 쿼리를 날려 DB에 Write 요청이 많은 것도 큰 문제가 되었음<br>
따라서 이를 해결하기 위해 Cache Counter를 도입하였고, 5분 단위로 해당 Cache Counter에 등록된 값이 DB에 반영되도록 결정함 <br>
프론트에서 좋아요 수를 요청할 때는 Cache Counter에 등록된 값과 DB에 있는 값을 더해서 내려주는 방식으로 실시간성 해소 및 성능 % 개선 (수치화)