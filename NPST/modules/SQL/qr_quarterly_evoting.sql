DROP TABLE IF EXISTS "qr_quarterly_evoting";
	DROP SEQUENCE IF EXISTS qr_quarterly_evoting_id__seq;
	CREATE SEQUENCE qr_quarterly_evoting_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

	CREATE TABLE "public"."qr_quarterly_evoting" (
		"id_" integer DEFAULT nextval('qr_quarterly_evoting_id__seq') NOT NULL,
		
		
		"pension_fund_name"	character varying,
		"meeting_date"	date,
		"company_name"	character varying,
		"type_of_meetings"	character varying,
		"resolution_by_shareholder"	character varying,
		"resolution_description"	character varying,
		"investee_company_recomm"	character varying,
		"pf_voting_recomm"	character varying,
		"pf_rationale_recomm"	character varying,
		"final_vote"	character varying,
			
		"createdate" date,
		"createdby" bigint
	) WITH (oids = false);
	
	
	
		