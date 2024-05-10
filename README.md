# riset : sunrise + sunset : 근태 관리프로젝트
![riset](https://github.com/hyeok96/lotto-program/assets/86933513/17049543-6226-4be1-aea5-223f7d7dac1b)

### DESCRIPTION
#### 프로젝트 소개
- riset은 회사와 직원들의 효율적인 근태 관리를 위한 프로젝트입니다.
- 자신의 근태관리, 휴가, 일정을 관리할 수 있고 다른 직원과 자신의 일정을 공유할 수 있습니다.
- 등급에 따라 회사의 직원들 정보 관리, 회사의 정보 관리를 할 수 있습니다.

#### 개발 기간 및 인원
- 개발 기간 : 2024/03/25 ~ 2024/04/19
- 개발 인원 : 백엔드 3명, 프론트엔드 3명

#### ERD
https://www.erdcloud.com/d/EXbH3quR3kfHLPSBB
<img width="1213" alt="ERD" src="https://github.com/rise-set-riset/riset_backend/assets/86933513/52824d5d-0cad-4096-8915-49c82637b684">


### SKILL AND BRANCH STRATEGY
#### 스킬
> 프로젝트 스킬
>
> <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=OpenJDK&logoColor=white">
> <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=SpringBoot&logoColor=white">
> <img src="https://img.shields.io/badge/springsecurity-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white">
> <img src="https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white">
> <img src="https://img.shields.io/badge/Jpa-59666C?style=for-the-badge&logo=spring&logoColor=white">
> <img src="https://img.shields.io/badge/auth0-EB5424?style=for-the-badge&logo=auth0&logoColor=white">
>
> 데이터베이스 스킬
>
> <img src="https://img.shields.io/badge/mariaDB-003545?style=for-the-badge&logo=mariaDB&logoColor=white"> 
> <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white">
> <img src="https://img.shields.io/badge/mongoDB-47A248?style=for-the-badge&logo=MongoDB&logoColor=white">
> <img src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=Redis&logoColor=white"> 
>
> 배포 스킬
>
> <img src="https://img.shields.io/badge/amazonaws-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white"> 
> <img src="https://img.shields.io/badge/amazonec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white">
> <img src="https://img.shields.io/badge/amazonrds-527FFF?style=for-the-badge&logo=amazonrds&logoColor=white">
> <img src="https://img.shields.io/badge/amazons3-569A31?style=for-the-badge&logo=amazons3&logoColor=white">
> <img src="https://img.shields.io/badge/amazonroute53-8C4FFF?style=for-the-badge&logo=amazonroute53&logoColor=white">
> <img src="https://img.shields.io/badge/amazonelasticache-C925D1?style=for-the-badge&logo=amazonelasticache&logoColor=white">
> <img src="https://img.shields.io/badge/awselasticloadbalancing-8C4FFF?style=for-the-badge&logo=awselasticloadbalancing&logoColor=white">
>
> 협업스킬
>
> <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">
> <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
> <img src="https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white">
> <img src="https://img.shields.io/badge/discord-5865F2?style=for-the-badge&logo=discord&logoColor=white">

#### 브랜치 전략
- Git flow 전략을 기반으로 main, dev와 기능 개발을 하는 feature 브랜치를 나누어서 진행하였습니다.
  - main : 실 사용이 될 브랜치입니다.
  - dev : 실 사용 전 테스트를 하는 브랜치입니다.
  - feature : 기능 단위로 체크 아웃을 하여 독립적인 기능 개발을 하는 브랜치입니다.
- confict를 최소하기 위해서 pull request를 merge하는 시간을 정해 그 시간에 병합을 헸습니다.

### DIRECTORY STRUCTURE
```
📦
├─ gradle/wrapper
│  ├─ gradle-wrapper.jar
│  └─ gradle-wrapper.properties
├─ src
│  ├─ main
│  │  ├─ java/com/github/riset_backend
│  │  │  ├─ writeBoard (기능별 패키지)
│  │  │  │  ├─ board (기능별 필요한 엔엔티 페키지)
│  │  │  │  │  ├─ controller (엔티티 별 controller 패키지)
│  │  │  │  │  │  └─ BoardController.java
│  │  │  │  │  ├─ dto (엔티티 별 dto 패키지)
│  │  │  │  │  │  ├─ BoardRequestDto.java
│  │  │  │  │  │  ├─ BoardResponsetDto.java
│  │  │  │  │  │  └─ PostResponseDto.java 
│  │  │  │  │  ├─ entity (엔티티 별 entity 패키지)
│  │  │  │  │  │  └─ Board.java 
│  │  │  │  │  ├─ repository (엔티티 별 repository 패키지)
│  │  │  │  │  │  └─ BoardRepository.java
│  │  │  │  │  └─ service (엔티티 별 service 패키지)
│  │  │  │  │     └─ BoardService.java
│  │  │  │  ├─ boardFile
│  │  │  │  ├─ favorite
│  │  │  │  └─ reply
│  │  │  ├─ settlement
│  │  │  ├─ chating
│  │  │  ├─ file
│  │  │  ├─ login
│  │  │  ├─ manageCompany
│  │  │  ├─ menu
│  │  │  ├─ myPage
│  │  │  ├─ schedules
│  │  │  ├─ vacations
│  │  │  ├─ global
│  │  │  │   └─ config (패키지 및 각종 설정)
│  │  │  │     └─ cors (cors 설정)
│  │  │  │        └─ CorsConfig.java
│  │  │  └─ RisetBackendApplication.java
│  │  └─ resources
│  │     ├─ application-aws.yaml
│  │     ├─ application-dev.yaml
│  │     ├─ application-local.yaml
│  │     ├─ application-oauth.yaml
│  │     ├─ application-prod.yaml
│  │     ├─ application.yaml
│  │     ├─ logback-spring-dev.xml
│  │     ├─ logback-spring-local.xml
│  │     └─ logback-spring-prod.xml
│  └─ test
├─ .gitignore
├─ build.gradle
├─ gradlew
├─ gradlew.bat
└─ settings.gradlew
```

### DIVIDE UP
#### 역할 분담
👋 **이상혁** (CTO) 
 - 게시판 추가, 수정, 상세내용, 삭제, 게시판 파일 업로드 api
 - 게시판 즐겨찾기 추가, 드라그앤드랍 api
 - 채팅 방 성생, 나가기, 찾기, 검색 api
 - stomp를 이용한 채팅 구현
 - 채팅 파일 업로드 및 내용 검색 구현
 - 배포 (aws, rds, ec2, route53, loadbalance, elasticache)

👋 **김민석**
 - 로그인, 로그인 아웃 api
 - 회원 가입 api
 - oauth2를 이용한 소셜로그인 구현
 - 회사 생성 및 정보 수정 api
 - 회사 위도, 경도를 이용한 출퇴근, 출퇴근 관리 api
 - 리프레쉬 토큰 redis에 저장

 👋 **주진성**
 - 회사 및 직원 일정 생성, 수정, 불러오기 api
 - 직원 회사 초대 이메일 발송 api
 - 마이페이지 수정 api
 - 직원 정보 수정 api
 - 조직도 사진 업로드, 등급 수정 api
   
