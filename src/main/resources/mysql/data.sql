-- member
insert into member values(1, 'test1@email.com', '$2a$10$izT29G6lT4Djfnjh86yVQugQ/KHYgA7Nt/olr7uHiOBIKFG9z4sra', 'user1', 20, 'MALE', 'BASIC', '010-1234-1234', date(now()), date(now()), 'test1@email.com', 'test1@email.com');
insert into member values(2, 'test2@email.com', '$2a$10$izT29G6lT4Djfnjh86yVQugQ/KHYgA7Nt/olr7uHiOBIKFG9z4sra', 'user2', 20, 'MALE', 'BASIC', '010-1234-1234', date(now()), date(now()), 'test2@email.com', 'test2@email.com');
insert into member values(3, 'test3@email.com', '$2a$10$izT29G6lT4Djfnjh86yVQugQ/KHYgA7Nt/olr7uHiOBIKFG9z4sra', 'user3', 20, 'MALE', 'BASIC', '010-1234-1234', date(now()), date(now()), 'test3@email.com', 'test3@email.com');
insert into member values(4, 'test4@email.com', '$2a$10$izT29G6lT4Djfnjh86yVQugQ/KHYgA7Nt/olr7uHiOBIKFG9z4sra', 'user4', 20, 'MALE', 'BASIC', '010-1234-1234', date(now()), date(now()), 'test4@email.com', 'test4@email.com');
insert into member values(5, 'test5@email.com', '$2a$10$izT29G6lT4Djfnjh86yVQugQ/KHYgA7Nt/olr7uHiOBIKFG9z4sra', 'user5', 20, 'MALE', 'BASIC', '010-1234-1234', date(now()), date(now()), 'test5@email.com', 'test5@email.com');
insert into member values(6, 'test6@email.com', '$2a$10$izT29G6lT4Djfnjh86yVQugQ/KHYgA7Nt/olr7uHiOBIKFG9z4sra', 'user6', 20, 'MALE', 'BASIC', '010-1234-1234', date(now()), date(now()), 'test6@email.com', 'test6@email.com');
insert into member values(7, 'test7@email.com', '$2a$10$izT29G6lT4Djfnjh86yVQugQ/KHYgA7Nt/olr7uHiOBIKFG9z4sra', 'user7', 20, 'MALE', 'BASIC', '010-1234-1234', date(now()), date(now()), 'test7@email.com', 'test7@email.com');
insert into member values(8, 'test8@email.com', '$2a$10$izT29G6lT4Djfnjh86yVQugQ/KHYgA7Nt/olr7uHiOBIKFG9z4sra', 'user8', 8, 'MALE', 'BASIC', '010-1234-1234', date(now()), date(now()), 'test8@email.com', 'test8@email.com');
insert into member values(9, 'test9@email.com', '$2a$10$izT29G6lT4Djfnjh86yVQugQ/KHYgA7Nt/olr7uHiOBIKFG9z4sra', 'user9', 9, 'MALE', 'BASIC', '010-1234-1234', date(now()), date(now()), 'test9@email.com', 'test9@email.com');
insert into member values(10, 'test10@email.com', '$2a$10$izT29G6lT4Djfnjh86yVQugQ/KHYgA7Nt/olr7uHiOBIKFG9z4sra', 'user10', 10, 'MALE', 'BASIC', '010-1234-1234', date(now()), date(now()), 'test10@email.com', 'test10@email.com');
insert into member values(11, 'test11@email.com', '$2a$10$izT29G6lT4Djfnjh86yVQugQ/KHYgA7Nt/olr7uHiOBIKFG9z4sra', 'user11', 10, 'MALE', 'MANAGER', '010-1234-1234', date(now()), date(now()), 'test11@email.com', 'test11@email.com');

-- article
insert into article values(1, 1, 'title1', '<p><img src="http://localhost:8080/images/1c8b5ff5-9e8c-4f1a-bfcb-abf2c30c61d7/22887976-a7ff-4c67-885e-369783fcf3ed.jpg" /></p>', 0, '', 'BOARD', '1c8b5ff5-9e8c-4f1a-bfcb-abf2c30c61d7', date(now()), date(now()), 'test1@email.com', 'test1@email.com');
insert into article values(2, 2, 'title2', '<p><img src="http://localhost:8080/images/3c09e9f5-4fe7-42c6-b9f4-9dbe16eb8607/24847359-25b6-4efc-9be2-c2b2dd4b4787.jpg" /></p>', 0, '', 'BOARD', '3c09e9f5-4fe7-42c6-b9f4-9dbe16eb8607', date(now()), date(now()), 'test2@email.com', 'test2@email.com');

