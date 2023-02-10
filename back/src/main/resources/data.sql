-- authority
insert into authority (authority_name) values ('ROLE_USER');
insert into authority (authority_name) values ('ROLE_ADMIN');
insert into authority (authority_name) values ('ROLE_MANAGER');

-- members
-- $2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi -> 'admin'
insert into members (email, password, name, phone, nickName, joinDate, activated)
values ('admin@gmail.com', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'admin', '01094199571','tom', NOW(), 1);
insert into members (email, password, name, phone, nickName, joinDate, activated)
values ('user@gmail.com', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'user', '01094199571','selly', NOW(), 1);

-- members_authority
insert into members_authority (members_email, authority_name) values ('admin@gmail.com', 'ROLE_USER');
insert into members_authority (members_email, authority_name) values ('admin@gmail.com', 'ROLE_ADMIN');
insert into members_authority (members_email, authority_name) values ('user@gmail.com', 'ROLE_USER');

-- members: 카라 단체
insert into members (email, password, name, phone, nickName, joinDate, activated)
values ('kara@gmail.com', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', '전진경', '01094199571','kara', NOW(), 1);

-- foundation
INSERT INTO foundation (`businessNo`,`businessName`,`members_email`, `startDate`) VALUES ('1148209801','kara','kara@gmail.com', '20140804');

-- volunteer
INSERT INTO volunteer (`depTime`,`dest`,`flightName`,`members_email`)VALUES('2023-02-03','CAN','koreanair','user@gmail.com');