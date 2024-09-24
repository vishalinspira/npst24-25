DROP TABLE IF EXISTS "reportmaster";
CREATE TABLE "public"."reportmaster" (
    "reportmasterid" bigint NOT NULL,
    "reportname" character varying(75),
    "duedate" character varying(75),
    "reporttype" character varying(20),
    "uploaderrole" character varying(20),
    "cra" character varying(10),
    "department" character varying(20),
	"intermediarytype"	bigint NULL
) WITH (oids = false);

INSERT INTO "reportmaster" ("reportmasterid", "reportname", "duedate", "reporttype", "uploaderrole", "cra","department", "intermediarytype") VALUES
(1,	'Annexure 1 Account Statement daily',	'0 30 15 * * ?',	'Daily',	'Maker',	NULL, 'Trustee Bank',0),
(2,	'Annexure 3 daily average balance report',	'0 30 15 * * ?',	'Daily',	'Maker',	NULL,'Trustee Bank',0),
(3,	'Annexure 5 Rejection & Return MIS (Electronic)',	'0 30 15 * * ?',	'Daily',	'Maker',	'NSDL','Trustee Bank',0),
(4,	'Annexure 5a Rejection & Return MIS (Dremit)',	'0 30 15 * * ?',	'Daily',	'Maker',	'NSDL','Trustee Bank',0),
(5,	'Annexure 4 weekly average balance report',	'0 0 0 * * TUE',	'Weekly',	'Maker',	NULL,'Trustee Bank',0),
(6,	'Annexure 4a weekly outstanding with Trustee Bank',	'0 0 0 * * TUE',	'Weekly',	'Maker',	NULL,'Trustee Bank',0),
(7,	'Annexure 6 Consolidated Collection Report',	'0 0 0 * * TUE',	'Weekly',	'Maker',	'NSDL','Trustee Bank',0),
(8,	'Annexure 7 MIS Collection Rejection (monthly)',	'0 0 0 10 * ?',	'Monthly',	'Maker',	'NSDL','Trustee Bank',0),
(9,	'Annexure 7a MIS Collection Rejection D-remit',	'0 0 0 10 * ?',	'Monthly',	'Maker',	NULL,'Trustee Bank',0),
(10,	'Annexure 10 Summarized data of Performance Report',	'0 0 0 10 * ?',	'Monthly',	'Maker',	'NSDL','Trustee Bank',0),
(11,	'Annexure 10a Withdrawal MIS',	'0 0 0 10 * ?',	'Monthly',	'Maker',	'NSDL','Trustee Bank',0),
(12,	'Annexure 10b Email Complaint Tracker',	'0 0 0 10 * ?',	'Monthly',	'Maker',	NULL,'Trustee Bank',0),
(13,	'Annexure 14 List of Branches',	'0 0 0 1 * ?',	'Monthly',	'Supervisor',	NULL,'Trustee Bank',0),
(14,	'Annexure 12 Statutory Auditor Certificate',	'0 0 0 15 5 ?',	'Annually',	'Supervisor',	NULL,'Trustee Bank',0),
(15,	'Annexure 13 Compliance certificate',	'0 0 0 15 5 ?',	'Annually',	'Supervisor',	NULL,'Trustee Bank',0),
(16,	'Customized report for the submission',	'0 0 18 * * ?',	'Daily',	'Maker',	NULL,'Trustee Bank',0),
(17,	'Annexure 5 Rejection & Return MIS (Electronic)',	'0 30 15 * * ?',	'Daily',	'Maker',	'Kfintech','Trustee Bank',0),
(18,	'Annexure 5 Rejection & Return MIS (Electronic)',	'0 30 15 * * ?',	'Daily',	'Maker',	'CAMS','Trustee Bank',0),
(19,	'Annexure 5a Rejection & Return MIS (Dremit)',	'0 30 15 * * ?',	'Daily',	'Maker',	'Kfintech','Trustee Bank',0),
(20,	'Annexure 6 Consolidated Collection Report',	'0 0 0 * * TUE',	'Weekly',	'Maker',	'Kfintech','Trustee Bank',0),
(21,	'Annexure 7 MIS Collection Rejection (monthly)',	'0 0 0 10 * ?',	'Monthly',	'Maker',	'Kfintech','Trustee Bank',0),
(22,	'Annexure 10 Summarized data of Performance Report',	'0 0 0 10 * ?',	'Monthly',	'Maker',	'Kfintech','Trustee Bank',0),
(23,	'Annexure 10a Withdrawal MIS',	'0 0 0 10 * ?',	'Monthly',	'Maker',	'Kfintech', 'Trustee Bank',0),
(24,	'Annexure 2 Account Statement monthly',	'0 0 0 5 * ?',	'Monthly',	'Maker',	NULL, 'Trustee Bank',0),
(25,	'Annexure 8 Closing Balance Confirmation (monthly)',	'0 0 0 10 * ?',	'Monthly',	'Maker',	NULL, 'Trustee Bank',0),
(26,	'Annexure 9 Performance Report & Deviation',	'0 0 0 10 * ?',	'Monthly',	'Maker',	NULL, 'Trustee Bank',0),
(27,	'Annexure 16 Internal Audit Report',	'0 0 0 10 * ?',	'Periodically',	'Supervisor',	NULL, 'Trustee Bank',0),
(28,	'Annexure 18 Bio data of directors',	'0 0 0 10 * ?',	'Periodically',	'Supervisor',	NULL, 'Trustee Bank',0),
(29,	'Annexure 15 Annual Audit Report',	'0 0 0 15 5 ?',	'Annually',	'Supervisor',	NULL, 'Trustee Bank',0),
(30,	'Annexure 11 Concurrent Auditor’s certificate',	'0 0 0 30 1,4,7,10 ?',	'Quarterly',	'Supervisor',	NULL, 'Trustee Bank',0),
(31,	'Annexure 17 Change in Interest of Director',	'0 0 0 30 10 ?',	'Half-Yearly',	'Supervisor',	NULL, 'Trustee Bank',0),
(32,	'Annexure 11 Concurrent Auditor Report',	'0 0 0 30 1,4,7,10 ?',	'Quarterly',	'Supervisor',	'NSDL', 'Trustee Bank',0),
(33,	'Annexure 11 Concurrent Auditor Report',	'0 0 0 30 1,4,7,10 ?',	'Quarterly',	'Supervisor',	'Kfintech', 'Trustee Bank',0),
(34,	'Annexure 9 Performance Report & Deviation',	'0 0 0 10 * ?',	'Monthly',	'NPSTAM',	NULL, 'Trustee Bank',0),
(35,	'Annexure 11 Concurrent Auditor’s certificate',	'0 0 0 30 1,4,7,10 ?',	'Quarterly',	'NPSTAM',	NULL, 'Trustee Bank',0),
(36,	'Annexure 16 Internal Audit Report',	'0 0 0 10 * ?',	'Periodically',	'NPSTAM',	NULL, 'Trustee Bank',0),
(37,	'Annexure 12 Statutory Auditor Certificate',	'0 0 0 15 5 ?',	'Annually',	'NPSTAM',	NULL, 'Trustee Bank',0),
(38,	'Annexure 15 Annual Audit Report',	'0 0 0 15 5 ?',	'Annually',	'NPSTAM',	NULL, 'Trustee Bank',0),


