DROP TABLE IF EXISTS "mn_consensus_voting";

CREATE TABLE "public"."mn_consensus_voting" (
    "id_" integer NOT NULL,
	"s_no"	integer,
	"pension_fund_name"	character varying,
	"no_of_resolutions_req_voting"	bigint,
	"voted_For"	bigint,
	"abstained"	bigint,
	"voted_against"	bigint,






    "createdon" date NOT NULL,
    "createdby" bigint
) WITH (oids = false);