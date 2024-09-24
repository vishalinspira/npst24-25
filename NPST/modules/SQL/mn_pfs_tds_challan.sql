DROP TABLE IF EXISTS "mn_pfs_tds_challan";
DROP SEQUENCE IF EXISTS mn_pfs_tds_challan_id__seq1;
CREATE SEQUENCE mn_pfs_tds_challan_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_pfs_tds_challan (
    id_ bigint NOT NULL,
	pension_fund_name character varying,
	date_ date,
	challan_serial_no_401 numeric,
	income_tax_402 numeric,
	interest_403 numeric,
	fees_404 numeric,
	others_or_penalty_405 numeric,
	tax_deposited_or_book_adj_406 numeric,
	deposited_by_book_adj_407 character varying,
	bsr_code_408 character varying,
	tax_dep_or_transfer_date_410 date,
	challan_or_ddo_of_form_24g_409 character varying,
	receipt_no_of_form_24g_408 character varying,
	minor_head_of_challan_411 numeric,
	cheque_or_dd_no character varying,
	section_code character varying,
    createdon date,
    createdby bigint,
    CONSTRAINT mn_pfs_tds_challan_pkey PRIMARY KEY (id_)
)WITH (oids = false);