--DROP TABLE IF EXISTS "qr_acc_list";
--DROP SEQUENCE IF EXISTS qr_acc_list_id__seq1;
--CREATE SEQUENCE qr_acc_list_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
--
--CREATE TABLE public.qr_acc_list (
--	id_ bigint NOT NULL,
--	sno bigint,
--	nps_acc_no bigint,
--	nps_acc_name character varying(50),
--	cra character varying(10),
--	createdon date,
--	createdby bigint,
--	CONSTRAINT qr_acc_list_pkey PRIMARY KEY (id_)
--)WITH (oids = false);

DROP TABLE IF EXISTS "qr_acc_list";
DROP SEQUENCE IF EXISTS qr_acc_list_id__seq1;
CREATE SEQUENCE qr_acc_list_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.qr_acc_list (
	id_ bigint NOT NULL,
	sno bigint,
	nps_acc_no bigint,
	nps_acc_name character varying,
	cra character varying,
	createdon date,
	createdby bigint,
	reportuploadlogid bigint,
	CONSTRAINT qr_acc_list_pkey PRIMARY KEY (id_)
)WITH (oids = false);