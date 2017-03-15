-- Sequence: tb_tysysusr

DROP SEQUENCE tb_tysysusr;

CREATE SEQUENCE tb_tysysusr
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9999999
  START 1
  CACHE 5;
ALTER TABLE tb_tysysusr
  OWNER TO eager;

-- Table: tysysusr

DROP TABLE tysysusr;

CREATE TABLE tysysusr
(
  id integer NOT NULL, -- 主键ID
  username character varying(120) NOT NULL, -- 用户名
  password character varying(32) NOT NULL, -- 密码
  name character varying(60), -- 姓名
  phonenumber numeric(11,0), -- 手机号码
  email character varying(150), -- 邮箱地址
  isadmin character(1), -- 是否管理员
  islocked character(1), -- 是否锁住
  usergroup integer, -- 用户组
  failuretimes character(1), -- 登陆错误次数
  validperiod date, -- 有效期
  dataSource character varying(60), -- 数据源
  remark character varying(255), -- 备注
  previousloginip character varying(60) , -- 上次登陆IP
  previouslogintime timestamp without time zone , -- 上次登陆时间
  lastLoginlp character varying(60), -- 本次登陆IP
  lastLogintime timestamp without time zone, -- 本次登陆时间
  createuser character varying(60) NOT NULL, -- 创建人
  createdate timestamp without time zone NOT NULL, -- 创建时间
  updateuser character varying(60), -- 更新人
  updatedate timestamp without time zone, -- 更新日期

  CONSTRAINT pk_tysysusr PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE tysysusr
  OWNER TO eager;
COMMENT ON COLUMN tysysusr.id IS '主键ID';
COMMENT ON COLUMN tysysusr.username IS '用户名';
COMMENT ON COLUMN tysysusr.password IS '密码';
COMMENT ON COLUMN tysysusr.name IS '姓名';
COMMENT ON COLUMN tysysusr.phonenumber IS '手机号码';
COMMENT ON COLUMN tysysusr.email IS '邮箱地址';
COMMENT ON COLUMN tysysusr.isadmin IS '是否管理员';
COMMENT ON COLUMN tysysusr.islocked IS '是否锁住';
COMMENT ON COLUMN tysysusr.usergroup IS '用户组';
COMMENT ON COLUMN tysysusr.failuretimes IS '登陆错误次数';
COMMENT ON COLUMN tysysusr.validperiod IS '有效期';
COMMENT ON COLUMN tysysusr.dataSource IS '数据源';
COMMENT ON COLUMN tysysusr.remark IS '备注';
COMMENT ON COLUMN tysysusr.previousloginip IS '上次登陆IP';
COMMENT ON COLUMN tysysusr.previouslogintime IS '上次登陆时间';
COMMENT ON COLUMN tysysusr.lastLoginlp IS '本次登陆IP';
COMMENT ON COLUMN tysysusr.lastLogintime IS '本次登陆时间';
COMMENT ON COLUMN tysysusr.createuser IS '创建人';
COMMENT ON COLUMN tysysusr.createdate IS '创建时间';
COMMENT ON COLUMN tysysusr.updateuser IS '更新人';
COMMENT ON COLUMN tysysusr.updatedate IS '更新日期';
 
 

 
  --eager!@#$%
insert into tysysusr (id,username,password,name,phonenumber,email,isadmin,createdate,createuser) values (nextval('tb_tysysusr'),'admin','03ab1617f0a3f250872f608a037e8e72','系统大总管',13913932011,'469187984@qq.com','1', current_timestamp(0) :: timestamp without time zone,'admin');