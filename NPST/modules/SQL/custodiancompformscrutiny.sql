DROP TABLE IF EXISTS "custodiancompformscrutiny";

	CREATE TABLE "public"."custodiancompformscrutiny" (
		"id_" bigint NOT NULL,
		"reportuploadlogid" bigint NOT NULL,
		
		"observe_i_i"  character varying,
		"observe_i_ii"  character varying,
		"observe_ii"  character varying,
		"observe_iii"  character varying,
		"observe_iv"  character varying,
		"observe_v"  character varying,
		"observe_vi"  character varying,
		"observe_vii"  character varying,
		"observe_viii"  character varying,
		"observe_ix"  character varying,
		"observe_x"  character varying,
		"observe_xi"  character varying,
		"observe_xii"  character varying,
		"observe_xiii"  character varying,
		"createdate" date,
		"createdby"  bigint,
		"uuid_" character varying
		
) WITH (oids = false);
	
	
		