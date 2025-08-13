
<img width="7680" height="4320" alt="시니클 표지" src="https://github.com/user-attachments/assets/326fa178-dd79-4558-80f5-44e8e62faa05" />

<br/>
<br/>

<p align="center">
<a href="https://github.com/softeerbootcamp-6th/Team3-SockThree/wiki">🐭 wiki</a><br>
  <a href="#서비스-소개">🌱 서비스 소개</a> <br>
  <a href="#팀원-소개">🌱 팀원 소개</a> <br>
  <a href="#기술-스택">🌱️ 기술 스택</a> <br>
  <a href="#프로젝트-구조">🌱 프로젝트 구조</a> <br>
  <a href="#ERD">🌱 ERD</a> <br>

</p>

<br/>

# 서비스 소개

> 오직 시니어만을 위한 클래스, 시니클<br>
> 시니클은 액티브 시니어 대상 자기계발 강좌 통합 검색 & 수요기반 개설 플랫폼입니다.
> 
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/c0498221-d11f-4d9c-9562-3e1e671efc1d" />
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/02ec50bc-0578-448b-9c4a-f81c41691f7b" />



<br/>

# 팀원 소개

<table align="center">
  <tr>
    <td align="center">
      <a href="https://github.com/coffeesigma">
        <strong>이정빈</strong>
      </a><br />
      <img width="100" height="120" src="https://github.com/user-attachments/assets/ecb7411d-843d-46dc-aff3-a69625a3e85f" alt=" 프로필"/><br />
      <sub><strong>👑 팀장</strong></sub><br />
      <sub><code>Backend</code></sub>
    </td>
    <td align="center">
      <a href="https://github.com/yyeonzu">
        <strong>정연주</strong>
      </a><br />
      <img width="120" height="120" src="https://avatars.githubusercontent.com/u/100709499?v=4" alt="정연주 프로필"/><br />
      <sub><strong>팀원</strong></sub><br />
      <sub><code>Frontend</code></sub>
    </td>
    <td align="center">
      <a href="https://github.com/choi-jiae">
        <strong>최지애</strong>
      </a><br />
      <img width="120" height="120" src="https://t1.daumcdn.net/cafeattach/1IHuH/6557504925537b5af4eb1d1fce725c7d9b5f11a0" alt="최지애 프로필"/><br />
      <sub><strong>팀원</strong></sub><br />
      <sub><code>Frontend</code></sub>
    </td>
    <td align="center">
      <a href="https://github.com/hkjbrian">
        <strong>한경준</strong>
      </a><br />
      <img width="120" src="https://avatars.githubusercontent.com/u/63639963?v=4" alt="한경준 프로필"/><br />
      <sub><strong>팀원</strong></sub><br />
      <sub><code>Backend</code></sub>
    </td>
  </tr>
    <tr>
    <td align="center">
      담당 기능
    </td>
    <td align="center">
      담당 기능
    </td>
    <td align="center">
      담당 기능
    </td>
    <td align="center">
      담당 기능
    </td>
  </tr>
</table>

<br/>

# 기술 스택

<table>
  <tr>
    <td><strong>Frontend:</strong></td>
    <td>
      <img src="https://img.shields.io/badge/React-61DAFB?style=flat&logo=react&logoColor=black" />
      <img src="https://img.shields.io/badge/TypeScript-3178C6?style=flat&logo=typescript&logoColor=white" />
      <img src="https://img.shields.io/badge/Vite-646CFF?style=flat&logo=vite&logoColor=white" />
      <img src="https://img.shields.io/badge/Zustand-000000?style=flat&logo=zotero&logoColor=white" />
      <img src="https://img.shields.io/badge/TanStack_Query-FF4154?style=flat&logo=reactquery&logoColor=white" />
      <img src="https://img.shields.io/badge/TailwindCSS-06B6D4?style=flat&logo=tailwindcss&logoColor=white" />
    </td>
  </tr>
  <tr>
    <td><strong>Backend:</strong></td>
    <td>
      <img src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat&logo=springboot&logoColor=white" />
      <img src="https://img.shields.io/badge/Java-007396?style=flat&logo=openjdk&logoColor=white" />
      <img src="https://img.shields.io/badge/JPA-59666C?style=flat" />
      <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=mysql&logoColor=white" />
    </td>
  </tr>
</table>

<br/>

# 프로젝트 구조

## 전체 아키텍처
<img width="700" alt="image" src="https://github.com/user-attachments/assets/b9a0d787-f56a-440d-a4ac-e5b8b4ddc081" />


## 동영상 관련 아키텍처
<img width="500"  alt="image" src="https://github.com/user-attachments/assets/be924d29-e324-4e41-90a7-488396b2a226" />
<img width="500"  alt="image" src="https://github.com/user-attachments/assets/ad65f20c-4787-4b14-a518-f83cc3d7d19e" />



## FE 폴더 구조
```css
src
├── domain
│   ├── instructor
│   │   ├── api
│   │   ├── component
│   │   ├── hook
│   │   ├── page
│   │   └── types
│   ├── student
├── shared
├── routes
├── App.tsx
```
instructor 내부의 api, component, hook, page, types 구조는 student와 shared에도 동일하게 존재합니다.

# ERD
<img width="1890" height="1082" alt="image" src="https://github.com/user-attachments/assets/970531c9-7e83-4191-85c1-9d26e1866fbd" />

