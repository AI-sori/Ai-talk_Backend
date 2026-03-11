# 🎙️ Ai-talk : AI 기반 시선 추적 및 언어 분석을 활용한 영유아 언어 발달 지원 시스템
**"아이의 목소리와 시선을 분석하여 언어 발달의 새로운 길을 제시합니다."**
> **서울여자대학교 졸업 프로젝트 [동상(3위)] 수상작 🏆**

<img width="1673" height="985" alt="image" src="https://github.com/user-attachments/assets/ef56f0c2-af9e-47b4-a469-055eaddca2fb" />


## 📍 서비스 개요
Ai-talk은 아동의 발화 데이터와 시선 추적(Eye-Tracking) 데이터를 AI 모델로 정밀 분석하여 언어 발달 단계를 진단하고, 진단 결과에 최적화된 맞춤형 언어 프로그램과 보호자 커뮤니티를 제공하는 토탈 솔루션 플랫폼입니다.

---

## 🛠️ Tech Stack

| Category | Content |
| :--- | :--- |
| **Language** | Java 17, Python (AI) |
| **Framework** | Spring Boot 3.x, Flask (AI Server) |
| **Security** | Spring Security (Session-based), CORS/SameSite Policy |
| **Database** | MariaDB |
| **ORM** | Spring Data JPA (Hibernate Spatial) |
| **Infrastructure** | AWS EC2, Nginx, Route 53 (HTTPS/SSL), S3, GitHub Actions |
| **Documentation**| Swagger (SpringDoc OpenAPI 3.0) |

---

## 👥 팀 구성 및 역할 (Backend Lead)

**[한다은 - Backend Lead / Project Manager]**
- **기획 및 총괄**: 서비스 아이디어 기획, 전체 시스템 아키텍처 설계, 프로젝트 일정 및 회의 주도
- **백엔드 시스템 구축**: 도메인 중심(DDD) 하이브리드 아키텍처 설계 및 1인 백엔드 개발 완수
- **AI 연동 아키텍처**: Flask 서버와의 S2S(Server-to-Server) 통신 규격 설계 및 데이터 정합성 검증
- **인프라 및 데브옵스**: AWS EC2 인프라 구축, Nginx 리버스 프록시 및 SSL 설정, 배포 자동화
- **대외 성과**: 졸업 전시 진행 총괄 및 관련 학술 논문 투고

---

## 🖥️ 주요 기술적 성과

### 1. 도메인 중심(DDD) 하이브리드 아키텍처 리팩토링
- **문제 해결**: 초기 개발 단계의 스파게티 코드를 도메인별 응집도를 높인 구조로 전면 개편
- **성과**: 유지보수성 향상 및 `CommonResponse`와 `GlobalExceptionHandler`를 통한 표준 응답 규격 확립

### 2. 서버 간 통신(S2S) 인터페이스 및 보안 설계
- **문제 해결**: AI 서버(Flask)와 백엔드 서버 간의 데이터 전송 시 인증 및 권한 이슈 해결
- **성과**: AI 전용 API 엔드포인트 구축 및 보안 그룹 최적화를 통한 안정적인 데이터 파이프라인 확보

### 3. 실전형 클라우드 인프라 운영
- **성과**: AWS AMI 기반 서버 이전, 탄력적 IP(EIP) 적용, Nginx를 활용한 HTTPS 보안 환경 구축으로 실서비스 수준의 안정성 확보

---

## 🚀 프로젝트 성과
- 독창적인 기획과 기술적 완성도를 인정받아 우수 프로젝트 선정**학기말 프로젝트 동상(3위) 수상**
- **학술 논문 투고 및 졸업 전시**:
- : 기획부터 로직 설계, 인프라, 테스트, 배포까지 백엔드 전 과정 **Full-Cycle 개발 경험**
