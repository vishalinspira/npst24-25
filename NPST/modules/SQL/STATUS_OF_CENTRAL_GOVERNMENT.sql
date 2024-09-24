drop table if exists "status_of_central_government";

create table public.status_of_central_government(
    id_ bigint not null primary key,
    as_on_date date,
    accounting_formation character varying,
    change_subs_during_past_week integer,
    total_no_of_subscribers integer,
	no_of_non_ira_compliant_subs integer,
	non_ira_subs_flagged_in_system integer,
	contribution_amt_for_scfs_m_b numeric,
	contrib_amt_for_scfs_pend_m_b numeric,
	aum numeric,
	cra character varying,
	createdon date,
	createdby bigint,
	reportuploadlogid bigint);