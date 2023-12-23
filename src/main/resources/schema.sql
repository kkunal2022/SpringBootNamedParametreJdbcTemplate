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

    --INSERT INTO `users` (`user_name`, `user_pass`, `enable`) VALUES ('admin', '$2a$10$okLy2UqGmzjecYK.8zzOrOlYv7fd6/wx7/.MwanKyn9RupY7SCtum', 1);

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
	  profile_image BLOB
	);

	-- Stock Requisition Tables Creation with Foreign Relations & Normalization Starts Here
	DROP TABLE IF EXISTS stocks;
	CREATE TABLE IF NOT EXISTS stocks(
        id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
        username VARCHAR(100),
        password VARCHAR(100),
        department_requesting VARCHAR(100) DEFAULT NULL,
        stock_request_date DATETIME DEFAULT NULL,
        department_code VARCHAR(40) DEFAULT NULL,
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
        designated_person_approval_name VARCHAR(255) DEFAULT NULL,
        signature VARCHAR(300) DEFAULT NULL,
        date_of_confirmation DATETIME DEFAULT NULL,
        role VARCHAR(20)
	);

    DROP TABLE IF EXISTS stock_request;
    CREATE TABLE stock_request(
	  id BIGINT PRIMARY KEY,
	  -- id INT PRIMARY KEY,
	  stock_id BIGINT,
	  start_date DATE,
	  end_date DATE,
	  status VARCHAR(20),
	  department_code VARCHAR(40) DEFAULT NULL,
	  -- FOREIGN KEY (stock_id) REFERENCES stocks(id)
	  CONSTRAINT FOREIGN KEY (stock_id) REFERENCES stocks(id)
	);
