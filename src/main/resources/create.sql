CREATE TABLE location (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          name VARCHAR(255) NOT NULL,
                          region VARCHAR(255),
                          country VARCHAR(255),
                          local_date_time DATETIME
);

CREATE TABLE weather (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         temperature DOUBLE NOT NULL,
                         wind_speed DOUBLE NOT NULL,
                         pressure DOUBLE NOT NULL,
                         humidity DOUBLE NOT NULL,
                         conditions VARCHAR(255) NOT NULL,
                         timestamp DATETIME NOT NULL,
                         location_id BIGINT,
                         FOREIGN KEY (location_id) REFERENCES location(id)
);