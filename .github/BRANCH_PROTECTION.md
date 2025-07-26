# GitHub 브랜치 보호 규칙 설정 가이드

이 문서는 Team3-SockThree 프로젝트의 브랜치 보호 규칙 설정 방법을 안내합니다.

## 설정 방법

1. GitHub 저장소로 이동
2. **Settings** 탭 클릭
3. 좌측 메뉴에서 **Branches** 클릭
4. **Add rule** 버튼 클릭

## main 브랜치 보호 규칙

### Branch name pattern
```
main
```

### 설정해야 할 옵션들

#### Protect matching branches
- [x] **Require a pull request before merging**
  - [x] Require approvals: **1개 이상**
  - [x] Dismiss stale reviews when new commits are pushed
  - [x] Require review from code owners (CODEOWNERS 파일 생성 후)

- [x] **Require status checks to pass before merging**
  - [x] Require branches to be up to date before merging
  - 필수 상태 검사 (변경사항이 있을 때만 실행):
    - `backend-test` (백엔드 변경 시)
    - `frontend-test` (프론트엔드 변경 시)
    - `backend-quality` (백엔드 변경 시)
    - `frontend-quality` (프론트엔드 변경 시)

- [x] **Require conversation resolution before merging**
- [x] **Require signed commits** (선택사항)
- [x] **Require linear history**
- [x] **Include administrators**

#### Restrictions
- [x] **Restrict pushes that create files**
- [x] **Restrict force pushes**
- [x] **Allow deletions** (체크 해제)

## develop 브랜치 보호 규칙

### Branch name pattern
```
develop
```

### 설정해야 할 옵션들

#### Protect matching branches
- [x] **Require a pull request before merging**
  - [x] Require approvals: **1개 이상**
  - [x] Dismiss stale reviews when new commits are pushed

- [x] **Require status checks to pass before merging**
  - [x] Require branches to be up to date before merging
  - 필수 상태 검사:
    - `backend-test`
    - `frontend-test`

- [x] **Require conversation resolution before merging**
- [x] **Include administrators**

#### Restrictions
- [x] **Restrict force pushes**

## 추가 권장사항

1. **팀원들에게 알림**: 브랜치 보호 규칙 설정 후 팀원들에게 공지
2. **CODEOWNERS 파일 생성**: 코드 리뷰 담당자 지정
3. **정기적인 검토**: 프로젝트 진행에 따라 규칙 조정
