
drop table if exists USERS;
CREATE TABLE USERS (
  USER_ID bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Auto generated id for a user',
  USERNAME varchar(255) NOT NULL unique COMMENT 'User name for the User',
  PASSWORD varchar(255) NOT NULL COMMENT 'Hashed Password of the user',
  EMAIL varchar(255) NOT NULL COMMENT 'Email address of the User',
  STATUS int(11) DEFAULT NULL COMMENT 'Status can be new, etc',
  ENABLED BOOLEAN NOT NULL COMMENT '',
  CREATED_BY varchar(255) DEFAULT NULL,
  CREATED_TIME timestamp NULL DEFAULT NULL,
  UPDATED_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (USER_ID) USING HASH
);

drop table if exists AUTHORITIES;
CREATE TABLE AUTHORITIES (
    USERNAME varchar(255) not null,
    AUTHORITY varchar(50) not null,
    CREATED_BY varchar(255) DEFAULT NULL,
    CREATED_TIME timestamp NULL DEFAULT NULL,
    UPDATED_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
create unique index idx_auth_username on authorities (username,authority);

drop table if exists POST;
CREATE TABLE POST(
    POST_ID bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Auto generated id for a Post',
    TITLE varchar(255) NOT NULL COMMENT 'Title of the Post',
    BODY MEDIUMTEXT COMMENT 'All the text',
    CREATED_BY varchar(255) DEFAULT NULL,
    CREATED_TIME timestamp NULL DEFAULT NULL,
    UPDATED_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (POST_ID) USING HASH
);

