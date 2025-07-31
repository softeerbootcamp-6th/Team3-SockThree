# 기여 가이드라인

Team3-SockThree 프로젝트에 기여해주셔서 감사합니다! 이 문서는 프로젝트에 기여하는 방법을 안내합니다.

## 📋 목차

- [개발 환경 설정](#-개발-환경-설정)
- [브랜치 전략](#-브랜치-전략)
- [커밋 컨벤션](#-커밋-컨벤션)
- [PR 가이드라인](#-pr-가이드라인)
- [코드 스타일](#-코드-스타일)
- [이슈 관리](#-이슈-관리)

## 🚀 개발 환경 설정

### 필수 요구사항

- **Java**: 17 이상
- **Node.js**: 18 이상
- **MySQL**: 8.0 이상
- **Git**: 2.30 이상
- **Python & pip**: `pre-commit` 설치를 위해 필요합니다.

### 프로젝트 설정

```bash
# 저장소 클론
git clone https://github.com/softeerbootcamp-6th/Team3-SockThree.git
cd Team3-SockThree

# 백엔드 의존성 설치
cd server
./gradlew build

# 프론트엔드 의존성 설치
cd ../client
npm install
```

### Git Hooks 설정 (Pre-commit)

프로젝트는 커밋 시점에 코드 스타일과 품질을 자동으로 검사하기 위해 `pre-commit`을 사용합니다. 이를 통해 모든 커밋이 일관된 품질 기준을 만족하도록 보장합니다.

1.  **pre-commit 설치:**
    아직 `pre-commit`이 설치되지 않았다면, 다음 명령어를 실행하여 설치합니다.
    ```bash
    pip install pre-commit
    ```

2.  **Git 훅 활성화:**
    프로젝트의 루트 디렉토리에서 다음 명령어를 실행하여 Git 훅을 활성화합니다.
    ```bash
    pre-commit install
    ```

이제 `git commit`을 실행하면, 커밋 전에 자동으로 코드 검사가 실행됩니다. 검사에 실패하면 문제가 되는 부분을 수정한 후 다시 커밋해주세요.

- **백엔드 코드 자동 수정:** `server` 디렉토리에서 `./gradlew spotlessApply` 실행
- **프론트엔드 코드 자동 수정:** `client` 디렉토리에서 `npm run format` 실행


## 🌟 브랜치 전략

Git Flow를 기반으로 한 브랜치 전략을 사용합니다.

### 주요 브랜치

- `main`: 프로덕션 릴리스 브랜치
- `develop`: 개발 통합 브랜치

### 보조 브랜치

- `feature/*`: 새로운 기능 개발
- `fix/*`: 버그 수정
- `hotfix/*`: 긴급 수정
- `release/*`: 릴리스 준비
- `refactor/*`: 코드 리팩토링
- `docs/*`: 문서 작업
- `chore/*`: 빌드 시스템, 의존성 업데이트 등
- `style/*`: css등 style 관련

### 브랜치 네이밍 규칙

```
fe/feature/이슈번호-간단한설명
```

예시:
```
fe/feature/123-user-authentication
be/fix/456-login-bug
fe/hotfix/789-security-patch
release/101-v1.2.0
fe/refactor/202-database-layer
docs/303-api-documentation
fe/chore/404-update-dependencies
```

## 📝 커밋 컨벤션

[Conventional Commits](https://www.conventionalcommits.org/) 규칙을 따릅니다.

### 커밋 메시지 형식

```
<type>: <description>

[optional body]

[optional footer(s)]
```

### 타입 (Type)

- `feat`: 새로운 기능 추가
- `fix`: 버그 수정
- `docs`: 문서 수정
- `style`: 코드 포맷팅, 세미콜론 누락 등
- `refactor`: 코드 리팩토링
- `test`: 테스트 추가/수정
- `chore`: 빌드 과정 또는 보조 도구 변경
- `perf`: 성능 개선
- `ci`: CI/CD 관련 변경

### 예시

```
feat: 사용자 인증 API 구현

- JWT 토큰 기반 인증 시스템 구현
- 로그인/로그아웃 엔드포인트 추가
- 인증 미들웨어 구현

Closes #123
```

## 🔄 PR 가이드라인

### PR 작성 전 체크리스트

- [ ] 이슈와 연결되어 있는가?
- [ ] DDD 아키텍처 가이드를 준수하는가?
- [ ] 도메인 로직이 적절한 계층에 위치하는가?
- [ ] 테스트가 통과하는가?
- [ ] 코드 스타일 가이드를 준수하는가?
- [ ] 문서가 업데이트되었는가?

### PR 제목

```
간단한 설명
```
이후 frontend / backend, type label 등록

예시:
```
사용자 인증 기능 구현
로그인 버그 수정
```

### PR 설명

제공된 PR 템플릿을 사용하여 다음 내용을 포함해주세요:

- 관련 이슈 번호
- 작업 내용 및 특이사항
- 참고사항 (스크린샷, 링크 등)

## 🎨 코드 스타일

### Backend (Java 17/Spring Boot)

- **Java 17** 사용
- **Google Java Style Guide** 준수
- **Checkstyle** 설정 준수
- **SpotBugs** 경고 해결

### Frontend (React/TypeScript)

- **ESLint** 규칙 준수
- **Prettier** 포맷팅 적용
- **TypeScript strict mode** 사용

### 공통 규칙

- 의미 있는 변수명 사용
- 주석은 한국어로 작성
- 함수/메서드는 단일 책임 원칙 준수
- 매직 넘버 사용 금지

## 📋 이슈 관리

### 이슈 타입

제공된 이슈 템플릿을 사용해주세요:

- `✨ feature`: 새로운 기능 요청
- `🐛 fix`: 버그 신고
- `♻️ refactor`: 리팩토링 요청
- `⚙️ chore`: 기타 작업

### 이슈 라벨

- **우선순위**: `high`, `middle`, `low`
- **상태**: `Backlog`, `TODO`, `In Progress`, `Done`
- **영역**: `backend`, `frontend`
- **타입**: `epic`, `feature`, `fix`, `refactor`, `docs`, `design`, `test`

## 🔍 코드 리뷰

### 리뷰어 가이드라인

- 코드의 기능성, 가독성, 성능을 검토
- 건설적인 피드백 제공
- 칭찬과 개선사항 균형 있게 제시

### 리뷰이 가이드라인

- 피드백을 수용하는 자세
- 반박할 때는 충분한 근거 제시
- 리뷰 완료 후 충분한 테스트 수행

## 🤝 도움이 필요하다면

- **이슈**: GitHub Issues에 문의
- **토론**: GitHub Discussions 활용
- **급한 문의**: 팀 채널 활용

## 📚 추가 참고 문서

- [DDD 개발 가이드](.github/DDD_GUIDE.md) - Domain Driven Design 적용 방법
- [프로젝트 구조 가이드](.github/PROJECT_STRUCTURE.md) - 전체 프로젝트 구조
