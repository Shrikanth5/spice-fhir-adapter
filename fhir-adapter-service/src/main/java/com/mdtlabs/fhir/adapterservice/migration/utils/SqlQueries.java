package com.mdtlabs.fhir.adapterservice.migration.utils;

/**
 * The type Sql queries.
 */
public class SqlQueries {

    /**
     * The constant GET_LAST_CREATED_AT.
     */
    public static final String GET_LAST_CREATED_AT = "SELECT created_at FROM \"user_spice_fhir_mapping\" ORDER BY id DESC LIMIT 1";

    /**
     * The constant FIND_ALL_FHIR_USERS.
     */
    public static final String FIND_ALL_FHIR_USERS = "SELECT * FROM \"user_migration_fhir\"";

    /**
     * The constant INSERT_SPICE_USERS.
     */
    public static final String INSERT_SPICE_USERS = "INSERT INTO user_spice_fhir_mapping (spice_user_id, fhir_practitioner_id) VALUES (?, ?)";

    /**
     * The constant FIND_ALL_SPICE_USERS.
     */
    public static final String FIND_ALL_SPICE_USERS = "SELECT * FROM \"user\" ORDER BY id ASC";

    /**
     * The constant FIND_RECORDS_CREATED_AFTER.
     */
    public static final String FIND_RECORDS_CREATED_AFTER = "SELECT * FROM \"user\" WHERE created_at > ? ORDER BY id ASC";
    /**
     * The constant SELECT_QUERY_FOR_SUB_COUNTY_ID_AND_NAME.
     */
    public static final String SELECT_QUERY_FOR_SUB_COUNTY_ID_AND_NAME = "SELECT DISTINCT site.sub_county_id AS state_id, sub_county.name AS state_name FROM public.site site JOIN public.sub_county sub_county ON site.sub_county_id = sub_county.id ORDER BY site.sub_county_id";
    /**
     * The constant SELECT_QUERY_FOR_COUNTY_ID_AND_NAME.
     */
    public static final String SELECT_QUERY_FOR_COUNTY_ID_AND_NAME = "select distinct site.county_id as district_id, county.\"name\" as district_name from public.site site JOIN public.county county ON county.id = site.county_id order by site.county_id";
    /**
     * The constant SELECT_SITE_SPICE_QUERY.
     */
    public static final String SELECT_SITE_SPICE_QUERY = "select state.*, country.name as country_name, sub_county.name as sub_county_name from site as state inner join country as country on state.country_id = country.id inner join county as county on county.id = state.county_id inner join sub_county as sub_county on sub_county.id = state.sub_county_id order by state.id asc";
    /**
     * The constant INSERT_QUERY_FOR_SITE_FHIR_MAPPING.
     */
    public static final String INSERT_QUERY_FOR_SITE_FHIR_MAPPING = "INSERT INTO site_fhir_mapping (site_id, organization_id) VALUES (?, ?)";
}