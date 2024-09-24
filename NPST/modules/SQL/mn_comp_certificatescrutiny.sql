DROP TABLE IF EXISTS "mn_comp_certificatescrutiny";
DROP SEQUENCE IF EXISTS mn_comp_certificatescrutiny_id__seq1;
CREATE SEQUENCE mn_comp_certificatescrutiny_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_comp_certificatescrutiny (
    id_ bigint NOT NULL,
	username character varying,
	version double precision,
	userid  bigint,
	purchase_of_securities_rem character varying,
	detailed_investment_rem character varying,
	investments_approved_rem character varying,
	decision_of_investment_rem character varying,
	invest_non_dematerialized_rem character varying,
	all_investments_from_funds_rem character varying,
	delivery_of_security_purch_rem character varying,
	investment_done_in_ipo_rem character varying,
	scheme_investments_rem character varying,
	stop_loss_trigger_rem character varying,
	decision_appr_by_committee_rem character varying,
	decision_prop_documented_rem character varying,
	inter_scheme_transfer_rem character varying,
	investment_held_in_equity_rem character varying,
	invest_in_equity_shares_rem character varying,
	disinvestments_approved_rem character varying,
	decision_of_disinvestment_rem character varying,
	delivery_of_security_sale_rem character varying,
	all_investment_charges_rem character varying,
	pfm_adhered_rem character varying,
	records_of_audit_of_nav_rem character varying,
	scheme_wise_nav_uploaded_rem character varying,
	scheme_wise_nav_submitted_rem character varying,
	monthly_reports_submitted_rem character varying,
	scrip_wise_details_rem character varying,
	annexure_a_i_rem character varying,
	annexure_a_ii_rem character varying,
	annexure_b_rem character varying,
	annexure_c_rem character varying,
	annexure_d_rem character varying,
	annexure_e_rem character varying,
	annexure_f_rem character varying,
	annexure_g_rem character varying,
	annexure_h_rem character varying,
    createdate date,
    createdby bigint,
    CONSTRAINT mn_comp_certificatescrutiny_pkey PRIMARY KEY (id_)
)WITH (oids = false);