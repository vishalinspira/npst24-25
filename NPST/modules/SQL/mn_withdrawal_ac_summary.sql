	DROP TABLE IF EXISTS "mn_withdrawal_ac_summary";
	DROP SEQUENCE IF EXISTS mn_withdrawal_ac_summary_id__seq;
	CREATE SEQUENCE mn_withdrawal_ac_summary_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

	CREATE TABLE "public"."mn_withdrawal_ac_summary" (
		"id_" integer DEFAULT nextval('mn_withdrawal_ac_summary_id__seq') NOT NULL,
		
		
		"instruction_date"	date,
		"pran_pao"	bigint,
		"subscriber_name"	character varying,
		"payee_name"	character varying,
		"payee_bank_ifsc"	character varying,
		"payee_bank_acc_no"	bigint,
		"net_amt"	numeric(15,2),
		"pay_in_date"	date,
		"execution_date"	date,
		"utr_no"	character varying,
		"status"	character varying,
		"return_reason"	character varying,
		"standard_reason"	character varying,
		"cra"character varying	not null,	
			
		"createdate" date,
		"createdby" bigint,
		"reportuploadlogid" bigint
	) WITH (oids = false);