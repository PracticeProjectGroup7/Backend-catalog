CREATE TABLE patient (
     `patientid` BINARY(16) PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())),
     `userid` BINARY(16) NOT NULL,
     `bloodgroup` CHAR(4) NULL,
     `medicalcondition` LONGTEXT NULL,
     `is_active` tinyint(1) DEFAULT '1',
     `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
     `modified_at` datetime ON UPDATE CURRENT_TIMESTAMP,
     FOREIGN KEY (userid) REFERENCES useraccount(userid) ON DELETE CASCADE
);