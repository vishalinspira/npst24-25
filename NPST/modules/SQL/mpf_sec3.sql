DROP TABLE IF EXISTS "mpf_sec3";

CREATE TABLE public.mpf_sec3 (
    manpowersec3id bigint NOT NULL,
    reportuploadlogid bigint NOT NULL,
    name character varying,
    typeofdirector character varying,
    chairperson_member character varying,
    independent_nonindependent character varying,
    directoridentificationnumber character varying,
    dateofappointment date,
    highestqualification character varying,
    totalprofessionalexperience bigint,
    resume_biodata bigint,
    formmbp1ofthedirector character varying
    
) WITH (oids = false);