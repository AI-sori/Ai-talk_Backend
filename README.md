# 🎙️ Ai-talk : AI 기반 시선 추적 및 음성 분석을 활용한 영유아 언어 발달 지원 시스템
> **"아이의 목소리와 시선을 분석하여 언어 발달의 새로운 길을 제시합니다."**

<img width="1673" height="985" alt="image" src="https://github.com/user-attachments/assets/ef56f0c2-af9e-47b4-a469-055eaddca2fb" />

## 📍 서비스 개요
Ai-talk은 아동의 발화 데이터와 시선 추적(Eye-Tracking) 데이터를 AI 모델로 정밀 분석하여 언어 발달 단계를 진단하고, 진단 결과에 최적화된 맞춤형 언어 프로그램과 보호자 커뮤니티를 제공하는 토탈 솔루션 플랫폼입니다.

---

## 📱 주요 기능

### 1. AI 언어 발달 정밀 진단
- 아동의 음성 데이터와 발화 정보를 바탕으로 발음 명확성, 어휘력, 문장 구성력 등을 정량화하여 현재 언어 발달 수준을 정밀 진단합니다.

### 2. 맞춤형 교육 프로그램 큐레이션
- 진단된 레벨(Level 1~3)과 부족한 언어 지표에 맞춰 아동에게 최적화된 맞춤형 언어 재활 교육 콘텐츠를 추천합니다.

### 3. 성장 리포트 및 전문가 소통(Q&A)
- 과거 진단 기록을 바탕으로 언어 발달 추이를 시각화하여 제공하며, 1:1 Q&A 기능을 통해 전문가와 소통할 수 있는 창구를 지원합니다.

### 4. 정보 공유 커뮤니티
- 보호자들이 육아 정보를 공유하고 소통할 수 있도록 게시글 작성, 좋아요, 댓글 기능이 포함된 소셜 공간을 제공합니다.

---

## 🛠️ Tech Stack & Architecture

### [ Tech Stack ]
| Category | Content |
| :--- | :--- |
| **Language** | Java 17, Python (AI) |
| **Framework** | Spring Boot 3.x, Flask (AI Server) |
| **Security** | Spring Security (Session-based), CORS/SameSite Policy |
| **Database** | MariaDB |
| **ORM** | Spring Data JPA (Hibernate Spatial) |
| **Infrastructure** | AWS EC2, Nginx, Route 53 (HTTPS/SSL), S3, GitHub Actions |
| **Documentation**| Swagger (SpringDoc OpenAPI 3.0) |

### [ Project Structure ]
```text
com.example.aitalk
├── domain                           # 📦 도메인별 모듈 (핵심 비즈니스 경계)
│   ├── level                        # 🧠 진단 도메인: 아동 언어 발달 정밀 진단 로직
│   │   ├── api                      # 🌐 Presentation Layer: REST 컨트롤러 및 DTO
│   │   ├── application              # 🔧 Application Layer: 유스케이스 실행 및 서비스
│   │   ├── domain                   # 🎯 Domain Model Layer: 엔티티 및 인터페이스
│   │   └── infra                    # 🔌 Infrastructure Layer: JPA 및 DB 상세 구현
│   ├── program                      # 📚 교육 도메인: 맞춤형 언어 재활 프로그램 관리
│   ├── community                    # 💬 커뮤니티 도메인: 게시글, 댓글, 좋아요 로직
│   ├── member                       # 👤 회원 도메인: 인증 및 프로필 관리
│   └── mypage                       # 🏠 마이페이지 도메인: 성장 리포트 및 Q&A 관리
├── global                           # 🌍 전역 공통 모듈
│   ├── config/                      # 전역 설정 (Security, Swagger, S3)
│   ├── exception/                   # 공통 예외 처리 (GlobalExceptionHandler)
│   └── util/                        # 공통 유틸리티 (ResponseUtil)
└── infra                            # 🔌 공통 인프라 (AWS S3 기술 모듈 등)
```

---
## 👥 Contributor (Backend)

| [한다은 (Backend Lead / Project Manager)](https://github.com/daeun-han)| 핵심 기여도 |
| :---: | :--- |
| <a href="https://github.com/daeun-han"><img src="https://github.com/daeun-han.png" width="150" height="150" style="border-radius:50%;" /></a> | • **기획 및 총괄**: 서비스 아이디어 기획, 전체 시스템 아키텍처 설계, 프로젝트 일정 및 회의 주도 <br> • **백엔드 시스템 구축**: 도메인 중심(DDD) 하이브리드 아키텍처 설계 및 백엔드 전 로직 개발 <br> • **AI 연동 아키텍처**: Flask 서버와의 S2S(Server-to-Server) 통신 규격 설계 및 데이터 정합성 검증 <br> • **인프라 및 데브옵스**: AWS EC2 인프라 구축, Nginx 리버스 프록시 및 SSL 설정, 배포 자동화 <br> • **대외 성과**: 졸업 전시 진행 총괄 및 관련 학술 논문 투고 |

---


## 🚀 프로젝트 성과
- 독창적인 기획과 기술적 완성도를 인정받아 **학기말 프로젝트 발표에서 동상(3위) 수상**
- 시스템 설계 및 구현 성과를 정리한 **학술 논문 투고와 졸업 전시 수행**
- 기획부터 로직 설계, 인프라, 테스트, 배포까지 **Full-Cycle 개발 경험**
