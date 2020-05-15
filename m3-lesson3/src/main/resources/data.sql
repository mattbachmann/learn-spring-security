-- Test User
-- test@email.com/pass
create table if not exists user (
  id int auto_increment primary key,
  email varchar(64) not null,
  pass varchar(128) not null,
  enabled boolean,
  created timestamp not null
);

Delete from  user;
insert into user (id, email, pass, enabled, created) values (1, 'test@email.com', '$2a$04$kqRvgmJBlWZQQ2c9NT9IH.ZhxFY07Y2xE73vmLHxBq2hNTvGvUc5m', true, '2008-08-08 00:00:00');

create table if not exists persistent_logins (
  username varchar(64) not null,
  series varchar(64) primary key,
  token varchar(64) not null,
  last_used timestamp not null
);
