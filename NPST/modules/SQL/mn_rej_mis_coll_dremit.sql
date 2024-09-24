DROP TABLE IF EXISTS "mn_rej_mis_coll_dremit";
	DROP SEQUENCE IF EXISTS mn_rej_mis_coll_dremit_id__seq;
	CREATE SEQUENCE mn_rej_mis_coll_dremit_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

	CREATE TABLE "public"."mn_rej_mis_coll_dremit" (
		"id_" integer DEFAULT nextval('mn_rej_mis_coll_dremit_id__seq') NOT NULL,
		
		
		"virtual_id" bigint,
		"request_type" character varying  NULL,
        "sender_name" character varying  NULL,
        "pop_reg_no" integer,
     	"pop_sp_reg_no" integer,
        "nps_acc_number" bigint,
        "transaction_date" date,
        "transaction_amount" numeric(15,2),
        "transaction_ref_num" character varying  NULL,
        "transaction_sts" character varying  NULL,
		"filler_ref1"character varying  NULL,
     	"filler_ref2"character varying  NULL,
        "filler_ref3"character varying  NULL,
        "filler_ref4"character varying  NULL,
        "comments" character varying  NULL,
        "returned_date" date,
        "cra" character varying,	
			
			
		"createdate" date,
		"createdby" bigint,
		"reportuploadlogid" bigint
	) WITH (oids = false);