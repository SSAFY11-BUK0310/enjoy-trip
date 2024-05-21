-- member
insert into member values(1, 'test1@email.com', '12341234', 'user1', 20, 'MALE', 'BASIC', '010-1234-1234', date(now()), date(now()), 'test1@email.com', 'test1@email.com');
insert into member values(2, 'test2@email.com', '12341234', 'user2', 20, 'MALE', 'BASIC', '010-1234-1234', date(now()), date(now()), 'test2@email.com', 'test2@email.com');
insert into member values(3, 'test3@email.com', '12341234', 'user3', 20, 'MALE', 'BASIC', '010-1234-1234', date(now()), date(now()), 'test3@email.com', 'test3@email.com');
insert into member values(4, 'test4@email.com', '12341234', 'user4', 20, 'MALE', 'BASIC', '010-1234-1234', date(now()), date(now()), 'test4@email.com', 'test4@email.com');
insert into member values(5, 'test5@email.com', '12341234', 'user5', 20, 'MALE', 'BASIC', '010-1234-1234', date(now()), date(now()), 'test5@email.com', 'test5@email.com');
insert into member values(6, 'test6@email.com', '12341234', 'user6', 20, 'MALE', 'BASIC', '010-1234-1234', date(now()), date(now()), 'test6@email.com', 'test6@email.com');
insert into member values(7, 'test7@email.com', '12341234', 'user7', 20, 'MALE', 'BASIC', '010-1234-1234', date(now()), date(now()), 'test7@email.com', 'test7@email.com');

-- article
insert into article values(1, 1, 'title1', 'content1', 0, 'address1', 'TRIP', 'directory_uuid1', date(now()), date(now()), 'test1@email.com', 'test1@email.com');
insert into article values(2, 1, 'title2', 'content2', 0, 'address2', 'TRIP', 'directory_uuid2', date(now()), date(now()), 'test1@email.com', 'test1@email.com');
insert into article values(3, 1, 'title3', 'content3', 0, 'address3', 'TRIP', 'directory_uuid3', date(now()), date(now()), 'test1@email.com', 'test1@email.com');
insert into article values(4, 1, 'title4', 'content4', 0, 'address4', 'TRIP', 'directory_uuid4', date(now()), date(now()), 'test1@email.com', 'test1@email.com');
insert into article values(5, 2, 'title5', 'content5', 0, 'address5', 'TRIP', 'directory_uuid5', date(now()), date(now()), 'test2@email.com', 'test2@email.com');
insert into article values(6, 2, 'title6', 'content6', 0, 'address6', 'TRIP', 'directory_uuid6', date(now()), date(now()), 'test2@email.com', 'test2@email.com');
insert into article values(7, 2, 'title7', 'content7', 0, 'address7', 'TRIP', 'directory_uuid7', date(now()), date(now()), 'test2@email.com', 'test2@email.com');
insert into article values(8, 3, 'title8', 'content8', 0, 'address8', 'TRIP', 'directory_uuid8', date(now()), date(now()), 'test3@email.com', 'test3@email.com');
insert into article values(9, 5, 'title9', 'content9', 0, 'address9', 'BOARD', 'directory_uuid9', date(now()), date(now()), 'test5@email.com', 'test5@email.com');
insert into article values(10, 7, 'title10', 'content10', 0, 'address10', 'BOARD', 'directory_uuid10', date(now()), date(now()), 'test7@email.com', 'test7@email.com');

