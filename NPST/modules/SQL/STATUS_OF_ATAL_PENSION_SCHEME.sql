drop table if exists "status_of_atal_pension_scheme";

create table public.status_of_atal_pension_scheme(
    id_ bigint not null primary key,
	as_on_date date,
    tot_no_of_banks_reg_under_apy integer,
    total_num_of_subs_reg integer,
	total_contribution_amount numeric,
	contrib_amt_matched_booked numeric,
	amt_pend_matching_booking numeric,
	total_aum numeric,
	cra character varying,
	createdon date,
	createdby bigint,
	reportuploadlogid bigint);