drop table if exists "status_of_indivi_subscriber";

create table public.status_of_indivi_subscriber(
    id_ bigint not null primary key,
    as_on_date date,
    total_no_of_reg_pops_in_cra character varying,
    tot_no_of_reg_pop_sps_in_cra integer,
    total_no_of_subscribers integer,
	total_no_of_subs integer,
	tot_no_of_active_account_subs integer,
	total_amt_of_subs_contrib_m_b numeric,
	tieri_tierii character varying,
	cra character varying,
	createdon date,
	createdby bigint,
	reportuploadlogid bigint);
	
	alter table "status_of_indivi_subscriber"
	add "total_aum" numeric null;
	comment on table "status_of_indivi_subscriber" is '';