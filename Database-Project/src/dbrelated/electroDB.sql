CREATE TABLE seller(seller_id INTEGER PRIMARY KEY,
                    seller_name VARCHAR(255) NOT NULL,
                    seller_password VARCHAR(255) NOT NULL);

CREATE TABLE systemAdmin(system_id INTEGER PRIMARY KEY,
                         adminName VARCHAR(255) NOT NULL,
                         admin_Password VARCHAR(255) NOT NULL);

CREATE TABLE customer(user_id INTEGER PRIMARY KEY,
                      username VARCHAR(255) NOT NULL ,
                      customer_password VARCHAR(255) NOT NULL ,
                      address_city VARCHAR(255) NOT NULL ,
                      address_district VARCHAR(255) NOT NULL ,
                      address_zipcode VARCHAR(255) NOT NULL );

--All devices will have their own unique ID, instead of ID being for the batch of same device
--Displaying the stock amount can just use the COUNT function, going over the device name
CREATE TABLE handheld(inventory_id VARCHAR(255) PRIMARY KEY,
                      brand VARCHAR(255) NOT NULL,
                      manufacturer VARCHAR(255) NOT NULL,
                      reviewrating NUMERIC(2, 1) NOT NULL,
                      price NUMERIC(10, 2) NOT NULL,
                      devicetype VARCHAR(255) NOT NULL,
                      screensize NUMERIC(5,2) NOT NULL,
                      devicestorage INTEGER NOT NULL);

CREATE TABLE camera(inventory_id VARCHAR(255) PRIMARY KEY,
                    brand VARCHAR(255) NOT NULL,
                    manufacturer VARCHAR(255) NOT NULL,
                    reviewRating NUMERIC(2, 1) NOT NULL,
                    price NUMERIC(10, 2) NOT NULL,
                    focallength NUMERIC(4,2) NOT NULL,
                    cameratype VARCHAR(255) NOT NULL);

CREATE TABLE computer(inventory_id VARCHAR(255) PRIMARY KEY,
                      brand VARCHAR(255) NOT NULL,
                      manufacturer VARCHAR(255) NOT NULL,
                      reviewrating NUMERIC(2, 1) NOT NULL,
                      price NUMERIC(10, 2) NOT NULL,
                      cpu VARCHAR(255) NOT NULL,
                      gpu VARCHAR(255),
                      operatingsys VARCHAR(255) NOT NULL,
                      ram INTEGER NOT NULL,
                      devicestorage INTEGER NOT NULL,
                      devicetype VARCHAR(255) NOT NULL);

CREATE TABLE lists(seller_id INTEGER NOT NULL,
                   inventory_id VARCHAR(255) NOT NULL,
                   admin_id INTEGER NOT NULL,
                   CONSTRAINT fk1 FOREIGN KEY (seller_id) REFERENCES seller(seller_id),
                   CONSTRAINT fk2_1 FOREIGN KEY (inventory_id) REFERENCES handheld(inventory_id),
                   CONSTRAINT fk2_2 FOREIGN KEY (inventory_id) REFERENCES computer(inventory_id),
                   CONSTRAINT fk2_3 FOREIGN KEY (inventory_id) REFERENCES camera(inventory_id),
                   CONSTRAINT pk PRIMARY KEY(seller_id, inventory_id));

/*Essentially wrapping the issueOrder data and having them refer to the same orderID in
  a separate table
  Need to split the multivalued into other tables [Done]
 */
CREATE TABLE orders(order_id SERIAL PRIMARY KEY,
                    customer_id INTEGER NOT NULL,
                    order_date DATE NOT NULL,
                    CONSTRAINT fk FOREIGN KEY (customer_id) REFERENCES issueorder(customer_id));

CREATE TABLE orders1(order_id INTEGER NOT NULL,
                     seller_id INTEGER NOT NULL,
                     device_id VARCHAR(255) NOT NULL,
                     CONSTRAINT fk1 FOREIGN KEY (order_id) REFERENCES orders(order_id),
                     CONSTRAINT fk2 FOREIGN KEY (seller_id) REFERENCES issueorder(seller_id),
                     CONSTRAINT fk3 FOREIGN KEY (device_id) REFERENCES issueorder(inventory_id),
                     CONSTRAINT pk PRIMARY KEY(order_id, device_id));

--Issued orders get put into orders1, under the same serialised order_ID
--seller_id can be traced through the item's ID in the "lists" table
CREATE TABLE issueorder(inventory_id VARCHAR(255) NOT NULL,
                        seller_id INTEGER NOT NULL,
                        customer_id INTEGER,
                        CONSTRAINT fk1_1 FOREIGN KEY (inventory_id) REFERENCES handheld(inventory_id),
                        CONSTRAINT fk1_2 FOREIGN KEY (inventory_id) REFERENCES computer(inventory_id),
                        CONSTRAINT fk1_3 FOREIGN KEY (inventory_id) REFERENCES camera(inventory_id),
                        CONSTRAINT fk2 FOREIGN KEY (seller_id) REFERENCES lists(seller_id),
                        CONSTRAINT fk3 FOREIGN KEY (customer_id) REFERENCES customer(user_id));