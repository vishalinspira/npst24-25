

DROP TABLE IF EXISTS "daily_rej_return_mis_dremit";
DROP SEQUENCE IF EXISTS daily_rej_return_mis_dremit_id__seq;
CREATE SEQUENCE daily_rej_return_mis_dremit_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."daily_rej_return_mis_dremit" (
    "id_" integer DEFAULT nextval('daily_rej_return_mis_dremit_id__seq') NOT NULL,
	
	"value_date"	timestamp without time zone 	 NOT NULL,
	"trans_date"	timestamp without time zone 	 NOT NULL,
	"payment_id"	bigint	 NOT NULL,
	"return_ref"	character varying,
	"message_type"	character varying	 NOT NULL,
	"sender_name"	character varying	 NOT NULL,
	"utr_number"	character varying	 NOT NULL,
	"sender_ifsc"	character varying	 NOT NULL,
	"sender_acc_number"	bigint	 NOT NULL,
	"beneficiary_ifsc"	character varying	 NOT NULL,
	"beneficiary_acc_number"	integer,
	"credit_acc_number"	bigint	 NOT NULL,
	"corporate_code"	integer 	 NOT NULL,
	"beneficiary_acc_name"	character varying	 NOT NULL,
	"sender_info"	character varying,
	"amount"	numeric	,
	"beneficiary_acc_type"	character varying	NOT NULL,
	"payment_status"	character varying NOT NULL,
	"return_date"	timestamp without time zone NOT NULL,
	"sender_receiver_info"	character varying,	 
	"pop_reg_no"	integer,	
	"pop_sp_reg_no"	integer,	
	"filler_ref1"	character varying, 
	"filler_ref2" character varying, 
	"filler_ref3" character varying,
	"filler_ref4" character varying, 
	"comments"	character varying,
	"cra_id"	integer	,
	"reason_return"	character varying	NOT NULL,
	"cra" character varying,
    "createdate" date,
    "createdby" bigint,
    "reportuploadlogid" bigint
    
    --"returndate" character varying
) WITH (oids = false);
		





