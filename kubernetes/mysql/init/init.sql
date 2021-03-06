CREATE DATABASE webapp;
CREATE user 'mysql' identified by 'mysql';
GRANT all privileges on webapp.* to 'mysql';
use webapp;

CREATE TABLE msg (
  id int(5) unsigned not null auto_increment,
  msg varchar(255) not null,
  created_time datetime not null default current_timestamp,
  updated_time datetime not null default current_timestamp on update current_timestamp,
  primary key (id)
);
