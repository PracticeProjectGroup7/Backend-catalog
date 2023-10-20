CREATE TABLE appointments(
    id binary(16) PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())),
    patient_id binary(16) NOT NULL,
    diagnosis TEXT,
    prescription TEXT,
    is_active bit NOT NULL,
    created_at datetime DEFAULT CURRENT_TIMESTAMP,
    modified_at datetime ON UPDATE CURRENT_TIMESTAMP
);