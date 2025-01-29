CREATE TABLE clients (
                         id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
                         name varchar(100) NOT NULL,
                         created_at timestamptz DEFAULT current_timestamp
);

CREATE TABLE emails (
                        id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
                        client_id bigint NOT NULL,
                        email varchar(256) NOT NULL,

                        CONSTRAINT fk_client_id FOREIGN KEY (client_id) REFERENCES clients (id)
);

CREATE TABLE phones (
                        id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
                        client_id bigint NOT NULL,
                        number varchar(30) NOT NULL,

                        CONSTRAINT fk_client_id FOREIGN KEY (client_id) REFERENCES clients (id)

);