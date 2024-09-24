DROP TABLE IF EXISTS "mn_grievances_outstanding";
DROP SEQUENCE IF EXISTS mn_grievances_outstanding_id__seq1;
CREATE SEQUENCE mn_grievances_outstanding_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_grievances_outstanding (
    id_ bigint NOT NULL,
	particulars character varying,
	year_ character varying,
	apr numeric(15,2),
	may numeric(15,2),
	jun numeric(15,2),
	jul numeric(15,2),
	aug numeric(15,2),
	sep numeric(15,2),
	oct numeric(15,2),
	nov numeric(15,2),
	dec_ numeric(15,2),
	jan numeric(15,2),
	feb numeric(15,2),
	mar numeric(15,2),
	cra character varying,
    createdon date,
    createdby bigint,
    CONSTRAINT mn_grievances_outstanding_pkey PRIMARY KEY (id_)
)WITH (oids = false);	
	
DROP TABLE IF EXISTS "mn_total_grievance_received";
DROP SEQUENCE IF EXISTS mn_total_grievance_received_id__seq1;
CREATE SEQUENCE mn_total_grievance_received_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_total_grievance_received (
    id_ bigint NOT NULL,
	particulars character varying,
	year_ character varying,
	apr numeric(15,2),
	may numeric(15,2),
	jun numeric(15,2),
	jul numeric(15,2),
	aug numeric(15,2),
	sep numeric(15,2),
	oct numeric(15,2),
	nov numeric(15,2),
	dec_ numeric(15,2),
	jan numeric(15,2),
	feb numeric(15,2),
	mar numeric(15,2),
	cra character varying,
    createdon date,
    createdby bigint,
    CONSTRAINT mn_total_grievance_received_pkey PRIMARY KEY (id_)
)WITH (oids = false);	
	
DROP TABLE IF EXISTS "mn_nps_scheme_month_analysis";
DROP SEQUENCE IF EXISTS mn_nps_scheme_month_analysis_id__seq1;
CREATE SEQUENCE mn_nps_scheme_month_analysis_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_nps_scheme_month_analysis (
    id_ bigint NOT NULL,
	particulars character varying,
	year_ character varying,
	apr numeric(15,2),
	may numeric(15,2),
	jun numeric(15,2),
	jul numeric(15,2),
	aug numeric(15,2),
	sep numeric(15,2),
	oct numeric(15,2),
	nov numeric(15,2),
	dec_ numeric(15,2),
	jan numeric(15,2),
	feb numeric(15,2),
	mar numeric(15,2),
	cra character varying,
    createdon date,
    createdby bigint,
    CONSTRAINT mn_nps_scheme_month_analysis_pkey PRIMARY KEY (id_)
)WITH (oids = false);
	
DROP TABLE IF EXISTS "mn_npslite_scheme_month_analys";
DROP SEQUENCE IF EXISTS mn_npslite_scheme_month_analys_id__seq1;
CREATE SEQUENCE mn_npslite_scheme_month_analys_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_npslite_scheme_month_analys (
    id_ bigint NOT NULL,
	particulars character varying,
	year_ character varying,
	apr numeric(15,2),
	may numeric(15,2),
	jun numeric(15,2),
	jul numeric(15,2),
	aug numeric(15,2),
	sep numeric(15,2),
	oct numeric(15,2),
	nov numeric(15,2),
	dec_ numeric(15,2),
	jan numeric(15,2),
	feb numeric(15,2),
	mar numeric(15,2),
	cra character varying,
    createdon date,
    createdby bigint,
    CONSTRAINT mn_npslite_scheme_month_analys_pkey PRIMARY KEY (id_)
)WITH (oids = false);
	
