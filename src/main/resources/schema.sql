DROP TABLE IF EXISTS visits CASCADE;
DROP TABLE IF EXISTS doctors CASCADE;
DROP TABLE IF EXISTS patients CASCADE;

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
                        FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
                        FOREIGN KEY (doctor_id) REFERENCES doctors(id) ON DELETE CASCADE
);
