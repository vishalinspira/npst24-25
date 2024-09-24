DROP TABLE IF EXISTS "mn_collection_summary";
DROP SEQUENCE IF EXISTS mn_collection_summary_id__seq1;
CREATE SEQUENCE mn_collection_summary_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_collection_summary (
    id_ bigint NOT NULL,
    remittance_mode character varying(50) NOT NULL,
    auto_match_trans_no bigint NOT NULL,
    auto_match_amount numeric(15,2) NOT NULL,
    manual_match_trans_no bigint NOT NULL,
    manual_match_amount numeric(15,2) NOT NULL,
    tot_accepted_trans_no bigint NOT NULL,
    tot_accepted_amount numeric(15,2) NOT NULL,
    tot_rejected_trans_no bigint NOT NULL,
    tot_rejected_amount numeric(15,2) NOT NULL,
    grand_tot_trans_no bigint NOT NULL,
    grand_tot_amount numeric(15,2) NOT NULL,
    percentage_rejection numeric(3,2) NOT NULL,
    percentage_acceptance numeric(3,2) NOT NULL,
    cra character varying(10),
	"month" timestamp NOT NULL, --date
	
    createdon date,
    createdby bigint,
    reportuploadlogid bigint,
    CONSTRAINT mn_collection_summary_pkey PRIMARY KEY (id_)
) WITH (oids = false);

ALTER TABLE "mn_collection_summary"
ADD "month" timestamp;
COMMENT ON TABLE "mn_collection_summary" IS '';