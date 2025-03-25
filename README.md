# Spring-schedule-BackEnd

**Schedule** Lv 1. 일정 생성 및 조회 + Lv 2. 일정 수정 및 삭제 <br>
**Schedule Challenge** Lv 1. 일정 생성 및 조회 + Lv 2. 일정 수정 및 삭제 +  Lv 3. 연관 관계 설정 + Lv 4. 페이지네이션 + Lv 5. 예외 발생 처리 + Lv 6. null 체크 및 특정 패턴에 대한 검증 수행

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
![Image](https://github.com/user-attachments/assets/a406e282-2379-47c3-a79d-749ba5f92d35) <BR>
**Author 테이블** <BR>
AuthorID: 기본키 (Primary Key), 자동 증가 (AUTO_INCREMENT) <BR>
Email: 고유한 이메일 주소 (UNIQUE), 비어 있을 수 없음 (NOT NULL) <BR>
createPostTime: 레코드 생성 시간, 기본값은 현재 시간 <BR>
updatePostTime: 레코드 업데이트 시간, 기본값은 현재 시간, 수정 시 자동 업데이트 <BR>
<BR>
**Schedule 테이블** <BR>
ID: 기본키 (Primary Key), 자동 증가 (AUTO_INCREMENT)<BR>
Task: 작업 설명, 비어 있을 수 없음 (NOT NULL)<BR>
Name: 작업 이름, 비어 있을 수 없음 (NOT NULL)<BR>
AuthorID: 외래키 (Foreign Key), Author 테이블의 AuthorID를 참조 (NOT NULL)<BR>
Password: 비밀번호, 비어 있을 수 없음 (NOT NULL)<BR>
createPostTime: 레코드 생성 시간, 기본값은 현재 시간<BR>
updatePostTime: 레코드 업데이트 시간, 기본값은 현재 시간, 수정 시 자동 업데이트<BR>
<BR>
**관계**<BR>
Author와 Schedule 테이블 간의 관계: <BR>
1:N 관계 (한 명의 저자(Author)는 여러 작업(Schedule)을 가질 수 있음) <BR> 
Schedule.AuthorID는 Author.AuthorID를 외래키로 참조하며, 저자가 삭제되면 해당 저자와 관련된 모든 작업도 삭제됨(ON DELETE CASCADE). <BR>

# SQL
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
