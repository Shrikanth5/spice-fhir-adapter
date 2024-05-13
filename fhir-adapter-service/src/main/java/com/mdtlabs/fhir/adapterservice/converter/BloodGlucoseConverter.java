package com.mdtlabs.fhir.adapterservice.converter;

import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.constants.FhirConstants;
import com.mdtlabs.fhir.commonservice.common.exception.DataNotFoundException;
import com.mdtlabs.fhir.commonservice.common.logger.Logger;
import com.mdtlabs.fhir.commonservice.common.model.dto.GlucoseLogDTO;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Quantity;
import org.hl7.fhir.r4.model.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * <p>
 * Converts GlucoseLogDTO to FHIR Observation entity for Blood Glucose observations.
 * </p>
 * <p>
 * Author: Shrikanth
 * <p>
 * Created on: February 27, 2024
 */
@Component
public class BloodGlucoseConverter {
    @Value("${app.country-Id}")
    private Long countryId;

    /**
     * Converts GlucoseLogDTO to FHIR Observation entity.
     *
     * @param glucoseLog              The GlucoseLogDTO to convert.
     * @param fhirPatientId           The FHIR Patient ID.
     * @param updatedByPractitionerId The ID of the practitioner who updated the observation.
     * @return The FHIR Observation entity representing the Blood Glucose observation.
     */
    public Observation convertBloodGlucoseObservationToFhirBundleEntity(GlucoseLogDTO glucoseLog,
                                                                        String fhirPatientId,
                                                                        Long updatedByPractitionerId,
                                                                        Long country) {
        Observation glucoseLogObservation = new Observation();
        glucoseLogObservation.setStatus(Observation.ObservationStatus.FINAL);

        
        String loincCode = null;
        if (Constants.FBS.equalsIgnoreCase(glucoseLog.getGlucoseType())) {
            loincCode = FhirConstants.FBS_LOINC_CODE;
        }
        if (Constants.RBS.equalsIgnoreCase(glucoseLog.getGlucoseType())) {
            loincCode = FhirConstants.RBS_LOINC_CODE;
        }

        
        glucoseLogObservation.getCode().addCoding()
                .setSystem(FhirConstants.LOINC_SYSTEM_URL)
                .setCode(loincCode)
                .setDisplay(glucoseLog.getGlucoseType());
        Quantity glucoseValue = new Quantity();
        glucoseValue.setValue(glucoseLog.getGlucoseValue());
        glucoseValue.setUnit(glucoseLog.getGlucoseUnit());
        glucoseLogObservation.setValue(glucoseValue);

        //		 Set effectiveDateTime
        DateTimeType effectiveDateTime = new DateTimeType(glucoseLog.getGlucoseDateTime());
        if (null == effectiveDateTime.getSecond()) {
            effectiveDateTime.setSecond(0);
        }
        glucoseLogObservation.setEffective(effectiveDateTime);

        
        glucoseLogObservation.getCategoryFirstRep().addCoding()
                .setSystem(FhirConstants.LABORATORY_SYSTEM_URL)
                .setCode(FhirConstants.LABORATORY_CODE);
        glucoseLogObservation.getCode().setText(FhirConstants.BLOOD_GLUCOSE_TEXT);

        
        if (glucoseLog.getHba1c() != null) {
            Observation.ObservationComponentComponent hba1cObservation = new Observation.ObservationComponentComponent();
            hba1cObservation.getCode().addCoding()
                    .setSystem(FhirConstants.LOINC_SYSTEM_URL)
                    .setCode(FhirConstants.HBA1C_LOINC_CODE)
                    .setDisplay(FhirConstants.HBA1C_DISPLAY);
            
            if (Objects.equals(countryId, country)) {
                Coding nhddHBA1Coding = new Coding();
                nhddHBA1Coding.setSystem(FhirConstants.NHDD_SYSTEM_URL);
                nhddHBA1Coding.setCode(FhirConstants.NHDD_HBA1C_CODE);
                nhddHBA1Coding.setDisplay(FhirConstants.HBA1C_DISPLAY);
                hba1cObservation.getCode().addCoding(nhddHBA1Coding);
            }
            Quantity hba1cValue = new Quantity();
            hba1cValue.setValue(glucoseLog.getHba1c());
            hba1cValue.setUnit(glucoseLog.getHba1cUnit());
            hba1cObservation.setValue(hba1cValue);
            glucoseLogObservation.addComponent(hba1cObservation);
        }

        
        Observation.ObservationComponentComponent lastMealTimeObservation = new Observation.ObservationComponentComponent();
        lastMealTimeObservation.getCode().addCoding()
                .setSystem(FhirConstants.LOINC_SYSTEM_URL)
                .setCode(FhirConstants.LAST_MEAL_TIME)
                .setDisplay(FhirConstants.LAST_MEAL_TIME_DISPLAY);
        DateTimeType lastMealTime = new DateTimeType(glucoseLog.getLastMealTime());
        lastMealTimeObservation.setValue(lastMealTime);
        glucoseLogObservation.addComponent(lastMealTimeObservation);

        
        if (Objects.equals(countryId, country)) {
            setNHDDCodes(glucoseLog, glucoseLogObservation);
        }
        
        if (null == fhirPatientId) {
            glucoseLogObservation.setSubject(new Reference(FhirConstants.PATIENT +
                    Constants.FORWARD_SLASH + FhirConstants.PATIENT_FULL_URL));
        } else {
            glucoseLogObservation.setSubject(new Reference(FhirConstants.PATIENT +
                    Constants.FORWARD_SLASH + fhirPatientId));
        }
        if (null != updatedByPractitionerId) {
            glucoseLogObservation.addPerformer().setReference(FhirConstants.PRACTITIONER +
                    Constants.FORWARD_SLASH + updatedByPractitionerId);
        } else {
            Logger.logError(String.valueOf(1005));
            throw new DataNotFoundException(1005);
        }
        return glucoseLogObservation;
    }

    /**
     * Function to set NHDD codes.
     *
     * @param glucoseLog            The GlucoseLogDTO containing glucose information.
     * @param glucoseLogObservation The FHIR Observation entity for glucose.
     */
    private void setNHDDCodes(GlucoseLogDTO glucoseLog, Observation glucoseLogObservation) {
        switch (glucoseLog.getGlucoseType()) {
            case Constants.FBS:
                setNHDDCode(glucoseLogObservation, glucoseLog.getGlucoseType(), FhirConstants.NHDD_FBS_CODE);
                break;
            case Constants.RBS:
                setNHDDCode(glucoseLogObservation, glucoseLog.getGlucoseType(), FhirConstants.NHDD_RBS_CODE);
                break;
            default:
                break;
        }
    }

    /**
     * Function to set NHDD code.
     *
     * @param observation The FHIR Observation entity.
     * @param display     The display text for the NHDD code.
     * @param code        The NHDD code.
     */
    private void setNHDDCode(Observation observation, String display, String code) {
        Coding nhddCoding = new Coding();
        nhddCoding.setSystem(FhirConstants.NHDD_SYSTEM_URL);
        nhddCoding.setCode(code);
        nhddCoding.setDisplay(display);
        observation.getCode().addCoding(nhddCoding);
    }

}