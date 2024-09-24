-- Adminer 4.7.1 PostgreSQL dump

DROP TABLE IF EXISTS "rejectionandreturn";
DROP SEQUENCE IF EXISTS rejectionandreturn_id__seq;
CREATE SEQUENCE rejectionandreturn_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."rejectionandreturn" (
    "id_" integer DEFAULT nextval('rejectionandreturn_id__seq') NOT NULL,
    "srno" bigint NOT NULL,
    "paymentid" bigint NOT NULL,
    "retrefno" character varying NOT NULL,
    "accountno" bigint NOT NULL,
    "ifscno" character varying NOT NULL,
    "pmtreciptdate" character varying NOT NULL,
    "period" character varying NOT NULL,
    "utrno" character varying NOT NULL,
    "mode_" character varying NOT NULL,
    "utramount" character varying NOT NULL,
    "beneficiaryacno" character varying NOT NULL,
    "npsvirtualacno" character varying NOT NULL,
    "npssectoracname" character varying NOT NULL,
    "returndate" character varying NOT NULL,
    "returnutr" character varying NOT NULL,
    "tid" character varying NOT NULL,
    "reasonofreturn" character varying NOT NULL,
    "commentofnps" character varying NOT NULL,
    "paoname" character varying NOT NULL,
    "emailid" character varying NOT NULL,
    "addressi" character varying NOT NULL,
    "addressii" character varying NOT NULL,
    "pincode" character varying NOT NULL,
    "phonei" character varying NOT NULL,
    "phoneii" character varying NOT NULL,
    "sendertoreceverinfo" character varying NOT NULL,
    "returntat" character varying NOT NULL,
    "remarks" character varying NOT NULL,
    "srnoforcuriar" character varying,
    "curiorno" character varying,
    "cra" character varying(10),
    "createdate" date NOT NULL,
    "createdby" bigint NOT NULL
) WITH (oids = false);


-- 2022-04-26 19:35:06.487418+05:30
