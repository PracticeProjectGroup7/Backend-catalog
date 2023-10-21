CREATE TABLE fees(
    fee_id binary(16) PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())),
    booking_id binary(16) NOT NULL,
    type VARCHAR(16) NOT NULL,
    price decimal(10,2) NOT NULL,
    is_active bit NOT NULL,
    created_at datetime DEFAULT CURRENT_TIMESTAMP,
    modified_at datetime ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (booking_id) REFERENCES bookings(booking_id) ON DELETE CASCADE
);