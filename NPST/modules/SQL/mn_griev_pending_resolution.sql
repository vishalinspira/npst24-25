	
	DROP TABLE IF EXISTS "mn_griev_pending_resolution";
DROP SEQUENCE IF EXISTS mn_griev_pending_resolution_id__seq;
CREATE SEQUENCE mn_griev_pending_resolution_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."mn_griev_pending_resolution" (
    "id_" integer DEFAULT nextval('mn_griev_pending_resolution_id__seq') NOT NULL,
	
		"sector"	character varying,
		"grievances_escalated_to_pfrda"	integer,
		"grievances_resolved_in_45_days"	integer,
		"grievance_pending_post_45_days"	integer,
		"cra"	character varying,
     	
    "createdate" date,
    "createdby" bigint,
    "reportuploadlogid" bigint NULL
) WITH (oids = false);