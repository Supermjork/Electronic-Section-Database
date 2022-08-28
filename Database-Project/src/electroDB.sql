CREATE TABLE seller(seller_id SERIAL PRIMARY KEY,
                    seller_name VARCHAR(255),
                    seller_password VARCHAR(255));

CREATE TABLE systemAdmin(system_id INT PRIMARY KEY,
                         adminName VARCHAR(255),
                         admin_Password VARCHAR(255));

CREATE TABLE customer(user_id SERIAL PRIMARY KEY,
                      username VARCHAR(255),
                      customer_password VARCHAR(255),
                      address_city VARCHAR(255),
                      address_district VARCHAR(255),
                      address_zipcode VARCHAR(255));

CREATE TABLE handheld(inventory_id VARCHAR(255) PRIMARY KEY,
                      brand VARCHAR(255) NOT NULL,
                      manufacturer VARCHAR(255) NOT NULL,
                      reviewrating NUMERIC(2, 1) NOT NULL,
                      price NUMERIC(10, 2) NOT NULL,
                      amountinstock INTEGER NOT NULL,
                      devicetype VARCHAR(255) NOT NULL,
                      screensize NUMERIC(5,2) NOT NULL,
                      devicestorage INTEGER NOT NULL);

CREATE TABLE camera(inventory_id VARCHAR(255) PRIMARY KEY,
                    brand VARCHAR(255) NOT NULL,
                    manufacturer VARCHAR(255) NOT NULL,
                    reviewRating NUMERIC(2, 1) NOT NULL,
                    price NUMERIC(10, 2) NOT NULL,
                    amountinstock INTEGER NOT NULL,
                    focallength NUMERIC(4,2) NOT NULL,
                    cameratype VARCHAR(255) NOT NULL);

CREATE TABLE computer(inventory_id VARCHAR(255) PRIMARY KEY,
                      brand VARCHAR(255) NOT NULL,
                      manufacturer VARCHAR(255) NOT NULL,
                      reviewrating NUMERIC(2, 1) NOT NULL,
                      price NUMERIC(10, 2) NOT NULL,
                      amountinstock INTEGER NOT NULL,
                      cpu VARCHAR(255) NOT NULL,
                      gpu VARCHAR(255) NOT NULL,
                      operatingsys VARCHAR(255) NOT NULL,
                      ram INTEGER NOT NULL,
                      devicestorage INTEGER NOT NULL,
                      devicetype VARCHAR(255) NOT NULL);

CREATE TABLE lists(seller_id INTEGER NOT NULL,
                   inventory_id VARCHAR(255) NOT NULL,
                   admin_id INTEGER NOT NULL);

--Need to split the multivalued into other tables
CREATE TABLE orders(order_id SERIAL PRIMARY KEY,
                    customer_id INTEGER NOT NULL,
                    seller_id INTEGER NOT NULL,
                    device_id VARCHAR(255) NOT NULL,
                    order_date DATE NOT NULL);

CREATE TABLE issueorder(order_id INTEGER NOT NULL,
                        inventory_id VARCHAR(255) NOT NULL,
                        seller_id INTEGER NOT NULL,
                        customer_id INTEGER NOT NULL);