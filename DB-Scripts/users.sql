DROP TABLE IF EXISTS users;

CREATE TABLE users (
	user_id SERIAL PRIMARY KEY,
	first_name VARCHAR(50) NOT NULL,
	last_name VARCHAR(50) NOT NULL,
	username VARCHAR(100) NOT NULL,
	password VARCHAR(100) NOT NULL,
	email VARCHAR(20) NOT NULL,
	user_role VARCHAR(50) NOT NULL
);

-- ====== INSERT

INSERT INTO users (
	first_name, last_name, username, password, email, user_role
) VALUES
	('John', 'Doe', 'JohnD', 'John123', 'JohnDoe@gmail.com', 'finance manager'),
	('Jane', 'Doe', 'JaneD', 'Jane123', 'JaneDoe@gmail.com', 'employee'),
	('David', 'Good', 'DavidG', 'David123', 'DvaidGood@gmail.com', 'employee');

-- ====== QUERYING 

SELECT *
FROM "ERS_project".users;

-- 

SELECT *
FROM "ERS_project".users
WHERE 
	username = 'JohnD' 
AND 
	password = 'John123';
	
--

Select username, email
FROM "ERS_project".users
WHERE
	username = 'JohnD'
OR
	email = 'JohnDoe@gmail.com'