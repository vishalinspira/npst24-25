DROP TABLE IF EXISTS "an_annual_proxy_voting_report";
	DROP SEQUENCE IF EXISTS an_annual_proxy_voting_report_id__seq;
	CREATE SEQUENCE an_annual_proxy_voting_report_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

	CREATE TABLE "public"."an_annual_proxy_voting_report" (
		"id_" integer DEFAULT nextval('an_annual_proxy_voting_report_id__seq') NOT NULL,
		
		
		"pension_fund_name"	character varying,
		"meeting_date"	date,
		"company_name"	character varying,
		"type_of_meeting"	character varying,
		"proposal_by_shareholder"	character varying,
		"resolution_description"	character varying,
		"investee_company_recomm"	character varying,
		"pf_voting_recomm"	character varying,
		"pf_rationale_for_voting_recomm"	character varying,
		"final_vote"	character varying,
			
		"createdate" date,
		"createdby" bigint
	) WITH (oids = false);
	
	
	
		