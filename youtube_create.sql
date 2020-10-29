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
)
-- INSERT --                                                  168,1         98%
