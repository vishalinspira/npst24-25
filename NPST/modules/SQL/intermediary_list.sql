DROP TABLE IF EXISTS "intermediary_list";
DROP SEQUENCE IF EXISTS intermediary_list_id__seq;
CREATE SEQUENCE intermediary_list_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

CREATE TABLE "public"."intermediary_list" (
    "id_" integer DEFAULT nextval('intermediary_list_id__seq') NOT NULL,
    "intermediarytype" bigint NOT NULL,
    "intermediaryname" character varying NOT NULL,
    CONSTRAINT "intermediary_list_pkey" PRIMARY KEY ("id_")
) WITH (oids = false);

INSERT INTO "intermediary_list" ("id_", "intermediarytype", "intermediaryname") VALUES
(1,	1,	'KCRA'),
(2,	1,	'NCRA'),
(3,	1,	'CAMS');
(4,	2,	'SBI'),
(5,	2,	'HDFC'),
(6,	2,	'LIC'),
(7,	2,	'UTI'),
(8,	2,	'ICICI'),
(9,	2,	'Kotak'),
(10, 2,	'ABS'),
(11, 3,	'Deutsche'),