(39,	'Benchmark Returns for NPS Schemes Weekly','0 0 0 * * TUE',	'Weekly',	'NPSTAM-PFM',	NULL, 'PFM',0),
(40,	'Weekly CRA NSDL NAV Data',	'0 0 0 * * TUE',	'Weekly',	'CRA-Supervisor',	NULL, 'PFM',1),
(41,	'Weekly CRA Kfintech NAV Data',	'0 0 0 * * TUE',	'Weekly',	'CRA-Supervisor',	NULL, 'PFM',1),
(42,	'Valuation report CG','0 0 0 * * TUE',	'Weekly',	'NPSTAM-PFM',	NULL, 'PFM',0),
(43,	'Valuation report SG','0 0 0 * * TUE',	'Weekly',	'NPSTAM-PFM',	NULL, 'PFM',0),
(44,	'Valuation report Debt','0 0 0 * * TUE',	'Weekly',	'NPSTAM-PFM',	NULL, 'PFM',0),
(45,	'Valuation report Corporate bonds',	'0 0 0 * * TUE',	'Weekly',	'NPSTAM-PFM',	NULL, 'PFM',0),
(46,	'Valuation report Equity','0 0 0 * * TUE',	'Weekly',	'NPSTAM-PFM',	NULL, 'PFM',0),
(47,	'Monthly scheme data',	'0 0 0 5 * ?',	'Monthly',	'PFM-Maker',	NULL, 'PFM',2),
(48,    'Benchmark Returns for NPS Schemes Monthly',	'0 0 0 5 * ?',	'Monthly',	'NPSTAM-PFM',	NULL, 'PFM',0),
(49,	'Growth-Data',	'0 0 0 5 * ?',	'Monthly',	'CRA-Supervisor',	NULL, 'PFM',1),
(50,	'PFM-NPA-Progress',	'0 0 0 5 * ?',	'Monthly',	'PFM-Maker',	NULL, 'PFM',2),
(51,	'Monthly Form 1-14 ',	'0 0 0 5 * ?',	'Monthly',	'PFM-Maker',	NULL, 'PFM',2),
(52,	'Investment Management Fee',	'0 0 0 5 * ?',	'Monthly',	'PFM-Maker',	NULL, 'PFM',2),
(53,	'NPS Trust fee',	'0 0 0 5 * ?',	'Monthly',	'PFM-Maker',	NULL, 'PFM',2),
(54,	'Monthly Compliance certificate',	'0 0 0 5 * ?',	'Monthly',	'PFM-Maker',	NULL, 'PFM',2),
(55,	'CC report for the Month',	'0 0 0 5 * ?',	'Monthly',	'PFM-Maker',	NULL, 'PFM',2),
(56,	'Quarterly Compliance certificate',	'0 0 0 30 1,4,7,10 ?',	'Quarterly',	'PFM-Maker',	NULL, 'PFM',2),
(57,	'Quarterly CC report',	'0 0 0 30 1,4,7,10 ?',	'Quarterly',	'PFM-Maker',	NULL, 'PFM',2),
(58,	'IIAS Voting report',	'0 0 0 5 * ?',	'Monthly',	'NPSTAM-PFM',	NULL, 'PFM',0),
(59,	'SES Non-Unanimous Voting',	'0 0 0 5 * ?',	'Monthly',	'PFM-Maker',	NULL, 'PFM',2),
(60,	'SES Vote Matrix',	'0 0 0 5 * ?',	'Monthly',	'NPSTAM-PFM',	NULL, 'PFM',0),
(61,	'Divergence with Proxy Voters',	'0 0 0 5 * ?',	'Monthly',	'PFM-Maker',	NULL, 'PFM',2),
(62,	'NON- Unanimous Voting',	'0 0 0 5 * ?',	'Monthly',	'PFM-Maker',	NULL, 'PFM',2),
(63,	'Proxy voting Dashboard',	'0 0 0 5 * ?',	'Monthly',	'PFM-Maker',	NULL, 'PFM',2),
(64,	'NPA Development',	'0 0 0 5 * ?',	'Monthly',	'PFM-Maker',	NULL, 'PFM',2),
(65,	'PFM NPA tracking',	'0 0 0 5 * ?',	'Monthly',	'PFM-Maker',	NULL, 'PFM',2),
(66,	'NPA Dashboard',	'0 0 0 5 * ?',	'Monthly',	'PFM-Maker',	NULL, 'PFM',2),
(67,	'NPA Summary',	'0 0 0 5 * ?',	'Monthly',	'PFM-Maker',	NULL, 'PFM',2),
(68,	'NPA Update',	'0 0 0 5 * ?',	'Monthly',	'PFM-Maker',	NULL, 'PFM',2),
(69,	'PFM Scheme quarterly reporting',	'0 0 0 30 1,4,7,10 ?',	'Quarterly',	'PFM-Maker',	NULL, 'PFM',2),
(70,	'Form 1 (A) Report  on transactions in Securities by  Key Personnel in their own name',	'0 0 0 30 1,4,7,10 ?',	'Quarterly',	'PFM-Maker',	NULL, 'PFM',2),
(71,	'Form 3 Report by Director and Key Personnel on instances of self dealing or front running',	'0 0 0 30 1,4,7,10 ?',	'Quarterly',	'PFM-Maker',	NULL, 'PFM',2),
(72,	'Quarterly submission of sch V forms and Regulation 12 (ii) forms',	'0 0 0 30 1,4,7,10 ?',	'Quarterly',	'PFM-Maker',	NULL, 'PFM',2),
(73,	'Quaterly Reports',	'0 0 0 30 1,4,7,10 ?',	'Quarterly',	'PFM-Maker',	NULL, 'PFM',2),
(74,	'E-Voting Count',	'0 0 0 30 1,4,7,10 ?',	'Quarterly',	'PFM-Maker',	NULL, 'PFM',2),
(75,	'E-Voting summary',	'0 0 0 30 1,4,7,10 ?',	'Quarterly',	'PFM-Maker',	NULL, 'PFM',2),
(76,	'Majority Votes',	'0 0 0 30 1,4,7,10 ?',	'Quarterly',	'NPSTAM-PFM',	NULL, 'PFM',0),
(77,	'Vote Matrix',	'0 0 0 30 1,4,7,10 ?',	'Quarterly',	'NPSTAM-PFM',	NULL, 'PFM',0),
(78,	'Internal Audit report - PFM',	'0 0 0 15 5 ?',	'Annually',	'PFM-Maker',	NULL, 'PFM',2),
(79,	'Annual Compliance certificate',	'0 0 0 15 5 ?',	'Annually',	'PFM-Maker',	NULL, 'PFM',2),
(80,	'Annual E-Voting count',	'0 0 0 15 5 ?',	'Annually',	'PFM-Maker',	NULL, 'PFM',2),
(81,	'Customized for for change in  directors or key personnel',	'0 0 0 15 5 ?',	'Annually',	'PFM-Maker',	NULL, 'PFM',2),


