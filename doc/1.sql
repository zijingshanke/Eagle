/*==============================================================*/
/* DBMS name:      ORACLE Version 10g                           */
/* Created on:     2010-3-4 17:51:13                            */
/*==============================================================*/


DROP TABLE ASSIGNMENT CASCADE CONSTRAINTS;

DROP TABLE ASSIGN_MESSAGE CASCADE CONSTRAINTS;

DROP TABLE MESSAGE CASCADE CONSTRAINTS;

DROP TABLE MESSAGE_SECTION CASCADE CONSTRAINTS;

/*==============================================================*/
/* Table: ASSIGNMENT                                            */
/*==============================================================*/
CREATE TABLE ASSIGNMENT  (
   ID                   INTEGER                         NOT NULL,
   NAME                 VARCHAR2(100),
   CONTENT              long,
   FOUNDER              VARCHAR2(100),
   CREATE_TIME          TIMESTAMP,
   BEGIN_TIME           TIMESTAMP,
   FINISH_TIME          TIMESTAMP,
   STATUS               INTEGER,
   CONSTRAINT PK_ASSIGNMENT PRIMARY KEY (ID)
);

DROP SEQUENCE SEQ_ASSIGNMENT;
CREATE SEQUENCE SEQ_ASSIGNMENT INCREMENT BY 1  START  WITH  1 NOMAXVALUE  NOCYCLE  NOCACHE;
COMMIT;


INSERT INTO AGENT(ID,NAME,LOGIN_NAME,EMAIL,TEMP_EMAIL,PASSWORD,AGENT_STATUS) VALUES(SEQ_AGENT.NEXTVAL,'qmpay@qmpay.com.cn','qmpay@qmpay.com.cn','qmpay@qmpay.com.cn','qmpay@qmpay.com.cn','123456',1);
INSERT INTO AGENT(ID,NAME,LOGIN_NAME,EMAIL,TEMP_EMAIL,PASSWORD,AGENT_STATUS) VALUES(SEQ_AGENT.NEXTVAL ,'charge@qmpay.com.cn','charge@qmpay.com.cn','charge@qmpay.com.cn','charge@qmpay.com.cn','123456',1);
INSERT INTO AGENT(ID,NAME,LOGIN_NAME,EMAIL,TEMP_EMAIL,PASSWORD,AGENT_STATUS) VALUES(SEQ_AGENT.NEXTVAL,'rate@qmpay.com.cn' ,'rate@qmpay.com.cn','rate@qmpay.com.cn','rate@qmpay.com.cn','123456',1);
COMMIT;


/*==============================================================*/
/* Table: ASSIGN_MESSAGE                                        */
/*==============================================================*/
CREATE TABLE ASSIGN_MESSAGE  (
   ID                   INTEGER                         NOT NULL,
   MESSAGE_ID           INTEGER,
   ASSIGN_ID            INTEGER,
   TITLE                VARCHAR2(100),
   SENDEDTIME           TIMESTAMP,
   STATUS               INTEGER,
   CONSTRAINT PK_ASSIGN_MESSAGE PRIMARY KEY (ID)
);

DROP SEQUENCE SEQ_ASSIGN_MESSAGE;
CREATE SEQUENCE SEQ_ASSIGN_MESSAGE INCREMENT BY 1  START  WITH  1 NOMAXVALUE  NOCYCLE  NOCACHE;
COMMIT;


INSERT INTO AGENT(ID,NAME,LOGIN_NAME,EMAIL,TEMP_EMAIL,PASSWORD,AGENT_STATUS) VALUES(SEQ_AGENT.NEXTVAL,'qmpay@qmpay.com.cn','qmpay@qmpay.com.cn','qmpay@qmpay.com.cn','qmpay@qmpay.com.cn','123456',1);
INSERT INTO AGENT(ID,NAME,LOGIN_NAME,EMAIL,TEMP_EMAIL,PASSWORD,AGENT_STATUS) VALUES(SEQ_AGENT.NEXTVAL ,'charge@qmpay.com.cn','charge@qmpay.com.cn','charge@qmpay.com.cn','charge@qmpay.com.cn','123456',1);
INSERT INTO AGENT(ID,NAME,LOGIN_NAME,EMAIL,TEMP_EMAIL,PASSWORD,AGENT_STATUS) VALUES(SEQ_AGENT.NEXTVAL,'rate@qmpay.com.cn' ,'rate@qmpay.com.cn','rate@qmpay.com.cn','rate@qmpay.com.cn','123456',1);
COMMIT;


