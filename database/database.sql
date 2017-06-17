--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.0
-- Dumped by pg_dump version 9.6.0

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: t_class_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE t_class_type (
    taskclassname character varying(100) NOT NULL
);


ALTER TABLE t_class_type OWNER TO postgres;

--
-- Name: t_dep; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE t_dep (
    task_id integer NOT NULL,
    parent_id integer NOT NULL
);


ALTER TABLE t_dep OWNER TO postgres;

--
-- Name: TABLE t_dep; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE t_dep IS '任务依赖表';


--
-- Name: COLUMN t_dep.task_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN t_dep.task_id IS '任务ID';


--
-- Name: COLUMN t_dep.parent_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN t_dep.parent_id IS '前置任务ID';


--
-- Name: t_log; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE t_log (
    log_time timestamp(0) without time zone,
    task_id integer,
    log_level integer,
    log_type integer,
    log_msg text
);


ALTER TABLE t_log OWNER TO postgres;

--
-- Name: TABLE t_log; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE t_log IS '任务日志表';


--
-- Name: COLUMN t_log.log_time; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN t_log.log_time IS '日志记录时间';


--
-- Name: COLUMN t_log.task_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN t_log.task_id IS '任务ID';


--
-- Name: COLUMN t_log.log_level; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN t_log.log_level IS '日志级别';


--
-- Name: COLUMN t_log.log_type; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN t_log.log_type IS '日志类型';


--
-- Name: COLUMN t_log.log_msg; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN t_log.log_msg IS '日志内容';


--
-- Name: t_param; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE t_param (
    name character varying(50),
    value character varying(50)
);


ALTER TABLE t_param OWNER TO postgres;

--
-- Name: TABLE t_param; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE t_param IS '参数表';


--
-- Name: COLUMN t_param.name; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN t_param.name IS '参数名';


--
-- Name: COLUMN t_param.value; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN t_param.value IS '参数值';


--
-- Name: t_session; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE t_session (
    session_id character varying(500),
    tasks integer,
    total_cost numeric(19,6),
    session_date character varying(8)
);


ALTER TABLE t_session OWNER TO postgres;

--
-- Name: TABLE t_session; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE t_session IS '并发线程表';


--
-- Name: COLUMN t_session.session_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN t_session.session_id IS '线程ID';


--
-- Name: COLUMN t_session.tasks; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN t_session.tasks IS '线程任务数';


--
-- Name: COLUMN t_session.total_cost; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN t_session.total_cost IS '线程总代价';


--
-- Name: COLUMN t_session.session_date; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN t_session.session_date IS '会话日期（8位）';


--
-- Name: t_stock_info; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE t_stock_info (
    stock_id character varying(10) NOT NULL,
    stock_name character varying(20)
);


ALTER TABLE t_stock_info OWNER TO postgres;

--
-- Name: TABLE t_stock_info; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE t_stock_info IS '股票id表';


--
-- Name: COLUMN t_stock_info.stock_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN t_stock_info.stock_id IS '股票代码';


--
-- Name: COLUMN t_stock_info.stock_name; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN t_stock_info.stock_name IS '股票名称';


--
-- Name: t_stock_sys_param; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE t_stock_sys_param (
    param_id numeric(6,0) NOT NULL,
    param_name character varying(20),
    param_value character varying(20)
);


ALTER TABLE t_stock_sys_param OWNER TO postgres;

--
-- Name: TABLE t_stock_sys_param; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE t_stock_sys_param IS '股票分析系统参数表';


--
-- Name: COLUMN t_stock_sys_param.param_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN t_stock_sys_param.param_id IS '参数ID';


--
-- Name: COLUMN t_stock_sys_param.param_name; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN t_stock_sys_param.param_name IS '参数名称';


--
-- Name: COLUMN t_stock_sys_param.param_value; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN t_stock_sys_param.param_value IS '参数数值';


--
-- Name: t_stock_trainning_info; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE t_stock_trainning_info (
    stock_id character varying(10) NOT NULL,
    s_date character varying(8) NOT NULL,
    open_price numeric(9,3),
    close_price numeric(9,3),
    max_price numeric(9,3),
    min_price numeric(9,3)
);


ALTER TABLE t_stock_trainning_info OWNER TO postgres;

--
-- Name: TABLE t_stock_trainning_info; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE t_stock_trainning_info IS '股票价格明细表';


--
-- Name: COLUMN t_stock_trainning_info.stock_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN t_stock_trainning_info.stock_id IS '股票代码';


--
-- Name: COLUMN t_stock_trainning_info.s_date; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN t_stock_trainning_info.s_date IS '数据日期';


--
-- Name: COLUMN t_stock_trainning_info.open_price; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN t_stock_trainning_info.open_price IS '开盘价格';


--
-- Name: COLUMN t_stock_trainning_info.close_price; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN t_stock_trainning_info.close_price IS '关盘价格';


--
-- Name: COLUMN t_stock_trainning_info.max_price; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN t_stock_trainning_info.max_price IS '当天最高价';