(82,	'Monthly AUC Report','0 0 0 * * TUE',	'Monthly',	'Custodian-Maker',	NULL, 'Custodian', 0),
(83,	'Monthly Asset Not Under Custody Report','0 0 0 * * TUE',	'Monthly',	'PFM-Maker',	NULL, 'Custodian',2),
(84,	'Quarterly compliance certificate','0 0 0 * * TUE',	'Monthly',	'Custodian-Maker',	NULL, 'Custodian',0),
(85,	'Annexure 3a - Internal Audit Report submitted by custodian','0 0 0 * * TUE',	'Monthly',	'Custodian-Maker',	NULL, 'Custodian',0),
(86,	'Annexure 2b – NPS Trust observation on Compliance report','0 0 0 * * TUE',	'Monthly',	'Custodian-Maker',	NULL, 'Custodian',0),
(87,	'Annexure IV - PFM','0 0 0 * * TUE',	'Monthly',	'PFM-Maker',	NULL, 'Custodian',2),
(88,	'Annual Audit Report','0 0 0 * * TUE',	'Monthly',	'Custodian-Maker',	NULL, 'Custodian',0),
(89,	'Quarterly Custodian Bills','0 0 0 * * TUE',	'Monthly',	'Custodian-Maker',	NULL, 'Custodian',0),
(90,	'Annexure 5B - Data submitted by individual PFMs','0 0 0 * * TUE',	'Monthly',	'Custodian-Maker',	NULL, 'Custodian',0),



