# DDD (Domain Driven Design) 개발 가이드

Team3-SockThree 백엔드 프로젝트의 DDD 적용 가이드입니다.

## 📚 DDD 기본 개념

### 전략적 설계 (Strategic Design)

#### 🏢 **Bounded Context (경계 컨텍스트)**
각 도메인은 명확한 경계를 가지며 독립적으로 발전할 수 있습니다.

```
예시 Bounded Context:
- User Context: 사용자 관리, 인증, 권한
- Product Context: 상품 관리, 카테고리
- Order Context: 주문, 결제, 배송
- Inventory Context: 재고 관리
```

#### 🗺️ **Context Map**
컨텍스트 간의 관계를 명시합니다.

```
User Context → Order Context (Customer/Supplier)
Order Context → Product Context (Conformist)
Order Context → Inventory Context (Partnership)
```

### 전술적 설계 (Tactical Design)

#### 🎯 **핵심 구성 요소**

1. **Entity (엔티티)**
   - 고유 식별자를 가진 객체
   - 생명주기 동안 상태 변경 가능
   - 비즈니스 규칙 포함

2. **Value Object (값 객체)**
   - 불변 객체
   - 동등성은 속성 값으로 판단
   - 엔티티의 속성을 표현

3. **Aggregate (애그리거트)**
   - 연관된 객체들의 군집
   - 일관성 경계 정의
   - Root Entity를 통해서만 접근

4. **Repository (저장소)**
   - 도메인 객체 저장/조회 추상화
   - 컬렉션처럼 동작

5. **Domain Service (도메인 서비스)**
   - 엔티티나 값 객체에 속하지 않는 도메인 로직
   - 여러 애그리거트 간의 비즈니스 규칙

## 🏗️ 구현 가이드

### 1. Entity 구현 예시

```java
// domain/user/entity/User.java
@Entity
public class User {
    private UserId id;
    private Email email;
    private Password password;
    private UserStatus status;
    private LocalDateTime createdAt;
    
    // 비즈니스 메서드
    public void changePassword(Password newPassword) {
        validatePasswordPolicy(newPassword);
        this.password = newPassword;
    }
    
    public void activate() {
        if (this.status == UserStatus.DELETED) {
            throw new IllegalStateException("삭제된 사용자는 활성화할 수 없습니다.");
        }
        this.status = UserStatus.ACTIVE;
    }
    
    private void validatePasswordPolicy(Password password) {
        // 패스워드 정책 검증 로직
    }
}
```

### 2. Value Object 구현 예시

```java
// domain/user/vo/Email.java
@Embeddable
public class Email {
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    
    private String value;
    
    protected Email() {} // JPA용
    
    public Email(String value) {
        validate(value);
        this.value = value;
    }
    
    private void validate(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("이메일은 필수입니다.");
        }
        if (!EMAIL_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("유효하지 않은 이메일 형식입니다.");
        }
    }
    
    // equals, hashCode, toString 구현
}
```

### 3. Repository 인터페이스 구현

```java
// domain/user/repository/UserRepository.java
public interface UserRepository {
    Optional<User> findById(UserId id);
    Optional<User> findByEmail(Email email);
    List<User> findByStatus(UserStatus status);
    void save(User user);
    void delete(User user);
}
```

### 4. Domain Service 구현 예시

```java
// domain/user/service/UserDomainService.java
@Service
public class UserDomainService {
    private final UserRepository userRepository;
    
    public boolean isEmailDuplicated(Email email) {
        return userRepository.findByEmail(email).isPresent();
    }
    
    public void validateUserRegistration(User user) {
        if (isEmailDuplicated(user.getEmail())) {
            throw new DuplicateEmailException("이미 사용 중인 이메일입니다.");
        }
    }
}
```

### 5. Application Service 구현 예시