DROP TABLE IF EXISTS "mn_resolution_speed_analysis";
DROP SEQUENCE IF EXISTS mn_resolution_speed_analysis_id__seq1;
CREATE SEQUENCE mn_resolution_speed_analysis_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_resolution_speed_analysis (
    id_ bigint NOT NULL,
	grievance_month character varying,
	total_resolved int,
	within_0_to_7_days int,
	within_7_to_15_days int,
	within_15_to_30_days int,
	within_30_to_60_days int,
	after_60_days int,
	cra character varying,
    createdon date,
    createdby bigint,
    CONSTRAINT mn_resolution_speed_analysis_pkey PRIMARY KEY (id_)
)WITH (oids = false);
	
DROP TABLE IF EXISTS "mn_entity_wise_ageing_analysis";
DROP SEQUENCE IF EXISTS mn_entity_wise_ageing_analysis_id__seq1;
CREATE SEQUENCE mn_entity_wise_ageing_analysis_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_entity_wise_ageing_analysis (
    id_ bigint NOT NULL,
	entities character varying,
	month_ character varying,
	outstanding_opening_balance int,
	escalated_to_npst int,
	referrals_recv_during_month int,
	reviewed_and_assigned_by_npst int,
	referrals_resol_during_month int,
	outstanding_at_end_of_month int,
	cra character varying,
    createdon date,
    createdby bigint,
    CONSTRAINT mn_entity_wise_ageing_analysis_pkey PRIMARY KEY (id_)
)WITH (oids = false);

DROP TABLE IF EXISTS "mn_entity_wise_out_analysis";
DROP SEQUENCE IF EXISTS mn_entity_wise_out_analysis_id__seq1;
CREATE SEQUENCE mn_entity_wise_out_analysis_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_entity_wise_out_analysis (
    id_ bigint NOT NULL,
	entities character varying,
	month_ character varying,
	outstanding_opening_balance int,
	escalated_to_npst int,
	referrals_recv_during_month int,
	reviewed_and_assigned_by_npst int,
	referrals_resol_during_month int,
	outstanding_at_end_of_month int,
	cra character varying,
    createdon date,
    createdby bigint,
    CONSTRAINT mn_entity_wise_out_analysis_pkey PRIMARY KEY (id_)
)WITH (oids = false);

DROP TABLE IF EXISTS "mn_nps_entity_wise_ageing";
DROP SEQUENCE IF EXISTS mn_nps_entity_wise_ageing_id__seq1;
CREATE SEQUENCE mn_nps_entity_wise_ageing_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_nps_entity_wise_ageing (
    id_ bigint NOT NULL,
	entities character varying,
	month_ character varying,
	scheme_name character varying,
	_0_to_30_days int,
	_31_to_60_days int,
	_61_to_90_days int,
	_91_to_365_days int,
	more_than_365_days int,
	outstanding_at_end_of_month int,
	cra character varying,
    createdon date,
    createdby bigint,
    CONSTRAINT mn_nps_entity_wise_ageing_pkey PRIMARY KEY (id_)
)WITH (oids = false);

DROP TABLE IF EXISTS "mn_npslite_entity_wise_ageing";
DROP SEQUENCE IF EXISTS mn_npslite_entity_wise_ageing_id__seq1;
CREATE SEQUENCE mn_npslite_entity_wise_ageing_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_npslite_entity_wise_ageing (
    id_ bigint NOT NULL,
	entities character varying,
	month_ character varying,
	scheme_name character varying,
	_0_to_30_days int,
	_31_to_60_days int,
	_61_to_90_days int,
	_91_to_365_days int,
	more_than_365_days int,
	outstanding_at_end_of_month int,
	cra character varying,
    createdon date,
    createdby bigint,
    CONSTRAINT mn_npslite_entity_wise_ageing_pkey PRIMARY KEY (id_)
)WITH (oids = false);