/*(91,	'Esc grievances to NPST during Month','0 0 0 * * TUE',	'Monthly',	'CRA-Maker',	NULL, 'Grievances',0),
(92,	'Griev pending for more than 60 Days against NOs','0 0 0 * * TUE',	'Monthly',	'CRA-Maker',	NULL, 'Grievances',0),
(93,	'Monthly Report Format','0 0 0 * * TUE',	'Monthly',	'CRA-Maker',	NULL, 'Grievances',0),
(94,	'Status 45 days','0 0 0 * * TUE',	'Monthly',	'CRA-Maker',	NULL, 'Grievances',0),
(95,	'Top 5 Entities Month','0 0 0 * * TUE',	'Monthly',	'CRA-Maker',	NULL, 'Grievances',0),*/
(96,	'Category wise ageing analysis of grievances at NPST','0 0 0 * * TUE',	'Monthly',	'CRA-Maker',	NULL, 'Grievances',1),
(97,	'Summary of Grievance pending','0 0 0 * * TUE',	'Monthly',	'CRA-Maker',	NULL, 'Grievances',1),
(98,	'Top 5 data Monthly','0 0 0 * * TUE',	'Monthly',	'CRA-Maker',	NULL, 'Grievances',1),
(99,	'Status 45 days','0 0 0 * * TUE',	'Monthly',	'CRA-Maker',	NULL, 'Grievances',1),
(100,	'Monthly MIS Report','0 0 0 * * TUE',	'Monthly',	'CRA-Maker',	NULL, 'Grievances',1),
(101,	'Final Intimation Letters of NPS','0 0 0 * * TUE',	'Monthly',	'CRA-Maker',	NULL, 'Grievances',1),
(102,	'Final Intimation Letters of APY','0 0 0 * * TUE',	'Monthly',	'CRA-Maker',	NULL, 'Grievances',1),
(103,	'Final Intimation file NPS','0 0 0 * * TUE',	'Monthly',	'CRA-Maker',	NULL, 'Grievances',1),
(104,	'Final Intimation file APY','0 0 0 * * TUE',	'Monthly',	'CRA-Maker',	NULL, 'Grievances',1),



