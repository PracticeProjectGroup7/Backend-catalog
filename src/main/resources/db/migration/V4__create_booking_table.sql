CREATE TABLE bookings(
    id binary(16) PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())),
    appointment_id binary(16),
    test_id binary(16),
    service_id binary(16) NOT NULL,
    patient_id binary(16) NOT NULL,
    bill_number SERIAL NOT NULL,
    bill_status varchar(16),
    amount_paid decimal(10,2),
    paid_at datetime,
    gst int NOT NULL,
    start_time datetime NOT NULL,
    end_time datetime NOT NULL,
    is_active bit NOT NULL,
    created_at datetime DEFAULT CURRENT_TIMESTAMP,
    modified_at datetime ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (appointment_id) REFERENCES appointments(id) ON DELETE CASCADE,
    FOREIGN KEY (test_id) REFERENCES tests(id) ON DELETE CASCADE
);