```java
// application/service/UserApplicationService.java
@Service
@Transactional
public class UserApplicationService {
    private final UserRepository userRepository;
    private final UserDomainService userDomainService;
    
    public UserDto registerUser(RegisterUserCommand command) {
        // 1. DTO를 도메인 객체로 변환
        User user = User.create(
            new Email(command.getEmail()),
            new Password(command.getPassword())
        );
        
        // 2. 도메인 서비스를 통한 비즈니스 규칙 검증
        userDomainService.validateUserRegistration(user);
        
        // 3. 저장
        userRepository.save(user);
        
        // 4. 도메인 객체를 DTO로 변환하여 반환
        return UserDto.from(user);
    }
}
```

## 📋 네이밍 컨벤션

### 패키지 구조
```
com.softeer.backend.domain.{domain_name}.{layer}
```

### 클래스 네이밍
- **Entity**: `User`, `Product`, `Order`
- **Value Object**: `Email`, `Money`, `Address`
- **Repository Interface**: `UserRepository`, `ProductRepository`
- **Repository Implementation**: `UserJpaRepository`, `UserRepositoryImpl`
- **Domain Service**: `UserDomainService`, `OrderDomainService`
- **Application Service**: `UserApplicationService`, `OrderApplicationService`

### 메서드 네이밍
- **도메인 메서드**: 비즈니스 의미가 명확한 이름
  - `activate()`, `deactivate()`, `changePassword()`
- **Repository 메서드**: `find`, `save`, `delete` 접두사
  - `findById()`, `findByEmail()`, `save()`

## 🔄 계층 간 데이터 흐름

```
Request → Controller → Application Service → Domain Service → Repository
                   ↓                    ↓              ↓
               DTO 변환            도메인 로직        영속성 처리
                   ↓                    ↓              ↓
Response ← Controller ← Application Service ← Domain Service ← Repository
```

## 🧪 테스트 전략

### 1. Domain Layer 테스트
```java
@Test
class UserTest {
    @Test
    void 패스워드_변경_성공() {
        // given
        User user = createUser();
        Password newPassword = new Password("newPassword123!");
        
        // when
        user.changePassword(newPassword);
        
        // then
        assertThat(user.getPassword()).isEqualTo(newPassword);
    }
}
```

### 2. Application Service 테스트
```java
@ExtendWith(MockitoExtension.class)
class UserApplicationServiceTest {
    @Mock UserRepository userRepository;
    @Mock UserDomainService userDomainService;
    
    @InjectMocks UserApplicationService userApplicationService;
    
    @Test
    void 사용자_등록_성공() {
        // given, when, then
    }
}
```

## 📝 개발 체크리스트

### Entity 개발 시
- [ ] 비즈니스 규칙이 Entity 내부에 있는가?
- [ ] 불변 조건을 생성자에서 검증하는가?
- [ ] 상태 변경 메서드가 비즈니스 의미를 나타내는가?

### Value Object 개발 시
- [ ] 불변 객체로 구현되었는가?
- [ ] 유효성 검증이 생성자에서 이루어지는가?
- [ ] equals/hashCode가 올바르게 구현되었는가?

### Repository 개발 시
- [ ] 인터페이스는 도메인 레이어에 있는가?
- [ ] 구현체는 인프라스트럭처 레이어에 있는가?
- [ ] 도메인 객체만 다루는가? (JPA Entity 노출 금지)

### Application Service 개발 시
- [ ] 트랜잭션 경계가 명확한가?
- [ ] 도메인 로직을 호출만 하고 직접 구현하지 않는가?
- [ ] DTO 변환 로직이 포함되어 있는가?

## 🎯 DDD 모범 사례

### ✅ Good
```java
// 도메인 로직이 엔티티에 있음
public class Order {
    public void cancel() {
        if (this.status == OrderStatus.SHIPPED) {
            throw new IllegalStateException("배송된 주문은 취소할 수 없습니다.");
        }
        this.status = OrderStatus.CANCELLED;
    }
}
```

### ❌ Bad
```java
// 도메인 로직이 서비스에 있음 (빈약한 도메인 모델)
@Service
public class OrderService {
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId);
        if (order.getStatus() == OrderStatus.SHIPPED) {
            throw new IllegalStateException("배송된 주문은 취소할 수 없습니다.");
        }
        order.setStatus(OrderStatus.CANCELLED);
    }
}
```
