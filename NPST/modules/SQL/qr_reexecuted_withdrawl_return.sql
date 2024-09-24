--DROP TABLE IF EXISTS "qr_reexecuted_withdrawl_return";
--DROP SEQUENCE IF EXISTS qr_reexecuted_withdrawl_return_id__seq1;
--CREATE SEQUENCE qr_reexecuted_withdrawl_return_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
--
--CREATE TABLE public.qr_reexecuted_withdrawl_return (
--	id_ bigint NOT NULL,
--	sno bigint,
--	std_pran bigint,
--	process_type character varying(50),
--	name_ character varying(50),
--	net_amt numeric(15,2),
--	pay_in_date date,
--	execution_date date,
--	auditee_comment character varying(1000),
--	return_reason character varying(50),
--	cra character varying(50),
--	return_type character varying(50),
--	createdon date,
--	createdby bigint,
--	CONSTRAINT qr_reexecuted_withdrawl_return_pkey PRIMARY KEY (id_)
--)WITH (oids = false);

DROP TABLE IF EXISTS "qr_reexecuted_withdrawl_return";
DROP SEQUENCE IF EXISTS qr_reexecuted_withdrawl_return_id__seq1;
CREATE SEQUENCE qr_reexecuted_withdrawl_return_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.qr_reexecuted_withdrawl_return (
	id_ bigint NOT NULL,
	sno bigint,
	std_pran bigint,
	process_type character varying,
	name_ character varying,
	net_amt numeric(15,2),
	pay_in_date date,
	execution_date date,
	auditee_comment character varying,
	return_reason character varying,
	cra character varying,
	return_type character varying,
	createdon date,
	createdby bigint,
	reportuploadlogid bigint,
	CONSTRAINT qr_reexecuted_withdrawl_return_pkey PRIMARY KEY (id_)
)WITH (oids = false);