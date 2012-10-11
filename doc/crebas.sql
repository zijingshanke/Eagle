/*==============================================================*/
/* DBMS name:      ORACLE Version 10g                           */
/* Created on:     2010-3-8 10:28:58                            */
/*==============================================================*/


DROP TABLE ASSIGN_MESSAGE CASCADE CONSTRAINTS;

/*==============================================================*/
/* Table: ASSIGN_MESSAGE                                        */
/*==============================================================*/
CREATE TABLE ASSIGN_MESSAGE  (
   ID                   INTEGER                         NOT NULL,
   ASSIGN_ID            INTEGER,
   MESS_SEC_ID          INTEGER,
   USER_ID              INTEGER,
   ACCOUNT_ID           INTEGER,
   SENDEDTIME           TIMESTAMP,
   STATUS               INTEGER,
   CONSTRAINT PK_ASSIGN_MESSAGE PRIMARY KEY (ID)
);

DROP SEQUENCE SEQ_AGENT;
CREATE SEQUENCE SEQ_AGENT INCREMENT BY 1  START  WITH  1 NOMAXVALUE  NOCYCLE  NOCACHE;
COMMIT;


INSERT INTO AGENT(ID,NAME,LOGIN_NAME,EMAIL,TEMP_EMAIL,PASSWORD,AGENT_STATUS) VALUES(SEQ_AGENT.NEXTVAL,'qmpay@qmpay.com.cn','qmpay@qmpay.com.cn','qmpay@qmpay.com.cn','qmpay@qmpay.com.cn','123456',1);
INSERT INTO AGENT(ID,NAME,LOGIN_NAME,EMAIL,TEMP_EMAIL,PASSWORD,AGENT_STATUS) VALUES(SEQ_AGENT.NEXTVAL ,'charge@qmpay.com.cn','charge@qmpay.com.cn','charge@qmpay.com.cn','charge@qmpay.com.cn','123456',1);
INSERT INTO AGENT(ID,NAME,LOGIN_NAME,EMAIL,TEMP_EMAIL,PASSWORD,AGENT_STATUS) VALUES(SEQ_AGENT.NEXTVAL,'rate@qmpay.com.cn' ,'rate@qmpay.com.cn','rate@qmpay.com.cn','rate@qmpay.com.cn','123456',1);
COMMIT;


ALTER TABLE ASSIGN_MESSAGE
   ADD CONSTRAINT FK_ASSIGN_M_REFERENCE_SYS_USER FOREIGN KEY (USER_ID)
      REFERENCES SYS_USER (USER_ID);

ALTER TABLE ASSIGN_MESSAGE
   ADD CONSTRAINT FK_ASSIGN_M_REFERENCE_ASSIGNME FOREIGN KEY (ASSIGN_ID)
      REFERENCES ASSIGNMENT (ID);

ALTER TABLE ASSIGN_MESSAGE
   ADD CONSTRAINT FK_ASSIGN_M_REFERENCE_MESSAGE_ FOREIGN KEY (MESS_SEC_ID)
      REFERENCES MESSAGE_SECTION (ID);

ALTER TABLE ASSIGN_MESSAGE
   ADD CONSTRAINT FK_ASSIGN_M_REFERENCE_ACCOUNT FOREIGN KEY (ACCOUNT_ID)
      REFERENCES ACCOUNT (ID);

