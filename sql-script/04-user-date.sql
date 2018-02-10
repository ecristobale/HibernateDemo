--
-- Adding new field of type DATETIME for the table `user`
--
ALTER TABLE `hb_demo_schema`.`user` 
ADD COLUMN `date_of_birth` DATETIME NULL AFTER `last_name`;