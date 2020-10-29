DROP TABLE IF EXISTS Account CASCADE;--OK
DROP TABLE IF EXISTS Video CASCADE;--OK
DROP TABLE IF EXISTS Comments CASCADE;--OK
DROP TABLE IF EXISTS Database CASCADE;--OK

-------------
---DOMAINS---
-------------
CREATE DOMAIN us_postal_code AS TEXT CHECK(VALUE ~ '^\d{5}$' OR VALUE ~ '^\d{5}-\d{4}$');
CREATE DOMAIN _STATUS CHAR(1) CHECK (value IN ( 'W' , 'C', 'R' ) );
CREATE DOMAIN _GENDER CHAR(1) CHECK (value IN ( 'F' , 'M' ) );
CREATE DOMAIN _CODE CHAR(2) CHECK (value IN ( 'MJ' , 'MN', 'SV' ) ); --Major, Minimum, Service
CREATE DOMAIN _PINTEGER AS int4 CHECK(VALUE > 0);
CREATE DOMAIN _PZEROINTEGER AS int4 CHECK(VALUE >= 0);
CREATE DOMAIN _YEARS AS int4 CHECK(VALUE >= 0 AND VALUE < 100);
CREATE DOMAIN _YEAR AS int4 CHECK(VALUE >= 1970);

------------
---TABLES---
------------
CREATE TABLE Account
(
        account_id INTEGER NOT NULL,
        subcount INTEGER NOT NULL,
        video CHAR(100) NOT NULL,
        history CHAR(100) NOT NULL,
        recommended CHAR(100) NOT NULL,
        subscriptions CHAR(256) NOT NULL,
        PRIMARY KEY (id)
);

CREATE TABLE Video
(
        account_id INTEGER NOT NULL,
        video_id INTEGER NOT NULL,
        views CHAR(64) NOT NULL,
        rating CHAR(3) NOT NULL,
        publication_date VARCHAR(10) NOT NULL,
        description CHAR(5000) NOT NULL,
        video_length INTEGER NOT NULL,
        video_link CHAR(1024) NOT NULL,
        title CHAR(100) NOT NULL,
        likes_dislikes INTEGER NOT NULL,
        tags CHAR(100) NOT NULL,
        publisher CHAR(100) NOT NULL,
        PRIMARY KEY (account_id, video_id)
);

CREATE TABLE Comments
(
        account_id INTEGER NOT NULL,
        video_id INTEGER NOT NULL,
        num_replies INTEGER NOT NULL,
        num_comments INTEGER NOT NULL,
        comment_replies CHAR(5000) NOT NULL,
        comment_content CHAR(5000) NOT NULL,
        comment_likes INTEGER NOT NULL,
        PRIMARY KEY (account_id, video_id)
);

CREATE TABLE Database
{
        account_id INTEGER NOT NULL,
        recommended CHAR(100) NOT NULL,
        subscription_count INTEGER NOT NULL,
        most_watched_video CHAR(100) NOT NULL,
        channels CHAR(100) NOT NULL,
        category CHAR(100) NOT NULL,
        FOREIGN KEY (account_id) REFERENCES Account (id)
};
---------------
---RELATIONS---

CREATE TABLE Manages
(
        adds INTEGER NOT NULL,
        deletes INTEGER NOT NULL,
        edits INTEGER NOT NULL,
        PRIMARY KEY (ownership_id),
        FOREIGN KEY (customer_id) REFERENCES Customer(id),
        FOREIGN KEY (car_vin) REFERENCES Car(vin)
);

----------------------------
-- INSERT DATA STATEMENTS --
----------------------------

COPY Account (
        account_id,
        subcount,
        video,
        history,
        recommended,
        subscriptions
)
FROM 'account.csv'
WITH DELIMITER ',';

COPY Video (
        account_id,
        video_id,
        views,
        rating,
        publication_date,
        description
        video_length,
        video_link,
        video_title,
        likes_dislikes,
        tags,
        publisher
)
FROM 'video.csv'
WITH DELIMITER ',';

COPY Comments (
        account_id,
        video_id,
        num_replies,
        num_comments,
        comment_replies,
        comment_content,
        comment_likes
)
FROM 'comments.csv'
WITH DELIMITER ',';

COPY Database (
        account_id,
        recommended,
        subscription_count,
        most_watched_video,
        channels,
        category
)
FROM 'database.csv'
WITH DELIMITER ',';

COPY Manages (
        adds,
        deletes,
        edits
)
FROM 'service_request.csv'
WITH DELIMITER ',';