DROP TABLE IF EXISTS "mn_nps_category_wise_ageing";
DROP SEQUENCE IF EXISTS mn_nps_category_wise_ageing_id__seq1;
CREATE SEQUENCE mn_nps_category_wise_ageing_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_nps_category_wise_ageing (
    id_ bigint NOT NULL,
	category character varying,
	month_ character varying,
	scheme_name character varying,
	_0_to_30_days int,
	_31_to_60_days int,
	_61_to_90_days int,
	_91_to_365_days int,
	more_than_365_days int,
	outstanding_at_end_of_month int,
	cra character varying,
    createdon date,
    createdby bigint,
    CONSTRAINT mn_nps_category_wise_ageing_pkey PRIMARY KEY (id_)
)WITH (oids = false);

DROP TABLE IF EXISTS "mn_npslite_cat_wise_ageing";
DROP SEQUENCE IF EXISTS mn_npslite_cat_wise_ageing_id__seq1;
CREATE SEQUENCE mn_npslite_cat_wise_ageing_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_npslite_cat_wise_ageing (
    id_ bigint NOT NULL,
	category character varying,
	month_ character varying,
	scheme_name character varying,
	_0_to_30_days int,
	_31_to_60_days int,
	_61_to_90_days int,
	_91_to_365_days int,
	more_than_365_days int,
	outstanding_at_end_of_month int,
	cra character varying,
    createdon date,
    createdby bigint,
    CONSTRAINT mn_npslite_cat_wise_ageing_pkey PRIMARY KEY (id_)
)WITH (oids = false);

DROP TABLE IF EXISTS "mn_nps_category_wise_grievance";
DROP SEQUENCE IF EXISTS mn_nps_category_wise_grievance_id__seq1;
CREATE SEQUENCE mn_nps_category_wise_grievance_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_nps_category_wise_grievance (
    id_ bigint NOT NULL,
	category character varying,
	year_ character varying,
	apr numeric(15,2),
	may numeric(15,2),
	jun numeric(15,2),
	jul numeric(15,2),
	aug numeric(15,2),
	sep numeric(15,2),
	oct numeric(15,2),
	nov numeric(15,2),
	dec_ numeric(15,2),
	jan numeric(15,2),
	feb numeric(15,2),
	mar numeric(15,2),
	cra character varying,
    createdon date,
    createdby bigint,
    CONSTRAINT mn_nps_category_wise_grievance_pkey PRIMARY KEY (id_)
)WITH (oids = false);

DROP TABLE IF EXISTS "mn_npslite_category_wise_griev";
DROP SEQUENCE IF EXISTS mn_npslite_category_wise_griev_id__seq1;
CREATE SEQUENCE mn_npslite_category_wise_griev_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_npslite_category_wise_griev (
    id_ bigint NOT NULL,
	category character varying,
	year_ character varying,
	apr numeric(15,2),
	may numeric(15,2),
	jun numeric(15,2),
	jul numeric(15,2),
	aug numeric(15,2),
	sep numeric(15,2),
	oct numeric(15,2),
	nov numeric(15,2),
	dec_ numeric(15,2),
	jan numeric(15,2),
	feb numeric(15,2),
	mar numeric(15,2),
	cra character varying,
    createdon date,
    createdby bigint,
    CONSTRAINT mn_npslite_category_wise_griev_pkey PRIMARY KEY (id_)
)WITH (oids = false);

DROP TABLE IF EXISTS "mn_referral_handled_at_nps";
DROP SEQUENCE IF EXISTS mn_referral_handled_at_nps_id__seq1;
CREATE SEQUENCE mn_referral_handled_at_nps_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_referral_handled_at_nps (
    id_ bigint NOT NULL,
	referrals character varying,
	month_ character varying,
	outstanding_opening_balance int,
	escalated_to_npst int,
	referrals_recv_during_month int,
	reviewed_and_assigned_by_npst int,
	referrals_resol_during_month int,
	outstanding_at_end_of_month int,
	cra character varying,
    createdon date,
    createdby bigint,
    CONSTRAINT mn_referral_handled_at_nps_pkey PRIMARY KEY (id_)
)WITH (oids = false);