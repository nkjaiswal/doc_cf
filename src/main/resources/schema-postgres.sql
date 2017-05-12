CREATE TABLE public.documents1
(
    documentid integer NOT NULL,
    document character varying(10000) COLLATE pg_catalog."default",
    documentname character varying(300) COLLATE pg_catalog."default",
    CONSTRAINT documents_pkey PRIMARY KEY (documentid)
)