--use this if you are creating the test database locally on your machine (e.g. not connected to Heroku)

--1) log into postgres (if locally, as psql postgres)
DROP DATABASE tutortestdb;
CREATE DATABASE tutortestdb;
GRANT ALL PRIVILEGES ON DATABASE tutortestdb TO ahardy; --change to your user name
\connect tutortestdb

--2.1) Create first set of tables

CREATE TABLE users(
    userId SERIAL NOT NULL,
    userName VARCHAR(256) UNIQUE NOT NULL,
    email VARCHAR(256) UNIQUE NOT NULL,
    salt VARCHAR(256) NOT NULL,
    passHash VARCHAR(256) NOT NULL,
    userType VARCHAR(8) NOT NULL,
    subjects VARCHAR[],
    PRIMARY KEY (userId)
);

CREATE TABLE tutors(
    userId INT NOT NULL,
    legalFirstName VARCHAR(256),
    legalLastName VARCHAR(256),
    bio VARCHAR(256),
    degrees VARCHAR(256),
    links VARCHAR(256),
    img VARCHAR(256),
    active BOOLEAN,
    creationDate TIMESTAMP,
    rating JSON,
    FOREIGN KEY (userId) REFERENCES users (userId)
);
    
CREATE TABLE students(
    userId INT NOT NULL,
    legalFirstName VARCHAR(256),
    legalLastName VARCHAR(256),
    bio VARCHAR(256),
    major VARCHAR(256),
    minor VARCHAR(256),
    img VARCHAR(256),
    active BOOLEAN,
    creationDate TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES users (userId)
);

--2.2) Create second set of tables

CREATE TABLE posts(
    postId SERIAL NOT NULL,
    posterType VARCHAR(16),
    ownerId INT,
    subject VARCHAR(64),
    location VARCHAR(32),
    availability JSON,
    acceptsPaid BOOLEAN,
    rate real,
    unit VARCHAR(32),
    createdTs TIMESTAMP,
    active BOOLEAN,
    acceptsGroupTutoring BOOLEAN,
    currentlySignedUp int,
    PRIMARY KEY (postId),
    FOREIGN KEY (ownerID) REFERENCES users(userId)
);

--             List of relations
-- Schema |   Name   | Type  |     Owner
----------+----------+-------+----------------
-- public | posts    | table | cvkvbqcbpioxkj
-- public | students | table | cvkvbqcbpioxkj
-- public | ticks    | table | cvkvbqcbpioxkj
-- public | tutors   | table | cvkvbqcbpioxkj
-- public | users    | table | cvkvbqcbpioxkj
--(5 rows)

--3.1) Insert data into tables

INSERT INTO users (userName, email, salt, passHash, userType, subjects)
VALUES ('joetest', 'joetest@gmail.com', '$2a$06$g3pbxKcEzacSPdcRMXO0R.', '$2a$06$rs/x6ytn4dfMgG1L3oIrS.g3F.rb4lqDzD4IXkhOU/sTuAs019Blm', 'student', '{English,Biology,Math}')
    ,('susantutor', 'susantutor@yahoo.com', '$2a$06$yz8Sn02hVENjJAuDeeKkIO', '$2a$06$rs/x6ytn4dfMgG1L3oIrS.g3F.rb4lqDzD4IXkhOU/sTuAs019Blm', 'tutor', '{Computer_Science}')
    ,('steveadmin', 'steveadmin@gmail.com', '$2a$06$tbkeEJ/ww6f3l6JgaJqQlu', '$2a$06$I/wdUrsjWpMK5dNPzjxrJ.ydO6HGMEdkAAH1iey9Bb3xqWcVhdkYe', 'admin', '{Math,Biology}')
    ,('sallygamemaker', 'sallygamemaker@gmail.com', '$2a$06$rEA98lJAlF3mV91i.efste', '$2a$06$rnWGs.cVl9aa/vdgoZvWxOfjN7NS/dGae8fqRLNhf3eghfobw6NUq', 'tutor', '{Math}')
    ,('jameskirk', 'captain@enterprise.com', '$2a$06$67JgcEqgm3cYrZeiBumox.', '$2a$06$LWWpmrf7VOx6CAaRAiVdE.SZJQd6qXgDIjDhsof.5Ga2P.6eT1Bha', 'student', '{Physics}');

INSERT INTO tutors (userId, legalFirstName, legalLastName, bio, degrees, links, img, active, creationDate, rating)
VALUES (2, 'Susan', 'Test', 'I am an amazing tutor', 'Masters in Biology', 'www.google.com', 'https://i.imgur.com/IW0RAD8.jpg', TRUE, '2018-02-01 08:15:36', '{1:5, 5:5}')
    ,(4, 'Sally', 'Game-Maker', 'I like games', 'PhD in video gaming', 'www.ea.com', 'http://downdetector.com/status/ea', TRUE, '2018-09-09 09:09:09', '{1:4, 5:5}');

INSERT INTO students (userId, legalFirstName, legalLastName, bio, major, minor, img, active, creationDate)
VALUES (1, 'Joe', 'Test', 'I am a really cool student', 'CIS', 'Math', 'https://i.imgur.com/MU2dD8E.jpg', TRUE, '2018-02-01 09:14:28')
    ,(5, 'Jame', 'Kirk', 'I boldly go', 'CIS', 'Game Design', 'startrek.com', FALSE, '2233-03-22 00:00:00');

--3.2) Finish inserting data

INSERT INTO posts (posterType, ownerId, subject, location, availability, acceptsPaid, rate, unit, createdTs, active, acceptsGroupTutoring, currentlySignedUp)
VALUES ('tutor', 2, 'PSYCHOLOGY', 'Starbucks in Belmont', '{"Monday":"Night","Tuesday":"Morning","Friday":"Afternoon"}', TRUE, 20.50, 'dollars/hour', '2018-02-02 09:17:17', TRUE, TRUE, 2)
    ,('tutor', 4, 'COMPUTER AND INFORMATION SYSTEMS', 'Computers in NDNU Library', '{"Saturday":"All Day"}', TRUE, 50, 'dollars/session', '2018-01-01 14:22:23', TRUE, FALSE, 3)
    ,('student', 1, 'SOCIAL WORK', 'NDNU Library', '{"Monday":"Morning","Wednesday":"Morning"}', FALSE, 0, 'dollars/hour', '2018-02-03 12:24:46', TRUE, FALSE, 0)
    ,('student', 1, 'HISTORY', 'Mac Lab', '{"Sunday":"All Day"}', TRUE, 20, 'dollars/session', '2017-12-01 08:10:23', FALSE, TRUE, 0);

--4) Test query
SELECT
    u.userName
    ,t.legalFirstName
    ,p.location
FROM users u

LEFT JOIN tutors t
ON u.userId = t.userId

LEFT JOIN posts p
ON u.userId = p.ownerId

WHERE u.userName = 'susantutor';
--expected output
--  username  | legalfirstname |       location
--------------+----------------+----------------------
-- susantutor | Susan          | Starbucks in Belmont
--(1 row)