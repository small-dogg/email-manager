# CLAUDE.md

## 1. Purpose of This Document
이 문서는 프로젝트를 설명하고,
프로젝트 내 반드시 준수해야할 아키텍처 및 규칙 등을 제공한다.

모든 프로젝트는 생성 및 수정시 반드시 이 문서를 적용해야한다.
---
## 2. Project Context
이 프로젝트는 POP3/IMAP 설정을 기반으로 대상 메일에 연동하고,
메일을 자동 제거, 읽기, 목록 조회 등을 제공할 수 있도록 API를 제공하는 애플리케이션이다.
---
## 3. Technology Stack Constraints

Language & Runtime
- Java 24

Frameworks & Libraries
- Spring Boot 4.x
- Lombok

Build Tool
- Gradle(Groovy)
---
## 4. Architectural Style
- Layered Architecture 를 준수한다.
- SOLID 원칙에 의거해 코딩한다.
- Controller 계층에서 Repository를 바로 의존하지 않는다.
- Controller 계층에서 Service 계층의 역할을 흡수하지 않는다.
---
## 5. 공통 응답/예외
- 공통 응답을 정의하고 항상 동일한 포멧으로 응답결과를 제공한다.
- 공통 예외를 핸들링하도록 RestControllerAdvice를 정의하고 관리한다.

```json
{
  "data": {
    "response":""
  }
}
```
```json
{
  "error": {
    "code": 400,
    "message": ""
  }
}

```
---
## 6. Commit Convention
- Conventional Commit 규약에 의거하여 커밋메시지를 작성한다.
---