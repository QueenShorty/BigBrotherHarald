drop database if exists TECH; 

create database TECH;

use TECH;

CREATE TABLE USERINFO (
USERID varchar(255) not NULL,
EMAIL varchar(255) not NULL, 
PASS varchar(255) not NULL,
USERNAME varchar(255) not NULL,
USERLOC varchar(255) not NULL, 
PRIMARY KEY (USERID)
); 

CREATE TABLE DEVICES (
DEVID int not NULL, 
DEVNAME varchar(255) not NULL,
DEVTYPE varchar(255) not NULL, 
DEVLOC varchar(255) not NULL,   
PRIMARY KEY (DEVID)
); 

CREATE TABLE USERDEVICE (
DEVID int NOT NULL, 
USERID varchar(255) NOT NULL, 
constraint USERDEVICE_PK PRIMARY KEY (USERID, DEVID),
FOREIGN KEY DEV_FK01(DEVID) REFERENCES DEVICES(DEVID),
FOREIGN KEY USER_FK02(USERID) REFERENCES USERINFO(USERID)
);

CREATE Table ENVIRLAYOUT (
BEACID varchar(255) not NULL,
ALIAS varchar(255) not NULL, 
LOCTYPE varchar(255) not NULL, 
DEVSINRANG enum('t', 'u', 'v', 'w', 'x', 'y', 'z'),
PRIMARY KEY (BEACID)
); 

CREATE Table TIMEDEVICE (
BEACID varchar(255) not NULL,
DEVID int NOT NULL,
constraint BEAC_PK PRIMARY KEY (BEACID, DEVID),
FOREIGN KEY BEAC_FK01(BEACID) REFERENCES ENVIRLAYOUT(BEACID),
FOREIGN KEY DEV_FK02(DEVID) REFERENCES DEVICES(DEVID),
STARTTIME timestamp NOT NULL,
ENDTIME timestamp
);

INSERT INTO USERINFO VALUES ('nisl Morbi odio eleifend','NICH@latech.edu','Mauris ligula pharetra sapien quis consequat Donec ut tempor rutrum Cras','condimentum','Home');
INSERT INTO USERINFO VALUES ('consectetuer erat sem feugiat','JP@latech.edu','Sed Nulla a ornare ridiculus convallis erat neque facilisi Phasellus erat tellus Aenean','scelerisque','Den');
INSERT INTO USERINFO VALUES ('vulputate vel orci congue','JASON@latech.edu','velit morbi est natoque aptent justo sagittis Nunc ad posuere nisi','Praesent','Den');
INSERT INTO USERINFO VALUES ('facilisis et dolor torquent','NICH@latech.edu','ipsum ultrices ante quis quis Vestibulum dignissim pellentesque est feugiat','semper','Bed');
INSERT INTO USERINFO VALUES ('suscipit luctus Class malesuada','DESTANY@latech.edu','auctor malesuada ligula per condimentum Suspendisse fermentum porttitor nulla tempor','ligula','Home');
INSERT INTO USERINFO VALUES ('Fusce nascetur aliquet elementum','LUKE@latech.edu','per metus malesuada pede velit diam nisi a condimentum sagittis tincidunt semper lacinia Maecenas ipsum','Pellentesque','Bed');
INSERT INTO USERINFO VALUES ('ligula facilisis eros pharetra','JOHN@latech.edu','Integer luctus Phasellus taciti Sed posuere commodo odio Nam in sollicitudin elit pellentesque risus','eros','Home');
INSERT INTO USERINFO VALUES ('quis fringilla Curae Cum','LUKE@latech.edu','tellus at massa Maecenas natoque Class litora eros aptent sollicitudin','vel','Home');
INSERT INTO USERINFO VALUES ('fames Nulla bibendum odio','DANIEL@latech.edu','iaculis tempus magna diam ut Proin id lobortis Integer a Maecenas euismod viverra rutrum','commodo','Bed');
INSERT INTO USERINFO VALUES ('tempus ad dictum per','JOHN@latech.edu','Etiam parturient placerat metus mi Duis aliquam Mauris Aenean nulla morbi conubia Nam non','blandit','Den');

INSERT INTO DEVICES VALUES (877,'ridiculus Vestibulum senectus hymenaeos Nunc ridiculus hendrerit dapibus hymenaeos pretium','Android','Den');
INSERT INTO DEVICES VALUES (608,'aliquet Fusce natoque Nullam faucibus Duis imperdiet sociis nisl pellentesque','iPhone','Bathroom');
INSERT INTO DEVICES VALUES (932,'natoque tellus ac tincidunt fermentum porttitor montes euismod vitae non','Android','Den');
INSERT INTO DEVICES VALUES (730,'netus tellus Aenean Curae ornare a elit primis vitae dignissim','Android','Bathroom');
INSERT INTO DEVICES VALUES (749,'in taciti neque ornare facilisis tellus orci Nam erat feugiat','Android','Kitchen');
INSERT INTO DEVICES VALUES (564,'nisl vehicula sapien aliquet felis mus ultricies justo ut tempus','Android','Bathroom');
INSERT INTO DEVICES VALUES (871,'natoque eros ultricies per conubia magna posuere malesuada viverra mattis','Android','Den');
INSERT INTO DEVICES VALUES (766,'Nulla litora aliquam vel netus habitant sit pretium sollicitudin facilisis','FitBit','Kitchen');
INSERT INTO DEVICES VALUES (372,'dictum consequat euismod turpis lectus Morbi parturient Suspendisse luctus massa','Android','Den');
INSERT INTO DEVICES VALUES (614,'Class torquent at tortor libero tellus sociosqu enim nibh aptent','FitBit','Den');
