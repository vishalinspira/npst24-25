drop table if exists "status_of_nps_lite";

create table public.status_of_nps_lite(
    id_ bigint not null primary key,
	as_on_date date,
    total_number_of_aggregators integer,
    total_no_of_subscribers_reg integer,
	total_contribution_amount numeric,
	contrib_amt_matched_booked numeric,
	amt_pend_matching_booking numeric,
	total_aum numeric,
	cra character varying,
	createdon date,
	createdby bigint,
	reportuploadlogid bigint);