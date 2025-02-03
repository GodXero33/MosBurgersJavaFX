DROP DATABASE IF EXISTS mos_burgers;
CREATE DATABASE mos_burgers;
USE mos_burgers;

CREATE TABLE report (
	report_id INT AUTO_INCREMENT,
	creation_date DATE NOT NULL,
	type ENUM('Monthly', 'Annual') NOT NULL,
	detail VARCHAR(256),
	PRIMARY KEY (report_id)
);

CREATE TABLE food_item (
	item_id INT AUTO_INCREMENT,
	name VARCHAR(30) NOT NULL,
	code VARCHAR(15) NOT NULL UNIQUE,
	price DOUBLE(8, 2) NOT NULL,
	discount DOUBLE(8, 2) DEFAULT 0.0,
	quantity INT(10) NOT NULL,
	category ENUM('Burgers', 'Submarines', 'Beverages', 'Other'),
	PRIMARY KEY (item_id)
);

CREATE TABLE `admin` (
	admin_id INT AUTO_INCREMENT,
	name VARCHAR(30),
	phone VARCHAR(12) UNIQUE,
	email VARCHAR(30),
	address VARCHAR(60),
	salary DOUBLE(8, 2),
	position ENUM('Cashier', 'Manager'),
	dob DATE,
	password VARCHAR(255),
	PRIMARY KEY (admin_id)
);

CREATE TABLE customer (
	customer_id INT AUTO_INCREMENT,
	name VARCHAR(30),
	phone VARCHAR(12),
	email VARCHAR(30),
	address VARCHAR(60),
	PRIMARY KEY (customer_id)
);

CREATE TABLE mos_order (
	order_id INT AUTO_INCREMENT,
	place_date DATE NOT NULL,
	total_amount DOUBLE(9, 2) NOT NULL,
	discount DOUBLE(7, 2) NOT NULL DEFAULT 0.0,
	final_amount DOUBLE(9, 2) NOT NULL,
	customer_id INT,
	admin_id INT,
	FOREIGN KEY (admin_id) REFERENCES admin (admin_id),
	FOREIGN KEY (customer_id) REFERENCES customer (customer_id),
	PRIMARY KEY (order_id)
);

CREATE TABLE order_item (
	item_id INT,
	order_id INT,
	quantity INT(5) NOT NULL,
	total_price DOUBLE(8, 2) NOT NULL,
	price_per_unit DOUBLE(7, 2) NOT NULL,
	FOREIGN KEY (item_id) REFERENCES food_item (item_id),
	FOREIGN KEY (order_id) REFERENCES mos_order (order_id),
	PRIMARY KEY (item_id, order_id)
);

CREATE TABLE receipt (
	receipt_id INT AUTO_INCREMENT,
	receipt_date DATE NOT NULL,
	receipt_time TIME NOT NULL,
	order_id INT,
	FOREIGN KEY (order_id) REFERENCES mos_order (order_id),
	PRIMARY KEY (receipt_id)
);

DESC report;
DESC food_item;
DESC `admin`;
DESC customer;
DESC mos_order;
DESC order_item;
DESC receipt;

INSERT INTO report (creation_date, type, detail) VALUES
('2024-01-01', 'Annual', 'Annual performance and sales report.'),
('2024-03-01', 'Monthly', 'Sales analysis for February.'),
('2024-04-01', 'Monthly', 'Customer feedback summary.'),
('2024-05-01', 'Monthly', 'Inventory management insights.'),
('2023-12-01', 'Annual', 'Yearly report of expenses and revenue.'),
('2024-06-01', 'Monthly', 'Promotional campaign results.'),
('2023-11-01', 'Monthly', 'Operational challenges summary.'),
('2024-02-01', 'Monthly', 'Special offers impact review.'),
('2023-10-01', 'Monthly', 'Staff performance evaluation.'),
('2023-09-01', 'Monthly', 'Quarterly targets overview.');

