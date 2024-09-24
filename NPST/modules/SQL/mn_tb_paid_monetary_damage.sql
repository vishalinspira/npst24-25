DROP TABLE IF EXISTS "mn_tb_paid_monetary_damage";
DROP SEQUENCE IF EXISTS mn_tb_paid_monetary_damage_id_seq;
CREATE SEQUENCE mn_tb_paid_monetary_damage_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."mn_tb_paid_monetary_damage" (
    "id_" bigint DEFAULT nextval('mn_tb_paid_monetary_damage_id_seq') NOT NULL,
		"sno"	integer	,
		"nps_acc_name"	character varying	,
		"amt_involved"	numeric(15,2)	,
		"delay_days"	integer	,
		"comp_paid"	numeric(15,2)	,
		"remarks"	character varying,
		"cra"character varying	,
    "createdate" date NOT NULL,
	"createdby" bigint NOT NULL,
	"reportuploadlogid" bigint,
    CONSTRAINT "mn_tb_paid_monetary_damage_id_" PRIMARY KEY ("id_")
) WITH (oids = false);