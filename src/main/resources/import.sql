-- ADMIN USER
INSERT INTO user (usr_dob,created_date,last_modified_date,usr_id,usr_frt_name,usr_img_prf,usr_lst_name,usr_role) VALUES ('2000-01-01',NOW(),NOW(),1,'Enrico','https://img.favpng.com/10/24/6/user-profile-instagram-computer-icons-png-favpng-rzQf3Y9u65VmEgArYxVb3Dd7H.jpg','Stucchi','ADMIN');
-- ADMIN VAULTS
INSERT INTO vault (vlt_cpt, created_date, last_modified_date, vlt_id, vlt_usr_id, vlt_img, vlt_name) VALUES (0, NOW(), NOW(), 1, 1, 'https://public.bnbstatic.com/20190405/eb2349c3-b2f8-4a93-a286-8f86a62ea9d8.png', 'Binance');
INSERT INTO vault (vlt_cpt, created_date, last_modified_date, vlt_id, vlt_usr_id, vlt_img, vlt_name) VALUES (0, NOW(), NOW(), 2, 1, 'https://play-lh.googleusercontent.com/aFNf3nI0e4QWdjpIzmLcoBaQesFdV5nigTa1oOdA0ZnsG7F5IM6FRKKRbqXzd7Cxe2df', 'CTrader');

-- CATEGORIES
INSERT INTO CATEGORY (ctg_id, created_date, last_modified_date, ctg_name) VALUES ('1', NOW(), NOW(), 'Salary');
INSERT INTO CATEGORY (ctg_id, created_date, last_modified_date, ctg_name) VALUES ('2', NOW(), NOW(), 'Fitness and Sports');
INSERT INTO CATEGORY (ctg_id, created_date, last_modified_date, ctg_name) VALUES ('3', NOW(), NOW(), 'Commissions');
INSERT INTO CATEGORY (ctg_id, created_date, last_modified_date, ctg_name) VALUES ('5', NOW(), NOW(), 'Groceries');
INSERT INTO CATEGORY (ctg_id, created_date, last_modified_date, ctg_name) VALUES ('6', NOW(), NOW(), 'Clothing and Items');
INSERT INTO CATEGORY (ctg_id, created_date, last_modified_date, ctg_name) VALUES ('7', NOW(), NOW(), 'Restaurants and Outings');
INSERT INTO CATEGORY (ctg_id, created_date, last_modified_date, ctg_name) VALUES ('8', NOW(), NOW(), 'Health and Wellness');
INSERT INTO CATEGORY (ctg_id, created_date, last_modified_date, ctg_name) VALUES ('9', NOW(), NOW(), 'Fuel');
INSERT INTO CATEGORY (ctg_id, created_date, last_modified_date, ctg_name) VALUES ('10', NOW(), NOW(), 'Transport');
INSERT INTO CATEGORY (ctg_id, created_date, last_modified_date, ctg_name) VALUES ('11', NOW(), NOW(), 'ATM Withdrawal');
INSERT INTO CATEGORY (ctg_id, created_date, last_modified_date, ctg_name) VALUES ('12', NOW(), NOW(), 'Bank Transfer');
INSERT INTO CATEGORY (ctg_id, created_date, last_modified_date, ctg_name) VALUES ('13', NOW(), NOW(), 'Insurance');
INSERT INTO CATEGORY (ctg_id, created_date, last_modified_date, ctg_name) VALUES ('14', NOW(), NOW(), 'Travel and Experiences');
INSERT INTO CATEGORY (ctg_id, created_date, last_modified_date, ctg_name) VALUES ('15', NOW(), NOW(), 'Household Expenses');
INSERT INTO CATEGORY (ctg_id, created_date, last_modified_date, ctg_name) VALUES ('16', NOW(), NOW(), 'Accounting');
INSERT INTO CATEGORY (ctg_id, created_date, last_modified_date, ctg_name) VALUES ('17', NOW(), NOW(), 'Courses and Study');
INSERT INTO CATEGORY (ctg_id, created_date, last_modified_date, ctg_name) VALUES ('18', NOW(), NOW(), 'Games and Leisure');
INSERT INTO CATEGORY (ctg_id, created_date, last_modified_date, ctg_name) VALUES ('19', NOW(), NOW(), 'Investments');