(105,	'Exit & Withdrawal Quarterly Data','0 0 0 30 1,4,7,10 ?',	'Quarterly',	'CRA-Maker',	NULL, 'CRA',1),
(106,	'Monthly Compliance Certificate E&W','0 0 0 1 * ?',	'Monthly',	'CRA-Maker',	NULL, 'CRA',1),
(107,	'Monthly Compliance E&W','0 0 0 1 * ?',	'Monthly',	'CRA-Maker',	NULL, 'CRA',1),
/*(108,	'Withdrawal Summary Report_Month','0 0 0 * * TUE',	'Weekly',	'CRA-Maker',	NULL, 'CRA',0),*/

(109,	'Annual report on proxy voting','0 0 0 15 5 ?',	'Annually',	'PFM-Maker',	NULL, 'PFM',2),
(110,	'Half yearly unaudited financial report','0 0 0 30 10 ?',	'Half-Yearly',	'PFM-Maker',	NULL, 'PFM',2),
(111,	'TDS Report - PFM','0 0 0 * * TUE',	'Monthly',	'PFM-Maker',	NULL, 'PFM',2),
(112,	'TDS Report - Custodian','0 0 0 * * TUE',	'Monthly',	'PFM-Maker',	NULL, 'PFM',2),
(113,	'Quaterly Internal Audit Report',	'0 0 0 30 1,4,7,10 ?',	'Quarterly',	'PFM-Maker',	NULL, 'PFM',2),
(114,	'Half yearly  Internal Audit Report ','0 0 0 30 10 ?',	'Half-Yearly',	'PFM-Maker',	NULL, 'PFM',2),
(115,	'Vote Report',	'0 0 0 30 1,4,7,10 ?',	'Quarterly',	'NPSTAM-PFM',	NULL, 'PFM',0),
(116,	'Stewardship Report',	'0 0 0 30 1,4,7,10 ?',	'Quarterly',	'PFM-Maker',	NULL, 'PFM',2),
(117,	'Form 6',	'0 0 0 30 1,4,7,10 ?',	'Quarterly',	'PFM-Maker',	NULL, 'PFM',2),
(118,	'Half yearly Compliance certificate','0 0 0 30 10 ?',	'Half-Yearly',	'PFM-Maker',	NULL, 'PFM',2),
(119,	'Quarterly Non Unanimous Report','0 0 0 30 1,4,7,10 ?',	'Quarterly',	'PFM-Maker',	NULL, 'PFM',2),
(120,	'Website Data','0 0 0 1 * ?',	'Monthly',	'CRA-Supervisor',	NULL, 'PFM',1),
(121,	'Final Count','0 0 0 30 1,4,7,10 ?',	'Quarterly',	'PFM-Maker',	NULL, 'PFM',2),
(122,	'PF Voting Recommendation','0 0 0 30 1,4,7,10 ?',	'Quarterly',	'PFM-Maker',	NULL, 'PFM',2),

