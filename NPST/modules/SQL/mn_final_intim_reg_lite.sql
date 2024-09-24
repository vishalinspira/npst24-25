DROP TABLE IF EXISTS "mn_final_intim_reg_lite";
	DROP SEQUENCE IF EXISTS mn_final_intim_reg_lite_id__seq;
	CREATE SEQUENCE mn_final_intim_reg_lite_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

	CREATE TABLE "public"."mn_final_intim_reg_lite" (
		"id_" integer DEFAULT nextval('mn_final_intim_reg_lite_id__seq') NOT NULL,
		
		"prao_reg_no" character varying NOT NULL,
		"prao_name" character varying,
		"prao_add_line1" character varying, 
		"prao_add_line2" character varying, 
		"prao_add_line3"  character varying,
		"prao_add_line4" character varying,
		"prao_pin_code" integer NOT NULL,
		"count_of_prao_grievanc_pending" integer,
		"pao_reg_no" character varying NOT NULL,
		"pao_name" character varying,
		"add_line1" character varying,
		"add_line2"  character varying,
		"add_line3"  character varying,
		"add_line4"  character varying,
		"pin_code" integer NOT NULL,
		"count_of_pao_grievanc_pending" integer NOT NULL,
		"sector" character varying NOT NULL,
		"cra" character varying NOT NULL,
		"createdate" date,
        "createdby" bigint,
        "reportuploadlogid" bigint NULL
        
	) WITH (oids = false);
	
		
