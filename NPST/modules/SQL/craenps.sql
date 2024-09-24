DROP TABLE IF EXISTS "craenps";
CREATE TABLE "public"."craenps" (
	"id_" bigint NOT NULL,
    "reportuploadlogid" bigint ,
    "srno" integer,
    "pran" bigint,
	"subname" character varying,
	"claimentname" character varying,
    "authclaimdate" date,
    "claimdispatchdate" date,
    "trackid" bigint,
    "receiptauthformdate" date,
    "claimprocessdate" date,
	"tat" integer,
	"createdby" bigint,
	"createdate" date,
	"uuid_" character varying
) WITH (oids = false);