(123,	'eNPS Death Claim Data','0 0 0 1 * ?',	'Monthly',	'NPSTAM-CRA',	NULL, 'PFM',1);

INSERT INTO "reportmaster" ("reportmasterid", "reportname", "duedate", "reporttype", "uploaderrole", "cra","department", "intermediarytype") VALUES

(124,	'Annexure 5a Rejection & Return MIS (Dremit)',	'0 30 15 * * ?',	'Daily',	'Maker',	'CAMS','Trustee Bank',0),
(125,	'Annexure 6 Consolidated Collection Report',	'0 0 0 * * TUE',	'Weekly',	'Maker',	'CAMS','Trustee Bank',0),
(126,	'Annexure 7 MIS Collection Rejection (monthly)',	'0 0 0 10 * ?',	'Monthly',	'Maker',	'CAMS','Trustee Bank',0),
(127,	'Annexure 10 Summarized data of Performance Report',	'0 0 0 10 * ?',	'Monthly',	'Maker',	'CAMS','Trustee Bank',0),
(128,	'Annexure 10a Withdrawal MIS',	'0 0 0 10 * ?',	'Monthly',	'Maker',	'CAMS','Trustee Bank',0),
(129,	'Annexure 11 Concurrent Auditor Report',	'0 0 0 30 1,4,7,10 ?',	'Quarterly',	'Supervisor',	'CAMS', 'Trustee Bank',0),
(130,	'Form 3',	'0 0 0 30 1,4,7,10 ?',	'Quarterly',	'PFM-Maker',	NULL, 'PFM',2);

INSERT INTO "reportmaster" ("reportmasterid", "reportname", "duedate", "reporttype", "uploaderrole", "cra", "department", "intermediarytype") VALUES
(132,	'Man Power Form 1',	'0 0 0 30 1 ?',	'Monthly',	'PFM-Maker',	NULL,	'PFM',	2),
(133,	'Man Power Form 2',	'0 0 0 30 1 ?',	'Monthly',	'PFM-Maker',	NULL,	'PFM',	2),
(131,	'SLA Form',	'0 30 15 * * ?',	'Daily',	'Maker',	NULL,	'Trustee Bank',	0);


INSERT INTO "reportmaster" ("reportmasterid", "reportname", "duedate", "reporttype", "uploaderrole", "cra","department", "intermediarytype") VALUES

