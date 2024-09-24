--DROP TABLE IF EXISTS "qr_avg_amt";
--DROP SEQUENCE IF EXISTS qr_avg_amt_id__seq1;
--CREATE SEQUENCE qr_avg_amt_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
--
--CREATE TABLE public.qr_avg_amt (
--	id_ bigint NOT NULL,
--	sno bigint,
--	month_ character varying(50),
--	nps_acc_no bigint,
--	nps_acc_name character varying(50),
--	average numeric(15,2),
--	cra character varying(10),
--	createdon date,
--	createdby bigint,
--	CONSTRAINT qr_avg_amt_pkey PRIMARY KEY (id_)
--)WITH (oids = false);

DROP TABLE IF EXISTS "qr_avg_amt";
DROP SEQUENCE IF EXISTS qr_avg_amt_id__seq1;
CREATE SEQUENCE qr_avg_amt_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.qr_avg_amt (
	id_ bigint NOT NULL,
	sno bigint,
	month_ character varying,
	nps_acc_no bigint,
	nps_acc_name character varying,
	average numeric(15,2),
	cra character varying,
	createdon date,
	createdby bigint,
	reportuploadlogid bigint,
	CONSTRAINT qr_avg_amt_pkey PRIMARY KEY (id_)
)WITH (oids = false);