-- image
insert into image values(1, 1, 'image_name1', 'image_uuid1', 'directory_uuid1', date(now()), date(now()), 'test1@email.com', 'test1@email.com');
insert into image values(2, 1, 'image_name2', 'image_uuid2', 'directory_uuid2', date(now()), date(now()), 'test1@email.com', 'test1@email.com');
insert into image values(3, 1, 'image_name3', 'image_uuid3', 'directory_uuid3', date(now()), date(now()), 'test1@email.com', 'test1@email.com');
insert into image values(4, 2, 'image_name4', 'image_uuid4', 'directory_uuid4', date(now()), date(now()), 'test1@email.com', 'test1@email.com');
insert into image values(5, 3, 'image_name5', 'image_uuid5', 'directory_uuid5', date(now()), date(now()), 'test1@email.com', 'test1@email.com');
insert into image values(6, 3, 'image_name6', 'image_uuid6', 'directory_uuid6', date(now()), date(now()), 'test1@email.com', 'test1@email.com');
insert into image values(7, 3, 'image_name7', 'image_uuid7', 'directory_uuid7', date(now()), date(now()), 'test1@email.com', 'test1@email.com');
insert into image values(8, 3, 'image_name8', 'image_uuid8', 'directory_uuid8', date(now()), date(now()), 'test1@email.com', 'test1@email.com');
insert into image values(9, 4, 'image_name9', 'image_uuid9', 'directory_uuid9', date(now()), date(now()), 'test1@email.com', 'test1@email.com');

-- comment
insert into comment values(1, 1, 1, null, 'content1', date(now()), date(now()), 'test1@email.com', 'test1@email.com');
insert into comment values(2, 2, 1, 1, 'content2', date(now()), date(now()), 'test2@email.com', 'test2@email.com');
insert into comment values(3, 3, 1, 1, 'content3', date(now()), date(now()), 'test3@email.com', 'test3@email.com');
insert into comment values(4, 4, 1, 1, 'content4', date(now()), date(now()), 'test4@email.com', 'test4@email.com');
insert into comment values(5, 2, 2, null, 'content5', date(now()), date(now()), 'test2@email.com', 'test2@email.com');
insert into comment values(6, 3, 2, 5, 'content6', date(now()), date(now()), 'test3@email.com', 'test3@email.com');
insert into comment values(7, 4, 2, 5, 'content7', date(now()), date(now()), 'test4@email.com', 'test4@email.com');
insert into comment values(8, 5, 2, null, 'content8', date(now()), date(now()), 'test5@email.com', 'test5@email.com');
insert into comment values(9, 6, 2, 8, 'content9', date(now()), date(now()), 'test6@email.com', 'test6@email.com');
insert into comment values(10, 7, 3, null, 'content10', date(now()), date(now()), 'test7@email.com', 'test7@email.com');

-- message_room
insert into message_room values(1, 1, 1, 2, date(now()), date(now()), 'test1@email.com', 'test2@email.com');
insert into message_room values(2, 1, 3, 2, date(now()), date(now()), 'test3@email.com', 'test3@email.com');
insert into message_room values(3, 2, 4, 1, date(now()), date(now()), 'test4@email.com', 'test4@email.com');
insert into message_room values(4, 3, 5, 4, date(now()), date(now()), 'test5@email.com', 'test4@email.com');

-- message
insert into message values(1, 1, 1, 'content1', date(now()), date(now()), 'test1@email.com', 'test1@email.com');
insert into message values(2, 2, 1, 'content2', date(now()), date(now()), 'test2@email.com', 'test2@email.com');
insert into message values(3, 1, 1, 'content3', date(now()), date(now()), 'test1@email.com', 'test1@email.com');
insert into message values(4, 2, 1, 'content4', date(now()), date(now()), 'test2@email.com', 'test2@email.com');
insert into message values(5, 3, 2, 'content5', date(now()), date(now()), 'test3@email.com', 'test3@email.com');
insert into message values(6, 2, 2, 'content6', date(now()), date(now()), 'test2@email.com', 'test2@email.com');
insert into message values(7, 3, 2, 'content7', date(now()), date(now()), 'test3@email.com', 'test3@email.com');
insert into message values(8, 4, 3, 'content8', date(now()), date(now()), 'test4@email.com', 'test4@email.com');
insert into message values(9, 5, 4, 'content9', date(now()), date(now()), 'test5@email.com', 'test5@email.com');
insert into message values(10, 4, 4, 'content10', date(now()), date(now()), 'test4@email.com', 'test4@email.com');