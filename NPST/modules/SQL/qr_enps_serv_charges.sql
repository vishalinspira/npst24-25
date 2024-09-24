--DROP TABLE IF EXISTS "qr_enps_serv_charges";
--DROP SEQUENCE IF EXISTS qr_enps_serv_charges_id__seq1;
--CREATE SEQUENCE qr_enps_serv_charges_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
--
--CREATE TABLE public.qr_enps_serv_charges (
--	id_ bigint NOT NULL,
--	sno bigint,
--	nps_acc_no bigint,
--	nps_acc_name character varying(50),
--	tran_date date,
--	debit_trans numeric(15,2),
--	credit_trans numeric(15,2),
--	amt numeric(15,2),
--	auditee_remarks character varying(1000),
--	cra character varying(10),
--	createdon date,
--	createdby bigint,
--	CONSTRAINT qr_enps_serv_charges_pkey PRIMARY KEY (id_)
--)WITH (oids = false);

DROP TABLE IF EXISTS "qr_enps_serv_charges";
DROP SEQUENCE IF EXISTS qr_enps_serv_charges_id__seq1;
CREATE SEQUENCE qr_enps_serv_charges_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.qr_enps_serv_charges (
	id_ bigint NOT NULL,
	sno bigint,
	nps_acc_no bigint,
	nps_acc_name character varying,
	tran_date date,
	debit_trans numeric(15,2),
	credit_trans numeric(15,2),
	amt numeric(15,2),
	auditee_remarks character varying,
	cra character varying,
	createdon date,
	createdby bigint,
	reportuploadlogid bigint,
	CONSTRAINT qr_enps_serv_charges_pkey PRIMARY KEY (id_)
)WITH (oids = false);