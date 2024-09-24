DROP TABLE IF EXISTS "mn_pfs_deductee_detail";
DROP SEQUENCE IF EXISTS mn_pfs_deductee_detail_id__seq1;
CREATE SEQUENCE mn_pfs_deductee_detail_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_pfs_deductee_detail (
    id_ bigint NOT NULL,
	pension_fund_name character varying,
	date_ date,
	challan_serial_no numeric,
	section_code character varying,
	type_of_rent character varying,
	deductee_identf_no character varying,
	deductee_code_414 numeric ,
	pan_of_deductee_415 character varying,
	deductee_refno_invalid_pan_413 character varying,
	name_of_deductee_416 character varying,
	amt_paid_or_credited_date_418 date,
	tax_deducted_date_422 date,
	amt_of_payment_419 numeric,
	tax_deduction_rate_423 numeric,
	amount_of_tax_deducted numeric,
	total_tax_deposited_421 numeric,
	reason_for_non_deduction_424 character varying,
	non_deduct_certno_issue_ao_425 character varying,
	deductee_flat_no numeric,
	deductee_building_name character varying,
	deductee_street_name character varying,
	deductee_area character varying,
	deductee_city_or_town character varying,
	deductee_pin numeric,
	deductee_state character varying,
    createdon date,
    createdby bigint,
    CONSTRAINT mn_pfs_deductee_detail_pkey PRIMARY KEY (id_)
)WITH (oids = false);