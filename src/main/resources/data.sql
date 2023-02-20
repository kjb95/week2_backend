insert into client (username, password) values ('client', '$2a$12$6cuRHVh4sceyZhFR53TZFeu.VkjnXUjwsngqMIMaOde1I6TOWNvwq');
insert into client (username, password) values ('manager', '$2a$12$IXMdh27dezFNPkupRXPIteJ3uPXRot7ZQDG0XPqbEkjRbswz4XkKK');
insert into client (username, password) values ('admin', '$2a$12$p2VML75CqyKcn0YZ9VF3ee.mQcVvQE5Xru86T0DNi7Qm1xMWaDBtS');

insert into authority (authority_name) values ('ROLE_USER');
insert into authority (authority_name) values ('ROLE_MANAGER');
insert into authority (authority_name) values ('ROLE_ADMIN');

insert into client_authority (client_id, authority_name) values (1, 'ROLE_USER');
insert into client_authority (client_id, authority_name) values (2, 'ROLE_USER');
insert into client_authority (client_id, authority_name) values (2, 'ROLE_MANAGER');
insert into client_authority (client_id, authority_name) values (3, 'ROLE_USER');
insert into client_authority (client_id, authority_name) values (3, 'ROLE_MANAGER');
insert into client_authority (client_id, authority_name) values (3, 'ROLE_ADMIN');
