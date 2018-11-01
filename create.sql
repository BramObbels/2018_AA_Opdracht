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
 *   - group
 * 
 * - GROUPS
 *   - ID
 *   - name
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
 *
 * - TICKETS
 *   - ID
 *   - account ID
 *   - play ID
 *
 * /!\ Drop tables in reverse order of creation
 *
 */

DROP TABLE IF EXISTS tickets;
DROP TABLE IF EXISTS seats;
DROP TABLE IF EXISTS plays;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS groups;

CREATE TABLE groups(
    id INT,
    name VARCHAR(50),
    PRIMARY KEY(id)
);

CREATE TABLE accounts(
    id INT,
    name VARCHAR(50),
    groupId INT,
    PRIMARY KEY(id),
    FOREIGN KEY(groupId) REFERENCES groups(id)
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
    PRIMARY KEY(id),
    CONSTRAINT checkRank CHECK (rank >= 0), -- price = basic price + (rank * additional fee) defined for each play
    CONSTRAINT checkStatus CHECK (status >= 0), -- 0 = free, 1 = reserved, 2 = occupied
    CONSTRAINT checkRowColumn UNIQUE(rowNumber, columnNumber) -- row and column of a seat are unique
);

CREATE TABLE tickets(
    id INT,
    accountId INT,
    playId INT,
    seatId INT,
    PRIMARY KEY(id),
    FOREIGN KEY(accountId) REFERENCES accounts(id),
    FOREIGN KEY(playId) REFERENCES plays(id),
    FOREIGN KEY(seatId) REFERENCES seats(id)
);