--
-- Name: COLUMN t_stock_trainning_info.min_price; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN t_stock_trainning_info.min_price IS '当天对低价';


--
-- Name: t_task; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE t_task (
    task_id bigint NOT NULL,
    name character varying(20),
    des character varying(500),
    taskclassname character varying(500),
    type integer,
    st integer,
    para character varying(500),
    prior numeric(10,0),
    cost numeric(19,6),
    avg_cost numeric(19,6)
);


ALTER TABLE t_task OWNER TO postgres;

--
-- Name: TABLE t_task; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE t_task IS '任务信息表';


--
-- Name: COLUMN t_task.task_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN t_task.task_id IS '任务ID';


--
-- Name: COLUMN t_task.name; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN t_task.name IS '任务名称';


--
-- Name: COLUMN t_task.des; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN t_task.des IS '任务描述';


--
-- Name: COLUMN t_task.taskclassname; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN t_task.taskclassname IS '任务类名';


--
-- Name: COLUMN t_task.type; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN t_task.type IS '任务类型';


--
-- Name: COLUMN t_task.st; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN t_task.st IS '任务状态';


--
-- Name: COLUMN t_task.para; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN t_task.para IS '任务参数';


--
-- Name: COLUMN t_task.prior; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN t_task.prior IS '优先级';


--
-- Name: COLUMN t_task.cost; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN t_task.cost IS '任务代价';


--
-- Name: COLUMN t_task.avg_cost; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN t_task.avg_cost IS '任务平均代价';


--
-- Name: t_task_list; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE t_task_list (
    task_id integer NOT NULL,
    st integer,
    t_date character varying(8) NOT NULL,
    beg_time timestamp(0) without time zone,
    end_time timestamp(0) without time zone
);


ALTER TABLE t_task_list OWNER TO postgres;

--
-- Name: TABLE t_task_list; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE t_task_list IS '任务运行表';


--
-- Name: COLUMN t_task_list.task_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN t_task_list.task_id IS '任务ID';


--
-- Name: COLUMN t_task_list.st; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN t_task_list.st IS '任务状态';


--
-- Name: COLUMN t_task_list.t_date; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN t_task_list.t_date IS '任务日期（8位）';


--
-- Name: COLUMN t_task_list.beg_time; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN t_task_list.beg_time IS '任务开始时间';


--
-- Name: COLUMN t_task_list.end_time; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN t_task_list.end_time IS '任务结束时间';


--
-- Name: t_task_st; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE t_task_st (
    st integer,
    des character varying(50)
);


ALTER TABLE t_task_st OWNER TO postgres;

--
-- Name: TABLE t_task_st; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE t_task_st IS '任务状态表';


--
-- Name: COLUMN t_task_st.st; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN t_task_st.st IS '状态ID';


--
-- Name: COLUMN t_task_st.des; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN t_task_st.des IS '状态说明';


--
-- Name: t_task_task_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE t_task_task_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE t_task_task_id_seq OWNER TO postgres;

--
-- Name: t_task_task_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE t_task_task_id_seq OWNED BY t_task.task_id;


--
-- Name: t_task_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE t_task_type (
    type integer,
    des character varying(50)
);


ALTER TABLE t_task_type OWNER TO postgres;

--
-- Name: TABLE t_task_type; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE t_task_type IS '任务类型表';


--
-- Name: COLUMN t_task_type.type; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN t_task_type.type IS '类型ID';


--
-- Name: COLUMN t_task_type.des; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN t_task_type.des IS '类型说明';


--
-- Name: t_task task_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY t_task ALTER COLUMN task_id SET DEFAULT nextval('t_task_task_id_seq'::regclass);


--
-- Name: t_class_type t_class_type_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY t_class_type
    ADD CONSTRAINT t_class_type_pkey PRIMARY KEY (taskclassname);


--
-- Name: t_dep t_dep_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY t_dep
    ADD CONSTRAINT t_dep_pkey PRIMARY KEY (task_id, parent_id);


--
-- Name: t_stock_info t_stock_info_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY t_stock_info
    ADD CONSTRAINT t_stock_info_pkey PRIMARY KEY (stock_id);


--
-- Name: t_stock_sys_param t_stock_sys_param_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY t_stock_sys_param
    ADD CONSTRAINT t_stock_sys_param_pkey PRIMARY KEY (param_id);


--
-- Name: t_stock_trainning_info t_stock_trainning_info_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY t_stock_trainning_info
    ADD CONSTRAINT t_stock_trainning_info_pkey PRIMARY KEY (stock_id, s_date);


--
-- Name: t_task_list t_task_list_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY t_task_list
    ADD CONSTRAINT t_task_list_pkey PRIMARY KEY (task_id, t_date);


--
-- Name: t_task t_task_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY t_task
    ADD CONSTRAINT t_task_pkey PRIMARY KEY (task_id);


--
-- PostgreSQL database dump complete
--

