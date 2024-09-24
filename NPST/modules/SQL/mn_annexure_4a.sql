DROP TABLE IF EXISTS "mn_annexure_4a";
DROP SEQUENCE IF EXISTS mn_annexure_4a_id_seq;
CREATE SEQUENCE mn_annexure_4a_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."mn_annexure_4a" (
    "id_" bigint DEFAULT nextval('mn_annexure_4a_id_seq') NOT NULL,
		"sno"	integer	not null,
		"pao_reg_no"	bigint	not null,
		"tran_id"	bigint	not null,
		"amt"	numeric(15,2)	not null,
		"file_ref_no"	character varying,	
		"record_no"	integer,	
		"receipt_date"	date,	
		"fund_realised_date"	date,	
		"frc_upload_date"	date,	
		"trust_bank_tat"	character varying,	
		"matching_type"	character varying,	
		"days"	integer,
		"cra"character varying	not null,
    "createdate" date NOT NULL,
	"createdby" bigint NOT NULL,
	"reportuploadlogid" bigint,
    CONSTRAINT "mn_annexure_4a_id_" PRIMARY KEY ("id_")
) WITH (oids = false);