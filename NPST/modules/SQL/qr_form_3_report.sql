
DROP TABLE IF EXISTS "qr_form_3_report";
DROP SEQUENCE IF EXISTS qr_form_3_report_id__seq1;
CREATE SEQUENCE qr_form_3_report_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.qr_form_3_report (
    id_ bigint NOT NULL,
    date_	date,
	pension_fund_name	character varying,
	sno character varying,
	type_purchase_or_sale	character varying,
	name_of_security	character varying,
	isin	character varying(12),
	name_of_issuer_or_company	character varying,
	transaction_cost  numeric,
	date_of_transaction	date,
	name	character varying,
	designation	character varying,
    createdon date NOT NULL,
    createdby bigint,
    
    reportuploadlogid bigint,
     CONSTRAINT qr_form_3_report_pkey PRIMARY KEY (id_)
) WITH (oids = false);

