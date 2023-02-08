insert into ACCOUNT (CREATED_AT, MODIFIED_AT, DELETED_AT, IS_DELETED, USER_ID, USER_NAME, USER_NICKNAME, USER_PASSWORD, USER_PHONE_NUM)
VALUES (now(),now(),null,false,'test','test','test','$2a$10$DAd.J6N1fv8ecD0UsYKOu.yPnrAQe.lw4pJmLaX6d3fhJ5Bzllw5.','test');

insert into BOARD (CREATED_AT, MODIFIED_AT, DELETED_AT, IS_DELETED, BOARD_TITLE, BOARD_CONTENT, BOARD_TYPE,ACCOUNT_ID)
values (now(),now(),null,false,'test','test4214',2,1);