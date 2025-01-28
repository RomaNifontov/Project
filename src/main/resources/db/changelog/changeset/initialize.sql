CREATE TABLE clients (
                      id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
                      firstName varchar(100) NOT NULL
                      lastname varchar(100) NOT NULL,
                      phoneNumbers VARCHAR(30)[],
                      emails VARCHAR(150) [],
                      created_at timestamptz DEFAULT current_timestamp
);