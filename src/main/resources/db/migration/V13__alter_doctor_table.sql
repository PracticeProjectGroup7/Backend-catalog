ALTER TABLE `healdb`.`doctor`
DROP FOREIGN KEY `doctor_ibfk_1`;

ALTER TABLE `healdb`.`doctor`
ADD CONSTRAINT `doctor_ibfk_1`
FOREIGN KEY (`userid`)
REFERENCES `healdb`.`useraccount` (`userid`)
ON DELETE CASCADE;