-- THIRD_PARTY
INSERT INTO THIRD_PARTY (thp_id, thp_name) VALUES ('1', 'Supermercato La Spesa');
INSERT INTO THIRD_PARTY (thp_id, thp_name) VALUES ('2', 'Pasticceria Dolce Miele');
INSERT INTO THIRD_PARTY (thp_id, thp_name) VALUES ('3', 'Ristorante Il Girasole');
INSERT INTO THIRD_PARTY (thp_id, thp_name) VALUES ('4', 'Cinema Aurora');
INSERT INTO THIRD_PARTY (thp_id, thp_name) VALUES ('5', 'Farmacia Centrale');
INSERT INTO THIRD_PARTY (thp_id, thp_name) VALUES ('6', 'TecnoCasa Solutions');
INSERT INTO THIRD_PARTY (thp_id, thp_name) VALUES ('7', 'Vini e Sapori');
INSERT INTO THIRD_PARTY (thp_id, thp_name) VALUES ('8', 'Autofficina Ferrari');
INSERT INTO THIRD_PARTY (thp_id, thp_name) VALUES ('9', 'Centro Benessere Oasi');
INSERT INTO THIRD_PARTY (thp_id, thp_name) VALUES ('10', 'Elettrodomestici Casa Facile');

-- REVENUE
INSERT INTO REVENUE (rev_opr_id, created_date, last_modified_date, opr_amount, opr_causal, opr_end, opr_prg, opr_start, opr_ctg_id, opr_thp_id, opr_vlt_id) VALUES (1, NOW(), NOW(), 100.00, 'Stipendio di Gennaio', '2024-01-10', 0, '2024-01-10', 1, 1, 1);
INSERT INTO REVENUE (rev_opr_id, created_date, last_modified_date, opr_amount, opr_causal, opr_end, opr_prg, opr_start, opr_ctg_id, opr_thp_id, opr_vlt_id) VALUES (2, NOW(), NOW(), 100.00, 'Stipendio di Febbraio', '2024-02-10', 0, '2024-02-10', 1, 1, 1);
INSERT INTO REVENUE (rev_opr_id, created_date, last_modified_date, opr_amount, opr_causal, opr_end, opr_prg, opr_start, opr_ctg_id, opr_thp_id, opr_vlt_id) VALUES (3, NOW(), NOW(), 100.00, 'Stipendio di Marzo', '2024-03-10', 0, '2024-03-10', 1, 1, 1);
INSERT INTO REVENUE (rev_opr_id, created_date, last_modified_date, opr_amount, opr_causal, opr_end, opr_prg, opr_start, opr_ctg_id, opr_thp_id, opr_vlt_id) VALUES (4, NOW(), NOW(), 100.00, 'Stipendio di Aprile', '2024-04-10', 0, '2024-04-10', 1, 1, 1);
INSERT INTO REVENUE (rev_opr_id, created_date, last_modified_date, opr_amount, opr_causal, opr_end, opr_prg, opr_start, opr_ctg_id, opr_thp_id, opr_vlt_id) VALUES (5, NOW(), NOW(), 100.00, 'Stipendio di Maggio', '2024-05-10', 0, '2024-05-10', 1, 1, 1);
INSERT INTO REVENUE (rev_opr_id, created_date, last_modified_date, opr_amount, opr_causal, opr_end, opr_prg, opr_start, opr_ctg_id, opr_thp_id, opr_vlt_id) VALUES (6, NOW(), NOW(), 100.00, 'Stipendio di Giugno', '2024-06-10', 0, '2024-06-10', 1, 1, 1);
INSERT INTO REVENUE (rev_opr_id, created_date, last_modified_date, opr_amount, opr_causal, opr_end, opr_prg, opr_start, opr_ctg_id, opr_thp_id, opr_vlt_id) VALUES (7, NOW(), NOW(), 100.00, 'Stipendio di Luglio', '2024-07-10', 0, '2024-07-10', 1, 1, 1);
INSERT INTO REVENUE (rev_opr_id, created_date, last_modified_date, opr_amount, opr_causal, opr_end, opr_prg, opr_start, opr_ctg_id, opr_thp_id, opr_vlt_id) VALUES (8, NOW(), NOW(), 100.00, 'Stipendio di Agosto', '2024-08-10', 0, '2024-08-10', 1, 1, 1);
INSERT INTO REVENUE (rev_opr_id, created_date, last_modified_date, opr_amount, opr_causal, opr_end, opr_prg, opr_start, opr_ctg_id, opr_thp_id, opr_vlt_id) VALUES (9, NOW(), NOW(), 100.00, 'Stipendio di Settembre', '2024-09-10', 0, '2024-09-10', 1, 1, 1);
INSERT INTO REVENUE (rev_opr_id, created_date, last_modified_date, opr_amount, opr_causal, opr_end, opr_prg, opr_start, opr_ctg_id, opr_thp_id, opr_vlt_id) VALUES (10, NOW(), NOW(), 100.00, 'Stipendio di Ottobre', '2024-10-10', 0, '2024-10-10', 1, 1, 1);
INSERT INTO REVENUE (rev_opr_id, created_date, last_modified_date, opr_amount, opr_causal, opr_end, opr_prg, opr_start, opr_ctg_id, opr_thp_id, opr_vlt_id) VALUES (11, NOW(), NOW(), 100.00, 'Stipendio di Gennaio', '2024-01-10', 0, '2024-01-10', 1, 1, 2);
INSERT INTO REVENUE (rev_opr_id, created_date, last_modified_date, opr_amount, opr_causal, opr_end, opr_prg, opr_start, opr_ctg_id, opr_thp_id, opr_vlt_id) VALUES (12, NOW(), NOW(), 100.00, 'Stipendio di Febbraio', '2024-02-10', 0, '2024-02-10', 1, 1, 2);
INSERT INTO REVENUE (rev_opr_id, created_date, last_modified_date, opr_amount, opr_causal, opr_end, opr_prg, opr_start, opr_ctg_id, opr_thp_id, opr_vlt_id) VALUES (13, NOW(), NOW(), 100.00, 'Stipendio di Marzo', '2024-03-10', 0, '2024-03-10', 1, 1, 2);
INSERT INTO REVENUE (rev_opr_id, created_date, last_modified_date, opr_amount, opr_causal, opr_end, opr_prg, opr_start, opr_ctg_id, opr_thp_id, opr_vlt_id) VALUES (14, NOW(), NOW(), 100.00, 'Stipendio di Aprile', '2024-04-10', 0, '2024-04-10', 1, 1, 2);
INSERT INTO REVENUE (rev_opr_id, created_date, last_modified_date, opr_amount, opr_causal, opr_end, opr_prg, opr_start, opr_ctg_id, opr_thp_id, opr_vlt_id) VALUES (15, NOW(), NOW(), 100.00, 'Stipendio di Maggio', '2024-05-10', 0, '2024-05-10', 1, 1, 2);
INSERT INTO REVENUE (rev_opr_id, created_date, last_modified_date, opr_amount, opr_causal, opr_end, opr_prg, opr_start, opr_ctg_id, opr_thp_id, opr_vlt_id) VALUES (16, NOW(), NOW(), 100.00, 'Stipendio di Giugno', '2024-06-10', 0, '2024-06-10', 1, 1, 2);
INSERT INTO REVENUE (rev_opr_id, created_date, last_modified_date, opr_amount, opr_causal, opr_end, opr_prg, opr_start, opr_ctg_id, opr_thp_id, opr_vlt_id) VALUES (17, NOW(), NOW(), 100.00, 'Stipendio di Luglio', '2024-07-10', 0, '2024-07-10', 1, 1, 2);
INSERT INTO REVENUE (rev_opr_id, created_date, last_modified_date, opr_amount, opr_causal, opr_end, opr_prg, opr_start, opr_ctg_id, opr_thp_id, opr_vlt_id) VALUES (18, NOW(), NOW(), 100.00, 'Stipendio di Agosto', '2024-08-10', 0, '2024-08-10', 1, 1, 2);
INSERT INTO REVENUE (rev_opr_id, created_date, last_modified_date, opr_amount, opr_causal, opr_end, opr_prg, opr_start, opr_ctg_id, opr_thp_id, opr_vlt_id) VALUES (19, NOW(), NOW(), 100.00, 'Stipendio di Settembre', '2024-09-10', 0, '2024-09-10', 1, 1, 2);
INSERT INTO REVENUE (rev_opr_id, created_date, last_modified_date, opr_amount, opr_causal, opr_end, opr_prg, opr_start, opr_ctg_id, opr_thp_id, opr_vlt_id) VALUES (20, NOW(), NOW(), 100.00, 'Stipendio di Ottobre', '2024-10-10', 0, '2024-10-10', 1, 1, 2);

