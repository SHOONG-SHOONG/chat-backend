# 1단계: 빌드용 Gradle 환경
FROM gradle:8.5-jdk17 AS builder

# 루트 → /app 으로 복사
WORKDIR /app

# chat-backend 프로젝트 전체 복사
COPY chat-backend /app

# 의존성 캐싱 및 JAR 빌드 (테스트 제외)
RUN gradle build -x test --no-daemon

# 2단계: 경량 JRE 기반 이미지
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# 빌드한 JAR 복사 (실행용)
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
