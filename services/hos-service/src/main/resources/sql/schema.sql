create sequence UserSeqGen
    START WITH 100
    MINVALUE 1
    MAXVALUE 1000000
    NO CYCLE
    INCREMENT BY 1;

create sequence UserDevSeqGen
    START WITH 100
    MINVALUE 1
    MAXVALUE 1000000
    NO CYCLE
    INCREMENT BY 1;

create sequence AuthRoleSeqGen
    START WITH 100
    MINVALUE 1
    MAXVALUE 1000000
    NO CYCLE
    INCREMENT BY 1;
