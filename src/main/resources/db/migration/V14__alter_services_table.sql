ALTER TABLE `healdb`.`services`
DROP FOREIGN KEY `services_ibfk_1`;

ALTER TABLE `healdb`.`services`
ADD CONSTRAINT `services_ibfk_1`
FOREIGN KEY (`doctorid`)
REFERENCES `healdb`.`doctor` (`doctorid`)
ON DELETE CASCADE;