/*==============================================================*/
/* Table: MESSAGE                                               */
/*==============================================================*/
CREATE TABLE MESSAGE  (
   ID                   INTEGER                         NOT NULL,
   TITLE                VARCHAR2(100),
   TOPIC                VARCHAR2(100),
   CONTENT              long,
   STATUS               INTEGER,
   CONSTRAINT PK_MESSAGE PRIMARY KEY (ID)
);

DROP SEQUENCE SEQ_MESSAGE;
CREATE SEQUENCE SEQ_MESSAGE INCREMENT BY 1  START  WITH  1 NOMAXVALUE  NOCYCLE  NOCACHE;
COMMIT;


INSERT INTO AGENT(ID,NAME,LOGIN_NAME,EMAIL,TEMP_EMAIL,PASSWORD,AGENT_STATUS) VALUES(SEQ_AGENT.NEXTVAL,'qmpay@qmpay.com.cn','qmpay@qmpay.com.cn','qmpay@qmpay.com.cn','qmpay@qmpay.com.cn','123456',1);
INSERT INTO AGENT(ID,NAME,LOGIN_NAME,EMAIL,TEMP_EMAIL,PASSWORD,AGENT_STATUS) VALUES(SEQ_AGENT.NEXTVAL ,'charge@qmpay.com.cn','charge@qmpay.com.cn','charge@qmpay.com.cn','charge@qmpay.com.cn','123456',1);
INSERT INTO AGENT(ID,NAME,LOGIN_NAME,EMAIL,TEMP_EMAIL,PASSWORD,AGENT_STATUS) VALUES(SEQ_AGENT.NEXTVAL,'rate@qmpay.com.cn' ,'rate@qmpay.com.cn','rate@qmpay.com.cn','rate@qmpay.com.cn','123456',1);
COMMIT;


/*==============================================================*/
/* Table: MESSAGE_SECTION                                       */
/*==============================================================*/
CREATE TABLE MESSAGE_SECTION  (
   ID                   INTEGER                         NOT NULL,
   MESSAGE_ID           INTEGER,
   SECTION_ID           INTEGER,
   STATUS               INTEGER,
   CONSTRAINT PK_MESSAGE_SECTION PRIMARY KEY (ID)
);

DROP SEQUENCE SEQ_MESSAGE_SECTION;
CREATE SEQUENCE SEQ_MESSAGE_SECTION INCREMENT BY 1  START  WITH  1 NOMAXVALUE  NOCYCLE  NOCACHE;
COMMIT;


INSERT INTO AGENT(ID,NAME,LOGIN_NAME,EMAIL,TEMP_EMAIL,PASSWORD,AGENT_STATUS) VALUES(SEQ_AGENT.NEXTVAL,'qmpay@qmpay.com.cn','qmpay@qmpay.com.cn','qmpay@qmpay.com.cn','qmpay@qmpay.com.cn','123456',1);
INSERT INTO AGENT(ID,NAME,LOGIN_NAME,EMAIL,TEMP_EMAIL,PASSWORD,AGENT_STATUS) VALUES(SEQ_AGENT.NEXTVAL ,'charge@qmpay.com.cn','charge@qmpay.com.cn','charge@qmpay.com.cn','charge@qmpay.com.cn','123456',1);
INSERT INTO AGENT(ID,NAME,LOGIN_NAME,EMAIL,TEMP_EMAIL,PASSWORD,AGENT_STATUS) VALUES(SEQ_AGENT.NEXTVAL,'rate@qmpay.com.cn' ,'rate@qmpay.com.cn','rate@qmpay.com.cn','rate@qmpay.com.cn','123456',1);
COMMIT;


ALTER TABLE ASSIGN_MESSAGE
   ADD CONSTRAINT FK_ASSIGN_M_REFERENCE_ASSIGNME FOREIGN KEY (ASSIGN_ID)
      REFERENCES ASSIGNMENT (ID);

ALTER TABLE ASSIGN_MESSAGE
   ADD CONSTRAINT FK_ASSIGN_M_REFERENCE_MESSAGE FOREIGN KEY (MESSAGE_ID)
      REFERENCES MESSAGE (ID);

ALTER TABLE MESSAGE_SECTION
   ADD CONSTRAINT FK_MESSAGE__REFERENCE_SECTION FOREIGN KEY (SECTION_ID)
      REFERENCES SECTION (ID);

ALTER TABLE MESSAGE_SECTION
   ADD CONSTRAINT FK_MESSAGE__REFERENCE_MESSAGE FOREIGN KEY (MESSAGE_ID)
      REFERENCES MESSAGE (ID);

