--DROP TABLE IF EXISTS "qr_annexure_to_car";
--DROP SEQUENCE IF EXISTS qr_annexure_to_car_id__seq1;
--CREATE SEQUENCE qr_annexure_to_car_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
--
--CREATE TABLE public.qr_annexure_to_car (
--	id_ bigint NOT NULL,
--	sno bigint,
--	sum_closing_bal numeric(15,2),
--	avg_daily_closing_bal numeric(15,2),
--	annual_fee numeric(15,2),
--	cra character varying(10),
--	createdon date,
--	createdby bigint,
--	CONSTRAINT qr_annexure_to_car_pkey PRIMARY KEY (id_)
--)WITH (oids = false);

DROP TABLE IF EXISTS "qr_annexure_to_car";
DROP SEQUENCE IF EXISTS qr_annexure_to_car_id__seq1;
CREATE SEQUENCE qr_annexure_to_car_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.qr_annexure_to_car (
	id_ bigint NOT NULL,
	sno bigint,
	sum_closing_bal numeric(15,2),
	avg_daily_closing_bal numeric(15,2),
	annual_fee numeric(15,2),
	cra character varying,
	createdon date,
	createdby bigint,
	reportuploadlogid bigint,
	CONSTRAINT qr_annexure_to_car_pkey PRIMARY KEY (id_)
)WITH (oids = false);