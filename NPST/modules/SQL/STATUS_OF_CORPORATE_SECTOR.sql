drop table if exists "status_of_corporate_sector";

create table public.status_of_corporate_sector(
    id_ bigint not null primary key,
    tot_no_of_sub_reg_corp_sector integer,
    total_contrib_scf_uploaded numeric,
	contrib_amt_matched_booked numeric,
	tot_amt_pend_matching_booking numeric,
	total_aum numeric,
	no_of_corp_contrib_remitted integer,
	cra character varying,
	createdon date,
	createdby bigint,
	reportuploadlogid bigint);