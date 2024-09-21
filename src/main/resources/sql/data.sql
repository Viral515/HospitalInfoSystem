--Insert patients--

INSERT INTO Patient(full_name, age, policy_number, phone_number)
VALUES ('Ivan Ivanov', 22, 11111111111, 89131111111);
INSERT INTO Patient(full_name, age, policy_number, phone_number)
VALUES ('Petr Petrov', 33, 22222222222, 89132222222);

--Insert specialties--

INSERT INTO Specialty(name)
VALUES ('Diagnost');
INSERT INTO Specialty(name)
VALUES ('Surgeon');

--Insert doctors--

INSERT INTO Doctor(full_name, cabinet, specialty_id)
VALUES ('Gregory House', 302, 1);
INSERT INTO Doctor(full_name, cabinet, specialty_id)
VALUES ('Shaun Murphy', 203, 2);

--Insert commentaries--

INSERT INTO Commentary(doctor_id, score, description)
VALUES (1, 5, 'Good doctor, xd.');
INSERT INTO Commentary(doctor_id, score, description)
VALUES (2, 4, 'Nice doctor.');

--Insert requests--

INSERT INTO Request(patient_id, doctor_id, desired_date)
VALUES (1, 1, '2024-10-21');
INSERT INTO Request(patient_id, doctor_id, desired_date)
VALUES (1, 2, '2024-10-25');
INSERT INTO Request(patient_id, doctor_id, desired_date)
VALUES (2, 1, '2024-10-26');