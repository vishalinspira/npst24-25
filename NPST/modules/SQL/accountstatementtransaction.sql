-- Adminer 4.7.1 PostgreSQL dump

DROP TABLE IF EXISTS "accountstatementtransaction";
DROP SEQUENCE IF EXISTS accountstatementtransuction_tranc_id_seq;
CREATE SEQUENCE accountstatementtransuction_tranc_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."accountstatementtransaction" (
    "transid" integer DEFAULT nextval('accountstatementtransuction_tranc_id_seq') NOT NULL,
    "trandate" date NOT NULL,
    "chqno" bigint NOT NULL,
    "particulars" bigint NOT NULL,
    "debit" bigint NOT NULL,
    "credit" bigint NOT NULL,
    "balance" bigint NOT NULL,
    "initbr" bigint NOT NULL,
    "accid" bigint NOT NULL,
    "reportuploadlogid" bigint,
    CONSTRAINT "accountstatementtransuction_tranc_id" PRIMARY KEY ("transid"),
    CONSTRAINT "accountstatementtransuction_accountstatement_id_fkey" FOREIGN KEY (accid) REFERENCES accountstatement(id_) NOT DEFERRABLE
) WITH (oids = false);


-- 2022-04-26 19:32:22.3412+05:30
