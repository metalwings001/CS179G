DROP TABLE IF EXISTS Account CASCADE;--OK
DROP TABLE IF EXISTS Video CASCADE;--OK
DROP TABLE IF EXISTS Comments CASCADE;--OK
DROP TABLE IF EXISTS Manages CASCADE;--OK

------------
---TABLES---
------------
CREATE TABLE Account
(
        account_id INTEGER NOT NULL,
        subcount INTEGER NOT NULL,
        num_videos INTEGER NOT NULL,
        username CHAR(64) NOT NULL,
        password CHAR(64) NOT NULL,
        PRIMARY KEY (account_id)
);

CREATE TABLE Video
(
        video_id INTEGER NOT NULL,
        account_id INTEGER NOT NULL,
        num_comments CHAR(32) NOT NULL, 
        publication_date CHAR(64) NOT NULL,
        description CHAR(5000) NOT NULL,
        views INTEGER NOT NULL,
        video_title CHAR(100) NOT NULL,
        likes INTEGER NOT NULL,
        dislikes INTEGER NOT NULL,
        video_length INTEGER NOT NULL,
        tags CHAR(128),
        PRIMARY KEY (video_id),
        FOREIGN KEY (account_id) REFERENCES Account(account_id)
);

CREATE TABLE Comments
(
        comment_id INTEGER NOT NULL,
        account_id INTEGER NOT NULL,
        video_id INTEGER NOT NULL,
        comment_content CHAR(5000) NOT NULL,
        PRIMARY KEY (comment_id),
        FOREIGN KEY (account_id) REFERENCES Account(account_id),
        FOREIGN KEY (video_id) REFERENCES Video(video_id)
);

---------------
---RELATIONS---

CREATE TABLE Manages
(
        account_id INTEGER NOT NULL,
        video_id INTEGER NOT NULL,
        comment_id INTEGER NOT NULL,
        adds INTEGER NOT NULL,
        deletes INTEGER NOT NULL,
        FOREIGN KEY (account_id) REFERENCES Account(account_id),
        FOREIGN KEY (video_id) REFERENCES Video(video_id),
        FOREIGN KEY (comment_id) REFERENCES Comments(comment_id)
);

----------------------------
-- INSERT DATA STATEMENTS --
----------------------------

COPY Account (
        account_id,
        subcount,
        num_videos,
        username,
        password
)
FROM 'C:\Users\Justin\Desktop\data\account.csv'
WITH DELIMITER ',';

COPY Video (
        video_id,
        account_id,
        num_comments,
        publication_date,
        description,
        views,
        video_title,
        likes,
        dislikes,
        video_length,
        tags
)
FROM 'C:\Users\Justin\Desktop\data\video.csv'
WITH DELIMITER ',';

COPY Comments (
        comment_id,
        account_id,
        video_id,
        comment_content
)
FROM 'C:\Users\Justin\Desktop\data\comments.csv'
WITH DELIMITER ',';

COPY Manages (
        account_id,
        video_id,
        comment_id,
        adds,
        deletes
)
FROM 'C:\Users\Justin\Desktop\data\manages.csv'
WITH DELIMITER ',';