INSERT INTO food_item (name, code, price, discount, category, quantity) VALUES
('Classic Burger', 'CLB001', 5.99, 0.50, 'Burgers', 200),
('Cheese Burger', 'CHB002', 6.49, 0.30, 'Burgers', 150),
('Veggie Burger', 'VGB003', 5.49, 0.00, 'Burgers', 120),
('Chicken Submarine', 'CHS004', 7.99, 1.00, 'Submarines', 100),
('Beef Submarine', 'BFS005', 8.49, 0.50, 'Submarines', 80),
('Coke', 'CK006', 1.99, 0.20, 'Beverages', 500),
('Orange Juice', 'OJ007', 2.49, 0.00, 'Beverages', 300),
('Fries', 'FR008', 2.99, 0.00, 'Other', 450),
('Ice Cream', 'IC009', 3.99, 0.25, 'Other', 200),
('Milkshake', 'MS010', 4.49, 0.50, 'Beverages', 180),
('Spicy Burger', 'SPB011', 6.99, 0.40, 'Burgers', 100),
('Double Cheese Burger', 'DCB012', 7.99, 0.30, 'Burgers', 90),
('BBQ Chicken Burger', 'BBQ013', 8.49, 0.20, 'Burgers', 85),
('Grilled Veggie Sub', 'GVS014', 7.49, 0.50, 'Submarines', 60),
('Fish Submarine', 'FSS015', 8.99, 0.60, 'Submarines', 50),
('Lemonade', 'LMD016', 2.99, 0.15, 'Beverages', 400),
('Strawberry Smoothie', 'STM017', 3.49, 0.25, 'Beverages', 300),
('Onion Rings', 'ONR018', 2.99, 0.10, 'Other', 350),
('Mozzarella Sticks', 'MST019', 4.49, 0.30, 'Other', 220),
('Chocolate Milkshake', 'CMS020', 4.99, 0.50, 'Beverages', 150),
('Chicken Nuggets', 'CHN021', 3.99, 0.20, 'Other', 250),
('Caesar Salad', 'CSS022', 5.99, 0.40, 'Other', 120),
('Greek Salad', 'GKS023', 6.49, 0.30, 'Other', 100),
('Veggie Wrap', 'VGW024', 4.99, 0.20, 'Other', 150),
('Chicken Wrap', 'CHW025', 6.99, 0.50, 'Other', 120),
('Choco Lava Cake', 'CLC026', 3.99, 0.25, 'Other', 100),
('Vanilla Ice Cream', 'VIC027', 3.49, 0.30, 'Other', 200),
('Fruit Punch', 'FRP028', 2.99, 0.15, 'Beverages', 150),
('Hot Dog', 'HTD029', 4.49, 0.10, 'Other', 180),
('Nachos', 'NCH030', 5.99, 0.30, 'Other', 200),
('Cheesy Fries', 'CHF031', 3.49, 0.20, 'Other', 240),
('Pepperoni Pizza', 'PPP032', 9.99, 0.50, 'Other', 80),
('Margherita Pizza', 'MGP033', 8.99, 0.40, 'Other', 70),
('Chicken BBQ Pizza', 'CBP034', 10.49, 0.60, 'Other', 60),
('Garlic Bread', 'GRB035', 3.99, 0.25, 'Other', 150),
('Buffalo Wings', 'BFW036', 6.99, 0.30, 'Other', 100),
('Chicken Popcorn', 'CHP037', 4.99, 0.20, 'Other', 200),
('Grilled Cheese Sandwich', 'GCS038', 5.49, 0.15, 'Other', 150),
('Club Sandwich', 'CLS039', 6.49, 0.20, 'Other', 140),
('Egg Salad Sandwich', 'ESS040', 4.99, 0.10, 'Other', 130),
('Mac and Cheese', 'MAC041', 7.49, 0.30, 'Other', 90),
('Pasta Alfredo', 'PAS042', 8.99, 0.40, 'Other', 80),
('Chicken Tenders', 'CHT043', 5.99, 0.20, 'Other', 120),
('Chocolate Cake', 'CHC044', 4.99, 0.25, 'Other', 80),
('Blueberry Muffin', 'BLM045', 3.49, 0.10, 'Other', 200),
('Espresso', 'ESP046', 2.49, 0.10, 'Beverages', 400),
('Cappuccino', 'CAP047', 3.99, 0.20, 'Beverages', 350),
('Latte', 'LAT048', 4.49, 0.25, 'Beverages', 300),
('Green Tea', 'GRT049', 2.99, 0.10, 'Beverages', 400),
('Iced Coffee', 'ICC050', 3.49, 0.15, 'Beverages', 280),
('Veggie Pasta', 'VGP051', 7.99, 0.30, 'Other', 90),
('Tomato Soup', 'TSP052', 4.49, 0.20, 'Other', 120),
('Minestrone Soup', 'MNS053', 5.49, 0.25, 'Other', 100),
('Garlic Parmesan Fries', 'GPF054', 4.49, 0.30, 'Other', 150),
('Spicy Chicken Burger', 'SCB055', 7.49, 0.30, 'Burgers', 90),
('Mango Smoothie', 'MNS056', 3.99, 0.20, 'Beverages', 200),
('Pineapple Juice', 'PNJ057', 3.49, 0.15, 'Beverages', 150),
('Berry Blast', 'BBL058', 3.99, 0.20, 'Beverages', 120),
('Apple Pie', 'APP059', 4.99, 0.25, 'Other', 90),
('Cherry Pie', 'CHP060', 4.49, 0.20, 'Other', 80),
('Chocolate Donut', 'CHD061', 2.99, 0.15, 'Other', 100),
('Vanilla Donut', 'VND062', 2.99, 0.15, 'Other', 100),
('Strawberry Cheesecake', 'SSC063', 5.99, 0.30, 'Other', 70),
('Fudge Brownie', 'FBR064', 3.99, 0.20, 'Other', 80),
('Chicken Caesar Wrap', 'CCW065', 6.99, 0.30, 'Other', 90),
('Veggie Delight Wrap', 'VDW066', 6.49, 0.20, 'Other', 100),
('BBQ Ribs', 'BBR067', 12.49, 0.50, 'Other', 50),
('Grilled Salmon', 'GSL068', 14.99, 0.60, 'Other', 40),
('Veggie Soup', 'VSP069', 4.99, 0.20, 'Other', 110),
('Cream of Mushroom', 'CRM070', 5.49, 0.25, 'Other', 100),
('Spaghetti Bolognese', 'SPB071', 9.49, 0.30, 'Other', 70),
('Lasagna', 'LSG072', 9.99, 0.40, 'Other', 60),
('Fruit Salad', 'FRS073', 5.99, 0.20, 'Other', 120),
('Yogurt Parfait', 'YPF074', 4.49, 0.15, 'Other', 100),
('Mint Lemonade', 'MLM075', 3.49, 0.10, 'Beverages', 150),
('Sparkling Water', 'SPW076', 1.99, 0.05, 'Beverages', 200),
('Energy Drink', 'END077', 2.99, 0.15, 'Beverages', 180),
('Veggie Pizza', 'VGP078', 8.49, 0.30, 'Other', 70),
('BBQ Chicken Pizza', 'BCP079', 10.49, 0.40, 'Other', 50),
('Cheesy Garlic Bread', 'CGB080', 3.99, 0.20, 'Other', 150),
('Stuffed Crust Pizza', 'SCP081', 12.99, 0.50, 'Other', 40),
('Mini Tacos', 'MNT082', 5.99, 0.30, 'Other', 90),
('Soft Tacos', 'STC083', 6.49, 0.30, 'Other', 80),
('Crispy Taco Bowl', 'CTB084', 7.49, 0.40, 'Other', 70),
('Chicken Salad', 'CHS085', 6.99, 0.30, 'Other', 100),
('Grilled Chicken Breast', 'GCB086', 8.99, 0.40, 'Other', 80),
('Vegetable Stir Fry', 'VSF087', 7.49, 0.30, 'Other', 90),
('Spring Rolls', 'SPR088', 4.99, 0.20, 'Other', 100),
('Egg Roll', 'EGR089', 5.49, 0.20, 'Other', 80),
('Chicken Chow Mein', 'CCM090', 9.49, 0.40, 'Other', 70),
('Sweet and Sour Chicken', 'SSC091', 9.99, 0.50, 'Other', 60),
('Pork Dumplings', 'PDM092', 6.99, 0.30, 'Other', 100),
('Beef Stir Fry', 'BSF093', 10.49, 0.50, 'Other', 60),
('Chicken Fried Rice', 'CFR094', 8.49, 0.40, 'Other', 80),
('Vegetable Fried Rice', 'VFR095', 7.99, 0.30, 'Other', 90),
('Shrimp Fried Rice', 'SFR096', 9.49, 0.50, 'Other', 70),
('Coconut Water', 'COW097', 3.49, 0.15, 'Beverages', 200),
('Protein Shake', 'PTS098', 4.99, 0.20, 'Beverages', 150),
('Berry Yogurt', 'BYG099', 3.99, 0.20, 'Other', 180),
('Grilled Veggie Plate', 'GVP100', 7.99, 0.30, 'Other', 90),
('Spicy Beef Jerky', 'SBJ094', 7.99, 0.40, 'Other', 150),
('Honey Glazed Ham', 'HGH095', 9.49, 0.30, 'Other', 80),
('Shrimp Scampi', 'SSP096', 12.49, 0.50, 'Other', 60),
('Lobster Bisque', 'LBS097', 14.99, 0.60, 'Other', 50),
('Stuffed Bell Peppers', 'SBP098', 7.99, 0.30, 'Other', 70),
('Baked Ziti', 'BZ099', 8.49, 0.30, 'Other', 80),
('Mango Pudding', 'MPD100', 3.99, 0.20, 'Other', 120),
('Coconut Custard', 'CCS101', 4.49, 0.25, 'Other', 100),
('Pumpkin Pie', 'PPI102', 5.99, 0.30, 'Other', 90),
('Seafood Paella', 'SDP103', 14.49, 0.60, 'Other', 50),
('Chicken Pot Pie', 'CPP104', 8.49, 0.40, 'Other', 70),
('Vegetarian Quiche', 'VEQ105', 6.99, 0.30, 'Other', 80),
('Spinach Lasagna', 'SPL106', 9.49, 0.40, 'Other', 60),
('Teriyaki Chicken', 'TKC107', 10.99, 0.50, 'Other', 70),
('Crab Cakes', 'CRC108', 12.99, 0.60, 'Other', 50),
('Steak Frites', 'SKF109', 14.49, 0.50, 'Other', 60),
('Carrot Cake', 'CRC110', 4.99, 0.25, 'Other', 90),
('Tofu Stir Fry', 'TSF111', 6.99, 0.30, 'Other', 100),
('Shrimp Tacos', 'SHT112', 7.49, 0.40, 'Other', 70),
('Beef Burrito', 'BFB113', 8.49, 0.50, 'Other', 80),
('Vegetable Curry', 'VCR114', 7.99, 0.30, 'Other', 100),
('Chicken Kebab', 'CHB115', 9.49, 0.40, 'Other', 90),
('Falafel Wrap', 'FLW116', 6.99, 0.20, 'Other', 120),
('Lamb Gyro', 'LGR117', 9.99, 0.50, 'Other', 70),
('Quinoa Salad', 'QNS118', 5.99, 0.20, 'Other', 130),
('Avocado Toast', 'AVT119', 6.49, 0.20, 'Other', 110),
('Pesto Pasta', 'PSP120', 8.99, 0.30, 'Other', 90),
('Thai Green Curry', 'TGC121', 10.49, 0.50, 'Other', 70),
('Pad Thai', 'PDT122', 9.49, 0.40, 'Other', 80),
('Miso Soup', 'MSS123', 3.99, 0.20, 'Other', 120),
('Tempura', 'TMP124', 7.49, 0.30, 'Other', 100),
('Sushi Platter', 'SPL125', 15.99, 0.60, 'Other', 50),
('Ramen Bowl', 'RMB126', 10.99, 0.50, 'Other', 80),
('Banh Mi Sandwich', 'BMS127', 7.49, 0.30, 'Other', 100),
('Pho Noodle Soup', 'PNS128', 9.99, 0.50, 'Other', 70),
('Cuban Sandwich', 'CBS129', 8.49, 0.40, 'Other', 90),
('BBQ Pulled Pork', 'BPP130', 9.99, 0.50, 'Other', 70),
('Cheesesteak Sandwich', 'CSS131', 8.99, 0.40, 'Other', 80),
('Fried Rice', 'FRR132', 7.49, 0.30, 'Other', 100),
('Chili Con Carne', 'CCC133', 9.49, 0.50, 'Other', 80),
('Jambalaya', 'JMB134', 10.49, 0.40, 'Other', 70),
('Gumbo', 'GMB135', 11.49, 0.50, 'Other', 60),
('Shrimp Creole', 'SHC136', 12.49, 0.60, 'Other', 50),
('Chicken Alfredo', 'CAF137', 9.99, 0.40, 'Other', 80),
('Buffalo Chicken Dip', 'BCD138', 6.99, 0.30, 'Other', 90),
('Spinach Artichoke Dip', 'SAD139', 7.49, 0.40, 'Other', 80),
('Loaded Nachos', 'LND140', 8.49, 0.50, 'Other', 70),
('Bacon Cheeseburger', 'BCB141', 8.99, 0.40, 'Burgers', 90),
('Avocado Burger', 'AVB142', 7.99, 0.30, 'Burgers', 100),
('Pineapple Fried Rice', 'PFR143', 9.49, 0.40, 'Other', 70),
('Coconut Shrimp', 'COS144', 12.49, 0.50, 'Other', 60),
('Sweet Potato Fries', 'SPF145', 4.49, 0.20, 'Other', 130),
('Baked Sweet Potatoes', 'BSP146', 5.49, 0.30, 'Other', 120),
('Veggie Sushi Roll', 'VSR147', 8.49, 0.40, 'Other', 90),
('California Roll', 'CAR148', 9.99, 0.50, 'Other', 80),
('Spicy Tuna Roll', 'STR149', 10.49, 0.50, 'Other', 70),
('Dragon Roll', 'DGR150', 12.99, 0.60, 'Other', 60);

