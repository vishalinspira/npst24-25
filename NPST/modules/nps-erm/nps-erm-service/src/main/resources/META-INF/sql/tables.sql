create table nps_ErmBatchInformation (
	uuid_ VARCHAR(75) null,
	ermBatchInformationId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	ermInformationIds VARCHAR(1000) null,
	batchGroupId LONG,
	batchType VARCHAR(75) null,
	batchTimePeriodFrom DATE null,
	batchTimePeriodTo DATE null,
	cutOffDate DATE null,
	submissionDate DATE null,
	remark VARCHAR(75) null,
	stipulatedTime VARCHAR(75) null,
	previousBatchId LONG
);

create table nps_ErmBatchNumber (
	uuid_ VARCHAR(75) null,
	ermBatchNumberId LONG not null primary key,
	ermInformationIds VARCHAR(75) null
);

create table nps_ErmInformation (
	uuid_ VARCHAR(75) null,
	ermInformationId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null,
	batchType VARCHAR(75) null,
	transactedAmount LONG,
	pran VARCHAR(75) null,
	transactionDate DATE null,
	transactionMode VARCHAR(75) null,
	transactionSettlementDate DATE null,
	tokenNo VARCHAR(75) null,
	rectificationRequestMode VARCHAR(75) null,
	rectificationDate DATE null,
	remittedAmount LONG,
	remittedDate DATE null,
	tierType VARCHAR(75) null,
	transactionType VARCHAR(75) null,
	transferAmount LONG,
	documentationsDate DATE null,
	rectificationAmount LONG,
	caseNo VARCHAR(75) null,
	subscriberName VARCHAR(75) null,
	enpsOrderId VARCHAR(75) null,
	rectificationType VARCHAR(75) null,
	rectificationRequestDate DATE null,
	grievanceReceivedDate DATE null,
	grievanceText VARCHAR(75) null,
	stipulated VARCHAR(75) null,
	submissionStipulatedTime VARCHAR(75) null,
	remark VARCHAR(75) null,
	recommendation VARCHAR(75) null,
	process VARCHAR(75) null,
	npstRemark VARCHAR(75) null,
	noNpstRemark VARCHAR(75) null,
	pfmName VARCHAR(75) null,
	batchNo VARCHAR(75) null,
	batchStatus INTEGER,
	isForwardToNpst INTEGER,
	selfDeclarationFileId LONG,
	accountStatementFileId LONG,
	transactionsStatementFileId LONG,
	documentNameFileId LONG
);

create table npst_ErmBatchInformation (
	uuid_ VARCHAR(75) null,
	ermBatchInformationId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	ermInformationIds VARCHAR(1000) null,
	batchGroupId LONG,
	batchType VARCHAR(75) null,
	batchTimePeriod DATE null,
	cutOffDate DATE null,
	remark VARCHAR(75) null,
	stipulatedTime VARCHAR(75) null
);

create table npst_ErmInformation (
	uuid_ VARCHAR(75) null,
	ermInformationId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null,
	batchType VARCHAR(75) null,
	transactedAmount LONG,
	pran VARCHAR(75) null,
	transactionDate DATE null,
	transactionMode VARCHAR(75) null,
	transactionSettlementDate DATE null,
	tokenNo VARCHAR(75) null,
	rectificationRequestMode VARCHAR(75) null,
	rectificationDate DATE null,
	remittedAmount LONG,
	remittedDate DATE null,
	tierType VARCHAR(75) null,
	transactionType VARCHAR(75) null,
	transferAmount LONG,
	documentationsDate DATE null,
	rectificationAmount LONG,
	caseNo VARCHAR(75) null,
	subscriberName VARCHAR(75) null,
	enpsOrderId VARCHAR(75) null,
	rectificationType VARCHAR(75) null,
	rectificationRequestDate DATE null,
	grievanceReceivedDate DATE null,
	grievanceText VARCHAR(75) null,
	stipulated VARCHAR(75) null,
	remark VARCHAR(75) null,
	batchNo VARCHAR(75) null,
	batchStatus INTEGER,
	selfDeclarationFileId LONG,
	accountStatementFileId LONG,
	transactionsStatementFileId LONG,
	documentNameFileId LONG
);