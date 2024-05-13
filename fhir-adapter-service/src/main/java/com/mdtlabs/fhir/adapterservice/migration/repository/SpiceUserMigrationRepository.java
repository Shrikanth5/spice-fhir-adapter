package com.mdtlabs.fhir.adapterservice.migration.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mdtlabs.fhir.adapterservice.migration.model.District;
import com.mdtlabs.fhir.adapterservice.migration.model.Site;
import com.mdtlabs.fhir.adapterservice.migration.model.State;
import com.mdtlabs.fhir.adapterservice.migration.utils.SqlQueries;

/**
 * The type Spice user migration repository.
 * Author: Akash Gopinath
 * Created on: April 10, 2024
 */
@Repository
public class SpiceUserMigrationRepository {

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    public SpiceUserMigrationRepository(@Value("${app.sourceUrl}") String url,
                                       @Value("${app.userName}") String userName,
                                       @Value("${app.password}") String password,
                                       @Value("${app.driverClassName}") String driverClassName) {
        this.dataSource = DataSourceBuilder.create()
                .url(url)
                .username(userName)
                .password(password)
                .driverClassName(driverClassName)
                .build();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Find all users list.
     *
     * @return the list
     */
    public List<Map<String, Object>> findAllUsers() {
        return jdbcTemplate.queryForList(SqlQueries.FIND_ALL_SPICE_USERS);
    }

    /**
     * Find records created after list.
     *
     * @param timestamp the timestamp
     * @return the list
     */
    public List<Map<String, Object>> findRecordsCreatedAfter(Timestamp timestamp) {
        return jdbcTemplate.queryForList(SqlQueries.FIND_RECORDS_CREATED_AFTER, timestamp);
    }

    /**
     * Gets sub county id and name.
     *
     * @return the sub county id and name
     */
    public List<State> getSubCountyIdAndName() {
        return jdbcTemplate.query(SqlQueries.SELECT_QUERY_FOR_SUB_COUNTY_ID_AND_NAME,
                new BeanPropertyRowMapper<>(State.class));
    }

    /**
     * Gets district id and name.
     *
     * @return the district id and name
     */
    public List<District> getDistrictIdAndName() {
        return jdbcTemplate.query(SqlQueries.SELECT_QUERY_FOR_COUNTY_ID_AND_NAME,
                new BeanPropertyRowMapper<>(District.class));
    }

    /**
     * Gets site dt os.
     *
     * @return the site dt os
     */
    public List<Site> getSiteDTOs() {
        return jdbcTemplate.query(SqlQueries.SELECT_SITE_SPICE_QUERY, new BeanPropertyRowMapper<>(Site.class));
    }
}
