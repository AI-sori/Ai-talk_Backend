CREATE TABLE `member` (
                        `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
                        `email` VARCHAR(255) NOT NULL UNIQUE, -- 이메일 회원가입
                        `password` VARCHAR(255) NOT NULL, -- 비밀번호 (암호화 저장을 고려하여 넉넉한 길이)
                        `nickname` VARCHAR(255) NULL DEFAULT NULL, -- 닉네임
                        `profileImage` VARCHAR(255) NULL DEFAULT NULL, -- 프로필 사진 URL (NULL 허용)
                        `createdAt` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP, -- 생성 시간
                        PRIMARY KEY (`id`) USING BTREE
);
