# Spring-schedule-BackEnd
사용자는 작성자 정보, 스케줄을 입력하여 일정을 생성하고, 검색조회/수정/삭제할 수 있습니다.

# 주요 기능
| 기능 | 설명 |
|---|---|
|작성자 정보 입력|이메일 정보 입력 후 ID 부여|
|일정 생성|일정 내용, 이름, 작성자ID, 비밀번호 입력|
|작성자ID 조회|특성 작성자의 모든 일정 조회|
|특정 날짜 조회|특정 날짜로 특정 날짜 모든 일정 조회|
|작성자 이름 조회|작성자 이름 모든 일정 조회|
|페이지 네이션|1페이지에 설정 갯수 일정을 조회|
|일정 수정|작성자명, 할 일 수정 / 비밀번호 일치 시|
|일정 삭제|일정 삭제 가능|

# 학습 포인트
**1. 계층 구조 설계** <br>
- MVC 아키텍처: 컨트롤러, 서비스, 레포지토리 계층이 분리 <br>

**2. DTO 사용** <br>
- Data Transfer Object (DTO): 데이터 전송을 위한 객체를 사용하여 비즈니스 로직과 데이터베이스 모델을 분리 <br>

**3. 유효성 검사** <br>
- @Valid, @NotBlank, @Email 등의 어노테이션을 사용 <br>

**4. JDBC와 RowMapper** <br>
- RowMapper: JDBC를 사용할 때 RowMapper를 활용하여 ResultSet을 Java 객체로 변환하는 방법 사용 <br>

**5. 관계형 데이터베이스 설계**

# 회고록
> "Entity, DTO, Repository, Service, Controller 구조와 명확한 역할에 대해서 익히는데 많은 도움이 되었다. 내 코드가 구조와 역할에 맞게 잘짜여졌는 지는 좀 더 공부하면서 보안해 나가야할듯하다."

# 보안해야할 점
**비밀번호 저장 방식** <br>
- 비밀 번호는 민감한 정보이다. 안전하게 처리 할 수 있게 수정이 필요한 부분. 

<hr>

# API 명세서
## 1. Author API
| 메서드 | URL         | 설명           | 요청 본문 예시                              | 응답 예시                                      | 상태 코드       |
|--------|-------------|----------------|------------------------------------------|------------------------------------------------|------------------|
| POST   | /authors    | 작성자 생성      | `json`<br>{<br>"email":"이메일 형식"<br>}<br> | `json`<br>{<br>"authorId": "작성자 ID",<br>"email": "이메일 형식",<br>"createPostTime": "생성 날짜",<br> "updatePostTime": "수정 날짜"<br>}<br> | 201 Created      |

## 2. Schedule API
| 메서드 | URL                | 설명           | 요청 본문 예시                                      | 응답 예시                                              | 상태 코드       |
|--------|--------------------|----------------|--------------------------------------------------|------------------------------------------------------|------------------|
| POST   | /schedules          | 일정 생성      | `json` <br>{<br>  "task": "일정 내용",<br>  "name": "작성자 이름",<br> "authorid": "작성자 ID",<br>  "password": "비밀번호"<br>}<br> | `json` <br>{<br>  "id": 1,<br>  "task": "일정 내용",<br>  "name": "일정 이름",<br>  "createdDate": "생성YYYY-MM-DD",<br> "updatePostTime": "수정YYYY-MM-DD"<br>}<br>| 201 Created      |
| GET    | /schedules          | 전체 일정 조회 | **Query Parameters**:<br> - `findDate` (optional): 특정 날짜<br> - `findName` (optional): 일정 이름<br> - `findAuthor` (optional): 작성자 ID<br> - `page` (optional, default=0): 페이지 번호<br> - `size` (optional, default=5): 페이지 크기 |  `json` <br>[<br>{<br>  "id": 1,<br>  "task": "일정 내용",<br>  "name": "일정 이름",<br>  "createdDate": "YYYY-MM-DD",<br> "updatePostTime": "YYYY-MM-DD"<br>}<br>  ...<br>]<br> | 200 OK           |
| GET    | /schedules/{id}     | 일정 조회      | -                                                |  `json` <br>{<br>  "id": 1,<br>  "task": "일정 내용",<br>  "name": "일정 이름",<br>  "createdDate": "YYYY-MM-DD",<br> "updatePostTime": "YYYY-MM-DD"<br>}<br> | 200 OK           |
| PATCH  | /schedules/{id}     | 일정 수정      | `json`<br>{<br>  "task": "수정된 일정 내용",<br>  "name": "수정된 일정 이름",<br>  "password": "비밀번호"<br>}<br> | `json`<br>{<br>  "id": 1,<br>  "task": "수정된 일정 내용",<br>  "name": "수정된 일정 이름",<br>  "createPostTime": "생성 날짜",<br>"updatePostTime": "수정 날짜"<br>}<br>| 200 OK           |
| DELETE | /schedules/{id}     | 일정 삭제      | -                                                | -                                                    | 204 No Content    |

# ERD
![123](https://github.com/user-attachments/assets/470f6ec8-05d3-4484-9ca5-4a12c8112137)
<BR>
**Author 테이블** <BR>
- AuthorID: 기본키 (Primary Key), 자동 증가 (AUTO_INCREMENT) <BR>
- Email: 고유한 이메일 주소 (UNIQUE), 비어 있을 수 없음 (NOT NULL) <BR>
- createPostTime: 레코드 생성 시간, 기본값은 현재 시간 <BR>
- updatePostTime: 레코드 업데이트 시간, 기본값은 현재 시간, 수정 시 자동 업데이트 <BR>
<BR>

**Schedule 테이블** <BR>
- ID: 기본키 (Primary Key), 자동 증가 (AUTO_INCREMENT)<BR>
- Task: 작업 설명, 비어 있을 수 없음 (NOT NULL)<BR>
- Name: 작업 이름, 비어 있을 수 없음 (NOT NULL)<BR>
- AuthorID: 외래키 (Foreign Key), Author 테이블의 AuthorID를 참조 (NOT NULL)<BR>
- Password: 비밀번호, 비어 있을 수 없음 (NOT NULL)<BR>
- createPostTime: 레코드 생성 시간, 기본값은 현재 시간<BR>
- updatePostTime: 레코드 업데이트 시간, 기본값은 현재 시간, 수정 시 자동 업데이트<BR>
<BR>

**관계**<BR>
- Author와 Schedule 테이블 간의 관계: <BR>
- 1:N 관계 (한 명의 저자(Author)는 여러 작업(Schedule)을 가질 수 있음) <BR> 
- Schedule.AuthorID는 Author.AuthorID를 외래키로 참조하며, 저자가 삭제되면 해당 저자와 관련된 모든 작업도 삭제됨(ON DELETE CASCADE). <BR>

# SQL Schema
```
-- Author table
CREATE TABLE Author (
                        AuthorID INT AUTO_INCREMENT PRIMARY KEY,
                        Email VARCHAR(100) NOT NULL UNIQUE,
                        createPostTime DATETIME DEFAULT CURRENT_TIMESTAMP,
                        updatePostTime DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Schedule table
CREATE TABLE Schedule (
                          ID INT AUTO_INCREMENT PRIMARY KEY,
                          Task VARCHAR(255) NOT NULL,
                          Name VARCHAR(100) NOT NULL,
                          AuthorID INT NOT NULL,
                          Password VARCHAR(255) NOT NULL,
                          createPostTime DATETIME DEFAULT CURRENT_TIMESTAMP,
                          updatePostTime DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          FOREIGN KEY (AuthorID) REFERENCES Author(AuthorID) ON DELETE CASCADE
);
```
