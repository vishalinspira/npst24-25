DROP TABLE IF EXISTS "qr_custodian_bills";
DROP SEQUENCE IF EXISTS qr_custodian_bills_id__seq1;
CREATE SEQUENCE qr_custodian_bills_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.qr_custodian_bills (
    id_ bigint NOT NULL,
	scheme_name character varying,
	transaction_charges_trades numeric,
	custody_charges_demat_holdings numeric,
	nsccl_ccil_charges numeric,
	sebi_charges numeric,
	gross_bill_as_per_pf numeric,
	bill_value_excluding_gst numeric,
	cgst numeric,
	sgst numeric,
	bill_value_including_gst numeric,
	month_ date,
	account_number numeric,
    createdon date,
    createdby bigint,
    CONSTRAINT qr_custodian_bills_pkey PRIMARY KEY (id_)
)WITH (oids = false);