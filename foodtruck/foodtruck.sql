create table if not exists foodtruck.CUSTOMERS
(
	ID int auto_increment
		primary key,
	NAME varchar(255) null,
	EMAIL varchar(255) null,
	ADDRESS varchar(255) null
);

create table if not exists foodtruck.ORDERS
(
	ID int auto_increment
		primary key,
	CUSTOMER_ID int null,
	SELLER_ID int null,
	ORDER_DATE datetime null,
	PREPARED_DATE datetime null,
	DELIVERY_DATE datetime null,
	constraint ORDERS_CUSTOMERS_ID_fk
		foreign key (CUSTOMER_ID) references foodtruck.CUSTOMERS (ID)
);

create table if not exists foodtruck.PRODUCTS
(
	ID int auto_increment
		primary key,
	DESCRIPTION varchar(500) null,
	PRICE decimal null
);

create table if not exists foodtruck.ORDERITEMS
(
	ID int auto_increment
		primary key,
	QUANTITY decimal null,
	PRODUCT_ID int null,
	ORDER_ID int null,
	UNIT_PRICE int null,
	constraint ORDERITEMS_ORDERS_ID_fk
		foreign key (ORDER_ID) references foodtruck.ORDERS (ID),
	constraint ORDERITEMS_PRODUCTS_ID_fk
		foreign key (PRODUCT_ID) references foodtruck.PRODUCTS (ID)
);

create table if not exists foodtruck.USERS
(
	ID int auto_increment
		primary key,
	NAME varchar(255) null,
	EMAIL varchar(255) null
);
