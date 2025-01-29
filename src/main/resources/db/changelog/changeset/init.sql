CREATE TABLE clients (
                         id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
                         firstName varchar(100) NOT NULL,
                         created_at timestamptz DEFAULT current_timestamp
);

CREATE TABLE emails (
                         id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
                         email varchar(256) NOT NULL
);

CREATE TABLE phones (
                         id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
                         number varchar(30) NOT NULL
);