CREATE TABLE public.notid (
    id_ integer NOT NULL,
    srno character varying(100),
    paymentid character varying(100),
    retrefno character varying(100),
    accountno character varying(100),
    ifscno character varying(100),
    paymentreceiptdate character varying(20),
    period_ character varying(20),
    originalutr character varying(100),
    mode_ character varying(100),
    utramount character varying(100),
    beneficiaryaccno character varying(100),
    npsvirtualaccno character varying(100),
    npssectoraccno character varying(100),
    returndate character varying(20),
    returnedutr character varying(100),
    tid_if_available character varying(100),
    reasonofreturn character varying(100),
    commentsofnpstb character varying(100),
    paoname character varying(100),
    emailid character varying(100),
    createdon date,
    createdby integer
);