drop table if exists "overall_status_sector_wise";

create table public.overall_status_sector_wise(
    id_ bigint not null primary key,
    as_on_date date,
    sector character varying,
    number_of_subscriber integer,
    subscriber_reg_during_week integer,
	contribution_m_and_b numeric,
	change_in_contrib_during_week numeric,
	pending_for_m_and_b numeric,
	total numeric,
	net_amount_invested numeric,
	total_assets_under_mgmt numeric,
	cra character varying,
	createdon date,
	createdby bigint,
	reportuploadlogid bigint);