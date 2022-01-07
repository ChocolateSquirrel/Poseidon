insert into users(id, fullname, username, password, role) values(nextval('users_id_seq'), 'Administrator', 'admin', '$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa', 'ADMIN');
insert into users(id, fullname, username, password, role) values(nextval('users_id_seq'), 'User', 'user', '$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa', 'USER');
insert into users(id, fullname, username, password, role) values(nextval('users_id_seq'), 'User2', 'user2', '$2a$10$a0hlNqCasHViWB8VkJrjj./z1OgM9WShM6B6BJNOwCJuQJ4sSS9gm', 'USER');
insert into users(id, fullname, username, password, role) values(nextval('users_id_seq'), 'Administrator2', 'admin2', '$2a$10$a0hlNqCasHViWB8VkJrjj./z1OgM9WShM6B6BJNOwCJuQJ4sSS9gm', 'ADMIN');

insert into bidlist(bid_list_id, account, bid_quantity, type) values(nextval('bidlist_bid_list_id_seq'), 'FR76', '10', 'action');
insert into bidlist(bid_list_id, account, bid_quantity, type) values(nextval('bidlist_bid_list_id_seq'), 'FR76', '20', 'sous');

insert into curvepoint(id, curve_id, term, value) values(nextval('curvepoint_id_seq'), '1', '5', '10');