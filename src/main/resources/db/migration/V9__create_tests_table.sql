CREATE TABLE tests(
    test_id binary(16) PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())),
    patient_id binary(16) NOT NULL,
    test_date DATE NOT NULL,
    test_name TEXT NOT NULL,
    test_report TEXT,
    status VARCHAR(20) NOT NULL,
    is_active bit NOT NULL,
    created_at datetime DEFAULT CURRENT_TIMESTAMP,
    modified_at datetime ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patient(patientid) ON DELETE CASCADE
);