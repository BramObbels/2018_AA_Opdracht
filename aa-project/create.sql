/**
 * Author:  Dylan Van Assche
 * Created: Nov 1, 2018
 * SQL tables for AA project 2018
 * See https://github.com/BramObbels/2018_AA_Opdracht/issues/1
 * Create a SQL database with the following tables:
 * /!\ VARCHAR is compatible with MariaDB and Oracle
 * CREATE TABLE syntax: https://mariadb.com/kb/en/library/create-table/
 *
 * JEE Security
 * - ACCOUNTS
 *   - ID
 *   - name
 * 
 * - GROUPS
 *   - ID
 *   - name
 *   - accountId
 *
 * JEE Application
 * - PLAYS
 *   - ID
 *   - date
 *   - name
 *
 * - SEATS
 *   - ID
 *   - row
 *   - column
 *   - price
 *   - rank
 *   - state (free, reserved, occupied)
 *   - playId
 *
 * - TICKETS
 *   - ID
 *   - account ID
 *   - play ID
 *   - valid
 *   - buyer
 *
 * /!\ Drop tables in reverse order of creation
 *
 */

DROP TABLE IF EXISTS tickets;
DROP TABLE IF EXISTS seats;
DROP TABLE IF EXISTS plays;
DROP TABLE IF EXISTS groups;
DROP TABLE IF EXISTS accounts;

CREATE TABLE accounts(
    id INT,
    username VARCHAR(50),
    password VARCHAR(50),
    PRIMARY KEY(id)
);

CREATE TABLE groups(
    id INT,
    username VARCHAR(50),
    groupname VARCHAR(50),
    PRIMARY KEY(id)
);
    
CREATE TABLE plays(
    id INT,
    name VARCHAR(50),
    date DATE,
    basicPrice FLOAT,
    rankFee FLOAT,
    PRIMARY KEY(id)
);

CREATE TABLE seats(
    id INT,
    rowNumber INT,
    columnNumber INT,
    rank INT,
    status INT,
    playId INT,
    PRIMARY KEY(id),
    FOREIGN KEY(playId) REFERENCES plays(id),
    CONSTRAINT checkRank CHECK (rank >= 0), -- price = basic price + (rank * additional fee) defined for each play
    CONSTRAINT checkStatus CHECK (status >= 0), -- 0 = free, 1 = reserved, 2 = occupied
    CONSTRAINT checkRowColumn UNIQUE(rowNumber, columnNumber, playId) -- row and column of a seat are unique
);

CREATE TABLE tickets(
    id BIGINT,
    accountId INT,
    playId INT,
    seatId INT,
    valid INT,
    buyer VARCHAR(50),
    CONSTRAINT checkValid CHECK (valid = 0 OR valid = 1),
    PRIMARY KEY(id),
    FOREIGN KEY(accountId) REFERENCES accounts(id),
    FOREIGN KEY(playId) REFERENCES plays(id),
    FOREIGN KEY(seatId) REFERENCES seats(id)
);

INSERT INTO accounts VALUES(1, 'a', '123');
INSERT INTO accounts VALUES(2, 'b', '123');
INSERT INTO accounts VALUES(3, 'c', '123');
INSERT INTO groups VALUES(1,'a', 'members');
INSERT INTO groups VALUES(2,'b', 'public');
INSERT INTO groups VALUES(3,'c', 'administrators');
INSERT INTO plays VALUES(1, 'Sneeuwitje', '2018-04-19 13:08:22.0', 5.0, 2.0);
INSERT INTO plays VALUES(2, 'Girl', '2018-05-19 13:08:22.0', 6.0, 3.0);
INSERT INTO seats VALUES(1, 0, 1, 0, 2, 2);
INSERT INTO seats VALUES(2, 0, 2, 0, 0, 2);
INSERT INTO seats VALUES(3, 1, 0, 1, 0, 2);
INSERT INTO seats VALUES(4, 1, 1, 0, 2, 2);
INSERT INTO seats VALUES(5, 1, 2, 2, 2, 2);
INSERT INTO seats VALUES(6, 2, 0, 0, 1, 2);
INSERT INTO seats VALUES(7, 2, 1, 0, 1, 2);
INSERT INTO seats VALUES(8, 2, 2, 3, 1, 2);
INSERT INTO seats VALUES(9, 0, 0, 1, 1, 1);
INSERT INTO seats VALUES(10, 0, 1, 0, 2, 1);
INSERT INTO seats VALUES(11, 0, 2, 0, 0, 1);
INSERT INTO seats VALUES(12, 1, 0, 1, 0, 1);
INSERT INTO seats VALUES(13, 0, 0, 1, 1, 2);
INSERT INTO tickets VALUES(1, 1, 1, 1, 1, 'Jefke');
INSERT INTO tickets VALUES(2, 2, 2, 1, 1, 'Marie');
