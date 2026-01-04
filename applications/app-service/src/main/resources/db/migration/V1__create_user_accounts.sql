CREATE TABLE useraccounts (
    usac_id BIGSERIAL PRIMARY KEY,
    cust_id BIGINT NOT NULL,
    usac_balance NUMERIC(15,2) NOT NULL,
    usac_status VARCHAR(20) NOT NULL,
    usac_createdat DATE NOT NULL
);
