CREATE TABLE orderdetail(
    id SERIAL NOT NULL,
    order_status varchar(255),
    total_amount numeric,
    PRIMARY KEY(id)
);