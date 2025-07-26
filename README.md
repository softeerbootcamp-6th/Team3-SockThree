# Team3-SockThree

Softeer bootcamp 6기 3조 개발 레포지토리입니다.

## 🏗️ 프로젝트 구조

```
Team3-SockThree/
├── apps/
│   ├── backend/     # Spring Boot 백엔드
│   └── frontend/    # React 프론트엔드
├── .github/         # GitHub 설정 및 워크플로우
└── docs/           # 프로젝트 문서
```

## 🚀 시작하기

### 필수 요구사항

- **Java**: 21 이상
- **Node.js**: 18 이상
- **Docker**: 최신 버전 (선택사항)

### 로컬 개발 환경 설정

```bash
# 저장소 클론
git clone https://github.com/softeerbootcamp-6th/Team3-SockThree.git
cd Team3-SockThree

# 백엔드 설정 (추후 추가)
cd apps/backend
# ./gradlew build

# 프론트엔드 설정 (추후 추가)
cd ../frontend
# npm install
# npm start
```

## 🔄 CI/CD 워크플로우

### 자동 실행 워크플로우

- **CI**: 변경된 부분만 테스트 및 빌드
  - `apps/backend/` 변경 시 → 백엔드 테스트 실행
  - `apps/frontend/` 변경 시 → 프론트엔드 테스트 실행

- **Code Quality**: 변경된 부분만 품질 검사
  - 백엔드: Checkstyle, SpotBugs
  - 프론트엔드: ESLint, Prettier

- **Build Images**: main 브랜치 푸시 시 Docker 이미지 빌드

### 수동 실행 워크플로우

- **Manual Deploy**: GitHub Actions에서 수동으로 배포 실행 가능
  - 환경 선택: development, staging, production
  - 서비스 선택: all, backend, frontend
  - 이미지 태그 지정 가능

## 📝 개발 가이드

- [기여 가이드라인](CONTRIBUTING.md)
- [프로젝트 구조 가이드](.github/PROJECT_STRUCTURE.md)
- [DDD 개발 가이드](.github/DDD_GUIDE.md)
- [브랜치 보호 규칙](.github/BRANCH_PROTECTION.md)

## 🛠️ 개발 도구

- **Backend**: Spring Boot (Java 21), Gradle, JPA, MySQL
- **Frontend**: React, TypeScript, Vite
- **CI/CD**: GitHub Actions
- **Container**: Docker, GitHub Container Registry
