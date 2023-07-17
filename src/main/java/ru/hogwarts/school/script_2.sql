CREATE TABLE cars
(
    id    BIGSERIAL PRIMARY KEY,
    brand VARCHAR(15)             NOT NULL,
    model VARCHAR(31)             NOT NULL,
    price INT CHECK ( price > 0 ) NOT NULL
);

CREATE TABLE owners
(
    id    BIGSERIAL PRIMARY KEY,
    name  VARCHAR(15)             NOT NULL,
    age   INT CHECK ( age > 17 )  NOT NULL,
    has_driver_license BOOLEAN DEFAULT true NOT NULL,
    car_id BIGINT REFERENCES cars (id) NOT NULL
);

INSERT INTO cars(brand, model, price) VALUES ('lada','vesta',123333);
INSERT INTO owners(name, age, car_id) VALUES ('vasia',24,1);