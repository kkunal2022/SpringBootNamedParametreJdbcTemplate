	DROP TABLE IF EXISTS Users;
	CREATE TABLE Users(
	  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
	  first_name VARCHAR(50) NOT NULL, 
	  last_name VARCHAR(50) NOT NULL, 
	  email VARCHAR(100) NOT NULL, 
	  password VARCHAR(255) DEFAULT NULL --CONSTRAINT UQ_Users_Email UNIQUE (email)
	  );
	
	DROP TABLE IF EXISTS Roles;
	CREATE TABLE Roles (
	  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
	  name VARCHAR(50) NOT NULL, 
	  permission VARCHAR(255) NOT NULL, 
	  CONSTRAINT UQ_Roles_Name UNIQUE (name)
	);
	
	-- DROP TABLE IF EXISTS UserRoles;
	CREATE TABLE UserRoles (
	  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
	  user_id BIGINT NOT NULL, 
	  role_id BIGINT NOT NULL, 
	  FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE ON UPDATE CASCADE, 
	  FOREIGN KEY (role_id) REFERENCES Roles (id) ON DELETE RESTRICT ON UPDATE CASCADE, 
	  CONSTRAINT UQ_UserRoles_User_Id UNIQUE (user_id)
	);
	
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
	
	DROP TABLE IF EXISTS employees;
	CREATE TABLE IF NOT EXISTS employees(
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

    DROP TABLE IF EXISTS leave_requests;
	CREATE TABLE IF NOT EXISTS leave_requests (
        id BIGINT PRIMARY KEY AUTO_INCREMENT,
    	employee_id INT,
    	reason VARCHAR(255),
    	start_date DATE,
    	end_date DATE,
    	approved BOOLEAN,
    	FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE
    );
    
    CREATE TABLE leave_request (
	  id INT PRIMARY KEY,
	  employee_id INT,
	  start_date DATE,
	  end_date DATE,
	  status VARCHAR(20),
	  FOREIGN KEY (employee_id) REFERENCES employees(id)
	);

    --insert into employees (id ,username,password, department_requesting, stock_request_date ,department_code ,purpose_of_issue ,stock_date ,item_no ,item_reference_no ,
	--item_description ,date_of_previous_issue ,previous_issue_quantity,quantity_requested ,department_initiated_by,department_authorised_by ,
	--department_confirmed_by,department_received_by,designated_person_approval_name ,signature ,date_of_confirmation ,role)
	--values (1,'employee', 'employee','Kunal', '2023-11-17 10:34:23.55', 1,'Test Employee' ,'2023-11-17 10:34:23.55',11 ,'12','Kunal Test Employee', '2023-11-17 10:34:23.55',
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
	
	
	INSERT INTO ROLES (name, permission) 
	VALUES 
	  (
	    'ROLE_USER', 'READ:USER,READ:CUSTOMER,READ:USER,UPDATE:USER,READ:USER,UPDATE:CUSTOMER'
	  ), 
	  (
	    'ROLE_MANAGER', 'READ:USER,READ:CUSTOMER,UPDATE:USER,UPDATE:CUSTOMER,READ:USER,UPDATE:USER,READ:CUSTOMER'
	  ), 
	  (
	    'ROLE_ADMIN', 'READ:USER,UPDATE:USER,READ:USER,UPDATE:CUSTOMER,READ:USER,UPDATE:USER,READ:CUSTOMER,UPDATE:USER,DELETE:USER'
	  ), 
	  (
	    'ROLE_SYSTEM', 'READ:USER,READ:CUSTOMER,DELETE:USER,DELETE:CUSTOMER,READ:USER,DELETE:USER,DELETE:CUSTOMER,UPDATE:USER,UPDATE:CUSTOMER'
	  );
