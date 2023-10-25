CREATE TABLE services (
    `servicesid` BINARY(16) PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())),
    `doctorid` BINARY(16) DEFAULT NULL,
    `type` varchar(100) DEFAULT NULL,
    `name` varchar(200) DEFAULT NULL,
    `description` text(500) DEFAULT NULL,
    `duration` varchar(200) DEFAULT NULL,
    `estimated_price` INTEGER DEFAULT NULL,
    `is_active` tinyint(1) DEFAULT '1',
    `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
    `modified_at` datetime ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (doctorid) REFERENCES doctor(doctorid)
);