create table cache_reportstatus (
	uuid_ VARCHAR(75) null,
	rscId VARCHAR(75) not null primary key,
	createDate DATE null,
	modifiedDate DATE null,
	data_ VARCHAR(75) null
);