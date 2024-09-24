	

DROP TABLE IF EXISTS "cl_bal";
DROP SEQUENCE IF EXISTS excelaccountstatement_id__seq;
CREATE SEQUENCE excelaccountstatement_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."cl_bal" (
    "id_" integer DEFAULT nextval('excelaccountstatement_id__seq') NOT NULL,
    "cust_id" bigint,
    "nps_acc_no" bigint,
    "nps_acc_name" character varying,
    "opening_bal" character varying,
    "total_credit" character varying,
    "total_debit" character varying,
    "closing_bal" character varying,
    "createdate" date,
    "createdby" bigint,
    "reportuploadlogid" bigint
) WITH (oids = false)


cust_id	bigint 	 not null
nps_acc_number	bigint 	 not null change
nps_acc_name	character varying(100)	 not null
opening_bal	numeric(15,2)	 not null
total_credit	numeric(15,2)	 not null
total_debit	numeric(15,2)	 not null
closing_bal	numeric(15,2)	 not null