insert into "members" (email, password, name, phone, nickName, joinDate, activated)
values ('admin', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'admin', '01094199571','tom', TIMESTAMP, 1);
insert into "members" (email, password, name, phone, nickName, joinDate, activated)
values ('user', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'user', '01094199571','selly', TIMESTAMP, 1);

insert into authority (authority_name) values ('ROLE_USER');
insert into authority (authority_name) values ('ROLE_ADMIN');

insert into user_authority (email, authority_name) values ('user@gmail.com', 'ROLE_USER');
insert into user_authority (email, authority_name) values ('admin@gmail.com', 'ROLE_ADMIN');
insert into user_authority (email, authority_name) values ('user@gmail.com', 'ROLE_USER');