DROP TABLE IF EXISTS "daily_rej_return_mis_elect";
DROP SEQUENCE IF EXISTS daily_rej_return_mis_elect_id__seq;
CREATE SEQUENCE daily_rej_return_mis_elect_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."daily_rej_return_mis_elect" (
    "id_" integer DEFAULT nextval('daily_rej_return_mis_elect_id__seq') NOT NULL,
	
	
	
	"sr_no" integer,
	"payment_id" bigint  NULL,
	"ret_ref_no" character varying  NULL,
	"source_acc_no_nodal" character varying  NULL,
	"source_acc_no_ifsc" character varying  NULL,
	"payment_receipt_date" date  NULL,
	"period" character varying  NULL,
	"utr_no" character varying  NULL,
	"mode_" character varying  NULL,
	"utr_amount" numeric(15,2)  NULL,
	"beneficiary_acc_no" bigint  NULL,
	"nps_virtual_acc_no" interger  NULL,
	"nps_acc_name" character varying  NULL,
	"return_date" date  NULL,
	"returned_utr" character varying  NULL,
	"tid" bigint  NULL,
	"reason_return" character varying  NULL,
	"additional_comments" character varying  NULL,
	"pao_name" character varying  NULL,
	"email_id" character varying  NULL,
	"address_line1" character varying  NULL,
	"address_line2" character varying  NULL,
	"pincode" interger  NULL,
	"phone_no1" bigint  NULL,
	"phone_no2" bigint  NULL,
	"sender_receiver_info" character varying  NULL,
	"return_tat" integer  NULL,
	"delay_remakrs" character varying  NULL,
	"outward_srno" character varying  NULL,
	"courier_consignment_no" character varying  NULL,
	"cra" character varying(100)	 not null,
    "createdate" date NOT NULL,
    "createdby" bigint NOT NULL,
    "reportuploadlogid" bigint
) WITH (oids = false);


		
			




