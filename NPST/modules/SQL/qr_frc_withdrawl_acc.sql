--DROP TABLE IF EXISTS "qr_frc_withdrawl_acc";
--DROP SEQUENCE IF EXISTS qr_frc_withdrawl_acc_id__seq1;
--CREATE SEQUENCE qr_frc_withdrawl_acc_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
--
--CREATE TABLE public.qr_frc_withdrawl_acc (
--	id_ bigint NOT NULL,
--	sno bigint,
--	std_pran bigint,
--	sub_name character varying(50),
--	type_ character varying(50),
--	pay_in_date date,
--	execution_date date,
--	net_amt numeric(15,2),
--	auditees_comment character varying(1000),
--	cra character varying(50),
--	createdon date,
--	createdby bigint,
--	CONSTRAINT qr_frc_withdrawl_acc_pkey PRIMARY KEY (id_)
--)WITH (oids = false);

DROP TABLE IF EXISTS "qr_frc_withdrawl_acc";
DROP SEQUENCE IF EXISTS qr_frc_withdrawl_acc_id__seq1;
CREATE SEQUENCE qr_frc_withdrawl_acc_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.qr_frc_withdrawl_acc (
	id_ bigint NOT NULL,
	sno bigint,
	std_pran bigint,
	sub_name character varying,
	type_ character varying,
	pay_in_date date,
	execution_date date,
	net_amt numeric(15,2),
	auditees_comment character varying,
	cra character varying,
	createdon date,
	createdby bigint,
	reportuploadlogid bigint,
	CONSTRAINT qr_frc_withdrawl_acc_pkey PRIMARY KEY (id_)
)WITH (oids = false);