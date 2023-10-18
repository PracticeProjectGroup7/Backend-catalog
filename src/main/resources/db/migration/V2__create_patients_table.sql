CREATE TABLE patients (
is_active bit not null,
created_at datetime(6) not null,
modified_at datetime(6),
id binary(16) not null,
user_id binary(16),
blood_type VARCHAR(2) not null,
medical_condition varchar(255),
 PRIMARY KEY (id),
 FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);