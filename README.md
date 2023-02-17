# PuppyLink 포팅 메뉴얼

https://harimchung.notion.site/PuppyLink-5f704cc87531409b96124b2c11a4bd1c

## 0. 서비스 소개
![image](/uploads/238ed16f7465805572be76bb841ea6a3/image.png)
- PuppyLink는 해외이동봉사자와 단체를 연결해주는 서비스 플랫폼입니다.

### 타깃

- 해외이동봉사자
- 동물단체

### 주요기능

1. 카카오 소셜로그인
2. 단체 회원가입 시 공공데이터포털 API 이용한 사업자인증
3. 봉사신청의 필수서류 제출에서 OCR 통해 간편 서류 기입
4. 민감 서류들은 aws s3에 보관 후 봉사완료 시 서류 삭제
5. 봉사 진행중인 활동은 GPS 통해 이동중인 강아지의 위치 조회 가능
6. 플랫폼 서비스 이용 편의를 위한 챗봇
7. 봉사후기를 작성할 수 있는 후기 게시판 작성, 댓글 작성, 좋아요
8. 단체의 봉사자 관리
9. 해외 이동 봉사 키워드 관련 뉴스 크롤링

## 1. 개발환경

[개발환경](https://harimchung.notion.site/ffc149bf72984ccc8f47c7b44bdc736e)

[docker 명령어 정리](https://harimchung.notion.site/docker-93d64e7df0284623a32fe8f147ed908b)

[Jenkins 설정](https://harimchung.notion.site/Jenkins-322d548b60dd47d2948f4e743dcd3185)

[AWS EC2 접속 ](https://harimchung.notion.site/AWS-EC2-803ec9aae64847cea2dcd1b6da5e85d7)



## 2. 빌드 방법

[FE, BE 빌드 방법](https://harimchung.notion.site/FE-BE-e068dde7e09e48b3b6debb62064dbd58)

### gitignore

- application-dev.yml (aws, gps api, 공공데이터포털api)
![image](/uploads/ac1a7432a6019f36fb627c6e82c1cb22/image.png)

## 3. 외부 기술

[API : 사업자 등록번호 인증](https://harimchung.notion.site/API-84db524e33e14cdb8b95b0d91849ecf6)

[OCR : 광학 문자 인식](https://harimchung.notion.site/OCR-5d0f64ec3a9b4793b4904c09522bd4be)

[AIRLABS API & GOOGLE MAPS API](https://harimchung.notion.site/AIRLABS-API-GOOGLE-MAPS-API-f32206e625464292a3b784e4803436d2)

[채널톡](https://harimchung.notion.site/789335204a7a463f818fd728025d3e8c)

[카카오 로그인](https://harimchung.notion.site/21e7f7bd730f4358b1e58abb1cc73db3)

[JWT ](https://harimchung.notion.site/JWT-e3a411c4a6884b44b9d38c6875969611)

## 4. 사용자 인터페이스

![First](/uploads/c492f896294fd93b9bf80b625bdfbbdd/First.gif)

![Second](/uploads/6dfece0856f23c1cf69c32835f0005e2/Second.gif)
