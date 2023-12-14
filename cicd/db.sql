create table hibernate_sequences
(
    sequence_name varchar(255) not null
        primary key,
    next_val      bigint
);


create table users
(
    id            bigint default nextval('users_id_seq'::regclass) not null
        primary key,
    code          varchar(255),
    firstname     varchar(255),
    lastname      varchar(255),
    passwd        varchar(255),
    phone         varchar(255)
        constraint uk_du5v5sr43g5bfnji4vb8hg5s3
            unique,
    ref_id        varchar(255),
    role          varchar(255),
    created_at    timestamp(6),
    created_by    varchar(255),
    updated_at    timestamp(6),
    updated_by    varchar(255),
    is_active     boolean,
    is_lock_point boolean,
    balance       double precision
);


create table invest_package
(
    id          serial
        primary key,
    created_at  timestamp(6),
    created_by  varchar(255),
    updated_at  timestamp(6),
    updated_by  varchar(255),
    amt         double precision,
    duration    integer,
    invest_type smallint,
    rate        double precision,
    description varchar(5000),
    title       varchar(256),
    is_active   boolean,
    image       varchar(255)
);



create table leader_package
(
    id          serial
        primary key,
    created_at  timestamp(6),
    created_by  varchar(255),
    updated_at  timestamp(6),
    updated_by  varchar(255),
    amt         bigint,
    duration    integer,
    invest_type smallint,
    rate        double precision,
    description varchar(5000),
    title       varchar(256),
    is_active   boolean,
    image       varchar(255)
);


create table user_package
(
    id            bigserial
        primary key,
    package_id    integer
        constraint fk8hpqt7scsojf6d1dsl0620l3l
            references invest_package,
    user_id       bigint
        constraint fk23wrg2jabxivswndr07og5q0y
            references users,
    created_at    timestamp(6),
    created_by    varchar(255),
    updated_at    timestamp(6),
    updated_by    varchar(255),
    withdraw_date timestamp(6),
    amt           double precision,
    duration      integer,
    interest_date timestamp(6) with time zone,
    invest_type   smallint
        constraint user_package_invest_type_check
            check ((invest_type >= 0) AND (invest_type <= 4)),
    rate          double precision,
    status        smallint
        constraint user_package_status_check
            check ((status >= 0) AND (status <= 1))
);


create table interest_his
(
    id             serial
        primary key,
    created_at     timestamp(6),
    created_by     varchar(255),
    updated_at     timestamp(6),
    updated_by     varchar(255),
    amount         double precision,
    package_id     integer
        constraint fkrirslxeuwcahof5t65b49hiwa
            references invest_package,
    leader_id      integer
        constraint fk6uwk035o1arfsamsavf8mgw0k
            references leader_package,
    user_id        bigint
        constraint fkiyailecn6sfnvhb3afcti9i6t
            references users,
    remain_balance double precision,
    ref_id         bigint
        constraint fk6xl2tsm7d7v7w8gkjmfdy7uwx
            references users
);

create table multi_level_rate
(
    id         serial
        primary key,
    created_at timestamp(6),
    created_by varchar(255),
    updated_at timestamp(6),
    updated_by varchar(255),
    level      integer,
    rate       double precision
);


create table transaction_his
(
    id               serial
        primary key,
    created_at       timestamp(6),
    created_by       varchar(255),
    updated_at       timestamp(6),
    updated_by       varchar(255),
    account_name     varchar(128),
    amount           double precision,
    bank             varchar(128),
    description      varchar(1024),
    number_account   varchar(20),
    status           smallint,
    transaction_type smallint,
    user_id          bigint
        constraint fk4khpekatuie5ykhe2nrnet0eh
            references users,
    remain_balance   double precision
);

create table user_bank
(
    id             bigserial
        primary key,
    created_at     timestamp(6),
    created_by     varchar(255),
    updated_at     timestamp(6),
    updated_by     varchar(255),
    bank           varchar(256),
    number_account varchar(20),
    user_id        bigint
        constraint fkacjnlbp54g1psri2bt17q739r
            references users,
    account_name   varchar
);


create table user_leader
(
    id            bigserial
        primary key,
    created_at    timestamp(6) with time zone,
    created_by    varchar(255),
    updated_at    timestamp(6) with time zone,
    updated_by    varchar(255),
    amt           double precision,
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
    user_id    bigint
        constraint fkq50wsn94sc3mi90gtidk0k34a
            references users,
    status     smallint
        constraint report_status_check
            check ((status >= 0) AND (status <= 3)),
    title      varchar(255)
);


