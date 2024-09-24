DROP TABLE IF EXISTS "an_comp_certificate";

CREATE TABLE public.an_comp_certificate (
    reportuploadlogid bigint NOT NULL,
	date_1 date,
	address character varying,
	eligibilityia character varying,
	eligibilityib character varying,
	eligibilityic character varying,
	eligibilityid character varying,
	eligibilityie character varying,
	eligibilityif character varying,
	eligibilityig character varying,
	eligibilityih character varying,
	eligibilityii character varying,
	eligibilityij character varying,
	eligibilityik character varying,
	eligibilityil character varying,
	eligibilityim character varying,
	eligibilityin character varying,
	eligibilityio character varying,
	eligibilityip character varying,
	eligibilityiq character varying,
	eligibilityir character varying,
	eligibilityis character varying,
	eligibilityIa_rem character varying,
	eligibilityIb_rem character varying,
	eligibilityIc_rem character varying,
	eligibilityId_rem character varying,
	eligibilityIe_rem character varying,
	eligibilityIf_rem character varying,
	eligibilityIg_rem character varying,
	eligibilityIh_rem character varying,
	eligibilityIi_rem character varying,
	eligibilityIj_rem character varying,
	eligibilityIk_rem character varying,
	eligibilityIl_rem character varying,
	eligibilityIm_rem character varying,
	eligibilityIn_rem character varying,
	eligibilityIo_rem character varying,
	eligibilityIp_rem character varying,
	eligibilityIq_rem character varying,
	eligibilityIr_rem character varying,
	eligibilityIs_rem character varying,
	booka character varying,
	bookb character varying,
	bookc character varying,
	bookIIa_rem character varying,
	bookIIb_rem character varying,
	bookIIc_rem character varying,
	audita character varying,
	auditb character varying,
	auditc character varying,
	audita_rem character varying,
	auditb_rem character varying,
	auditc_rem character varying,
	stewardshipa character varying,
	stewardshipb character varying,
	stewardshipc character varying,
	stewardshipa_rem character varying,
	stewardshipb_rem character varying,
	stewardshipc_rem character varying,
	othersa character varying,
	othersb character varying,
	othersc character varying,
	othersd character varying,
	otherse character varying,
	othersf character varying,
	othersa_rem character varying,
	othersb_rem character varying,
	othersc_rem character varying,
	othersd_rem character varying,
	otherse_rem character varying,
	othersf_rem character varying,
	meetingdate character varying,
	company_name character varying,
	emplolyee_name character varying,
	roles character varying,
	date_2 date,
	place character varying,
	annexurei bigint,
	annexureii bigint,
	annexureiii bigint,
	annexureiv bigint,
	annexurev bigint,
	
	reportmasterid bigint,
    reportdate timestamp,
    fileentryid bigint,
	
	status integer,
    statusbyuserid bigint,
    statusbyusername character varying,
    statusdate timestamp,
	workflowcontext text,
    remarks text,
	uuid_ character varying,
    
    modifyby bigint,
    modifydate timestamp,
	createdate timestamp,
    createdby bigint
)WITH (oids = false);




DROP TABLE IF EXISTS "an_comp_certificatescrutiny";
DROP SEQUENCE IF EXISTS an_comp_certificatescrutiny_id__seq1;
CREATE SEQUENCE an_comp_certificatescrutiny_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.an_comp_certificatescrutiny (
    id_ bigint NOT NULL,
	reportuploadlogid bigint,
	username character varying,
	version numeric,
	userid bigint,
	eligibilityia_rem character varying,
	eligibilityib_rem character varying,
	eligibilityic_rem character varying,
	eligibilityid_rem character varying,
	eligibilityie_rem character varying,
	eligibilityif_rem character varying,
	eligibilityig_rem character varying,
	eligibilityih_rem character varying,
	eligibilityii_rem character varying,
	eligibilityij_rem character varying,
	eligibilityik_rem character varying,
	eligibilityil_rem character varying,
	eligibilityim_rem character varying,
	eligibilityin_rem character varying,
	eligibilityio_rem character varying,
	eligibilityip_rem character varying,
	eligibilityiq_rem character varying,
	eligibilityir_rem character varying,
	eligibilityis_rem character varying,
	booka_rem character varying,
	bookb_rem character varying,
	bookc_rem character varying,
	audita_rem character varying,
	auditb_rem character varying,
	auditc_rem character varying,
	stewardshipa_rem character varying,
	stewardshipb_rem character varying,
	stewardshipc_rem character varying,
	othersa_rem character varying,
	othersb_rem character varying,
	othersc_rem character varying,
	othersd_rem character varying,
	otherse_rem character varying,
	othersf_rem character varying,
	annexurei_rem character varying,
	annexureii_rem character varying,
	annexureiii_rem character varying,
	annexureiv_rem character varying,
	annexurev_rem character varying,
	
	createdate date,
    createdby bigint,
	
    CONSTRAINT an_comp_certificatescrutiny_pkey PRIMARY KEY (id_)
)WITH (oids = false);