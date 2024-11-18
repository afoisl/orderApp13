# <div align="center">배달 사이트 </div>

## <div align="center">주문배달 프로젝트 </div>

### <div align="center">8조 준비용</div>

<div align=center> 팀장 | 최영준 🐲 <br/> 팀원 | 윤지용 🐂  오겸비 🐰</div>

---

<br/>

## - 팀원 역할분담

| 이름 | 역할 | 담당 업무 | 
| :-:  | :-: | :-: |
| 최영준 | 백엔드 개발 | AI, Food, Category, Review |
| 윤지용 | 백엔드 개발 | User, 사용자 인증, Address |
| 오겸비 | 백엔드 개발 | Order, Store, Payment |
<br/>

| <img src="https://avatars.githubusercontent.com/u/82498610?v=4" alt="" width="150"> | <img src="https://avatars.githubusercontent.com/u/150976474?v=4" alt="" width="150"> | <img src="https://avatars.githubusercontent.com/u/160198328?v=4" alt="" width="150"> |
|:------:|:------:|:------:|
| [최영준](https://github.com/choiy6) | [윤지용](https://github.com/jeffyun3061) | [오겸비](https://github.com/afoisl) |
<br/>


## - 프로젝트 개요

<div markdown="1">

#### 📌주제
배달 서비스를 위한 웹 애플리케이션으로, 사용자가 가게와 주문을 연결하여 손쉽게 주문 및 배달 상태를 확인할 수 있도록 지원하는 시스템.

#### 📌목표 

1.사용자 편의성 제공:

2. 음식 주문 및 배달 상태 실시간 확인.
다양한 가게와 메뉴 정보를 쉽게 검색.
효율적인 주문 및 배달 관리:

3. 가게와 고객 간 빠르고 정확한 정보 전달.
배달 상태 업데이트 및 관리자 권한 설정.
안전한 사용자 인증:

Spring Security와 JWT 기반의 인증/인가 체계 구축.
사용자 역할(CUSTOMER, OWNER, MANAGER, MASTER)에 따른 접근 권한 관리.



<br/>

</div>


---


## - 기술 스택

<div markdown="1">

#### 📌공통
- 버전 관리 및 협업: <img src="https://img.shields.io/badge/git-F05032?style=flat-square&logo=git&logoColor=white"/> <img src="https://img.shields.io/badge/github-181717?style=flat-square&logo=github&logoColor=white"/>
- 데이터베이스: <img src="https://img.shields.io/badge/postgresql-4169E1?style=flat-square&logo=postgresql&logoColor=white"/>
- 운영 체제: 
- 커뮤니케이션: <img src="https://img.shields.io/badge/Slack-4A154B?style=flat-square&logo=Slack&logoColor=white"/>

#### 📌백엔드
- 개발 환경: <img src="https://img.shields.io/badge/window-80B3FF?style=flat-square&logo=window&logoColor=white"/> <img src="https://img.shields.io/badge/macOS-000000?style=flat-square&logo=macos&logoColor=white"/>
- 프로그래밍 언어: <img src="https://img.shields.io/badge/java-00465B?style=flat-square&logo=java&logoColor=white"/>
- 빌드 도구: <img src="https://img.shields.io/badge/gradle-02303A?style=flat-square&logo=gradle&logoColor=white"/>
- 프레임워크: <img src="https://img.shields.io/badge/spring-6DB33F?style=flat-square&logo=spring&logoColor=white"/> <img src="https://img.shields.io/badge/springboot-6DB33F?style=flat-square&logo=springboot&logoColor=white"/>
- 보안: <img src="https://img.shields.io/badge/springsecurity-6DB33F?style=flat-square&logo=springsecurity&logoColor=white"/>

#### 📌배포
- <img src="https://img.shields.io/badge/AWS-232F3E?style=flat-square&logo=amazonwebservices&logoColor=white"/>

<br/>

</div>


---



## - 요구사항 정의서

<div markdown="1">

#### 📌 목적
- 회원 관리

회원가입 및 로그인.
사용자 권한(CUSTOMER, OWNER, MANAGER, MASTER) 기반 접근 관리.
JWT를 활용한 인증/인가 구현.
주문 관리

CUSTOMER: 메뉴 검색 및 주문.
OWNER: 가게 및 메뉴 관리.
MANAGER/MASTER: 전체 주문 및 배달 상태 관리.
배달 관리

주문 상태 업데이트(준비 중, 배달 중, 완료).
실시간 배달 추적 기능.
관리자 기능

MASTER: 모든 사용자 및 주문 정보 관리.
MANAGER: 일부 관리자 권한으로 가게 및 주문 관리.
<br/>

</div>


---



## - ERD

<div markdown="1">
<br/>
  
![sparta13 (1)](https://github.com/user-attachments/assets/396d1681-4b3c-4210-85cb-efa30389af3e)

</div>


---

## - 시스템 아키텍처 설계서

<div markdown="1">
 <img width="690" alt="스크린샷 2024-11-07 20 34 27" src="https://github.com/user-attachments/assets/a3dd7152-d49e-4a25-af4e-47081762d505">


<p align="center">

</p>

</div>
