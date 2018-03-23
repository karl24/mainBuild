--use this if you are creating the test database locally on your machine (e.g. not connected to Heroku)

--1) log into postgres (if locally, as psql postgres)
DROP DATABASE tutortestdb;
CREATE DATABASE tutortestdb;
GRANT ALL PRIVILEGES ON DATABASE tutortestdb TO ahardy; --change to your user name
\connect tutortestdb

--2.1) Create first set of tables

CREATE TABLE users(
    userId INT,
    userName VARCHAR(256),
    email VARCHAR(256),
    salt VARCHAR(256),
    passHash VARCHAR(256),
    userType VARCHAR(8),
    PRIMARY KEY (userId)
);

CREATE TABLE subject(
    subjectId INT,
    subjectName VARCHAR(32),
    PRIMARY KEY (subjectId)
);

CREATE TABLE tutors(
    userId int,
    legalFirstName VARCHAR(256),
    legalLastName VARCHAR(256),
    bio VARCHAR(256),
    degrees VARCHAR(256),
    links VARCHAR(256),
    img VARCHAR(256),
    active BOOLEAN,
    creationDate TIMESTAMP,
    avgRating real,
    PRIMARY KEY (userId)
);
    
CREATE TABLE students(
    userId INT,
    legalFirstName VARCHAR(256),
    legalLastName VARCHAR(256),
    bio VARCHAR(256),
    major VARCHAR(256),
    minor VARCHAR(256),
    img VARCHAR(256),
    active BOOLEAN,
    creationDate TIMESTAMP,
    PRIMARY KEY (userId)
);

--2.2) Create second set of tables

CREATE TABLE tutorSubjects(
    tutorUserId INT,
    subjectId INT,
    FOREIGN KEY (tutorUserId)
    REFERENCES tutors(userId),
    FOREIGN KEY (subjectId)
    REFERENCES subject (subjectId)
);

/*CREATE TABLE tutorPost(
    tutorPostId INT,
    ownerId INT,
    subjectId INT,
    location VARCHAR(32),
    availability JSON,
    rate real,
    unit VARCHAR(32),
    createdTs TIMESTAMP,
    active BOOLEAN,
    maxGroupSize INT,
    currentSignedUp INT,
    acceptsGroups BOOLEAN,
    PRIMARY KEY (tutorPostId),
    FOREIGN KEY (ownerId) REFERENCES tutors(userId),
    FOREIGN KEY (subjectId) REFERENCES subject(subjectId)
);

CREATE TABLE studentPost(
    studentPostId INT,
    ownerId INT,
    subjectId INT,
    location VARCHAR(32),
    availability JSON,
    acceptsPaid BOOLEAN,
    rate real,
    unit VARCHAR(32),
    createdTs TIMESTAMP,
    active BOOLEAN,
    acceptsGroupTutoring BOOLEAN,
    PRIMARY KEY (studentPostId),
    FOREIGN KEY (ownerId) REFERENCES students(userId),
    FOREIGN KEY (subjectId) REFERENCES subject(subjectId)
);*/

CREATE TABLE post(
    postId INT,
    posterType VARCHAR(16),
    ownerId INT,
    subjectId INT,
    location VARCHAR(32),
    availability JSON,
    acceptsPaid BOOLEAN,
    rate real,
    unit VARCHAR(32),
    createdTs TIMESTAMP,
    active BOOLEAN,
    acceptsGroupTutoring BOOLEAN,
    PRIMARY KEY (postId),
    FOREIGN KEY (ownerID) REFERENCES users(userId),
    FOREIGN KEY (subjectId) REFERENCES subject(subjectId)
);

--2.3) Create last set of tables

CREATE TABLE tutorSignUp(
    tutorPostId INT,
    studentUserId INT,
    FOREIGN KEY (tutorPostId) REFERENCES post(postId),
    FOREIGN KEY (studentUserId) REFERENCES students(userId)
);

CREATE TABLE tutorRating(
    tutorUserId INT,
    studentUserId INT,
    tutorPostId INT,
    rating INT,
    subject VARCHAR(32),
    comments VARCHAR(256),
    FOREIGN KEY (tutorUserId) REFERENCES tutors(userId),
    FOREIGN KEY (studentUserId) REFERENCES students(userId),
    FOREIGN KEY (tutorPostId) REFERENCES post(postId)
);

--\dt will show you all of the tables
--            List of relations
-- Schema |     Name      | Type  | Owner
----------+---------------+-------+--------
-- public | post          | table | ahardy
-- public | students      | table | ahardy
-- public | subject       | table | ahardy
-- public | tutorrating   | table | ahardy
-- public | tutors        | table | ahardy
-- public | tutorsignup   | table | ahardy
-- public | tutorsubjects | table | ahardy
-- public | users         | table | ahardy
--(8 rows)

--3.1) Insert data into tables

INSERT INTO users (userId, userName, email, salt, passHash, userType)
VALUES (1, 'joetest', 'joetest@gmail.com', 'asdf1234', 'asdfqwer12345678', 'student')
    ,(2, 'susantutor', 'susantutor@yahoo.com', '4321fdsa', '87654321fdsarewq', 'tutor')
    ,(3, 'steveadmin', 'steveadmin@gmail.com', 'trewq654321', 'asdfzxcvqwer12345678', 'admin')
    ,(4, 'sallygamemaker', 'sallygamemaker@gmail.com', '654321yuiop', '0987poiu', 'tutor')
    ,(5, 'jameskirk', 'captain@enterprise.com', 'star1234', 'rats4321', 'student');

