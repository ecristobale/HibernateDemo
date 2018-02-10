--
-- Select all data for the table `user`
--
SELECT * FROM hb_demo_schema.user;

--
-- Reset table `user`
--
truncate hb_demo_schema.user;

--
-- Set autogeneration of the index to 3000
--
ALTER TABLE hb_demo_schema.user AUTO_INCREMENT = 3000;