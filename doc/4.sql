/*==============================================================*/
/* DBMS name:      ORACLE Version 10g                           */
/* Created on:     2010-3-18 17:12:45                           */
/*==============================================================*/


DROP TABLE MESSAGE_SECTION CASCADE CONSTRAINTS;

/*==============================================================*/
/* Table: MESSAGE_SECTION                                       */
/*==============================================================*/
CREATE TABLE MESSAGE_SECTION  (
   ID                   INTEGER                         NOT NULL,
   MESSAGE_ID           INTEGER,
   SECTION_ID           INTEGER,
   TOPIC_ID             INTEGER,
   STATUS               INTEGER,
   CONSTRAINT PK_MESSAGE_SECTION PRIMARY KEY (ID)
);

DROP SEQUENCE SEQ_AGENT;
CREATE SEQUENCE SEQ_AGENT INCREMENT BY 1  START  WITH  1 NOMAXVALUE  NOCYCLE  NOCACHE;
COMMIT;


INSERT INTO AGENT(ID,NAME,LOGIN_NAME,EMAIL,TEMP_EMAIL,PASSWORD,AGENT_STATUS) VALUES(SEQ_AGENT.NEXTVAL,'qmpay@qmpay.com.cn','qmpay@qmpay.com.cn','qmpay@qmpay.com.cn','qmpay@qmpay.com.cn','123456',1);
INSERT INTO AGENT(ID,NAME,LOGIN_NAME,EMAIL,TEMP_EMAIL,PASSWORD,AGENT_STATUS) VALUES(SEQ_AGENT.NEXTVAL ,'charge@qmpay.com.cn','charge@qmpay.com.cn','charge@qmpay.com.cn','charge@qmpay.com.cn','123456',1);
INSERT INTO AGENT(ID,NAME,LOGIN_NAME,EMAIL,TEMP_EMAIL,PASSWORD,AGENT_STATUS) VALUES(SEQ_AGENT.NEXTVAL,'rate@qmpay.com.cn' ,'rate@qmpay.com.cn','rate@qmpay.com.cn','rate@qmpay.com.cn','123456',1);
COMMIT;


ALTER TABLE MESSAGE_SECTION
   ADD CONSTRAINT FK_MESSAGE__REFERENCE_TOPIC FOREIGN KEY (TOPIC_ID)
      REFERENCES TOPIC (ID);

ALTER TABLE MESSAGE_SECTION
   ADD CONSTRAINT FK_MESSAGE__REFERENCE_SECTION FOREIGN KEY (SECTION_ID)
      REFERENCES SECTION (ID);

ALTER TABLE MESSAGE_SECTION
   ADD CONSTRAINT FK_MESSAGE__REFERENCE_MESSAGE FOREIGN KEY (MESSAGE_ID)
      REFERENCES MESSAGE (ID);

