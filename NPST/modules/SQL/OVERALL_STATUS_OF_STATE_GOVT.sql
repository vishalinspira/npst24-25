drop table if exists "overall_status_of_state_govt";

create table public.overall_status_of_state_govt(
    id_ bigint not null primary key,
    as_on_date date,
    state_govt character varying,
    date_of_notification date,
    date_of_adoption date,
	total_no_of_subscriber numeric,
	no_of_non_ira_subscribers numeric,
	reg_during_this_week numeric,
	total_contribution numeric,
	contribution_m_b numeric,
	change_contrib_m_b_during_week numeric,
	aum numeric,
	cra character varying,
	createdon date,
	createdby bigint,
	reportuploadlogid bigint);