-- File: data.sql

-- Insert data in table client and person

INSERT INTO persons (name, gender, age, identification, address, phone) VALUES
('Jose Lerma', 'male', 30, '1019039', 'Otavalo sn y principal', '098254785'),
('Mariniela Montavlo', 'female', 25, '1019040', 'Amazonas y NNUU', '097548965'),
('Juan Osorio', 'male', 40, '1019041', '13 junio Equinoccial', '098874587');


INSERT INTO clients (person_id, client_id, password, status) VALUES
((SELECT id FROM persons WHERE identification = '1019039'), 1825, '1234', true),
((SELECT id FROM persons WHERE identification = '1019040'), 1826, '5678', true),
((SELECT id FROM persons WHERE identification = '1019041'), 1827, '1245', true);