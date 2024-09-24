DROP TABLE IF EXISTS "pfm_hy_comp_cert_scrutiny";

CREATE TABLE public.pfm_hy_comp_cert_scrutiny(
	scrutinyid bigint,
	reportuploadlogid bigint NOT NULL,
	username character varying(50) NOT NULL,
	version numeric,
	userid bigint,
	nps_comments character varying,
	createdon timestamp,
	createdby bigint
	
) WITH (oids = false);

DROP TABLE IF EXISTS "pfm_hy_comp_cert";

CREATE TABLE public.pfm_hy_comp_cert (
	reportuploadlogid bigint NOT NULL,
	year character varying,
	hycc_details character varying,
	date1 timestamp,
	date2 timestamp,
	date3 timestamp,
	hycc_place character varying,
	hyccfile bigint,
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
	createdon timestamp,
	createdby bigint
	
) WITH (oids = false);



		 