(134,	'Monthly report to Authority',	'0 0 0 20 * ?',	'Monthly',	'NPSTAM-PFM',	NULL,'PFM',0),
(135,	'Monthly AUM and return data',	'0 0 0 20 * ?',	'Monthly',	'NPSTAM-PFM',	NULL,'PFM',0),
(136,	'Monthly Exception reporting',	'0 0 0 20 * ?',	'Monthly',	'NPSTAM-PFM',	NULL, 'PFM',0),
(137,	'Quarterly Exception reporting',	'0 0 0 30 1,4,7,10 ?',	'Quarterly',	'NPSTAM-PFM',	NULL, 'PFM',0),
(138,	'Quarterly Reports',	'0 0 0 30 1,4,7,10 ?', 'Quarterly',	'NPSTAM-PFM',	NULL, 'PFM',0),
(139,	'Quarterly Submission of Sch V Forms and Regulation 12 (ii) Forms',	'0 0 0 30 1,4,7,10 ?',	'Quarterly',	'NPSTAM-PFM',	NULL, 'PFM',0),
(140,	'Proxy Voting Report',	'0 0 0 30 1,4,7,10 ?',	'Quarterly',	'NPSTAM-PFM',	NULL, 'PFM',0),
(141,	'Stewardship Reoprt',	'0 0 0 30 1,4,7,10 ?',	'Quarterly',	'NPSTAM-PFM',	NULL, 'PFM',0),
(142,	'Internal Audit Reports',	'0 0 0 30 1,4,7,10 ?',	'Quarterly',	'NPSTAM-PFM',	NULL, 'PFM',0),
(143,	'Half yearly Exception reporting',	'0 0 0 30 10 ?',	'Half-Yearly',	'NPSTAM-PFM',	NULL, 'PFM',0),
(144,	'Yearly Exception reporting',	'0 0 0 15 5 ?',	'Annually',	'NPSTAM-PFM',	NULL, 'PFM',0),
(145,	'Annual Proxy Voting Reports',	'0 0 0 15 5 ?',	'Annually',	'NPSTAM-PFM',	NULL, 'PFM',0),
(146,	'Detailed Audit Report',	'0 0 0 15 5 ?',	'Annually',	'NPSTAM-PFM',	NULL, 'PFM',0),
(147,	'Asset Not under Custody Report',	'0 0 0 20 * ?',	'Monthly',	'NPSTAM-Custodian',	NULL, 'Custodian',0),
(148,	'Quarterly Compliance Certificate',	'0 0 0 30 1,4,7,10 ?',	'Quarterly',	'NPSTAM-Custodian',	NULL, 'Custodian',0),
(149,	'Internal Audit report',	'0 0 0 30 1,4,7,10 ?',	'Quarterly',	'NPSTAM-Custodian',	NULL, 'Custodian',0),
(150,	'Annual Audit Report',	'0 0 0 30 1,4,7,10 ?',	'Quarterly',	'NPSTAM-Custodian',	NULL, 'Custodian',0),
(151,	'Grievances handled at NPS Trust',	'0 0 0 20 * ?',	'Monthly',	'NPSTMGR-Grievances',	NULL, 'Grievances',0),
(152,	'Transferring of grievances at PFRDA',	'0 0 0 20 * ?',	'Monthly',	'NPSTMGR-Grievances',	NULL, 'Grievances',0),
(153,	'Top 5 entries - max grievance',	'0 0 0 20 * ?',	'Monthly',	'NPSTMGR-Grievances',	NULL, 'Grievances',0),
(154,	'Final Intimation forwarding to PFRDA',	'0 0 0 30 1,4,7,10 ?','Quarterly',	'NPSTMGR-Grievances',	NULL, 'Grievances',0);
INSERT INTO "reportmaster" ("reportmasterid", "reportname", "duedate", "reporttype", "uploaderrole", "cra","department", "intermediarytype") VALUES
(155,	'Annual Detailed Audit Report',	'0 0 0 15 5 ?',	'Annually',	'PFM-Maker',	NULL,	'PFM',	2),

(156,	'Valuation Reports',	'0 0 0 * * TUE',	'Weekly',	'NPSTAM-PFM',	NULL,	'PFM',	0);

INSERT INTO "reportmaster" ("reportmasterid", "reportname", "duedate", "reporttype", "uploaderrole", "cra","department", "intermediarytype") VALUES
(157,	'PFM-Detailed Audit Report', '0 0 0 * * TUE',	'Annually',	'PFM-Maker',	NULL,	'PFM',	2),
(158,	'ASP Report', '0 0 0 20 * ?',	'Monthly',	'NPSTAM-PFM',	NULL,	'PFM',	0);
INSERT INTO "reportmaster" ("reportmasterid", "reportname", "duedate", "reporttype", "uploaderrole", "cra","department", "intermediarytype") VALUES
(159,	'Quarterly submission of forms as per PFRDA regualtions',	'0 0 0 30 1,4,7,10 ?',	'Quarterly',	'PFM-Maker',	NULL, 'PFM',2);


