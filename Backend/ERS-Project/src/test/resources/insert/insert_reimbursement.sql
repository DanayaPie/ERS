INSERT INTO reimbursement (
	amount,
	type,
	description,
	author_id
)
VALUES
	('499.99', 'travel', 'plane ticket', '2'),
	('180.00', 'food', 'food expences though out 2 days trip', '3');

--
INSERT INTO reimbursement (
	amount,
	submitted_time,
	type,
	description,
	receipt,
	author_id
)
VALUES
	('800.0', CURRENT_TIMESTAMP, 'other', 'drugs', 'reciept', 3);



INSERT INTO users (
	first_name, last_name, username, password, email, user_role
) VALUES
	('John', 'Doe', 'JohnD', 'John123', 'JohnDoe@gmail.com', 'finance manager'),
	('Jane', 'Doe', 'JaneD', 'Jane123', 'JaneDoe@gmail.com', 'employee'),
	('David', 'Good', 'DavidG', 'David123', 'DvaidGood@gmail.com', 'employee');