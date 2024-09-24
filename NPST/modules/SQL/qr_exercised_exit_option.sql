DROP TABLE IF EXISTS "qr_exercised_exit_option";
CREATE TABLE IF NOT EXISTS public.qr_exercised_exit_option
(
    id_ bigint NOT NULL,
    cra character varying,
	year character varying,
	quarter character varying,
	sector character varying,
	partial_withdrawal_req_prcd integer,
	gsec_subs_exercised_family_pn integer,
	subscriber_deferred_lumpsum integer,
	subscriber_who_deferred_anuty integer,
	voluntarily_continued_sub integer,
	auto_continued_subscriber integer,
    createdon date,
    createdby bigint
) WITH (oids = false);