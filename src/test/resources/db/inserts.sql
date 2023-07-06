INSERT INTO users (user_id, email, password) VALUES (1, 'user1@gmail.com', '$2a$10$Z00/0b/Aj/NuQGosWtQLu.7LTYZ57pqPHMhaiIoga.IrmUrcNHsbS');
INSERT INTO users (user_id, email, password) VALUES (2, 'user2@gmail.com', '$2a$10$r0HV.zbs3aKPOBLbh0OdqezDMlIxfuFX4zPOV3lvjGE4n9N7yKRLO');
INSERT INTO users (user_id, email, password) VALUES (3, 'clUser1@gmail.com', '$2a$10$aF.X1F3m1C149YxqGAVXDuWwXijYIYYLCImBC9sbXyxjdm2EMSBbG');
INSERT INTO users (user_id, email, password) VALUES (4, 'clUser2@gmail.com', '$2a$10$uwugassMd1hoS5DSSISLWOV/X5vyEi0ntJLD/SfBM1ivDB0e0Je4W');

INSERT INTO roles (role_id, role_name) VALUES (1, 'Admin');
INSERT INTO roles (role_id, role_name) VALUES (2, 'Client');

INSERT INTO user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO user_role (user_id, role_id) VALUES (2, 2);
INSERT INTO user_role (user_id, role_id) VALUES (3, 2);
INSERT INTO user_role (user_id, role_id) VALUES (4, 2);

INSERT INTO banks (bank_id, a1_number, address, life_number, logo, mts_number, bank_name) VALUES (1, '+375441111111', 'address1', '+375291111111', 'logo1', '+375251111111', 'bank1');
INSERT INTO banks (bank_id, a1_number, address, life_number, logo, mts_number, bank_name) VALUES (2, '+375442222222', 'address2', '+375292222222', 'logo2', '+375252222222', 'bank2');

INSERT INTO passports (passport_id, date_of_issue, organization, passport_number, validity_period) VALUES (1, '1990-11-11', 'organization1', '3456789', '2029-11-12');
INSERT INTO passports (passport_id, date_of_issue, organization, passport_number, validity_period) VALUES (2, '2010-11-05', 'organization2', '3456888', '2030-11-12');

INSERT INTO clients (client_id, date_of_birth, gender, name, patronymic, surname, phone_number, passport_data_id, user_id) VALUES (1, '2000-11-03', 'male', 'client1LN', 'client1P', 'client1FN', '+375441111111', 1, 3);
INSERT INTO clients (client_id, date_of_birth, gender, name, patronymic, surname, phone_number, passport_data_id, user_id) VALUES (2, '1999-11-04', 'male', 'client2LN', 'client2P', 'client2FN', '+375442222222', 2, 4);

INSERT INTO cards (card_id, payment_system, card_balance, month, number, year, cvv, client_id) VALUES (1, '/payment_systems/Visa.png' ,3000, 11, 1111111111111111, 2020, 111, 1);
INSERT INTO cards (card_id, payment_system, card_balance, month, number, year, cvv, client_id) VALUES (2, '/payment_systems/Mastercard.png', 4000, 9, 2222222222222222, 2022, 222, 2);

INSERT INTO demand_deposits (deposit_id, deposit_amount, currency, capitalization, early_withdrawal, partial_withdrawal, replenishment, interest_rate, deposit_name, bank_id) VALUES (1, 1000, 'USD', true, true, false, true, 10, 'demandDeposit1', 1);
INSERT INTO demand_deposits (deposit_id, deposit_amount, currency, capitalization, early_withdrawal, partial_withdrawal, replenishment, interest_rate, deposit_name, bank_id) VALUES (2, 2000, 'USD', true, true, false, false, 12, 'demandDeposit2', 2);

INSERT INTO term_deposits (deposit_id, deposit_amount, currency, capitalization, early_withdrawal, partial_withdrawal, replenishment, interest_rate, deposit_name, bank_id, term) VALUES (3, 1000, 'USD', true, true, false, true, 10, 'termDeposit1', 1, 10);
INSERT INTO term_deposits (deposit_id, deposit_amount, currency, capitalization, early_withdrawal, partial_withdrawal, replenishment, interest_rate, deposit_name, bank_id, term) VALUES (4, 2000, 'USD', true, true, false, false, 12, 'termDeposit2', 2, 20);

INSERT INTO opened_demand_deposits (opened_deposit_id, amount, opening_date, status, card_id, client_id, demand_deposit_id) VALUES (1, 1000, '2022-10-10', 'Открыт', 1, 1, 1);
INSERT INTO opened_demand_deposits (opened_deposit_id, amount, opening_date, status, card_id, client_id, demand_deposit_id) VALUES (2, 2000, '2022-10-20', 'Открыт', 2, 2, 2);

INSERT INTO opened_term_deposits (opened_deposit_id, amount, opening_date, status, card_id, client_id, term_deposit_id) VALUES (3, 1111, '2022-11-11', 'Открыт', 1, 1, 3);
INSERT INTO opened_term_deposits (opened_deposit_id, amount, opening_date, status, card_id, client_id, term_deposit_id) VALUES (4, 2222, '2022-11-12', 'Открыт', 2, 2, 4);

INSERT INTO demand_deposit_operations (operation_id, amount, balance, date, operation_type, opened_demand_deposit_id) VALUES (1, 100, 1000, '2023-11-11', 0, 1);
INSERT INTO demand_deposit_operations (operation_id, amount, balance, date, operation_type, opened_demand_deposit_id) VALUES (2, 200, 2000, '2023-12-12', 2, 2);

INSERT INTO term_deposit_operations (operation_id, amount, balance, date, operation_type, opened_term_deposit_id) VALUES (3, 100, 1000, '2023-11-11', 1, 3);
INSERT INTO term_deposit_operations (operation_id, amount, balance, date, operation_type, opened_term_deposit_id) VALUES (4, 200, 2000, '2023-12-12', 0, 4);





