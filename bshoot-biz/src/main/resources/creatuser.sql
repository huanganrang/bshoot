 insert into mysql.user(Host,User,Password) values("localhost","bshoot",password("123456"));
 flush privileges;
 create database bshoot;
 grant all privileges on bshoot.* to bshoot@localhost identified by '123456';
 flush privileges;
 
 ---GRANT USAGE ON *.* TO 'appmonitor'@'localhost' IDENTIFIED BY '123456' WITH GRANT OPTION;