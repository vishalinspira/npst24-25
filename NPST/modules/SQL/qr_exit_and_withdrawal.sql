DROP TABLE IF EXISTS "qr_exit_and_withdrawal";
DROP SEQUENCE IF EXISTS qr_exit_and_withdrawal_id__seq1;
CREATE SEQUENCE qr_exit_and_withdrawal_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE IF NOT EXISTS public.qr_exit_and_withdrawal
(
    id_ bigint NOT NULL,
    fiscal_year character varying,
    cra character varying,
    exits_and_withdrawals_during character varying,
    quarter character varying,
    values_ numeric, 
    createdon date,
    createdby bigint,
     "reportuploadlogid" bigint NULL
    CONSTRAINT qr_exit_and_withdrawal_pkey PRIMARY KEY (id_)
) WITH (oids = false);