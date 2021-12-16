DROP TABLE IF EXISTS reimbursement; 

CREATE TABLE reimbursement (
	reimb_id SERIAL PRIMARY KEY,
	amount NUMERIC NOT NULL,
	submitted_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	resolved_time TIMESTAMP,
	status VARCHAR(20) DEFAULT 'Pending',
	type VARCHAR(50) NOT NULL,
	description VARCHAR(200) NOT NULL,
	receipt BYTEA,
	author_id INTEGER NOT NULL,
	resolver_id INTEGER,
	CONSTRAINT author_fk FOREIGN KEY (author_id) REFERENCES users(user_id),
	CONSTRAINT resolver_fk FOREIGN KEY (resolver_id) REFERENCES users(user_id)
);

-- ====== INSERT 

INSERT INTO "ERS_project".reimbursement (
	amount,
	type,
	description,
	author_id
)
VALUES
	('499.99', 'travel', 'plane ticket', '2'),
	('180.00', 'food', 'food expences though out 2 days trip', '3');

--
INSERT INTO "ERS_project".reimbursement (
	amount,
	submitted_time,
	type,
	description,
	receipt,
	author_id
)
VALUES
	('800.0', CURRENT_TIMESTAMP, 'other', 'drugs', 'reciept', 3);


-- ====== UPDATE 

UPDATE "ERS_project".reimbursement
SET 
	status = 'denied',
	resolved_time = CURRENT_TIMESTAMP,
	resolver_id = 1
WHERE reimb_id = 2;

-- ====== QUERYING

SELECT *
FROM "ERS_project".reimbursement;

--

SELECT *
FROM "ERS_project".reimbursement
ORDER BY reimb_id; 

--

SELECT *
FROM "ERS_project".reimbursement
WHERE author_id = 2;

--

SELECT *
FROM "ERS_project".reimbursement
WHERE reimb_id = 2;

--

SELECT receipt
FROM "ERS_project".reimbursement
WHERE reimb_id = 2;

--

SELECT *
FROM "ERS_project".reimbursement
WHERE status = 'PENDING';

	