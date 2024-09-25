DROP TABLE IF EXISTS Request CASCADE;
DROP TABLE IF EXISTS Patient CASCADE;
DROP TABLE IF EXISTS Commentary CASCADE;
DROP TABLE IF EXISTS Doctor CASCADE;
DROP TABLE IF EXISTS Specialty CASCADE;

CREATE TABLE Patient
(
    id            BIGINT PRIMARY KEY AUTO_INCREMENT,
    full_name     varchar(30) NOT NULL,
    age           int         NOT NULL,
    policy_number varchar(11) NOT NULL UNIQUE,
    phone_number  varchar(11) NOT NULL
);

CREATE TABLE Specialty
(
    id   BIGINT PRIMARY KEY AUTO_INCREMENT,
    name varchar(20) NOT NULL UNIQUE
);

CREATE TABLE Doctor
(
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    full_name    varchar(30) NOT NULL,
    cabinet      varchar(3)  NOT NULL,
    specialty_id BIGINT         REFERENCES Specialty (id) ON DELETE SET NULL
);

CREATE TABLE Request
(
    id            BIGINT PRIMARY KEY AUTO_INCREMENT,
    patient_id    BIGINT  REFERENCES Patient (id) ON DELETE SET NULL,
    doctor_id     BIGINT  REFERENCES Doctor (id) ON DELETE SET NULL,
    desired_date  DATE NOT NULL,
    approved_date TIMESTAMP
);

CREATE TABLE Commentary
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    doctor_id   BIGINT REFERENCES Doctor (id) ON DELETE SET NULL,
    score       int NOT NULL,
    description varchar(200)
);