초기 일정 테이블
CREATE TABLE schedule
(
	id INT AUTO_INCREMENT PRIMARY KEY COMMENT "등록 id",
	user VARCHAR(10) NOT NULL COMMENT "작성자명",
	password VARCHAR(20) NOT NULL COMMENT "비밀번호",
	todo VARCHAR(200) NOT NULL COMMENT "할 일",
	createdate DATE COMMENT "작성일",
	updatedate DATE COMMENT "수정일"
)

일정 테이블
CREATE TABLE todo
(
	id INT AUTO_INCREMENT PRIMARY KEY COMMENT '등록 id',
	userid INT NOT NULL COMMENT '유저 id',
	password VARCHAR(20) NOT NULL COMMENT '비밀번호',
	todo VARCHAR(200) NOT NULL COMMENT '할 일',
	todocreatedate DATE COMMENT '작성일',
	todoupdatedate DATE COMMENT '수정일',
	FOREIGN KEY (userid) REFERENCES USER (userid)
);

사용자 테이블
CREATE TABLE user
(
	userid INT AUTO_INCREMENT PRIMARY KEY COMMENT '유저 id',
	user VARCHAR(10) NOT NULL COMMENT '작성자명',
	mail VARCHAR(50) NOT NULL UNIQUE COMMENT '이메일',
	usercreatedate DATE COMMENT '정보작성일',
	userupdatedate DATE COMMENT '정보수정일'
);

일정 생성
INSERT INTO schedule(user, password, todo, createdate, updatedate) VALUES(?, ?, ?,date_format(now(), '%Y-%m-%d'), date_format(now(), '%Y-%m-%d'))
테이블 분리 수정
INSERT INTO todo(userid, password, todo, todocreatedate, todoupdatedate) VALUES(?, ?, ?, date_format(now(), '%Y-%m-%d'), date_format(now(), '%Y-%m-%d'))

사용자 생성
INSERT INTO user(user, mail, usercreatedate, userupdatedate) VALUES(?, ?, date_format(now(), '%Y-%m-%d'), date_format(now(), '%Y-%m-%d'))

유저 조회
SELECT userid FROM user WHERE user = ?

조건 일정 조회
select id, user, todo, createdate, updatedate from schedule where (? is null or user = ?) and (? is null or updatedate = ?) order by updatedate desc;

선택 일정 조회
SELECT id, user, todo, createdate, updatedate FROM schedule WHERE id = ?
테이블 분리 수정
SELECT id, userid, todo, todocreatedate, todoupdatedate FROM todo WHERE id = ?

유저 ID 일정 조회
SELECT t.id, t.userid, u.user, t.todo, t.todocreatedate, t.todoupdatedate FROM todo t JOIN user u ON t.userid = u.userid WHERE t.userid = ? ORDER BY t.todoupdatedate DESC

페이지 일정 조회
SELECT t.id, t.userid, u.user, t.todo, t.todocreatedate, t.todoupdatedate FROM todo t JOIN user u ON t.userid = u.userid LIMIT ? OFFSET ?

비밀번호 조회
SELECT password FROM schedule WHERE id = ?
테이블 분리 수정
SELECT userid, password FROM todo WHERE id = ?

일정 수정
UPDATE schedule SET user = ?, todo = ?, updatedate = date_format(now(), '%Y-%m-%d') WHERE id = ?
테이블 분리 수정
UPDATE todo t JOIN user u ON t.userid = u.userid SET u.user = ?, t.todo = ?, t.todoupdatedate = date_format(now(), '%Y-%m-%d') WHERE t.id = ?

일정 삭제
DELETE from schedule WHERE id = ?
테이블 분리 수정
DELETE FROM todo WHERE id = ?

