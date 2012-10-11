/*==============================================================*/
/* DBMS name:      ORACLE Version 10g                           */
/* Created on:     2010-3-25 14:13:20                           */
/*==============================================================*/


DROP TABLE ASSIGN_MESSAGE_LOG CASCADE CONSTRAINTS;

/*==============================================================*/
/* Table: ASSIGN_MESSAGE_LOG                                    */
/*==============================================================*/
CREATE TABLE ASSIGN_MESSAGE_LOG  (
   ID                   INTEGER                         NOT NULL,
   ASSIGNMESS_ID        INTEGER,
   LOG_TIME             TIMESTAMP,
   LOG_TYPE             VARCHAR2(100),
   CONTENT              VARCHAR2(50000),
   STATUS               INTEGER,
   CONSTRAINT PK_ASSIGN_MESSAGE_LOG PRIMARY KEY (ID)
);

DROP SEQUENCE SEQ_AGENT;
CREATE SEQUENCE SEQ_AGENT INCREMENT BY 1  START  WITH  1 NOMAXVALUE  NOCYCLE  NOCACHE;
COMMIT;


INSERT INTO AGENT(ID,NAME,LOGIN_NAME,EMAIL,TEMP_EMAIL,PASSWORD,AGENT_STATUS) VALUES(SEQ_AGENT.NEXTVAL,'qmpay@qmpay.com.cn','qmpay@qmpay.com.cn','qmpay@qmpay.com.cn','qmpay@qmpay.com.cn','123456',1);
INSERT INTO AGENT(ID,NAME,LOGIN_NAME,EMAIL,TEMP_EMAIL,PASSWORD,AGENT_STATUS) VALUES(SEQ_AGENT.NEXTVAL ,'charge@qmpay.com.cn','charge@qmpay.com.cn','charge@qmpay.com.cn','charge@qmpay.com.cn','123456',1);
INSERT INTO AGENT(ID,NAME,LOGIN_NAME,EMAIL,TEMP_EMAIL,PASSWORD,AGENT_STATUS) VALUES(SEQ_AGENT.NEXTVAL,'rate@qmpay.com.cn' ,'rate@qmpay.com.cn','rate@qmpay.com.cn','rate@qmpay.com.cn','123456',1);
COMMIT;


ALTER TABLE ASSIGN_MESSAGE_LOG
   ADD CONSTRAINT FK_ASSIGN_M_REFERENCE_ASSIGN_M FOREIGN KEY (ASSIGNMESS_ID)
      REFERENCES ASSIGN_MESSAGE (ID);

