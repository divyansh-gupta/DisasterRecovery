SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS Responder;
DROP TABLE IF EXISTS Request;
DROP TABLE IF EXISTS Location;
DROP TABLE IF EXISTS Item;
DROP TABLE IF EXISTS Need;
DROP TABLE IF EXISTS Message;

CREATE TABLE Responder (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    responder_name VARCHAR(100) NOT NULL,
    image VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    location_id INT,
    FOREIGN KEY (location_id) REFERENCES Location(ID)
);

CREATE TABLE Request (
    ID INT PRIMARY KEY NOT NULL,
    status ENUM('REQUESTED', 'IN_PROGRESS', 'COMPLETED') NOT NULL,
    from_location_id INT NOT NULL,
    to_location_id INT NOT NULL,
    FOREIGN KEY (from_location_id) REFERENCES Location(ID),
    FOREIGN KEY (to_location_id) REFERENCES Location(ID)
);

CREATE TABLE Need (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    item_id INT NOT NULL,
    request_id INT NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (item_id) REFERENCES Item(ID),
    FOREIGN KEY (request_id) REFERENCES Request(ID)
);

CREATE TABLE Location (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    location_name VARCHAR(100) NOT NULL,
    latitude DECIMAL(13,10) NOT NULL,
    longitude DECIMAL(13,10) NOT NULL,
    triggered BOOLEAN NOT NULL DEFAULT FALSE,
    emergency_description VARCHAR(500)
);

CREATE TABLE Item (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    location_id INT NOT NULL,
    item_type ENUM('WATER', 'CANNED_GOODS', 'BLANKETS', 'SHELTER', 'EMERGENCY_KITS', 'USD') NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (location_id) REFERENCES Location(ID)
);

CREATE TABLE Message (
    ID INT PRIMARY KEY NOT NULL,
    time_stamp DATETIME NOT NULL,
    sender_location INT NOT NULL,
    reciever_location INT NOT NULL,
    description VARCHAR(500) NOT NULL,
    FOREIGN KEY (sender_location) REFERENCES Location(ID),
    FOREIGN KEY (reciever_location) REFERENCES Location(ID)
);

-- Locations
INSERT INTO Location (location_name, latitude, longitude, triggered) VALUES
('Blacksburg, VA 24060', 37.2724841, -80.4326521, false), ('Richmond, VA 23173', 37.5745428, -77.5433122000, false),
('Roanoke, VA 24001', 37.27, -79.94, false);

-- Responders
INSERT INTO Responder (username, email, responder_name, image, password, location_id) VALUES
('mr_root', 'divyg@vt.edu', 'Responder1', 'defaultResponderPhoto.png', 'password', 1),
('mr_root2', 'bhaanuk5@vt.edu', 'Responder2', 'defaultResponderPhoto.png', 'password', 1),
('mr_root3', 'bhaanukaul@gmail.com', 'Responder3', 'defaultResponderPhoto.png', 'password', 1);

-- Resquests
INSERT INTO Request (ID, status, from_location_id, to_location_id) VALUES
(1, 'REQUESTED', 1, 2), (2, 'IN_PROGRESS', 1, 2), (3, 'COMPLETED', 1, 2);

-- Items
INSERT INTO Item (location_id, item_type, quantity) VALUES
(1, 'WATER', 5), (1, 'CANNED_GOODS', 5), (1, 'BLANKETS', 5), (1, 'USD', 5), (1, 'SHELTER', 5), (1, 'EMERGENCY_KITS', 5),
(2, 'WATER', 5), (2, 'CANNED_GOODS', 5), (2, 'BLANKETS', 5), (2, 'USD', 5), (2, 'SHELTER', 5), (2, 'EMERGENCY_KITS', 5),
(3, 'WATER', 5), (3, 'CANNED_GOODS', 5), (3, 'BLANKETS', 5), (3, 'USD', 5), (3, 'SHELTER', 5), (3, 'EMERGENCY_KITS', 5);

SET FOREIGN_KEY_CHECKS = 1;