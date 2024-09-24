DROP TABLE IF EXISTS "mn_list_of_branches";
DROP SEQUENCE IF EXISTS mn_list_of_branches_id_seq;
CREATE SEQUENCE mn_list_of_branches_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."mn_list_of_branches" (
    "id_" bigint DEFAULT nextval('mn_list_of_branches_id_seq') NOT NULL,
		
		"sno"	integer,
		"branch_code"	integer,
		"date_of_opening"	date,
		"branch_name"	character varying,
		"address"	character varying,
		"district"	character varying,
		"state_ut"	character varying,
		"centre_asper_census2011"	character varying,
		"whether_relocated"	character varying,
		"upgraded_relocation"	character varying,
		"relocation_date"	date,
		"uniformcode_i"	integer,
		"uniformcode_ii"	integer,
		"board_line_no"	character varying,
		"fax_no"	character varying,
		"mobile_no"	bigint,
    "createdate" date NOT NULL,
	"createdby" bigint NOT NULL,
	"reportuploadlogid" bigint,
    CONSTRAINT "mn_list_of_branches_id_" PRIMARY KEY ("id_")
) WITH (oids = false);