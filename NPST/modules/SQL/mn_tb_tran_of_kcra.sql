DROP TABLE IF EXISTS "mn_tb_tran_of_kcra";
DROP SEQUENCE IF EXISTS mn_tb_tran_of_kcra_id_seq;
CREATE SEQUENCE mn_tb_tran_of_kcra_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."mn_tb_tran_of_kcra" (
    "id_" bigint DEFAULT nextval('mn_tb_tran_of_kcra_id_seq') NOT NULL,
		"no_of_accepted_tran"	integer,
		"no_of_rejected_tran"	integer,
		"total_transactions"	integer,
		"rejection_prcnt"	numeric(6,2),
		"cra" character varying	not null,
		"month" timestamp not null, --date
    "createdate" date NOT NULL,
	"createdby" bigint NOT NULL,
	"reportuploadlogid" bigint,
    CONSTRAINT "mn_tb_tran_of_kcra_id_" PRIMARY KEY ("id_")
) WITH (oids = false);


ALTER TABLE "mn_tb_tran_of_kcra"
ADD "month" timestamp; --date
COMMENT ON TABLE "mn_tb_tran_of_kcra" IS '';