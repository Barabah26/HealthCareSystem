INSERT INTO doctors (first_name, last_name, timezone) VALUES
                                                         ('Pavlo', 'Barabah', 'America/New_York'),
                                                         ('Stepan', 'Popov', 'Europe/London');

INSERT INTO patients (first_name, last_name) VALUES
                                                ('Anna', 'Johnson'),
                                                ('Emily', 'Davis');

INSERT INTO visits (start_date_time, end_date_time, patient_id, doctor_id) VALUES
                                                                              ('2024-02-17 10:00:00', '2024-02-17 10:30:00', 1, 1),
                                                                              ('2024-02-17 11:00:00', '2024-02-17 11:30:00', 2, 2);
