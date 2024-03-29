# 개요
대학교 점심시간에 음악을 1시간 가량 틀어주는데 음악을 선정할 때 재학생들이 직접 신청하여 참여 할 수 있게 만든 웹 사이트 

노래 검색은 총 3가지로 제목 검색, 앨범 검색, 가수 검색 으로 검색 할 수 있다.

# 기술아키텍처
<img width="951" alt="아키텍처" src="https://github.com/lip112/MusicSite/assets/42790121/5b8dc9dd-b9f5-4d3c-b875-5bab2d0bcb8e">


# 기술스택
<img width="736" alt="기술스택" src="https://github.com/lip112/MusicSite/assets/42790121/6488d81a-049d-441f-ab99-8cfb7e3ea287">

원래 목적은 음악 API를 통해 노래를 가져오려고 했지만 국내 사이트 에서는 별도의 API를 제공해주지 않아서 크롤링을 선택했고 지니뮤직에서 직접 크롤링을 통해 노래 정보를 가져옴
자바11 , 스프링 2.7.xx, 구글 버전 -> 104.0.5112.102

# ERD 설계
<img width="653" alt="이알디" src="https://github.com/lip112/MusicSite/assets/42790121/9600da2b-3591-4f50-9d27-292d23c98079">

# 요구사항 명세서

<img width="1186" alt="스크린샷 2024-03-29 오전 9 55 38" src="https://github.com/lip112/MusicSite/assets/42790121/5a797d90-49c4-448a-94a4-8c53c3f7a913">

https://dark-porter-a0f.notion.site/fffb12859e594ef8a1b91cf63570a2bb?v=ed19286e6b7b4f7d984fb1a2ef435eae&pvs=4

# API 명세서

<img width="1054" alt="image" src="https://github.com/lip112/MusicSite/assets/42790121/155cd793-1a0e-4c02-a2f7-d7e45b306d57">

<img width="1272" alt="image" src="https://github.com/lip112/MusicSite/assets/42790121/dffd64e4-4524-41a2-ab1f-b41e656bde59">

https://dark-porter-a0f.notion.site/API-09da39851de44bab8a075c75693ded05?pvs=4

# 작동 화면

## 로그인
<img width="1728" alt="스크린샷 2024-03-29 오전 9 24 51" src="https://github.com/lip112/MusicSite/assets/42790121/2a27e55c-f66e-419b-b87d-944a79608b64">

## 회원가입 이메일 인증
<img width="1728" alt="스크린샷 2024-03-29 오전 9 24 58" src="https://github.com/lip112/MusicSite/assets/42790121/04eb4271-3e0e-4201-8ba9-0673b5f340ff">

## 노래 통계( 금일신청내역, 특정 기간 노래 신청 내역)
<img width="1728" alt="스크린샷 2024-03-29 오전 9 25 28" src="https://github.com/lip112/MusicSite/assets/42790121/f91a7de7-0362-4d51-b213-f27b31e720f6">

## 제목 검색
<img width="1728" alt="스크린샷 2024-03-29 오전 9 24 02" src="https://github.com/lip112/MusicSite/assets/42790121/1c7550be-1dd3-4db6-b78f-5f175ab02af1">

## 앨범 검색
<img width="1728" alt="스크린샷 2024-03-29 오전 9 24 15" src="https://github.com/lip112/MusicSite/assets/42790121/1cc579b6-6e15-40a6-ba0f-0f5e501e756c">

<img width="1728" alt="스크린샷 2024-03-29 오전 9 24 23" src="https://github.com/lip112/MusicSite/assets/42790121/8aa944b5-cd52-49fa-b585-1dd183c86e6d">

## 가수 검색
<img width="1728" alt="스크린샷 2024-03-29 오전 9 25 45" src="https://github.com/lip112/MusicSite/assets/42790121/c84c51bc-c288-43c5-8c95-4a2463d5709f">

<img width="1728" alt="스크린샷 2024-03-29 오전 9 25 53" src="https://github.com/lip112/MusicSite/assets/42790121/ea147c80-b255-44dc-b243-65bce2a4c490">

<img width="1728" alt="스크린샷 2024-03-29 오전 9 26 03" src="https://github.com/lip112/MusicSite/assets/42790121/97dfb5f8-87ea-4b70-aa8a-9b6bc81e747c">




