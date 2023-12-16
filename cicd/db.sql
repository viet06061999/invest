create table hibernate_sequences
(
    sequence_name varchar(255) not null
        primary key,
    next_val      bigint
);

create sequence users_id_seq
    as integer;

create table users
(
    id                bigint default nextval('users_id_seq'::regclass) not null
        primary key,
    code              varchar(10),
    firstname         varchar(44),
    lastname          varchar(44),
    passwd            varchar(128),
    phone             varchar(12)
        constraint uk_du5v5sr43g5bfnji4vb8hg5s3
            unique,
    ref_id            varchar(10),
    role              varchar(255),
    created_at        timestamp(6) with time zone,
    created_by        varchar(255),
    updated_at        timestamp(6) with time zone,
    updated_by        varchar(255),
    is_active         boolean,
    is_lock_point     boolean,
    available_balance bigint,
    deposit_balance   bigint
);

create table invest_package
(
    id          serial
        primary key,
    created_at  timestamp(6) with time zone,
    created_by  varchar(255),
    updated_at  timestamp(6) with time zone,
    updated_by  varchar(255),
    amt         bigint not null,
    description varchar(5000),
    duration    integer,
    image       varchar(255),
    invest_type smallint
        constraint invest_package_invest_type_check
            check ((invest_type >= 0) AND (invest_type <= 4)),
    is_active   boolean,
    rate        double precision,
    title       varchar(256)
);

create table leader_package
(
    id          serial
        primary key,
    created_at  timestamp(6) with time zone,
    created_by  varchar(255),
    updated_at  timestamp(6) with time zone,
    updated_by  varchar(255),
    amt         bigint,
    description varchar(5000),
    duration    integer,
    image       varchar(255),
    invest_type smallint
        constraint leader_package_invest_type_check
            check ((invest_type >= 0) AND (invest_type <= 4)),
    is_active   boolean,
    rate        double precision,
    title       varchar(256)
);

create table invest_his
(
    id                       serial
        primary key,
    created_at               timestamp(6) with time zone,
    created_by               varchar(255),
    updated_at               timestamp(6) with time zone,
    updated_by               varchar(255),
    amount                   bigint not null,
    interest_amount          bigint not null,
    remain_available_balance bigint not null,
    package_id               integer
        constraint fk4xxtplglhbr2r6m4l6ipfmgs4
            references invest_package,
    leader_id                integer
        constraint fkm58m973ym7h9qtuai87wq6fa4
            references leader_package,
    ref_id                   bigint
        constraint fk6ad4pc4s87wpbv2gwfhmxfgq5
            references users,
    user_id                  bigint
        constraint fk2090hghmbyo4nswl5h1w7t1dn
            references users
);

create table multi_level_rate
(
    id         serial
        primary key,
    created_at timestamp(6) with time zone,
    created_by varchar(255),
    updated_at timestamp(6) with time zone,
    updated_by varchar(255),
    level      integer,
    rate       double precision
);

create table report
(
    id         bigserial
        primary key,
    created_at timestamp(6) with time zone,
    created_by varchar(255),
    updated_at timestamp(6) with time zone,
    updated_by varchar(255),
    attach     varchar(255),
    report     varchar(255),
    status     smallint
        constraint report_status_check
            check ((status >= 0) AND (status <= 3)),
    title      varchar(255),
    user_id    bigint
        constraint fkq50wsn94sc3mi90gtidk0k34a
            references users
);

create table transaction_his
(
    id                       serial
        primary key,
    created_at               timestamp(6) with time zone,
    created_by               varchar(255),
    updated_at               timestamp(6) with time zone,
    updated_by               varchar(255),
    account_name             varchar(128),
    amount                   bigint not null,
    bank                     varchar(128),
    description              varchar(1024),
    number_account           varchar(20),
    remain_available_balance bigint not null,
    remain_deposit_balance   bigint not null,
    status                   smallint
        constraint transaction_his_status_check
            check ((status >= 0) AND (status <= 2)),
    transaction_type         smallint
        constraint transaction_his_transaction_type_check
            check ((transaction_type >= 0) AND (transaction_type <= 1)),
    user_id                  bigint
        constraint fk4khpekatuie5ykhe2nrnet0eh
            references users
);

create table user_bank
(
    id             bigserial
        primary key,
    created_at     timestamp(6) with time zone,
    created_by     varchar(255),
    updated_at     timestamp(6) with time zone,
    updated_by     varchar(255),
    account_name   varchar(255),
    bank           varchar(256),
    number_account varchar(20),
    user_id        bigint
        constraint fkacjnlbp54g1psri2bt17q739r
            references users
);

create table user_leader
(
    id            bigserial
        primary key,
    created_at    timestamp(6) with time zone,
    created_by    varchar(255),
    updated_at    timestamp(6) with time zone,
    updated_by    varchar(255),
    amt           bigint not null,
    duration      integer,
    interest_date timestamp(6) with time zone,
    invest_type   smallint
        constraint user_leader_invest_type_check
            check ((invest_type >= 0) AND (invest_type <= 4)),
    rate          double precision,
    status        smallint
        constraint user_leader_status_check
            check ((status >= 0) AND (status <= 1)),
    withdraw_date timestamp(6) with time zone,
    package_id    integer
        constraint fkqlygkx2hfddmc9t821pfla53v
            references leader_package,
    user_id       bigint
        constraint fksoy95mi0u9ep3pqfit1m10ceu
            references users
);

create table user_package
(
    id            bigserial
        primary key,
    created_at    timestamp(6) with time zone,
    created_by    varchar(255),
    updated_at    timestamp(6) with time zone,
    updated_by    varchar(255),
    amt           bigint not null,
    duration      integer,
    interest_date timestamp(6) with time zone,
    invest_type   smallint
        constraint user_package_invest_type_check
            check ((invest_type >= 0) AND (invest_type <= 4)),
    rate          double precision,
    status        smallint
        constraint user_package_status_check
            check ((status >= 0) AND (status <= 1)),
    withdraw_date timestamp(6) with time zone,
    package_id    integer
        constraint fk8hpqt7scsojf6d1dsl0620l3l
            references invest_package,
    user_id       bigint
        constraint fk23wrg2jabxivswndr07og5q0y
            references users
);

