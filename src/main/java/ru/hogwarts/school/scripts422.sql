CREATE TABLE car (
                     id SERIAL UNIQUE,
                     name VARCHAR(100) NOT NULL,
                     model VARCHAR(100) NOT NULL,
                     price NUMERIC(10,2)
);

CREATE TABLE person (
                        id SERIAL UNIQUE,
                        name VARCHAR(100) NOT NULL,
                        age INTEGER NOT NULL CHECK (AGE>=18),
                        have_driving_licence BOOLEAN NOT NULL,
                        car_id INTEGER REFERENCES car(id)
);

INSERT INTO car (id, name, model, price) VALUES (1, 'Opel', 'Insignia', 2000000.00);
INSERT INTO car (id, name, model, price) VALUES (2, 'Toyota', 'aCorolla', 700000.00);
INSERT INTO car (id, name, model, price) VALUES (3, 'Porshe', 'Carrera', null);

INSERT INTO person (id, name, age, have_driving_licence, car_id) VALUES (1, 'Ольга Иванова', 30, '0', null);
INSERT INTO person (id, name, age, have_driving_licence, car_id) VALUES (2, 'Иван Яковлев', 44, '1', 2);
INSERT INTO person (id, name, age, have_driving_licence, car_id) VALUES (3, 'Жанна Базарова', 28, '1', 2);
INSERT INTO person (id, name, age, have_driving_licence, car_id) VALUES (4, 'Игнат Довгаль', 55, 'yes', 1);
INSERT INTO person (id, name, age, have_driving_licence, car_id) VALUES (5, 'Егор Батурин', 20, 'yes', 3);