insert into article values(3, 11, 'title3', '<p><img src="http://localhost:8080/images/3ee22827-dd34-4dc7-bd45-6b2ef79a6d5b/59160282-0f32-481c-a28d-bea1d50508c9.jpg" /></p> ||
                                            <p><img src="http://localhost:8080/images/3ee22827-dd34-4dc7-bd45-6b2ef79a6d5b/ea68f948-a686-4248-9f75-deaed3ca7a7a.jpg" /></p> ||
                                            <p><img src="http://localhost:8080/images/3ee22827-dd34-4dc7-bd45-6b2ef79a6d5b/ea290b96-eb41-403e-9d90-4c5739405d05.jpg" /></p>', 0, '서울', 'TRIP', '3ee22827-dd34-4dc7-bd45-6b2ef79a6d5b', date(now()), date(now()), 'test11@email.com', 'test11@email.com');
insert into article values(4, 11, 'title4', '<p><img src="http://localhost:8080/images/8c85d3ce-b527-403b-b57a-bfe6aab360ec/a01eedfb-c75d-4eec-9618-f35333f50cca.jpg" /></p>', 0, '서울', 'TRIP', '8c85d3ce-b527-403b-b57a-bfe6aab360ec', date(now()), date(now()), 'test11@email.com', 'test11@email.com');
insert into article values(5, 11, 'title5', '<p><img src="http://localhost:8080/images/9c7e5d0b-bdca-490a-b952-548e38278298/a3c3f62c-2c3e-41c1-84aa-7ea794f9aa91.jpg" /></p>', 0, '서울', 'TRIP', '9c7e5d0b-bdca-490a-b952-548e38278298', date(now()), date(now()), 'test11@email.com', 'test11@email.com');
insert into article values(6, 11, 'title6', '<p><img src="http://localhost:8080/images/9cd04528-7ce0-4792-a421-f37345ed377f/a95e0b63-cac9-48f4-ad94-c2f4d60fc25f.jpg" /></p>', 0, '서울', 'TRIP', '9cd04528-7ce0-4792-a421-f37345ed377f', date(now()), date(now()), 'test11@email.com', 'test11@email.com');
insert into article values(7, 11, 'title7', '<p><img src="http://localhost:8080/images/27a08f97-7591-470a-af4e-09c868e165b8/ad0af726-4e00-4395-aab6-b21da1bbd707.jpg" /></p>', 0, '서울', 'TRIP', '27a08f97-7591-470a-af4e-09c868e165b8', date(now()), date(now()), 'test11@email.com', 'test11@email.com');
insert into article values(8, 11, 'title8', '<p><img src="http://localhost:8080/images/87a07bab-0548-4513-867a-9c06d2af180b/b382a610-99b6-47a6-b409-a70ea560ca9a.jpg" /></p>', 0, '서울', 'TRIP', '87a07bab-0548-4513-867a-9c06d2af180b', date(now()), date(now()), 'test11@email.com', 'test11@email.com');
insert into article values(9, 11, 'title9', '<p><img src="http://localhost:8080/images/244d525b-e4e7-49e9-9ab5-81fc5aa37c36/d26073f7-3772-44a5-a6db-b8a95445c3a9.jpg" /></p>', 0, '서울', 'TRIP', '244d525b-e4e7-49e9-9ab5-81fc5aa37c36', date(now()), date(now()), 'test11@email.com', 'test11@email.com');
insert into article values(10, 11, 'title10', '<p><img src="http://localhost:8080/images/446a4271-b32c-4f96-ba33-a4f4b4c95c07/d666685b-e568-4131-b882-3dd9b84c0045.jpg" /></p>', 0, '서울', 'TRIP', '446a4271-b32c-4f96-ba33-a4f4b4c95c07', date(now()), date(now()), 'test11@email.com', 'test11@email.com');
insert into article values(11, 11, 'title11', '<p><img src="http://localhost:8080/images/21903477-62d2-4ef3-b5e6-1d2fb734b251/dafbe840-7fe9-413e-915e-413ff57baba4.jpg" /></p>', 0, '서울', 'TRIP', '21903477-62d2-4ef3-b5e6-1d2fb734b251', date(now()), date(now()), 'test11@email.com', 'test11@email.com');
insert into article values(12, 11, 'title12', '<p><img src="http://localhost:8080/images/594b7810-cf49-43d1-88d2-86d481269a80/ac5777ad-f316-4ee7-87f7-d24e72ee0a8c.jpg" /></p>', 0, '서울', 'TRIP', '594b7810-cf49-43d1-88d2-86d481269a80', date(now()), date(now()), 'test11@email.com', 'test11@email.com');
insert into article values(13, 11, 'title13', '<p><img src="http://localhost:8080/images/0b7f5d6c-065e-49c3-a901-9627a4847e5f/db67fc8a-826f-4a15-855c-a2d26165a1a8.jpg" /></p>', 0, '서울', 'TRIP', '0b7f5d6c-065e-49c3-a901-9627a4847e5f', date(now()), date(now()), 'test11@email.com', 'test11@email.com');
insert into article values(14, 11, 'title14', '<p><img src="http://localhost:8080/images/58d55e96-76e8-4ea4-97c7-0303fba4a791/45deb1d1-c47f-4a56-a480-24b9f1680f73.jpg" /></p>', 0, '서울', 'TRIP', '58d55e96-76e8-4ea4-97c7-0303fba4a791', date(now()), date(now()), 'test11@email.com', 'test11@email.com');
insert into article values(15, 11, 'title15', '<p><img src="http://localhost:8080/images/60e9e5c6-3de5-4c35-abe2-1cdefdc82ceb/191e002f-09f4-4bb8-82d7-aa688e9b5ee3.jpg" /></p>', 0, '서울', 'TRIP', '60e9e5c6-3de5-4c35-abe2-1cdefdc82ceb', date(now()), date(now()), 'test11@email.com', 'test11@email.com');
insert into article values(16, 11, 'title16', '<p><img src="http://localhost:8080/images/73d6ee6d-ef7a-454a-a9f1-32d20605ad63/819a3445-f31c-46e6-9c3c-f9367a119e2d.jpg" /></p>', 0, '서울', 'TRIP', '73d6ee6d-ef7a-454a-a9f1-32d20605ad63', date(now()), date(now()), 'test11@email.com', 'test11@email.com');
insert into article values(17, 11, 'title17', '<p><img src="http://localhost:8080/images/657558fe-5b8e-46d3-9912-920fd9e2c578/886d69dc-a012-47e5-8da4-0875692a601a.jpg" /></p>', 0, '서울', 'TRIP', '657558fe-5b8e-46d3-9912-920fd9e2c578', date(now()), date(now()), 'test11@email.com', 'test11@email.com');
insert into article values(18, 11, 'title18', '<p><img src="http://localhost:8080/images/e7a35fe8-7c94-417f-b707-2273352b620e/960851fe-f7da-4cca-bd80-3f90e53ee6d9.jpg" /></p>', 0, '서울', 'TRIP', 'e7a35fe8-7c94-417f-b707-2273352b620e', date(now()), date(now()), 'test11@email.com', 'test11@email.com');
insert into article values(19, 11, 'title19', '<p><img src="http://localhost:8080/images/59184ad4-7d0d-45dc-b97a-dc4b427a988f/aa48f5a0-cb58-416c-bb07-6170cf20b5f7.jpg" /></p>', 0, '서울', 'TRIP', '59184ad4-7d0d-45dc-b97a-dc4b427a988f', date(now()), date(now()), 'test11@email.com', 'test11@email.com');
insert into article values(20, 11, 'title20', '<p><img src="http://localhost:8080/images/518071a6-3c4b-49e3-ac25-5f4719a54aff/e374e83d-ec65-44e3-b4a7-1638dc49a68b.jpg" /></p>', 0, '서울', 'TRIP', '518071a6-3c4b-49e3-ac25-5f4719a54aff', date(now()), date(now()), 'test11@email.com', 'test11@email.com');
insert into article values(21, 11, 'title21', '<p><img src="http://localhost:8080/images/a8a57d48-8d12-4480-aa48-aee3e50127f2/d59a747d-562c-4657-8555-e5e2817c4fc0.jpg" /></p>', 0, '서울', 'TRIP', 'a8a57d48-8d12-4480-aa48-aee3e50127f2', date(now()), date(now()), 'test11@email.com', 'test11@email.com');

-- image
insert into image values(1, 1, 'image_name1', '22887976-a7ff-4c67-885e-369783fcf3ed.jpg', '1c8b5ff5-9e8c-4f1a-bfcb-abf2c30c61d7', date(now()), date(now()), 'test1@email.com', 'test1@email.com');
insert into image values(2, 2, 'image_name2', '24847359-25b6-4efc-9be2-c2b2dd4b4787.jpg', '3c09e9f5-4fe7-42c6-b9f4-9dbe16eb8607', date(now()), date(now()), 'test2@email.com', 'test2@email.com');


insert into image values(3, 3, 'image_name3', '59160282-0f32-481c-a28d-bea1d50508c9.jpg', '3ee22827-dd34-4dc7-bd45-6b2ef79a6d5b', date(now()), date(now()), 'test11@email.com', 'test11@email.com');
insert into image values(4, 3, 'image_name4', 'ea68f948-a686-4248-9f75-deaed3ca7a7a.jpg', '3ee22827-dd34-4dc7-bd45-6b2ef79a6d5b', date(now()), date(now()), 'test11@email.com', 'test11@email.com');
insert into image values(5, 3, 'image_name5', 'ea290b96-eb41-403e-9d90-4c5739405d05.jpg', '3ee22827-dd34-4dc7-bd45-6b2ef79a6d5b', date(now()), date(now()), 'test11@email.com', 'test11@email.com');


insert into image values(6, 4, 'image_name6', 'a01eedfb-c75d-4eec-9618-f35333f50cca.jpg', '8c85d3ce-b527-403b-b57a-bfe6aab360ec', date(now()), date(now()), 'test11@email.com', 'test11@email.com');
insert into image values(7, 5, 'image_name7', 'a3c3f62c-2c3e-41c1-84aa-7ea794f9aa91.jpg', '9c7e5d0b-bdca-490a-b952-548e38278298', date(now()), date(now()), 'test11@email.com', 'test11@email.com');
insert into image values(8, 6, 'image_name8', 'a95e0b63-cac9-48f4-ad94-c2f4d60fc25f.jpg', '9cd04528-7ce0-4792-a421-f37345ed377f', date(now()), date(now()), 'test11@email.com', 'test11@email.com');
insert into image values(9, 7, 'image_name9', 'ad0af726-4e00-4395-aab6-b21da1bbd707.jpg', '27a08f97-7591-470a-af4e-09c868e165b8', date(now()), date(now()), 'test11@email.com', 'test11@email.com');
insert into image values(10, 8, 'image_name10', 'b382a610-99b6-47a6-b409-a70ea560ca9a.jpg', '87a07bab-0548-4513-867a-9c06d2af180b', date(now()), date(now()), 'test11@email.com', 'test11@email.com');
insert into image values(11, 9, 'image_name11', 'd26073f7-3772-44a5-a6db-b8a95445c3a9.jpg', '244d525b-e4e7-49e9-9ab5-81fc5aa37c36', date(now()), date(now()), 'test11@email.com', 'test11@email.com');
insert into image values(12, 10, 'image_name12', 'd666685b-e568-4131-b882-3dd9b84c0045.jpg', '446a4271-b32c-4f96-ba33-a4f4b4c95c07', date(now()), date(now()), 'test11@email.com', 'test11@email.com');
insert into image values(13, 11, 'image_name13', 'dafbe840-7fe9-413e-915e-413ff57baba4.jpg', '21903477-62d2-4ef3-b5e6-1d2fb734b251', date(now()), date(now()), 'test11@email.com', 'test11@email.com');
insert into image values(14, 12, 'image_name14', 'ac5777ad-f316-4ee7-87f7-d24e72ee0a8c.jpg', '594b7810-cf49-43d1-88d2-86d481269a80', date(now()), date(now()), 'test11@email.com', 'test11@email.com');
insert into image values(15, 13, 'image_name15', 'db67fc8a-826f-4a15-855c-a2d26165a1a8.jpg', '0b7f5d6c-065e-49c3-a901-9627a4847e5f', date(now()), date(now()), 'test11@email.com', 'test11@email.com');
insert into image values(16, 14, 'image_name16', '45deb1d1-c47f-4a56-a480-24b9f1680f73.jpg', '58d55e96-76e8-4ea4-97c7-0303fba4a791', date(now()), date(now()), 'test11@email.com', 'test11@email.com');
insert into image values(17, 15, 'image_name17', '191e002f-09f4-4bb8-82d7-aa688e9b5ee3.jpg', '60e9e5c6-3de5-4c35-abe2-1cdefdc82ceb', date(now()), date(now()), 'test11@email.com', 'test11@email.com');
insert into image values(18, 16, 'image_name18', '819a3445-f31c-46e6-9c3c-f9367a119e2d.jpg', '73d6ee6d-ef7a-454a-a9f1-32d20605ad63', date(now()), date(now()), 'test11@email.com', 'test11@email.com');
insert into image values(19, 17, 'image_name19', '886d69dc-a012-47e5-8da4-0875692a601a.jpg', '657558fe-5b8e-46d3-9912-920fd9e2c578', date(now()), date(now()), 'test11@email.com', 'test11@email.com');
insert into image values(20, 18, 'image_name20', '960851fe-f7da-4cca-bd80-3f90e53ee6d9.jpg', 'e7a35fe8-7c94-417f-b707-2273352b620e', date(now()), date(now()), 'test11@email.com', 'test11@email.com');
insert into image values(21, 19, 'image_name21', 'aa48f5a0-cb58-416c-bb07-6170cf20b5f7.jpg', '59184ad4-7d0d-45dc-b97a-dc4b427a988f', date(now()), date(now()), 'test11@email.com', 'test11@email.com');
insert into image values(22, 20, 'image_name22', 'e374e83d-ec65-44e3-b4a7-1638dc49a68b.jpg', '518071a6-3c4b-49e3-ac25-5f4719a54aff', date(now()), date(now()), 'test11@email.com', 'test11@email.com');
insert into image values(23, 21, 'image_name23', 'd59a747d-562c-4657-8555-e5e2817c4fc0.jpg', 'a8a57d48-8d12-4480-aa48-aee3e50127f2', date(now()), date(now()), 'test11@email.com', 'test11@email.com');





-- comment
insert into comment values(1, 1, 1, 1, 'content1', date(now()), date(now()), 'test1@email.com', 'test1@email.com');
insert into comment values(2, 2, 1, 1, 'content2', date(now()), date(now()), 'test2@email.com', 'test2@email.com');
insert into comment values(3, 3, 1, 1, 'content3', date(now()), date(now()), 'test3@email.com', 'test3@email.com');
insert into comment values(4, 4, 1, 1, 'content4', date(now()), date(now()), 'test4@email.com', 'test4@email.com');
insert into comment values(5, 2, 2, 5, 'content5', date(now()), date(now()), 'test2@email.com', 'test2@email.com');
insert into comment values(6, 3, 2, 5, 'content6', date(now()), date(now()), 'test3@email.com', 'test3@email.com');
insert into comment values(7, 4, 2, 5, 'content7', date(now()), date(now()), 'test4@email.com', 'test4@email.com');
insert into comment values(8, 5, 2, 8, 'content8', date(now()), date(now()), 'test5@email.com', 'test5@email.com');
insert into comment values(9, 6, 2, 8, 'content9', date(now()), date(now()), 'test6@email.com', 'test6@email.com');
insert into comment values(10, 7, 3, 10, 'content10', date(now()), date(now()), 'test7@email.com', 'test7@email.com');
insert into comment values(11, 1, 2, 11, '글 잘 봤습니다! 혹시 관련 자료나 추천 서적이 있다면 공유해 주실 수 있을까요?', date(now()), date(now()), 'test1@email.com', 'test1@email.com');
insert into comment values(12, 2, 2, 11, '무라카미 하루키의 "먼 북소리" 추천드려요!', date(now()), date(now()), 'test2@email.com', 'test2@email.com');
insert into comment values(13, 2, 2, 11, '여행하면서 읽기 좋더라구요 :)', date(now()), date(now()), 'test2@email.com', 'test2@email.com');
insert into comment values(14, 1, 2, 11, '추천 감사합니다 ㅎㅎ', date(now()), date(now()), 'test1@email.com', 'test1@email.com');
insert into comment values(15, 1, 2, 15, '항상 좋은 글 감사합니다. 이번 글도 많은 도움이 되었습니다. 다음 포스트도 기대할게요!', date(now()), date(now()), 'test1@email.com', 'test1@email.com');
insert into comment values(16, 3, 2, 15, '안녕하세요, 질문이 있어서 댓글 남깁니다. 이 글과 관련된 주제에 대해 좀 더 자세히 알고 싶습니다.', date(now()), date(now()), 'test3@email.com', 'test3@email.com');
insert into comment values(17, 1, 2, 15, '안녕하세요! 관심 가져주셔서 감사합니다. 어떤 부분이 궁금하신가요?', date(now()), date(now()), 'test1@email.com', 'test1@email.com');
insert into comment values(18, 1, 2, 18, '구체적으로 질문해 주시면 최대한 자세히 답변드리겠습니다.', date(now()), date(now()), 'test1@email.com', 'test1@email.com');

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