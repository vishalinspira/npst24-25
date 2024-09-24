DROP TABLE IF EXISTS "annexure_casesummary";
DROP SEQUENCE IF EXISTS annexure_casesummary_id__seq1;
CREATE SEQUENCE annexure_casesummary_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."annexure_casesummary" (
	"id_" integer NOT NULL,
	"form" character varying(100) NOT NULL,
    "amount" integer NOT NULL,
    "prannum" integer NOT NULL,
    "prandate" date NOT NULL,
    "oneiia" character varying(100) NOT NULL,
    "crediteddate" date NOT NULL,
    "tokennum" integer NOT NULL,
    "one" character varying(100) NOT NULL,
    "emaildate" date NOT NULL,
    "remittedamnt" integer NOT NULL,
    "remittedate" date NOT NULL,
    "oneiic" character varying(100) NOT NULL,
    "subreq" character varying(100) NOT NULL,
    "requestedamnt" integer NOT NULL,
    "documentdate" date NOT NULL,
    "carriedamnt" integer NOT NULL,
    "casenum1" character varying(100) NOT NULL,
    "prannum1" integer NOT NULL,
    "name1" character varying(100) NOT NULL,
    "Oneiiay1" character varying(100) NOT NULL,
    "trnsctndate1" date NOT NULL,
    "refnoid1" character varying(100) NOT NULL,
    "cradate1" date NOT NULL,
    "trnsamount1" integer NOT NULL,
    "oneiicy1" character varying(100) NOT NULL,
    "recttype1" integer NOT NULL,
    "reqamount1" integer NOT NULL,
    "reqmode1" character varying(100) NOT NULL,
    "rcvddate1" date NOT NULL,
    "cgmsdate1" date NOT NULL,
    "grievtxt1" character varying(100) NOT NULL,
    
    "reportMasterId" bigint NOT NULL,
    "reportdate" date NOT NULL,
    "createdon" date NOT NULL,
    "createdby" bigint NOT NULL,
    "modifyby" bigint NOT NULL,
    "modifydate" date NOT NULL,
    "reportuploadlogid" bigint,
    
    
    CONSTRAINT "annexure_casesummary_id_" PRIMARY KEY ("id_")
) WITH (oids = false);