DROP TABLE IF EXISTS "accountstatement";
DROP SEQUENCE IF EXISTS accountstatement_id__seq1;
CREATE SEQUENCE accountstatement_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."accountstatement" (
    "id_" integer DEFAULT nextval('accountstatement_id__seq1') NOT NULL,
    "accountname" character varying(100) NOT NULL,
    "statement" character varying(100) NOT NULL,
    "adressi" character varying(100) NOT NULL,
    "adressii" character varying(100) NOT NULL,
    "adressiii" character varying(100) NOT NULL,
    "adressiv" character varying(100) NOT NULL,
    "adressv" character varying(100) NOT NULL,
    "pin" bigint NOT NULL,
    "customerno" bigint NOT NULL,
    "scheme" character varying(100) NOT NULL,
    "curencyinr" character varying(100) NOT NULL,
    "statementacno" bigint NOT NULL,
    "formdate" date NOT NULL,
    "todebit" bigint NOT NULL,
    "tocredit" bigint NOT NULL,
    "clbalance" bigint NOT NULL,
    "clinitbr" bigint NOT NULL,
    "todate" date NOT NULL,
    "uuid_" character varying(75),
    "createdby" bigint,
    "createdate" timestamp,
    "modifyby" bigint,
    "modifydate" timestamp,
    "status" integer,
    "statusbyuserid" bigint,
    "statusbyusername" character varying(75),
    "statusdate" timestamp,
    CONSTRAINT "accountstatement_id_" PRIMARY KEY ("id_")
) WITH (oids = false);


-- 2022-04-26 18:29:33.935563+05:30
