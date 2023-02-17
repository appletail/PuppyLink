insert into members (email, activated, joinDate, name, nickName, password, phone)
values('weact@test.com',1,NOW(),'위엑트','단체0231','$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi','01017412105')
    ('welcomedog@test.com',1,NOW(),'웰컴독','단체0211','$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi','01017412105'),
('kara@test.com',1,NOW(),'카라','단체1211','$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi','01017412105'),
('care@test.com',1,NOW(),'케어','단체4211','$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi','01017412105');

insert into foundation (businessNo, businessName, description, presidentName, profileURL, startDate, members_email)
values
('1131986965','카라','카라는 다양하고 근본 동물권 정책 및 교육 활동 동물권 영화제 등의 적극적 전개로 모든 동물 학대 없는 세상으로 더 힘차게 나아가려 합니다.','정수옥',NULL,'2009-04-29','kara@test.com')
('1309160516','위엑트','위액트는 학대 당하는 개들을 구조하는 직접적인 행동을 통해 그들이 행복한 삶을 살 수 있도록 돕습니다.','이상미',NULL,'2004-03-10','weact@test.com')
('6398600952','케어','케어는 동물보호법 개정, 교육, 캠페인, 동물학대 고발, 실태조사, 인식개선, 문화 확산, 동물구호활동을 펼쳐 나가며, 구호된 동물들을 보호하는 다양한 센터를 운영하고 있습니다.','전광수',NULL,'2018-01-01','care@test.com')
('3831301490','웰컴독','웰컴독레스큐는 열악한 환경에서 구조된 식용견, 번식견들의 해외입양을 진행하는 비영리단체입니다.','정상민',NULL,'2020-09-03','welcomedog@test.com');

insert into members_authority (members_email, authority_name) 
values
('weact@test.com','ROLE_USER'),
('weact@test.com','ROLE_MANAGER'),
('welcomedog@test.com','ROLE_USER'),
('welcomedog@test.com','ROLE_MANAGER'), 
('kara@test.com','ROLE_USER'),
('kara@test.com','ROLE_MANAGER'), 
('care@test.com','ROLE_USER'),
('care@test.com','ROLE_MANAGER');