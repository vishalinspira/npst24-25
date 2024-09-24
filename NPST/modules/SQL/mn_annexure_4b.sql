DROP TABLE IF EXISTS "mn_annexure_4b";
DROP SEQUENCE IF EXISTS mn_annexure_4b_id_seq;
CREATE SEQUENCE mn_annexure_4b_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."mn_annexure_4b" (
    "id_" bigint DEFAULT nextval('mn_annexure_4b_id_seq') NOT NULL,
		"sno"	integer	not null,
		"payment_id"	bigint	not null,
		"source_acc_no_nodal"	bigint	,
		"ifsc_source_no"	character varying,	
		"payment_receipt_date"	date,	
		"utr_no"	character varying,	
		"mode"	character(1),	
		"utr_amount"	numeric(15,2),	
		"nps_virtual_acc_no"	integer,	
		"nps_acc_number"	bigint	,
		"return_date"	date,	
		"returned_utr"	character varying,	
		"tid"	bigint,
		"return_reason"	character varying,
		"delay_reason"	character varying,
		"cra"character varying	not null,
    "createdate" date NOT NULL,
	"createdby" bigint NOT NULL,
	"reportuploadlogid" bigint,
    CONSTRAINT "mn_annexure_4b_id_" PRIMARY KEY ("id_")
) WITH (oids = false);