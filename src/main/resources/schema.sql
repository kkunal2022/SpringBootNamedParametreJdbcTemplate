	DROP TABLE IF EXISTS `users`;
    CREATE TABLE `users` (
      `user_name` varchar(30) NOT NULL,
      `user_pass` varchar(255) NOT NULL,
      -- `enable` tinyint(1) NOT NULL DEFAULT '1',
      enable TINYINT NOT NULL DEFAULT 1,
      PRIMARY KEY (`user_name`)
    );

    DROP TABLE IF EXISTS `user_role`;
    CREATE TABLE `user_role` (
      `user_name` varchar(30) NOT NULL,
      `user_role` varchar(15) NOT NULL,
      FOREIGN KEY (`user_name`) REFERENCES `users` (`user_name`)
    );

    --INSERT INTO `users` (`user_name`, `user_pass`, `enable`) VALUES
	--('admin', '$2a$10$okLy2UqGmzjecYK.8zzOrOlYv7fd6/wx7/.MwanKyn9RupY7SCtum', 1);

	--INSERT INTO `user_role` (`user_name`, `user_role`) VALUES
	--('admin', 'ROLE_USER'),
	--('admin', 'ROLE_ADMIN');

	DROP TABLE IF EXISTS PURCHASEREQUESTNEW;
	CREATE TABLE PURCHASEREQUESTNEW (
	  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
	  purchase_date Date NOT NULL, 
	  requesting_department varchar(255) NOT NULL, 
	  department_code int NOT NULL, 
	  request_reason varchar(255) NOT NULL, 
	  item_number int NOT NULL, 
	  item_description varchar(255) NOT NULL, 
	  unit_price int NOT NULL, 
	  quantity int, 
	  estimated_value int NOT NULL, 
	  email_address varchar(255), 
	  name varchar(255), 
	  type varchar(255), 
	  profile_image BLOB, 
	  primary key(id)
	);

	-- Stock Requisition Tables Creation with Foreign Relations & Normalization Starts Here
	DROP TABLE IF EXISTS stocks;
	CREATE TABLE IF NOT EXISTS stocks(
        id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
        username VARCHAR(100),
        password VARCHAR(100),
        department_requesting VARCHAR(100) DEFAULT NULL,
        stock_request_date DATETIME DEFAULT NULL,
        department_code int DEFAULT NULL,
        purpose_of_issue VARCHAR(200) DEFAULT NULL,
        stock_date DATETIME DEFAULT NULL,
        item_no int DEFAULT NULL,
        item_reference_no VARCHAR(200) DEFAULT NULL,
        item_description VARCHAR(200) DEFAULT NULL,
        date_of_previous_issue DATETIME DEFAULT NULL,
        previous_issue_quantity int DEFAULT NULL,
        quantity_requested int DEFAULT NULL,
        department_initiated_by VARCHAR(255) DEFAULT NULL,
        department_authorised_by VARCHAR(255) DEFAULT NULL,
        department_confirmed_by VARCHAR(255) DEFAULT NULL,
        department_received_by VARCHAR(255) DEFAULT NULL,
        -- name field on pdf is the name of designated person for approval
        designated_person_approval_name VARCHAR(255) DEFAULT NULL,
        signature VARCHAR(300) DEFAULT NULL,
        date_of_confirmation DATETIME DEFAULT NULL,
        role VARCHAR(20)
	);

    DROP TABLE IF EXISTS stock_requests;
	CREATE TABLE IF NOT EXISTS stock_requests (
        id BIGINT PRIMARY KEY AUTO_INCREMENT,
    	stock_id INT,
    	reason VARCHAR(255),
    	start_date DATE,
    	end_date DATE,
    	approved BOOLEAN,
    	FOREIGN KEY (stock_id) REFERENCES stocks(id) ON DELETE CASCADE
    );
    
    CREATE TABLE stock_request (
	  id INT PRIMARY KEY,
	  stock_id INT,
	  start_date DATE,
	  end_date DATE,
	  status VARCHAR(20),
	  FOREIGN KEY (stock_id) REFERENCES stocks(id)
	);

    --insert into employees (id ,username,password, department_requesting, stock_request_date ,department_code ,purpose_of_issue ,stock_date ,item_no ,item_reference_no ,
	--item_description ,date_of_previous_issue ,previous_issue_quantity,quantity_requested ,department_initiated_by,department_authorised_by ,
	--department_confirmed_by,department_received_by,designated_person_approval_name ,signature ,date_of_confirmation ,role)
	--values (1,'stocks', 'stocks','Kunal', '2023-11-17 10:34:23.55', 1,'Test Stocks' ,'2023-11-17 10:34:23.55',11 ,'12','Kunal Test Stocks', '2023-11-17 10:34:23.55',
	--98,99 ,'EMPLOYEE','MANAGER' ,'ADMIN','HRMS','Kumar Kunal' ,'KKKKKK' ,'2023-11-17 10:34:23.55' ,'ROLE_EMPLOYEE');

	--insert into employees (id ,username, password,department_requesting, stock_request_date ,department_code ,purpose_of_issue ,stock_date ,item_no ,item_reference_no ,
	--item_description ,date_of_previous_issue ,previous_issue_quantity,quantity_requested ,department_initiated_by,department_authorised_by ,
	--department_confirmed_by,department_received_by,designated_person_approval_name ,signature ,date_of_confirmation ,role)
	--values (2,'manager', 'manager','Kunal', '2023-11-17 10:34:23.55', 1,'Test MANAGER' ,'2023-11-17 10:34:23.55',11 ,'12','Kunal Test MANAGER', '2023-11-17 10:34:23.55',
	--98,99 ,'MANAGER','MANAGER' ,'MANAGER','HRMS MANAGER','Kumar Kunal' ,'KKKKKK' ,'2023-11-17 10:34:23.55' ,'ROLE_MANAGER');

	--insert into employees (id ,username,password,department_requesting, stock_request_date ,department_code ,purpose_of_issue ,stock_date ,item_no ,item_reference_no ,
	--item_description ,date_of_previous_issue ,previous_issue_quantity,quantity_requested ,department_initiated_by,department_authorised_by ,
	--department_confirmed_by,department_received_by,designated_person_approval_name ,signature ,date_of_confirmation ,role)
	--values (3,'admin','admin','Kunal', '2023-11-17 10:34:23.55', 1,'Test ADMIN' ,'2023-11-17 10:34:23.55',11 ,'12','Kunal Test ADMIN', '2023-11-17 10:34:23.55',
	--98,99 ,'ADMIN','ADMIN' ,'ADMIN','HRMS ADMIN','Kumar Kunal' ,'KKKKKK' ,'2023-11-17 10:34:23.55' ,'ROLE_ADMIN');
	
	-- Stock Requisition Tables Creation with Foreign Relations & Normalization Ends Here
	
	-- DROP TABLE IF EXISTS Users;
	-- CREATE TABLE Users(
	--  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	--  first_name VARCHAR(50) NOT NULL,
	--  last_name VARCHAR(50) NOT NULL,
	--  email VARCHAR(100) NOT NULL,
	--  password VARCHAR(255) DEFAULT NULL --CONSTRAINT UQ_Users_Email UNIQUE (email)
	--  );

	-- DROP TABLE IF EXISTS Roles;
	-- CREATE TABLE Roles (
	--   id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	--   name VARCHAR(50) NOT NULL,
	--   permission VARCHAR(255) NOT NULL,
	--   CONSTRAINT UQ_Roles_Name UNIQUE (name)
	-- );

    -- DROP TABLE IF EXISTS UserRoles;
	-- CREATE TABLE UserRoles (
	--   id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	--   user_id BIGINT NOT NULL,
	--   role_id BIGINT NOT NULL,
	--   FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE ON UPDATE CASCADE,
	--   FOREIGN KEY (role_id) REFERENCES Roles (id) ON DELETE RESTRICT ON UPDATE CASCADE,
	--   CONSTRAINT UQ_UserRoles_User_Id UNIQUE (user_id)
	-- );

	--INSERT INTO ROLES (name, permission)
	--VALUES
	--  (
	--    'ROLE_USER', 'READ:USER,READ:CUSTOMER,READ:USER,UPDATE:USER,READ:USER,UPDATE:CUSTOMER'
	--  ),
	--  (
	--    'ROLE_MANAGER', 'READ:USER,READ:CUSTOMER,UPDATE:USER,UPDATE:CUSTOMER,READ:USER,UPDATE:USER,READ:CUSTOMER'
	--  ),
	--  (
	--    'ROLE_ADMIN', 'READ:USER,UPDATE:USER,READ:USER,UPDATE:CUSTOMER,READ:USER,UPDATE:USER,READ:CUSTOMER,UPDATE:USER,DELETE:USER'
	--  ),
	--  (
	--    'ROLE_SYSTEM', 'READ:USER,READ:CUSTOMER,DELETE:USER,DELETE:CUSTOMER,READ:USER,DELETE:USER,DELETE:CUSTOMER,UPDATE:USER,UPDATE:CUSTOMER'
	--  );
