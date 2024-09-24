
	DROP TABLE IF EXISTS "mn_website_june";
	CREATE TABLE "public"."mn_website_june" (
		"id_" bigint NOT NULL,
		"date_" date,
		"nps" character varying(40),
		"no_of_subscribers" bigint,
		"total_contribution_m_and_b" numeric(20,2),
		"aum" numeric(20,2),
		"cra" character varying(40),
		"createdate" date,
		"createdby" bigint,
		reportuploadlogid bigint,
	) WITH (oids = false);

	