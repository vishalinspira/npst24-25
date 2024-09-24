DROP TABLE IF EXISTS "qr_majority_vote";
	DROP SEQUENCE IF EXISTS qr_majority_vote_id__seq;
	CREATE SEQUENCE qr_majority_vote_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

	CREATE TABLE "public"."qr_majority_vote" (
		"id_" integer DEFAULT nextval('qr_majority_vote_id__seq') NOT NULL,
		
		
		"company_name"	character varying,
		"isin"	character varying,
		"meeting_date"	date,
		"meeting_type"	character varying,
		"evoting_platform"	character varying,
		"resolution_sr_no"	numeric,
		"resolution_dtls"	character varying,
		"resolution_type"	character varying,
		"qty_prcnt_for"	character varying,
		"qty_prcnt_against"	character varying,
		"qty_prcnt_abstain"	character varying,
		"remarks"	character varying,
			
		"createdate" date,
		"createdby" bigint,
		"reportuploadlogid" bigint
	) WITH (oids = false);
	
	
	
		