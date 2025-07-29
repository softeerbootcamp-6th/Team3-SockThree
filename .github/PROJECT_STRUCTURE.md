# 프로젝트 구조 가이드

Team3-SockThree 프로젝트의 전체 구조와 각 디렉토리의 역할을 설명합니다.

## 📁 전체 구조

```
Team3-SockThree/
├── .github/                    # GitHub 설정
│   ├── ISSUE_TEMPLATE/         # 이슈 템플릿
│   ├── workflows/              # GitHub Actions 워크플로우
│   ├── CODEOWNERS              # 코드 소유자 정의
│   ├── PULL_REQUEST_TEMPLATE.md # PR 템플릿
│   └── BRANCH_PROTECTION.md    # 브랜치 보호 가이드
├── client/                     # React 프론트엔드
├── server/                     # Spring Boot 백엔드
├── libs/                       # 공통 라이브러리
├── docs/                       # 프로젝트 문서
├── scripts/                    # 유틸리티 스크립트
├── .gitignore                  # Git 무시 파일
├── README.md                   # 프로젝트 개요
├── CONTRIBUTING.md             # 기여 가이드라인
├── docker-compose.yml          # Docker 컴포즈 설정
└── package.json                # 루트 패키지 설정 (모노레포)
```

## 🌐 Backend 구조 (server/)

Spring Boot 기반 백엔드 애플리케이션으로 DDD(Domain Driven Design) 구조를 따릅니다.

```
server/
├── src/
│   ├── main/
│   │   ├── java/com/softeer/server/
│   │   │   ├── common/         # 공통 모듈
│   │   │   │   ├── config/     # 설정 클래스 (DB, Security, etc.)
│   │   │   │   ├── exception/  # 전역 예외 처리
│   │   │   │   ├── utils/      # 공통 유틸리티
│   │   │   │   └── response/   # 공통 응답 형식
│   │   │   ├── domain/         # 도메인 계층 (DDD Core)
│   │   │   │   ├── user/       # 사용자 도메인
│   │   │   │   │   ├── entity/     # 엔티티
│   │   │   │   │   ├── repository/ # 도메인 저장소 인터페이스
│   │   │   │   │   ├── service/    # 도메인 서비스
│   │   │   │   │   └── vo/         # 값 객체 (Value Object)
│   │   │   │   ├── product/    # 상품 도메인
│   │   │   │   └── order/      # 주문 도메인
│   │   │   ├── application/    # 응용 계층
│   │   │   │   ├── service/    # 응용 서비스
│   │   │   │   ├── dto/        # 데이터 전송 객체
│   │   │   │   └── facade/     # 파사드 패턴
│   │   │   ├── infrastructure/ # 인프라스트럭처 계층
│   │   │   │   ├── persistence/    # 데이터 접근 구현
│   │   │   │   │   ├── entity/     # JPA 엔티티
│   │   │   │   │   ├── repository/ # JPA Repository 구현
│   │   │   │   │   └── mapper/     # 도메인-JPA 매퍼
│   │   │   │   ├── external/       # 외부 API 연동
│   │   │   │   └── messaging/      # 메시징 (Kafka, RabbitMQ)
│   │   │   └── presentation/   # 표현 계층
│   │   │       ├── controller/ # REST 컨트롤러
│   │   │       ├── dto/        # 요청/응답 DTO
│   │   │       └── mapper/     # DTO-도메인 매퍼
│   │   └── resources/
│   │       ├── application.yml # 애플리케이션 설정
│   │       ├── application-dev.yml # 개발 환경 설정
│   │       ├── application-prod.yml # 운영 환경 설정
│   │       └── data.sql        # 초기 데이터
│   └── test/                   # 테스트 코드
│       ├── java/com/softeer/server/
│       │   ├── domain/         # 도메인 단위 테스트
│       │   ├── application/    # 응용 서비스 테스트
│       │   ├── infrastructure/ # 인프라스트럭처 테스트
│       │   └── presentation/   # 컨트롤러 테스트 (통합)
│       └── resources/
├── build.gradle                # Gradle 빌드 스크립트
├── Dockerfile                  # Docker 이미지 빌드
└── README.md                   # 백엔드 문서
```

### DDD 계층별 역할

#### 🏛️ **Domain Layer (도메인 계층)**
- **Entity**: 비즈니스 규칙과 상태를 포함하는 핵심 객체
- **Repository Interface**: 도메인 객체 저장/조회 추상화
- **Domain Service**: 도메인 로직 중 엔티티에 속하지 않는 비즈니스 규칙
- **Value Object**: 불변 값 객체 (예: 이메일, 전화번호)

#### 🔧 **Application Layer (응용 계층)**
- **Application Service**: 유스케이스 조정, 트랜잭션 관리
- **DTO**: 계층 간 데이터 전송
- **Facade**: 복잡한 도메인 로직의 단순한 인터페이스 제공