INSERT INTO subject (subjectId, subjectName)
VALUES (1, 'Calculus')
    ,(2, 'Java')
    ,(3, 'Biology')
    ,(4, 'Game Design')
    ,(5, 'Computer and Information Science');

INSERT INTO tutors (userId, legalFirstName, legalLastName, bio, degrees, links, img, active, creationDate, avgRating)
VALUES (2, 'Susan', 'Test', 'I am an amazing tutor', 'Masters in Biology', 'www.google.com', 'https://i.imgur.com/IW0RAD8.jpg', TRUE, '2018-02-01 08:15:36', 4)
    ,(4, 'Sally', 'Game-Maker', 'I like games', 'PhD in video gaming', 'www.ea.com', 'http://downdetector.com/status/ea', TRUE, '2018-09-09 09:09:09', 3);

INSERT INTO students (userId, legalFirstName, legalLastName, bio, major, minor, img, active, creationDate)
VALUES (1, 'Joe', 'Test', 'I am a really cool student', 'CIS', 'Math', 'https://i.imgur.com/MU2dD8E.jpg', TRUE, '2018-02-01 09:14:28')
    ,(5, 'Jame', 'Kirk', 'I boldly go', 'CIS', 'Game Design', 'startrek.com', FALSE, '2233-03-22 00:00:00');

--3.2) Finish inserting data

INSERT INTO tutorSubjects (tutorUserId, subjectId)
VALUES (2, 3)
    ,(4, 5)
    ,(4, 4);

/*INSERT INTO tutorPost (tutorPostId, ownerId, subjectId, location, availability, rate, unit, createdTs, active, maxGroupSize, currentSignedUp, acceptsGroups)
VALUES (1, 2, 3, 'Starbucks in Belmont', '{"Monday":"Night","Tuesday":"Morning","Friday":"Afternoon"}', 20.50, 'dollars/hour', '2018-02-02 09:17:17', TRUE, 5, 2, TRUE)
    ,(2, 4, 4, 'Computers in NDNU Library', '{"Saturday":"All Day"}', 50, 'dollars/session', '2018-01-01 14:22:23', TRUE, 1, 0, FALSE);

INSERT INTO studentPost (studentPostId, ownerId, subjectId, location, availability, acceptsPaid, rate, unit, createdTs, active, acceptsGroupTutoring)
VALUES (1, 1, 5, 'NDNU Library', '{"Monday":"Morning","Wednesday":"Morning"}', FALSE, 0, 'dollars/hour', '2018-02-03 12:24:46', TRUE, FALSE)
    ,(2, 1, 4, 'Mac Lab', '{"Sunday":"All Day"}', TRUE, 20, 'dollars/session', '2017-12-01 08:10:23', FALSE, TRUE);*/

INSERT INTO post (postId, posterType, ownerId, subjectId, location, availability, acceptsPaid, rate, unit, createdTs, active, acceptsGroupTutoring)
VALUES (1, 'tutor', 2, 3, 'Starbucks in Belmont', '{"Monday":"Night","Tuesday":"Morning","Friday":"Afternoon"}', TRUE, 20.50, 'dollars/hour', '2018-02-02 09:17:17', TRUE, TRUE) --keep working here
    ,(2, 'tutor', 4, 4, 'Computers in NDNU Library', '{"Saturday":"All Day"}', TRUE, 50, 'dollars/session', '2018-01-01 14:22:23', TRUE, FALSE)
    ,(3, 'student', 1, 5, 'NDNU Library', '{"Monday":"Morning","Wednesday":"Morning"}', FALSE, 0, 'dollars/hour', '2018-02-03 12:24:46', TRUE, FALSE)
    ,(4, 'student', 1, 4, 'Mac Lab', '{"Sunday":"All Day"}', TRUE, 20, 'dollars/session', '2017-12-01 08:10:23', FALSE, TRUE);

INSERT INTO tutorSignUp (tutorPostId, studentUserId)
VALUES (1, 1)
    ,(1, 5);

INSERT INTO tutorRating (tutorUserId, studentUserId, tutorPostId, rating, subject, comments)
VALUES (2, 1, 1, 4, 3, 'Biology is hard but Susan helped me understand cell division!');




--4) Test query
SELECT
    u.userName
    ,s.subjectName
    ,tr.rating
    ,tr.comments
    ,u2.userName AS tutoredStudent
FROM users u

LEFT JOIN post p
ON u.userId = p.ownerId

LEFT JOIN subject s
ON p.subjectId = s.subjectId

LEFT JOIN tutorRating tr
ON p.postId = tr.tutorPostId

LEFT JOIN users u2
ON tr.studentUserId = u2.userId


WHERE u.userName = 'susantutor';
--expected output
--   username  | subjectname | rating |                           comments                            | tutoredstudent
-- ------------+-------------+--------+---------------------------------------------------------------+----------------
--  susantutor | Biology     |      4 | Biology is hard but Susan helped me understand cell division! | joetest
-- (1 row)