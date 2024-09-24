create index IX_C986E69A on nps_ErmBatchInformation (batchType[$COLUMN_LENGTH:75$]);
create index IX_B6F22EDE on nps_ErmBatchInformation (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_3254D4E0 on nps_ErmBatchInformation (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_8716D97D on nps_ErmBatchNumber (uuid_[$COLUMN_LENGTH:75$]);

create index IX_A8CD1BF1 on nps_ErmInformation (batchNo[$COLUMN_LENGTH:75$], batchStatus);
create index IX_F80F6AED on nps_ErmInformation (groupId, pran[$COLUMN_LENGTH:75$]);
create index IX_C43A8D4 on nps_ErmInformation (groupId, status, batchType[$COLUMN_LENGTH:75$]);
create index IX_442FBB53 on nps_ErmInformation (groupId, status, pran[$COLUMN_LENGTH:75$]);
create index IX_C15F92F2 on nps_ErmInformation (pfmName[$COLUMN_LENGTH:75$]);
create index IX_1CF8145A on nps_ErmInformation (userId);
create index IX_43109614 on nps_ErmInformation (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_4A2F9196 on nps_ErmInformation (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_95CC3C28 on npst_ErmBatchInformation (batchType[$COLUMN_LENGTH:75$]);
create index IX_E5DA1D90 on npst_ErmBatchInformation (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_476CC812 on npst_ErmBatchInformation (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_36B38EFF on npst_ErmInformation (batchNo[$COLUMN_LENGTH:75$], batchStatus);
create index IX_66DF1962 on npst_ErmInformation (groupId, status, batchType[$COLUMN_LENGTH:75$]);
create index IX_7317AA05 on npst_ErmInformation (groupId, status, pran[$COLUMN_LENGTH:75$]);
create index IX_1356C022 on npst_ErmInformation (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_1674E724 on npst_ErmInformation (uuid_[$COLUMN_LENGTH:75$], groupId);