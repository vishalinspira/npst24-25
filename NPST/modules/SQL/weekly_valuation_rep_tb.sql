
	DROP TABLE IF EXISTS "weekly_valuation_rep_tb";
	CREATE TABLE "public"."weekly_valuation_rep_tb" (
		"id_" bigint NOT NULL,
		"client_code"	character varying(40),
		"isin"	character varying(40),
		"isin_desc"	character varying(40),
		"maturity_date"	date,
		"free_securities_rs"	bigint,
		"pledge_value_held_rs"	bigint,
		"securities_in_transit_rs"	bigint,
		"total_face_value_held_rs"	bigint,
		"rate"	numeric(15,4),
		"value"	numeric(15,4),
		"createdate" date,
		"createdby" bigint,
		"reportuploadlogid" bigint
	) WITH (oids = false);