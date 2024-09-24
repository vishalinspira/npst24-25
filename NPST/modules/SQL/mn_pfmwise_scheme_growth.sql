DROP TABLE IF EXISTS "mn_pfmwise_scheme_growth";
	DROP SEQUENCE IF EXISTS mn_pfmwise_scheme_growth_id__seq;
	CREATE SEQUENCE mn_pfmwise_scheme_growth_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

	CREATE TABLE "public"."mn_pfmwise_scheme_growth" (
		"id_" integer DEFAULT nextval('mn_pfmwise_scheme_growth_id__seq') NOT NULL,
		
		
		"sr_no" character varying,
    	"month" character varying,
		"pension_fund_name" character varying,
    	"scheme_name" character varying,
    	"thirteen_fourteen" numeric,
    	"fourteen_fifteen" numeric,
    	"fifteen_sixteen" numeric,
    	"sixteen_seventeen" numeric,
    	"seventeen_eighteen" numeric,
    	"eighteen_nineteen" numeric,
    	"nineteen_twenty" numeric,
    	"twenty_twentyone" numeric,
    	"twentyone_twentytwo" numeric,
    	"twentytwo_twentythree" numeric,
    	"twentythree_twentyfour" numeric,
    	"twentyfour_twentyfive" numeric,
    	"twentyfive_twentysix" numeric,
    	"twentysix_twentyseven" numeric,
    	"twentyseven_twentyeight" numeric,
    	"twentyeight_twentynine" numeric,
    	"prev_year_curr_month" numeric,
    	"curr_year_curr_month" numeric,
    	"gr_amt_curr_fy_over_prev_fy" numeric,
    	"gr_prnt_curr_fy_over_prev_fy" numeric,
    	"previous_fy_amt" numeric,
    	"previous_fy_prcnt" numeric,
    	"current_fy_amt" numeric,
    	"current_fy_prcnt" numeric,
    	"cra" character varying,
			
		"createdate" date,
		"createdby" bigint
	) WITH (oids = false);
	
	
	
		