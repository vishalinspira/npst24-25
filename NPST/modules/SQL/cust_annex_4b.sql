DROP TABLE IF EXISTS "cust_annex_4b";
DROP SEQUENCE IF EXISTS cust_annex_4b_id__seq1;
CREATE SEQUENCE cust_annex_4b_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.cust_annex_4b (
    id_ bigint NOT NULL,
    file_number character varying(100),
    date_1 date,
    custodian_name character varying(100),
    address character varying(100),
    date_2 date,
    -- date_3 date,
    name_ character varying(100),
    -- date_4 date,
	
    regulatory_comments_1 character varying(10),
    regulatory_remarks_1 character varying(1000),
    regulatory_sample_1 character varying(100),
    regulatory_nps_1 character varying(1000),
    
	regulatory_comments_2 character varying(10),
    regulatory_remarks_2 character varying(1000),
    regulatory_sample_2 character varying(100),
    regulatory_nps_2 character varying(1000),
	
	regulatory_comments_3 character varying(10),
    regulatory_remarks_3 character varying(1000),
    regulatory_sample_3 character varying(100),
    regulatory_nps_3 character varying(1000),
	
	regulatory_comments_4 character varying(10),
    regulatory_remarks_4 character varying(1000),
    regulatory_sample_4 character varying(100),
    regulatory_nps_4 character varying(1000),
	
	regulatory_comments_5 character varying(10),
    regulatory_remarks_5 character varying(1000),
    regulatory_sample_5 character varying(100),
    regulatory_nps_5 character varying(1000),
	
	regulatory_comments_6 character varying(10),
    regulatory_remarks_6 character varying(1000),
    regulatory_sample_6 character varying(100),
    regulatory_nps_6 character varying(1000),
	
	regulatory_comments_7 character varying(10),
    regulatory_remarks_7 character varying(1000),
    regulatory_sample_7 character varying(100),
    regulatory_nps_7 character varying(1000),
	
	regulatory_comments_8 character varying(10),
    regulatory_remarks_8 character varying(1000),
    regulatory_sample_8 character varying(100),
    regulatory_nps_8 character varying(1000),
	
	regulatory_comments_9 character varying(10),
    regulatory_remarks_9 character varying(1000),
    regulatory_sample_9 character varying(100),
    regulatory_nps_9 character varying(1000),
	
	operational_comments_1 character varying(10),
    operational_remarks_1 character varying(1000),
    operational_sample_1 character varying(100),
    operational_nps_1 character varying(1000),
	
	operational_comments_2 character varying(10),
    operational_remarks_2 character varying(1000),
    operational_sample_2 character varying(100),
    operational_nps_2 character varying(1000),
    
    operational_comments_3 character varying(10),
    operational_remarks_3 character varying(1000),
    operational_sample_3 character varying(100),
    operational_nps_3 character varying(1000),
    
    operational_comments_4 character varying(10),
    operational_remarks_4 character varying(1000),
    operational_sample_4 character varying(100),
    operational_nps_4 character varying(1000),
    
    operational_comments_5 character varying(10),
    operational_remarks_5 character varying(1000),
    operational_sample_5 character varying(100),
    operational_nps_5 character varying(1000),
    
    operational_comments_6 character varying(10),
    operational_remarks_6 character varying(1000),
    operational_sample_6 character varying(100),
    operational_nps_6 character varying(1000),
    
    operational_comments_7 character varying(10),
    operational_remarks_7 character varying(1000),
    operational_sample_7 character varying(100),
    operational_nps_7 character varying(1000),
    
    operational_comments_8 character varying(10),
    operational_remarks_8 character varying(1000),
    operational_sample_8 character varying(100),
    operational_nps_8 character varying(1000),
    
    operational_comments_9 character varying(10),
    operational_remarks_9 character varying(1000),
    operational_sample_9 character varying(100),
    operational_nps_9 character varying(1000),
    
    operational_comments_10 character varying(10),
    operational_remarks_10 character varying(1000),
    operational_sample_10 character varying(100),
    operational_nps_10 character varying(1000),
    
    operational_comments_11 character varying(10),
    operational_remarks_11 character varying(1000),
    operational_sample_11 character varying(100),
    operational_nps_11 character varying(1000),
    
    operational_comments_12 character varying(10),
    operational_remarks_12 character varying(1000),
    operational_sample_12 character varying(100),
    operational_nps_12 character varying(1000),
    
    operational_comments_13 character varying(10),
    operational_remarks_13 character varying(1000),
    operational_sample_13 character varying(100),
    operational_nps_13 character varying(1000),
    
    operational_comments_14 character varying(10),
    operational_remarks_14 character varying(1000),
    operational_sample_14 character varying(100),
    operational_nps_14 character varying(1000),
    
    operational_comments_15 character varying(10),
    operational_remarks_15 character varying(1000),
    operational_sample_15 character varying(100),
    operational_nps_15 character varying(1000),
    
    operational_comments_16 character varying(10),
    operational_remarks_16 character varying(1000),
    operational_sample_16 character varying(100),
    operational_nps_16 character varying(1000),
    
    operational_comments_17 character varying(10),
    operational_remarks_17 character varying(1000),
    operational_sample_17 character varying(100),
    operational_nps_17 character varying(1000),
    
    operational_comments_18 character varying(10),
    operational_remarks_18 character varying(1000),
    operational_sample_18 character varying(100),
    operational_nps_18 character varying(1000),
    
    operational_comments_19 character varying(10),
    operational_remarks_19 character varying(1000),
    operational_sample_19 character varying(100),
    operational_nps_19 character varying(1000),
    
    timely_comments_1 character varying(10),
    timely_remarks_1 character varying(1000),
    timely_sample_1 character varying(100),
    timely_nps_1 character varying(1000),
    
    timely_comments_2 character varying(10),
    timely_remarks_2 character varying(1000),
    timely_sample_2 character varying(100),
    timely_nps_2 character varying(1000),
    
    timely_comments_3 character varying(10),
    timely_remarks_3 character varying(1000),
    timely_sample_3 character varying(100),
    timely_nps_3 character varying(1000),
    
    timely_comments_4 character varying(10),
    timely_remarks_4 character varying(1000),
    timely_sample_4 character varying(100),
    timely_nps_4 character varying(1000),
    
    timely_comments_5 character varying(10),
    timely_remarks_5 character varying(1000),
    timely_sample_5 character varying(100),
    timely_nps_5 character varying(1000),
    
    timely_comments_6 character varying(10),
    timely_remarks_6 character varying(1000),
    timely_sample_6 character varying(100),
    timely_nps_6 character varying(1000),
    
    timely_comments_7 character varying(10),
    timely_remarks_7 character varying(1000),
    timely_sample_7 character varying(100),
    timely_nps_7 character varying(1000),
    
    timely_comments_8 character varying(10),
    timely_remarks_8 character varying(1000),
    timely_sample_8 character varying(100),
    timely_nps_8 character varying(1000),
    
    timely_comments_9 character varying(10),
    timely_remarks_9 character varying(1000),
    timely_sample_9 character varying(100),
    timely_nps_9 character varying(1000),
    
    timely_comments_10 character varying(10),
    timely_remarks_10 character varying(1000),
    timely_sample_10 character varying(100),
    timely_nps_10 character varying(1000),
    
    timely_comments_11 character varying(10),
    timely_remarks_11 character varying(1000),
    timely_sample_11 character varying(100),
    timely_nps_11 character varying(1000),
    
    timely_comments_12 character varying(10),
    timely_remarks_12 character varying(1000),
    timely_sample_12 character varying(100),
    timely_nps_12 character varying(1000),
    
    timely_comments_13 character varying(10),
    timely_remarks_13 character varying(1000),
    timely_sample_13 character varying(100),
    timely_nps_13 character varying(1000),
    
    custodian_comments_1 character varying(10),
    custodian_remarks_1 character varying(1000),
    custodian_sample_1 character varying(100),
    custodian_nps_1 character varying(1000),
    
    custodian_comments_2 character varying(10),
    custodian_remarks_2 character varying(1000),
    custodian_sample_2 character varying(100),
    custodian_nps_2 character varying(1000),
    
    admin_comments_1 character varying(10),
    admin_remarks_1 character varying(1000),
    admin_sample_1 character varying(100),
    admin_nps_1 character varying(1000),
    
    admin_comments_2 character varying(10),
    admin_remarks_2 character varying(1000),
    admin_sample_2 character varying(100),
    admin_nps_2 character varying(1000),
    
    admin_comments_3 character varying(10),
    admin_remarks_3 character varying(1000),
    admin_sample_3 character varying(100),
    admin_nps_3 character varying(1000),
    
    admin_comments_4 character varying(10),
    admin_remarks_4 character varying(1000),
    admin_sample_4 character varying(100),
    admin_nps_4 character varying(1000),
    
    admin_comments_5 character varying(10),
    admin_remarks_5 character varying(1000),
    admin_sample_5 character varying(100),
    admin_nps_5 character varying(1000),
    
    infra_comments_1 character varying(10),
    infra_remarks_1 character varying(1000),
    infra_sample_1 character varying(100),
    infra_nps_1 character varying(1000),
    
    infra_comments_2 character varying(10),
    infra_remarks_2 character varying(1000),
    infra_sample_2 character varying(100),
    infra_nps_2 character varying(1000),
    
    infra_comments_3 character varying(10),
    infra_remarks_3 character varying(1000),
    infra_sample_3 character varying(100),
    infra_nps_3 character varying(1000),
    
    infra_comments_4 character varying(10),
    infra_remarks_4 character varying(1000),
    infra_sample_4 character varying(100),
    infra_nps_4 character varying(1000),
    
    infra_comments_5 character varying(10),
    infra_remarks_5 character varying(1000),
    infra_sample_5 character varying(100),
    infra_nps_5 character varying(1000),
    
    infra_comments_6 character varying(10),
    infra_remarks_6 character varying(1000),
    infra_sample_6 character varying(100),
    infra_nps_6 character varying(1000),
    
    infra_comments_7 character varying(10),
    infra_remarks_7 character varying(1000),
    infra_sample_7 character varying(100),
    infra_nps_7 character varying(1000),
    
    infra_comments_8 character varying(10),
    infra_remarks_8 character varying(1000),
    infra_sample_8 character varying(100),
    infra_nps_8 character varying(1000),
    
    infra_comments_9 character varying(10),
    infra_remarks_9 character varying(1000),
    infra_sample_9 character varying(100),
    infra_nps_9 character varying(1000),
    
    infra_comments_10 character varying(10),
    infra_remarks_10 character varying(1000),
    infra_sample_10 character varying(100),
    infra_nps_10 character varying(1000),
    
    infra_comments_11 character varying(10),
    infra_remarks_11 character varying(1000),
    infra_sample_11 character varying(100),
    infra_nps_11 character varying(1000),
    
    infra_comments_12 character varying(10),
    infra_remarks_12 character varying(1000),
    infra_sample_12 character varying(100),
    infra_nps_12 character varying(1000),
    
    protection_comments_1 character varying(10),
    protection_remarks_1 character varying(1000),
    protection_sample_1 character varying(100),
    protection_nps_1 character varying(1000),
    
    protection_comments_2 character varying(10),
    protection_remarks_2 character varying(1000),
    protection_sample_2 character varying(100),
    protection_nps_2 character varying(1000),
    
    protection_comments_3 character varying(10),
    protection_remarks_3 character varying(1000),
    protection_sample_3 character varying(100),
    protection_nps_3 character varying(1000),
    
    protection_comments_4 character varying(10),
    protection_remarks_4 character varying(1000),
    protection_sample_4 character varying(100),
    protection_nps_4 character varying(1000),
    
    protection_comments_5 character varying(10),
    protection_remarks_5 character varying(1000),
    protection_sample_5 character varying(100),
    protection_nps_5 character varying(1000),
    
    protection_comments_6 character varying(10),
    protection_remarks_6 character varying(1000),
    protection_sample_6 character varying(100),
    protection_nps_6 character varying(1000),
    
    record_comments_1 character varying(10),
    record_remarks_1 character varying(1000),
    record_sample_1 character varying(100),
    record_nps_1 character varying(1000),
    
    record_comments_2 character varying(10),
    record_remarks_2 character varying(1000),
    record_sample_2 character varying(100),
    record_nps_2 character varying(1000),
    
    record_comments_3 character varying(10),
    record_remarks_3 character varying(1000),
    record_sample_3 character varying(100),
    record_nps_3 character varying(1000),
    
    record_comments_4 character varying(10),
    record_remarks_4 character varying(1000),
    record_sample_4 character varying(100),
    record_nps_4 character varying(1000),
    
    grievance_comments_1 character varying(10),
    grievance_remarks_1 character varying(1000),
    grievance_sample_1 character varying(100),
    grievance_nps_1 character varying(1000),
    
    grievance_comments_2 character varying(10),
    grievance_remarks_2 character varying(1000),
    grievance_sample_2 character varying(100),
    grievance_nps_2 character varying(1000),
    
    other_comments_1 character varying(10),
    other_remarks_1 character varying(1000),
    other_sample_1 character varying(100),
    other_nps_1 character varying(1000),
    
    other_comments_2 character varying(10),
    other_remarks_2 character varying(1000),
    other_sample_2 character varying(100),
    other_nps_2 character varying(1000),
    
    governance_comments_1 character varying(10),
    governance_remarks_1 character varying(1000),
    governance_sample_1 character varying(100),
    governance_nps_1 character varying(1000),
    
    governance_comments_2 character varying(10),
    governance_remarks_2 character varying(1000),
    governance_sample_2 character varying(100),
    governance_nps_2 character varying(1000),
    
    governance_comments_3 character varying(10),
    governance_remarks_3 character varying(1000),
    governance_sample_3 character varying(100),
    governance_nps_3 character varying(1000),
    
    governance_comments_4 character varying(10),
    governance_remarks_4 character varying(1000),
    governance_sample_4 character varying(100),
    governance_nps_4 character varying(1000),
	
	manager character varying(100),

    createdon date,
    createdby bigint,
    CONSTRAINT cust_annex_4b_pkey PRIMARY KEY (id_)
) WITH (oids = false);