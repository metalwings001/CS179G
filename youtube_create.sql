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
        PRIMARY KEY (account_id)
};
---------------
---RELATIONS---



CREATE TABLE Owns
(
        ownership_id INTEGER NOT NULL,
        customer_id INTEGER NOT NULL,
        car_vin VARCHAR(16) NOT NULL,
        PRIMARY KEY (ownership_id),
        FOREIGN KEY (customer_id) REFERENCES Customer(id),
        FOREIGN KEY (car_vin) REFERENCES Car(vin)
);

CREATE TABLE Service_Request
(
        rid INTEGER NOT NULL,
        customer_id INTEGER NOT NULL,
        car_vin VARCHAR(16) NOT NULL,
        date DATE NOT NULL,
        odometer _PINTEGER NOT NULL,
        complain TEXT,
        PRIMARY KEY (rid),
        FOREIGN KEY (customer_id) REFERENCES Customer(id),
        FOREIGN KEY (car_vin) REFERENCES Car(vin)
);

CREATE TABLE Closed_Request
(
        wid INTEGER NOT NULL,
        rid INTEGER NOT NULL,
        mid INTEGER NOT NULL,
        date DATE NOT NULL,
        comment TEXT,
        bill _PINTEGER NOT NULL,
        PRIMARY KEY (wid),
        FOREIGN KEY (rid) REFERENCES Service_Request(rid),
        FOREIGN KEY (mid) REFERENCES Mechanic(id)
);

----------------------------
-- INSERT DATA STATEMENTS --
----------------------------

COPY Customer (
        id,
        fname,
        lname,
        phone,
        address
)
FROM 'customer.csv'
WITH DELIMITER ',';

COPY Mechanic (
        id,
        fname,
        lname,
        experience
)
FROM 'mechanic.csv'
WITH DELIMITER ',';

COPY Car (
        vin,
        make,
        model,
        year
)
FROM 'car.csv'
WITH DELIMITER ',';

COPY Owns (
        ownership_id,
        customer_id,
        car_vin
)
FROM 'owns.csv'
WITH DELIMITER ',';

COPY Service_Request (
        rid,
        customer_id,
        car_vin,
        date,
        odometer,
        complain
)
FROM 'service_request.csv'
WITH DELIMITER ',';

COPY Closed_Request (
        wid,
        rid,
        mid,
        date,
        comment,
        bill
^_)
FROM 'closed_request.csv'
WITH DELIMITER ',';
