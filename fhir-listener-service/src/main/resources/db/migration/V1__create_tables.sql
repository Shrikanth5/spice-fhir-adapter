CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE audit (
 	id serial4 NOT NULL,
 	entity varchar NULL,
 	"action" varchar NULL,
 	entity_id int8 NULL,
 	column_name varchar NULL,
 	old_value varchar NULL,
 	new_value varchar NULL,
 	created_by int8 NULL,
 	updated_by int8 NULL,
 	created_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
 	updated_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
 	CONSTRAINT audit_pkey PRIMARY KEY (id)
 );

 CREATE TABLE country (
 	id serial4 NOT NULL,
 	"name" varchar NOT NULL,
 	code int8 NULL,
 	abbreviation varchar NOT NULL,
 	is_active bool NULL DEFAULT true,
 	is_deleted bool NULL DEFAULT false,
 	created_by int8 NULL,
 	updated_by int8 NULL,
 	created_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
 	updated_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
 	CONSTRAINT country_pkey PRIMARY KEY (id)
 );


 CREATE TABLE fhir_audit (
 	spice_id int8 NULL,
 	spice_request varchar NULL,
 	fhir_response varchar NULL,
 	fhir_id int8 NULL
 );

 CREATE TABLE fhir_mapping (
 	spice_id int8 NULL,
 	fhir_id int8 NULL,
 	status varchar NULL,
 	reason varchar NULL,
 	id serial4 NOT NULL,
 	spice_module varchar NULL,
 	fhir_resource_type varchar NULL,
 	spice_module_id int8 NULL,
 	created_by int8 NULL,
    updated_by int8 NULL,
    created_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP
 );

 CREATE TABLE organization (
 	id serial4 NOT NULL,
 	form_data_id int8 NULL,
 	form_name varchar NULL,
 	"name" varchar NULL,
 	parent_organization_id int8 NULL,
 	is_active bool NULL DEFAULT true,
 	is_deleted bool NULL DEFAULT false,
 	created_by int8 NULL,
 	updated_by int8 NULL,
 	created_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
 	updated_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
 	CONSTRAINT organization_pkey PRIMARY KEY (id)
 );

 CREATE TABLE "role" (
 	id serial4 NOT NULL,
 	"name" varchar NULL,
 	"level" int4 NULL,
 	is_active bool NULL DEFAULT true,
 	is_deleted bool NULL DEFAULT false,
 	created_by int8 NULL,
 	updated_by int8 NULL,
 	created_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
 	updated_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
 	CONSTRAINT role_pkey PRIMARY KEY (id)
 );

 CREATE TYPE "message_status_code" AS ENUM (
    'RECEIVED',
   	'SUCCESS',
   	'FAILURE',
   	'DUPLICATE');

 CREATE TABLE spice_message_tracker (
 	id serial4 NOT NULL,
 	message text NOT NULL,
 	deduplication_id varchar(255) NOT NULL,
 	status public."message_status_code" NOT NULL,
 	created_by int8 NOT NULL,
 	updated_by int8 NULL,
 	created_at timestamp NOT NULL,
 	updated_at timestamp NULL,
 	is_active bool NULL,
 	is_deleted bool NULL,
 	CONSTRAINT spice_message_tracker_pkey PRIMARY KEY (id)
 );

 CREATE TABLE timezone (
 	id serial4 NOT NULL,
 	description varchar NULL,
 	abbreviation varchar NULL,
 	"offset" varchar NULL,
 	is_active bool NULL DEFAULT true,
 	is_deleted bool NULL DEFAULT false,
 	created_by int8 NULL,
 	updated_by int8 NULL,
 	created_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
 	updated_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
 	CONSTRAINT timezone_pkey PRIMARY KEY (id)
 );

 CREATE TABLE user_spice_fhir_mapping (
 	id serial4 NOT NULL,
 	spice_user_id int8 NOT NULL UNIQUE,
 	fhir_practitioner_id int8 NULL,
 	created_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
 	updated_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
 	is_active bool NULL DEFAULT true,
 	is_deleted bool NULL DEFAULT false,
 	created_by int8 NULL,
 	updated_by int8 NULL,
 	CONSTRAINT user_spice_fhir_mapping_pkey PRIMARY KEY (id)
 );

 CREATE TABLE "user" (
 	id serial4 NOT NULL,
 	user_name varchar NULL,
 	"password" varchar NULL,
 	first_name varchar NULL,
 	middle_name varchar NULL,
 	last_name varchar NULL,
 	company_name varchar NULL,
 	company_email varchar NULL,
 	country_code varchar NULL,
 	phone varchar NULL,
 	address varchar NULL,
 	website varchar NULL,
 	country int8 NULL,
 	timezone int8 NULL,
 	forget_password varchar NULL,
 	forget_password_time timestamp NULL DEFAULT CURRENT_TIMESTAMP,
 	forget_password_count int4 NULL DEFAULT 0,
 	invalid_login_attempts int4 NULL DEFAULT 0,
 	is_active bool NULL DEFAULT true,
 	is_deleted bool NULL DEFAULT false,
 	is_blocked bool NULL DEFAULT false,
 	blocked_date timestamp NULL,
 	created_by int8 NULL,
 	updated_by int8 NULL,
 	created_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
 	updated_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
 	CONSTRAINT user_pkey PRIMARY KEY (id),
 	CONSTRAINT user_country_fkey FOREIGN KEY (country) REFERENCES country(id),
 	CONSTRAINT user_timezone_fkey FOREIGN KEY (timezone) REFERENCES timezone(id)
 );


 CREATE TABLE user_role (
 	user_id int8 NULL,
 	role_id int8 NULL,
 	CONSTRAINT user_role_role_id_fkey FOREIGN KEY (role_id) REFERENCES "role"(id),
 	CONSTRAINT user_role_user_id_fkey FOREIGN KEY (user_id) REFERENCES "user"(id)
 );

 CREATE TABLE user_token (
 	id serial4 NOT NULL,
 	user_id int8 NULL,
 	auth_token varchar NULL,
 	last_session_time timestamptz NULL DEFAULT CURRENT_TIMESTAMP,
 	is_active bool NULL DEFAULT true,
 	is_deleted bool NULL DEFAULT false,
 	created_by int8 NULL,
 	updated_by int8 NULL,
 	created_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
 	updated_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
 	CONSTRAINT user_token_pkey PRIMARY KEY (id),
 	CONSTRAINT user_token_user_id_fkey FOREIGN KEY (user_id) REFERENCES "user"(id)
 );

 CREATE TABLE api_key_manager (
 	id serial4 NOT NULL,
 	user_id int8 NULL,
 	access_key_id varchar(256) NOT NULL,
 	secret_access_key varchar(256) NOT NULL,
 	created_by int8 NOT NULL,
 	updated_by int8 NULL,
 	created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
 	updated_at timestamp NULL,
 	is_active bool NULL,
 	is_deleted bool NULL,
 	CONSTRAINT api_key_manager_pkey PRIMARY KEY (id),
 	CONSTRAINT api_key_manager_user_id_fkey FOREIGN KEY (user_id) REFERENCES "user"(id)
 );

 CREATE TABLE site_fhir_mapping (
     id SERIAL4 NOT NULL,
     site_id INT8 NOT NULL UNIQUE,
     organization_id VARCHAR(255) NOT NULL,
     created_by INT8 NULL,
     updated_by INT8 NULL,
     created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
     updated_at TIMESTAMP NULL,
     is_active BOOL NULL,
     is_deleted BOOL NULL,
     CONSTRAINT site_fhir_mapping_pkey PRIMARY KEY (id)
 );

  ALTER TABLE "user" ADD CONSTRAINT country_fkey FOREIGN KEY (country) REFERENCES country(id);
  ALTER TABLE "user" ADD CONSTRAINT timezone_fkey FOREIGN KEY (timezone) REFERENCES timezone(id);

  INSERT INTO "role"
  (id, "name", "level", is_active, is_deleted, created_by, updated_by, created_at, updated_at)
  VALUES(1, 'FHIR_Admin', 1, true, false, 1, 1, '2023-09-21 16:48:20.451', '2023-09-21 16:48:20.451'),
  (2, 'FHIR_User', 2, true, false, 1, 1, '2023-09-21 16:48:20.451', '2023-09-21 16:48:20.451');

  INSERT INTO country ("name", code, abbreviation, is_active, created_by, updated_by)
  VALUES ('United States', 1, 'US', true, 1, 1);

  INSERT INTO timezone (description, abbreviation, "offset", is_active, created_by, updated_by)
  VALUES ('Eastern Time Zone', 'EST', '-5:00', true, 1, 1);

  INSERT INTO "user" (user_name, "password", first_name, middle_name, last_name, company_name, company_email, country_code, phone, address, website, country, timezone, created_by, updated_by)
  VALUES ('fhiradmin@mtdlabs.com', 'fhirAdmin@123', 'FHIR', '', 'ADMIN', 'mdt_labs', 'fhiradmin@mtdlabs.com', 'US', '123-456-7890', '123 Main St', 'www.company.com', 1, 1, 1, 1);

  INSERT INTO user_role (user_id, role_id)
  VALUES(1, 1);

  UPDATE "user" SET password = PGP_SYM_ENCRYPT('fhirAdmin@123', 'Fh1R@dmIN') where user_name='fhiradmin@mtdlabs.com';

  SELECT pg_catalog.setval(pg_get_serial_sequence('user', 'id'), MAX(id)) FROM "user";