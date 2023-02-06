insert into authority (authority_name) values ('ROLE_USER');
insert into authority (authority_name) values ('ROLE_ADMIN');
insert into authority (authority_name) values ('ROLE_MANAGER');

-- $2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi -> 'admin'
insert into members (email, password, name, phone, nickName, joinDate, activated)
values ('admin@gmail.com', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'admin', '01094199571','tom', NOW(), 1);
insert into members (email, password, name, phone, nickName, joinDate, activated)
values ('user@gmail.com', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'user', '01094199571','selly', NOW(), 1);


insert into members_authority (email, authority_name) values ('admin@gmail.com', 'ROLE_USER');
insert into members_authority (email, authority_name) values ('admin@gmail.com', 'ROLE_ADMIN');
insert into members_authority (email, authority_name) values ('user@gmail.com', 'ROLE_USER');