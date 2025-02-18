DROP TABLE IF EXISTS visits;
DROP TABLE IF EXISTS doctors;
DROP TABLE IF EXISTS patients;

CREATE TABLE doctors (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         first_name VARCHAR(255) NOT NULL,
                         last_name VARCHAR(255) NOT NULL,
                         timezone VARCHAR(255) NOT NULL
);

CREATE TABLE patients (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          first_name VARCHAR(255) NOT NULL,
                          last_name VARCHAR(255) NOT NULL
);

CREATE TABLE visits (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        start_date_time TIMESTAMP NOT NULL,
                        end_date_time TIMESTAMP NOT NULL,
                        patient_id BIGINT NOT NULL,
                        doctor_id BIGINT NOT NULL,
                        CONSTRAINT fk_visits_patient FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
                        CONSTRAINT fk_visits_doctor FOREIGN KEY (doctor_id) REFERENCES doctors(id) ON DELETE CASCADE
);