--EXPENSE
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (15, '2024-01-05', 0, '2024-01-05', NOW(), 1, NOW(), 5, 1, 1, 'Small shopping');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (20, '2024-01-10', 0, '2024-01-10', NOW(), 2, NOW(), 9, 3, 1, 'Taxi ride');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (30, '2024-01-15', 0, '2024-01-15', NOW(), 3, NOW(), 7, 4, 1, 'Coffee with friends');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (25, '2024-01-20', 0, '2024-01-20', NOW(), 4, NOW(), 8, 2, 1, 'Medicine purchase');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (50, '2024-01-25', 0, '2024-01-25', NOW(), 5, NOW(), 12, 1, 1, 'Gift for colleague');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (12, '2024-03-01', 0, '2024-03-01', NOW(), 11, NOW(), 5, 1, 1, 'Weekly shopping');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (28, '2024-03-05', 0, '2024-03-05', NOW(), 12, NOW(), 9, 3, 1, 'Parking fee');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (35, '2024-03-10', 0, '2024-03-10', NOW(), 13, NOW(), 7, 4, 1, 'Lunch with friends');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (22, '2024-03-15', 0, '2024-03-15', NOW(), 14, NOW(), 8, 2, 1, 'Vitamins');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (48, '2024-03-20', 0, '2024-03-20', NOW(), 15, NOW(), 12, 1, 1, 'Online purchase');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (32, '2024-03-25', 0, '2024-03-25', NOW(), 21, NOW(), 6, 2, 1, 'Grocery shopping');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (22, '2024-04-05', 0, '2024-04-05', NOW(), 22, NOW(), 10, 3, 1, 'Gym membership');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (45, '2024-04-10', 0, '2024-04-10', NOW(), 23, NOW(), 7, 4, 1, 'Birthday party');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (18, '2024-04-15', 0, '2024-04-15', NOW(), 24, NOW(), 11, 1, 1, 'Pet supplies');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (38, '2024-04-20', 0, '2024-04-20', NOW(), 25, NOW(), 12, 5, 1, 'Book purchase');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (60, '2024-06-05', 0, '2024-06-05', NOW(), 31, NOW(), 8, 2, 1, 'Vacation deposit');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (45, '2024-06-15', 0, '2024-06-15', NOW(), 32, NOW(), 11, 3, 1, 'Car maintenance');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (38, '2024-06-20', 0, '2024-06-20', NOW(), 33, NOW(), 7, 1, 1, 'Summer camp');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (50, '2024-07-10', 0, '2024-07-10', NOW(), 34, NOW(), 6, 5, 1, 'Medical expenses');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (70, '2024-07-25', 0, '2024-07-25', NOW(), 35, NOW(), 9, 4, 1, 'Concert tickets');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (75, '2024-10-20', 0, '2024-10-20', NOW(), 36, NOW(), 11, 2, 1, 'Furniture purchase');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (50, '2024-11-05', 0, '2024-11-05', NOW(), 37, NOW(), 6, 4, 1, 'Dining out');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (120, '2024-11-20', 0, '2024-11-20', NOW(), 46, NOW(), 12, 5, 1, 'Home improvement');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (18, '2024-03-01', 0, '2024-03-01', NOW(), 16, NOW(), 5, 1, 2, 'Vegetables');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (30, '2024-03-05', 0, '2024-03-05', NOW(), 17, NOW(), 9, 2, 2, 'Metro card');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (12, '2024-03-10', 0, '2024-03-10', NOW(), 18, NOW(), 7, 3, 2, 'Quick snack');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (20, '2024-03-15', 0, '2024-03-15', NOW(), 19, NOW(), 8, 4, 2, 'Over-the-counter medicine');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (44, '2024-03-20', 0, '2024-03-20', NOW(), 20, NOW(), 12, 5, 2, 'Kitchen utensils');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (10, '2024-02-05', 0, '2024-02-05', NOW(), 6, NOW(), 5, 1, 2, 'Fruit purchase');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (12, '2024-02-10', 0, '2024-02-10', NOW(), 7, NOW(), 9, 2, 2, 'Bus ticket');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (18, '2024-02-15', 0, '2024-02-15', NOW(), 8, NOW(), 7, 3, 2, 'Coffee break');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (15, '2024-02-20', 0, '2024-02-20', NOW(), 9, NOW(), 8, 4, 2, 'First-aid items');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (40, '2024-02-25', 0, '2024-02-25', NOW(), 10, NOW(), 12, 5, 2, 'Home tools');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (14, '2024-04-25', 0, '2024-04-25', NOW(), 26, NOW(), 5, 1, 2, 'Office snacks');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (27, '2024-05-01', 0, '2024-05-01', NOW(), 27, NOW(), 8, 4, 2, 'House cleaning products');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (34, '2024-05-10', 0, '2024-05-10', NOW(), 28, NOW(), 9, 3, 2, 'Monthly subscription');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (25, '2024-05-15', 0, '2024-05-15', NOW(), 29, NOW(), 7, 2, 2, 'Stationery supplies');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (50, '2024-05-20', 0, '2024-05-20', NOW(), 30, NOW(), 10, 5, 2, 'Furniture');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (30, '2024-08-05', 0, '2024-08-05', NOW(), 47, NOW(), 10, 2, 2, 'Gift purchase');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (65, '2024-08-15', 0, '2024-08-15', NOW(), 48, NOW(), 12, 3, 2, 'Utility bills');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (40, '2024-08-20', 0, '2024-08-20', NOW(), 38, NOW(), 5, 1, 2, 'Back-to-school supplies');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (55, '2024-09-10', 0, '2024-09-10', NOW(), 39, NOW(), 8, 5, 2, 'Sport equipment');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (75, '2024-09-25', 0, '2024-09-25', NOW(), 40, NOW(), 6, 4, 2, 'Electronics purchase');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (50, '2024-10-05', 0, '2024-10-05', NOW(), 41, NOW(), 9, 3, 2, 'Charity donation');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (85, '2024-10-15', 0, '2024-10-15', NOW(), 42, NOW(), 7, 1, 2, 'Anniversary dinner');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (60, '2024-10-20', 0, '2024-10-20', NOW(), 43, NOW(), 11, 2, 2, 'Home decor');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (35, '2024-11-05', 0, '2024-11-05', NOW(), 44, NOW(), 6, 4, 2, 'Fast food');
INSERT INTO EXPENSE (opr_amount, opr_end, opr_prg, opr_start, created_date, exp_opr_id, last_modified_date, opr_ctg_id, opr_thp_id, opr_vlt_id, opr_causal) VALUES (95, '2024-11-20', 0, '2024-11-20', NOW(), 45, NOW(), 12, 5, 2, 'Appliance repair');