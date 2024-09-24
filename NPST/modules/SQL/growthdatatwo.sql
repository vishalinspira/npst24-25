DROP TABLE IF EXISTS "growthdatatwo";
	DROP SEQUENCE IF EXISTS growthdatatwo_id__seq;
	CREATE SEQUENCE growthdatatwo_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

	CREATE TABLE "public"."growthdatatwo" (
		"id_" integer DEFAULT nextval('growthdatatwo_id__seq') NOT NULL,
		
		
		"srno" character varying,
    	"pfm" character varying,
    	"thirteen_fourteen" character varying,
    	"fourteen_fifteen" character varying,
    	"fifteen_sixteen" character varying,
    	"sixteen_seventeen" character varying,
    	"seventeen_eighteen" character varying,
    	"eighteen_nineteen" character varying,
    	"nineteen_twenty" character varying,
    	"twenty_twentyone" character varying,
    	"twentyone_twentytwo" character varying,
    	"july_twentyone" character varying,
    	"july_twentytwo" character varying,
    	"growth_amount" character varying,
    	"growth_percentage" character varying,
    	"previous_amount" character varying,
    	"previous_percentage" character varying,
    	"current_amount" character varying,
    	"current_percentage" character varying,
			
		"createdate" date,
		"createdby" bigint
	) WITH (oids = false);
	
	
	
		