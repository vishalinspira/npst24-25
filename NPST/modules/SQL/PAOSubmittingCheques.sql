CREATE TABLE public.paosubmittingcheques (
    id_ integer NOT NULL,
    paoain character varying(100),
    total character varying(100),
    name_ character varying(100),
    emailid character varying(100),
    addressline1 character varying(100),
    addressline2 character varying(100),
    pin character varying(100),
    createdon date,
    createdby integer
);