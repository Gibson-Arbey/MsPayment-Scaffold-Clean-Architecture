CREATE TABLE refunds (
    refu_id BIGSERIAL PRIMARY KEY,
    paym_id BIGINT NOT NULL,
    refu_amount NUMERIC(15,2) NOT NULL,
    refu_reason VARCHAR(20) NOT NULL,
    refu_registeredat TIMESTAMPTZ NOT NULL,
    refu_createdat TIMESTAMP NOT NULL
);
