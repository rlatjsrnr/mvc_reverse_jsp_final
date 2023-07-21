CREATE TABLE mvc_member(
	num INT primary key auto_increment,
	id VARCHAR(50) UNIQUE NOT NULL,
	pass VARCHAR(50) NOT NULL,
	name VARCHAR(50),
	age INT(3) default 0,
	gender VARCHAR(10),
	joinYN char(1) DEFAULT 'Y',
	regdate TIMESTAMP default now(),
	updatedate TIMESTAMP default now()
);

-- 관리자 계정 추가
INSERT INTO mvc_member(id,pass,name,age,gender) 
VALUES('admin','admin','MASTER',0,'male');


-- 회원탈퇴 시
-- UPDATE mvc_member SET u_join = 'N' WHERE u_num = 회원번호;
commit;
SELECT * FROM mvc_member;

SELECT * FROM mvc_member ORDER BY num DESC;

-- 관리자가 아니고 탈퇴한 회원이 아닌 정보 출력
SELECT * FROM digital_member 
WHERE u_id != 'admin' AND u_join = 'Y' 
ORDER BY u_num DESC;


/* 
 	공지형 게시판 테이블
 */
CREATE TABLE IF NOT EXISTS notice_board(
	notice_num INT PRIMARY KEY AUTO_INCREMENT,	 -- 공지 번호
	notice_category VARCHAR(20) NOT NULL,		 -- 공지 분류
	notice_author VARCHAR(50)   NOT NULL,		 -- 작성자
	notice_title VARCHAR(50) NOT NULL,			 -- 제목
	notice_content TEXT NOT NULL,				 -- 내용
	notice_date TIMESTAMP DEFAULT now()			 -- 작성 시간
);

DESCRIBE notice_board;


INSERT INTO notice_board(notice_category,notice_author,notice_title,notice_content)
VALUES('공지','관리자','안녕하세요','처음입니다 우리사이트를 방문해주셔서 감사합니다.');

INSERT INTO notice_board
VALUES(null,'공지','관리자','방문해주셔서 감사합니다. 제곧내','냉무',now());

commit;

SELECT * FROM notice_board;

-- 페이징 처리 확인 용 sample data 추가
INSERT INTO notice_board(notice_category,notice_author,notice_title,notice_content)
SELECT 
	notice_category,notice_author,notice_title,notice_content 
FROM notice_board;


-- 질문과답변 - 자유게시판 table 
CREATE TABLE IF NOT EXISTS qna_board(
	qna_num INT PRIMARY KEY AUTO_INCREMENT,		-- 글번호
	qna_name VARCHAR(20) NOT NULL,				-- 작성자 이름
	qna_title VARCHAR(50) NOT NULL,				-- 글 제목
	qna_content TEXT NOT NULL,					-- 글 내용
	qna_re_ref INT NOT NULL DEFAULT 0 ,			--
	qna_re_lev INT NOT NULL,					-- 
	qna_re_seq INT NOT NULL DEFAULT 0,			--
	qna_writer_num INT NOT NULL,				-- 글작성자 회원 번호
	qna_readcount INT DEFAULT 0,				-- 조회수
	qna_date TIMESTAMP DEFAULT now()			-- 글 작성 시간
);

-- 원본글(질문글)일 경우 자신의 게시글 번호를 저장하여
-- 동일한 qna_re_ref값일 경우 묶어서 출력 할 수 있도록 저장
ALTER TABLE qna_board ADD COLUMN 
qna_re_ref INT NOT NULL DEFAULT 0 AFTER qna_content;

-- view화면에서 출력될 답변 글의 깊이
ALTER TABLE qna_board ADD COLUMN 
qna_re_lev INT NOT NULL DEFAULT 0 AFTER qna_re_ref;

-- 답변글 끼리의 정렬 순서 기준
ALTER TABLE qna_board ADD COLUMN 
qna_re_seq INT NOT NULL DEFAULT 0 AFTER qna_re_lev;

UPDATE qna_board SET qna_re_ref = qna_num;

SELECT * FROM qna_board;

DESC qna_board;

-- 게시글 삭제 여부 수정
ALTER TABLE qna_board qna_delete char(1) DEFAULT 'N' AFTER qna_readcount;


-- sample data 추가
INSERT INTO qna_board(qna_name,qna_title,qna_content,qna_writer_num) 
SELECT qna_name,qna_title,qna_content,qna_writer_num FROM qna_board;


commit;

SELECT * FROM qna_board;

DELETE FROM qna_board WHERE qna_num = 1;

commit;

ALTER TABLE qna_board ADD CONSTRAINT fk_qna_writer FOREIGN KEY(qna_writer_num) 
REFERENCES digital_member(u_num);

INSERT INTO qna_board 
VALUES(null,'최기근','테스트용 게시글입니다.','냉무',0,0,0,1,0,now());
SELECT * FROM qna_board ORDER BY qna_num DESC;
SELECT LAST_INSERT_ID();

UPDATE qna_board SET qna_re_ref = LAST_INSERT_ID()
WHERE qna_num = LAST_INSERT_ID();

commit;






