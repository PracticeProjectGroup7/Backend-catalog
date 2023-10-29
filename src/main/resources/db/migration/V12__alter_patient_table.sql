ALTER TABLE `healdb`.`patient`
DROP FOREIGN KEY `patient_ibfk_1`;

ALTER TABLE `healdb`.`patient`
ADD CONSTRAINT `patient_ibfk_1`
FOREIGN KEY (`userid`)
REFERENCES `healdb`.`useraccount` (`userid`)
ON DELETE CASCADE;
