CREATE TABLE if not exists users (
    id SERIAL PRIMARY KEY,
    name TEXT,
    email VARCHAR(255) UNIQUE,
    password TEXT
);

CREATE TABLE if not exists categories (
    id SERIAL PRIMARY KEY,
    name TEXT
);

INSERT INTO categories(name) VALUES ('Образование');
INSERT INTO categories(name) VALUES ('Работа');
INSERT INTO categories(name) VALUES ('Спорт');
INSERT INTO categories(name) VALUES ('Хобби');
INSERT INTO categories(name) VALUES ('Дом');
INSERT INTO categories(name) VALUES ('Другое');

CREATE TABLE if not exists items (
    id SERIAL PRIMARY KEY,
    name TEXT,
    description TEXT,
    created Timestamp,
    done boolean,
    user_id int not null references users(id)
);

CREATE TABLE if not exists items_categories (
    item_id int references items(id),
    categories_id int references categories(id)
);