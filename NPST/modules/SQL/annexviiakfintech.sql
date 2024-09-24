-- Adminer 4.7.1 PostgreSQL dump

DROP TABLE IF EXISTS "annexviiakfintech";
CREATE TABLE "public"."annexviiakfintech" (
    "id_" character varying NOT NULL,
    "virtualid" bigint,
    "requesttype" character varying,
    "accname" character varying,
    "popregno" character varying,
    "popspregno" character varying,
    "bankcraccno" character varying,
    "transactiondate" character varying,
    "transactionamount" character varying,
    "transactionrefno" character varying,
    "transactionsts" character varying,
    "fillerrefi" character varying,
    "fillerrefii" character varying,
    "fillerrefiii" character varying,
    "fillerrefiv" character varying,
    "comment_" character varying,
    "returndate" character varying,
    "createdate" date,
    "createdby" bigint
) WITH (oids = false);


-- 2022-04-26 19:33:56.509356+05:30
