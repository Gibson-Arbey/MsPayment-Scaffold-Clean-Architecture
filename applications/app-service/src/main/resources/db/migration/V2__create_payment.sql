CREATE TABLE payments (
    paym_id BIGSERIAL PRIMARY KEY,
    usac_id BIGINT NOT NULL,
    paym_amount NUMERIC(15,2) NOT NULL,
    paym_currency VARCHAR(20) NOT NULL,
    paym_status VARCHAR(20) NOT NULL,
    paym_registeredat TIMESTAMPTZ NOT NULL,
    paym_createdat TIMESTAMP NOT NULL
);