-- Key -> 12ab@3hsbv
-- 1234
-- manager123
-- cashier456
-- admin789
-- clerk888
-- shake999
INSERT INTO `admin` (name, phone, email, address, salary, position, dob, password) VALUES
('GodXero', '0770110422', 'godxero@heaven.com', '123 Burger St.', 300000.00, 'Manager', '2001-07-25', 'tsEZZ5/JSm1RiaoDfb9iDg=='),
('John Doe', '1234567890', 'john.doe@mos.com', '123 Burger St.', 3000.00, 'Manager', '1985-03-25', 'c59dNKuT74kUIbtIaIkbq1P5fnGZrLof'),
('Jane Smith', '9876543210', 'jane.smith@mos.com', '456 Submarine Ave.', 2500.00, 'Cashier', '1990-07-15',
'wwocnPAMdXG4j2kyTv9UWv6L231+ba3M'),
('Mark Lee', '1122334455', 'mark.lee@mos.com', '789 Fries Ln.', 3200.00, 'Manager', '1982-11-05', '02yHHX/gOg3dIm8K3dzzJb39dFI4hNXB'),
('Emily Clark', '2233445566', 'emily.clark@mos.com', '101 Dessert Rd.', 2800.00, 'Cashier', '1995-05-20', 'YblCJk8Un4E4YhaSI7ufuecuPP3hhrDh'),
('Alice Brown', '3344556677', 'alice.brown@mos.com', '202 Shake Blvd.', 2900.00, 'Manager', '1988-09-30', '1aSSqcRTrp+Qi+W2pqAHNKhUymg6ZbiY');

INSERT INTO customer (name, phone, email, address) VALUES
('Tom Hanks', '5551234567', 'tom.hanks@gmail.com', '25 Hollywood St.'),
('Sarah Connor', '5557654321', 'sarah.connor@gmail.com', '42 Sci-Fi Blvd.'),
('Jack Dawson', '5556789012', 'jack.dawson@gmail.com', '15 Titanic Ln.'),
('Rose DeWitt', '5558901234', 'rose.dewitt@gmail.com', '23 Drama Ave.'),
('Clark Kent', '5552468101', 'clark.kent@gmail.com', '77 Hero Rd.');

SELECT * FROM report;
SELECT * FROM food_item;
SELECT * FROM `admin`;
SELECT * FROM customer;
