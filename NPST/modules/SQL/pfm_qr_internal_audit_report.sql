DROP TABLE IF EXISTS "pfm_qr_iar_scrutiny";

CREATE TABLE public.pfm_qr_iar_scrutiny(
scrutinyid bigint,
 reportuploadlogid bigint NOT NULL,
 username character varying(50) NOT NULL,
 version numeric,
 userid bigint,
 extracts_board_pdf_rem character varying,
 annex_comp_certificate_rem character varying,
 annex_comp_certificate_rem_i character varying,
 annex_comp_certificate_rem_ii character varying,
 annex_comp_certificate_rem_iii character varying,
  nps_comments character varying,
createdon timestamp,
    createdby bigint
	
) WITH (oids = false);

DROP TABLE IF EXISTS "pfm_qr_internal_audit_report";
DROP SEQUENCE IF EXISTS pfm_qr_internal_audit_report_id__seq1;
CREATE SEQUENCE pfm_qr_internal_audit_report_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.pfm_qr_internal_audit_report (
    reportuploadlogid bigint NOT NULL,
    maker_comment_details character varying NOT NULL,
	
		
		annex_comp_certificate bigint,
		annex_comp_certificate_i bigint,
		annex_comp_certificate_ii bigint,
		annex_comp_certificate_iii bigint,
		extracts_board_pdf bigint,
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



		 