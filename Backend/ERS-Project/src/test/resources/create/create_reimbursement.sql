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