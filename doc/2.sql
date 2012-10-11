select * from message;
select * from assignment;
select * from assign_message;
select * from message_section;


select * from forum;
select * from section;
select * from section where forum_id=1;


select * from forum;

select * from account;



CREATE SEQUENCE SEQ_ASSIGNMESSAGE INCREMENT BY 1  START  WITH  1 NOMAXVALUE  NOCYCLE  NOCACHE;
CREATE SEQUENCE SEQ_MESSAGESECTION INCREMENT BY 1  START  WITH  1 NOMAXVALUE  NOCYCLE  NOCACHE;
CREATE SEQUENCE SEQ_TOPIC INCREMENT BY 1  START  WITH  1 NOMAXVALUE  NOCYCLE  NOCACHE;
  