#### 🏗️ **Infrastructure Layer (인프라스트럭처 계층)**
- **Persistence**: 데이터베이스 접근, JPA 구현
- **External**: 외부 API, 메시징 시스템 연동
- **Mapper**: 도메인 객체와 영속성 객체 변환

#### 🌐 **Presentation Layer (표현 계층)**
- **Controller**: HTTP 요청/응답 처리
- **Request/Response DTO**: API 스펙 정의
- **Mapper**: DTO와 도메인 객체 변환

### 📁 도메인별 구조 예시

```
domain/user/
├── entity/
│   ├── User.java           # 사용자 엔티티
│   └── UserRole.java       # 사용자 역할 엔티티
├── repository/
│   └── UserRepository.java # 사용자 저장소 인터페이스
├── service/
│   └── UserDomainService.java # 사용자 도메인 서비스
└── vo/
    ├── Email.java          # 이메일 값 객체
    └── Password.java       # 패스워드 값 객체
```

## ⚛️ Frontend 구조 (client/)

React 기반 프론트엔드 애플리케이션 구조입니다.

```
client/
├── public/                     # 정적 파일
│   ├── index.html
│   └── favicon.ico
├── src/
│   ├── components/             # 재사용 가능한 컴포넌트
│   │   ├── common/             # 공통 컴포넌트
│   │   ├── layout/             # 레이아웃 컴포넌트
│   │   └── ui/                 # UI 컴포넌트
│   ├── pages/                  # 페이지 컴포넌트
│   ├── hooks/                  # 커스텀 훅
│   ├── services/               # API 호출 서비스
│   ├── store/                  # 상태 관리 (Redux/Zustand)
│   ├── utils/                  # 유틸리티 함수
│   ├── types/                  # TypeScript 타입 정의
│   ├── styles/                 # 스타일 파일
│   ├── assets/                 # 이미지, 아이콘 등
│   ├── App.tsx                 # 메인 앱 컴포넌트
│   └── index.tsx               # 앱 진입점
├── package.json                # 의존성 관리
├── tsconfig.json               # TypeScript 설정
├── Dockerfile                  # Docker 이미지 빌드
└── README.md                   # 프론트엔드 문서
```

### 디렉토리별 역할

- **components**: 재사용 가능한 UI 컴포넌트들
- **pages**: 라우팅되는 페이지 컴포넌트들
- **hooks**: 커스텀 React 훅들
- **services**: API 통신 관련 함수들
- **store**: 전역 상태 관리 (Redux Toolkit 또는 Zustand)
- **utils**: 공통 유틸리티 함수들
- **types**: TypeScript 타입 및 인터페이스 정의
- **styles**: CSS/SCSS 스타일 파일들
- **assets**: 이미지, 폰트, 아이콘 등 정적 자원

## 📚 공통 라이브러리 (libs/)

프론트엔드와 백엔드에서 공통으로 사용하는 라이브러리들입니다.

```
libs/
├── shared-types/               # 공통 타입 정의
├── common-utils/               # 공통 유틸리티
├── validation/                 # 검증 로직
└── constants/                  # 상수 정의
```

## 📖 문서 (docs/)

프로젝트 관련 문서들을 관리합니다.

```
docs/
├── api/                        # API 문서
├── deployment/                 # 배포 가이드
├── development/                # 개발 가이드
└── architecture/               # 아키텍처 문서
```

## 🔧 스크립트 (scripts/)

프로젝트 관리용 스크립트들입니다.

```
scripts/
├── setup.sh                   # 초기 설정 스크립트
├── build.sh                   # 빌드 스크립트
├── deploy.sh                  # 배포 스크립트
└── test.sh                    # 테스트 실행 스크립트
```

## 📝 네이밍 컨벤션

### 파일/디렉토리 네이밍

- **디렉토리**: kebab-case (예: `user-management`)
- **컴포넌트 파일**: PascalCase (예: `UserProfile.tsx`)
- **유틸리티 파일**: camelCase (예: `dateUtils.ts`)
- **상수 파일**: UPPER_SNAKE_CASE (예: `API_ENDPOINTS.ts`)

### 코드 네이밍

- **변수/함수**: camelCase (예: `getUserData`)
- **클래스**: PascalCase (예: `UserService`)
- **상수**: UPPER_SNAKE_CASE (예: `MAX_RETRY_COUNT`)
- **인터페이스**: PascalCase with I prefix (예: `IUser`)

## 🚀 시작하기

1. **저장소 클론**
   ```bash
   git clone https://github.com/softeerbootcamp-6th/Team3-SockThree.git
   ```

2. **개발 서버 실행**
   ```bash
   # 백엔드
   cd server/
   ./gradlew bootRun
   
   # 프론트엔드
   cd client/
   npm start
   ```
