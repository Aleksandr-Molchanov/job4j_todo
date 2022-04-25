CREATE TABLE if not exists users (
    id SERIAL PRIMARY KEY,
    name TEXT,
    email VARCHAR(255) UNIQUE,
    password TEXT
);

CREATE TABLE if not exists items (
    id SERIAL PRIMARY KEY,
    name TEXT,
    description TEXT,
    created Timestamp,
    done boolean,
    user_id int not null references users(id)
);