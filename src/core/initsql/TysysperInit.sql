DROP SEQUENCE "EAGER"."TB_TYSYSPER";
CREATE SEQUENCE  "EAGER"."TB_TYSYSPER"  MINVALUE 1 MAXVALUE 999999 INCREMENT BY 1 START WITH 1 NOCACHE  ORDER  NOCYCLE ;
DROP TABLE "EAGER"."TYSYSPER";
CREATE TABLE "EAGER"."TYSYSPER" 
   (	"ID" NUMBER NOT NULL ENABLE, 
	"NAME" VARCHAR2(64 BYTE) NOT NULL ENABLE, 
	"URL" VARCHAR2(256 BYTE), 
	"PARENTID" NUMBER NOT NULL ENABLE, 
	"SHOWTEXT" VARCHAR2(128 BYTE) NOT NULL ENABLE, 
	"CREATEDATE" DATE NOT NULL ENABLE, 
	"CREATEUSER" VARCHAR2(32 BYTE) NOT NULL ENABLE, 
	"UPDATEDATE" DATE, 
	"UPDATEUSER" VARCHAR2(32 BYTE), 
	 CONSTRAINT "TYSYSPER_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 NOCOMPRESS LOGGING
  TABLESPACE "EAGER"  ENABLE
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  TABLESPACE "EAGER" ;
 

   COMMENT ON COLUMN "EAGER"."TYSYSPER"."ID" IS 'ID';
 
   COMMENT ON COLUMN "EAGER"."TYSYSPER"."NAME" IS '菜单识别名';
 
   COMMENT ON COLUMN "EAGER"."TYSYSPER"."URL" IS '菜单链接';
 
   COMMENT ON COLUMN "EAGER"."TYSYSPER"."PARENTID" IS '上级菜单';
 
   COMMENT ON COLUMN "EAGER"."TYSYSPER"."SHOWTEXT" IS '菜单展示名';
 
   COMMENT ON COLUMN "EAGER"."TYSYSPER"."CREATEDATE" IS '创建日期';
 
   COMMENT ON COLUMN "EAGER"."TYSYSPER"."CREATEUSER" IS '创建用户';
 
   COMMENT ON COLUMN "EAGER"."TYSYSPER"."UPDATEDATE" IS '更新时间';
 
   COMMENT ON COLUMN "EAGER"."TYSYSPER"."UPDATEUSER" IS '更新日期';