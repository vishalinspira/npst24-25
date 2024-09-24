DROP TABLE IF EXISTS "qr_custodian_bills";
DROP SEQUENCE IF EXISTS qr_custodian_bills_id__seq1;
CREATE SEQUENCE qr_custodian_bills_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."qr_custodian_bills" (
    "id_" integer DEFAULT nextval('qr_custodian_bills_id__seq1') NOT NULL,
    "scheme_name" character varying,
    "transaction_charges_dem_trades" numeric,
    "custody_charges_dema_holdings" numeric,
    "nsccl_ccil_charges" numeric,
    "sebi_charges" numeric,
    "gross_bill_as_per_pf" numeric,
    "bill_value_excluding_gst" numeric,
    "cgst" numeric,
    "sgst" numeric,
    "bill_value_including_gst" numeric,
    "month" date NOT NULL,
    "account_number" numeric,
    "createdon" date,
    "createdby" bigint,
   "reportuploadlogid" bigint,
  CONSTRAINT "qr_custodian_bills_id_" PRIMARY KEY ("id_")
) WITH (oids = false);


-- 2022-04-26 18:29:33.935563+05:30
