package com.mdtlabs.fhir.adapterservice.converter;

import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.constants.ErrorConstants;
import com.mdtlabs.fhir.commonservice.common.constants.FhirConstants;
import com.mdtlabs.fhir.commonservice.common.exception.DataNotFoundException;
import com.mdtlabs.fhir.commonservice.common.exception.FhirValidation;
import com.mdtlabs.fhir.commonservice.common.logger.Logger;
import com.mdtlabs.fhir.commonservice.common.model.dto.BpLogDTO;
import org.hl7.fhir.r4.model.BooleanType;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Quantity;
import org.hl7.fhir.r4.model.Reference;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * <p>
 * Converts BPLogDTO to FHIR Observation entity for Blood Pressure observations.
 * </p>
 * <p>
 * Author: Shrikanth
 * <p>
 * Created on: February 27, 2024
 */
@Component
public class BloodPressureConverter {
    @Value("${app.country-Id}")
    private Long countryId;

    /**
     * Converts a BPLogDTO to a FHIR Observation entity for Blood Pressure observations.
     *
     * @param createBPLog             The BPLogDTO to be converted.
     * @param fhirPatientId           The FHIR patient ID.
     * @param requestType             The SPICE request type.
     * @param updatedByPractitionerId The ID of the practitioner who updated the observation.
     * @return The converted FHIR Observation entity.
     */
    public Observation convertBloodPressureObservationToFhirBundleEntity(BpLogDTO createBPLog, String fhirPatientId,
                                                                         String requestType,
                                                                         Long updatedByPractitionerId,
                                                                         Boolean isPregnant, Long country) {
        try {
            Observation bpLogObservation = new Observation();

            bpLogObservation.setStatus(Observation.ObservationStatus.FINAL);


            Coding coding = new Coding()
                    .setSystem(FhirConstants.LOINC_SYSTEM_URL)
                    .setCode(FhirConstants.BLOOD_PRESSURE_CODE)
                    .setDisplay(FhirConstants.BLOOD_PRESSURE_DISPLAY);


            CodeableConcept codeableConcept = new CodeableConcept().addCoding(coding)
                    .setText(FhirConstants.BLOOD_PRESSURE_TEXT);

            bpLogObservation.setCode(codeableConcept);

            Observation.ObservationComponentComponent systolicComponent = new Observation.ObservationComponentComponent();
            systolicComponent.getCode().addCoding()
                    .setSystem(FhirConstants.LOINC_SYSTEM_URL)
                    .setCode(FhirConstants.SYSTOLIC_BLOOD_PRESSURE_CODE)
                    .setDisplay(FhirConstants.SYSTOLIC_BLOOD_PRESSURE_DISPLAY);
            if (Objects.equals(countryId, country)) {

                Coding nhddSystolicCoding = new Coding();
                nhddSystolicCoding.setSystem(FhirConstants.NHDD_SYSTEM_URL);
                nhddSystolicCoding.setCode(FhirConstants.NHDD_SYSTOLIC_BLOOD_PRESSURE_CODE);
                nhddSystolicCoding.setDisplay(FhirConstants.SYSTOLIC_BLOOD_PRESSURE_DISPLAY);
                systolicComponent.getCode().addCoding(nhddSystolicCoding);
            }

            Quantity systolicValue = new Quantity();
            systolicValue.setValue(createBPLog.getAvgSystolic());
            systolicValue.setUnit(FhirConstants.MM_HG_CODE);
            systolicValue.setSystem(FhirConstants.UOM_SYSTEM_URL);
            systolicValue.setCode(FhirConstants.MM_HG_CODE);
            systolicComponent.setValue(systolicValue);

            Observation.ObservationComponentComponent diastolicComponent = new Observation.ObservationComponentComponent();
            diastolicComponent.getCode().addCoding()
                    .setSystem(FhirConstants.LOINC_SYSTEM_URL)
                    .setCode(FhirConstants.DIASTOLIC_BLOOD_PRESSURE_CODE)
                    .setDisplay(FhirConstants.DIASTOLIC_BLOOD_PRESSURE_DISPLAY);
            if (Objects.equals(countryId, country)) {

                Coding nhddDiastolicCoding = new Coding();
                nhddDiastolicCoding.setSystem(FhirConstants.NHDD_SYSTEM_URL);
                nhddDiastolicCoding.setCode(FhirConstants.NHDD_DIASTOLIC_BLOOD_PRESSURE_CODE);
                nhddDiastolicCoding.setDisplay(FhirConstants.DIASTOLIC_BLOOD_PRESSURE_DISPLAY);
                diastolicComponent.getCode().addCoding(nhddDiastolicCoding);
            }

            Quantity diastolicValue = new Quantity();
            diastolicValue.setValue(createBPLog.getAvgDiastolic());
            diastolicValue.setUnit(FhirConstants.MM_HG_CODE);
            diastolicValue.setSystem(FhirConstants.UOM_SYSTEM_URL);
            diastolicValue.setCode(FhirConstants.MM_HG_CODE);
            diastolicComponent.setValue(diastolicValue);

            Observation.ObservationComponentComponent heightComponent = new Observation.ObservationComponentComponent();
            heightComponent.getCode().addCoding()
                    .setSystem(FhirConstants.LOINC_SYSTEM_URL)
                    .setCode(FhirConstants.HEIGHT_CODE)
                    .setDisplay(FhirConstants.HEIGHT_DISPLAY);
            if (Objects.equals(countryId, country)) {

                Coding nhddHeightCoding = new Coding();
                nhddHeightCoding.setSystem(FhirConstants.NHDD_SYSTEM_URL);
                nhddHeightCoding.setCode(FhirConstants.NHDD_HEIGHT_CODE);
                nhddHeightCoding.setDisplay(FhirConstants.HEIGHT_DISPLAY);
                heightComponent.getCode().addCoding(nhddHeightCoding);
            }

            Quantity heightValue = new Quantity();
            heightValue.setValue(createBPLog.getHeight());
            heightValue.setUnit(FhirConstants.CM_CODE);
            heightValue.setSystem(FhirConstants.UOM_SYSTEM_URL);
            heightValue.setCode(FhirConstants.CM_CODE);
            heightComponent.setValue(heightValue);

            Observation.ObservationComponentComponent weightComponent = new Observation.ObservationComponentComponent();
            weightComponent.getCode().addCoding()
                    .setSystem(FhirConstants.LOINC_SYSTEM_URL)
                    .setCode(FhirConstants.WEIGHT_CODE)
                    .setDisplay(FhirConstants.WEIGHT_DISPLAY);

            if (Objects.equals(countryId, country)) {
                Coding nhddWeightCoding = new Coding();
                nhddWeightCoding.setSystem(FhirConstants.NHDD_SYSTEM_URL);
                nhddWeightCoding.setCode(FhirConstants.NHDD_WEIGHT_CODE);
                nhddWeightCoding.setDisplay(FhirConstants.WEIGHT_DISPLAY);
                weightComponent.getCode().addCoding(nhddWeightCoding);
            }

            Quantity weightValue = new Quantity();
            weightValue.setValue(createBPLog.getWeight());
            weightValue.setUnit(FhirConstants.KG_CODE);
            weightValue.setSystem(FhirConstants.UOM_SYSTEM_URL);
            weightValue.setCode(FhirConstants.KG_CODE);
            weightComponent.setValue(weightValue);

            Observation.ObservationComponentComponent bmiComponent = new Observation.ObservationComponentComponent();
            bmiComponent.getCode().addCoding()
                    .setSystem(FhirConstants.LOINC_SYSTEM_URL)
                    .setCode(FhirConstants.BMI_CODE)
                    .setDisplay(FhirConstants.BMI_DISPLAY);

            if (Objects.equals(countryId, country)) {
                Coding nhddBmiCoding = new Coding();
                nhddBmiCoding.setSystem(FhirConstants.NHDD_SYSTEM_URL);
                nhddBmiCoding.setCode(FhirConstants.NHDD_BMI_CODE);
                nhddBmiCoding.setDisplay(FhirConstants.BMI_DISPLAY);
                bmiComponent.getCode().addCoding(nhddBmiCoding);
            }

            Quantity bmiValue = new Quantity();
            bmiValue.setValue(createBPLog.getBmi());
            bmiValue.setUnit(FhirConstants.KG_PER_M2_CODE);
            bmiValue.setSystem(FhirConstants.UOM_SYSTEM_URL);
            bmiValue.setCode(FhirConstants.KG_PER_M2_CODE);
            bmiComponent.setValue(bmiValue);

            Observation.ObservationComponentComponent pregnantComponent = getObservationComponentComponent(requestType, isPregnant, country);

            Observation.ObservationComponentComponent regularSmokerComponent =
                    new Observation.ObservationComponentComponent();
            if (null != createBPLog.getIsRegularSmoker()) {
                regularSmokerComponent.getCode().addCoding()
                        .setSystem(FhirConstants.LOINC_SYSTEM_URL)
                        .setCode(FhirConstants.SMOKING_STATUS)
                        .setDisplay(FhirConstants.REGULAR_SMOKER_DISPLAY);

                if (Objects.equals(countryId, country)) {
                    Coding nhddRegularSmokerComponentCoding = new Coding();
                    nhddRegularSmokerComponentCoding.setSystem(FhirConstants.NHDD_SYSTEM_URL);
                    nhddRegularSmokerComponentCoding.setCode(FhirConstants.NHDD_SMOKER_CODE);
                    nhddRegularSmokerComponentCoding.setDisplay(FhirConstants.NHDD_REGULAR_SMOKER_DISPLAY);
                    regularSmokerComponent.getCode().addCoding(nhddRegularSmokerComponentCoding);
                }

                regularSmokerComponent.setValue(new BooleanType(createBPLog.getIsRegularSmoker()));
            }


            bpLogObservation.addComponent(systolicComponent);
            bpLogObservation.addComponent(diastolicComponent);
            bpLogObservation.addComponent(heightComponent);
            bpLogObservation.addComponent(weightComponent);
            bpLogObservation.addComponent(bmiComponent);
            bpLogObservation.addComponent(pregnantComponent);
            bpLogObservation.addComponent(regularSmokerComponent);


            if (null == fhirPatientId) {
                bpLogObservation.setSubject(new Reference(FhirConstants.PATIENT +
                        Constants.FORWARD_SLASH + FhirConstants.PATIENT_FULL_URL));
            } else {
                bpLogObservation.setSubject(new Reference(FhirConstants.PATIENT +
                        Constants.FORWARD_SLASH + fhirPatientId));
            }
            if (null != updatedByPractitionerId) {
                bpLogObservation.addPerformer().setReference(FhirConstants.PRACTITIONER +
                        Constants.FORWARD_SLASH + updatedByPractitionerId);
            } else {
                Logger.logError(String.valueOf(1005));
                throw new DataNotFoundException(1005);
            }
            return bpLogObservation;
        } catch (DataNotFoundException dataNotFoundException) {
            Logger.logError(String.valueOf(1005), dataNotFoundException);
            throw dataNotFoundException;
        } catch (Exception error) {
            Logger.logError(ErrorConstants.ERROR_LOG, error.getMessage());
            throw new FhirValidation(1007, error.getMessage());
        }
    }

    /**
     * Retrieves the observation component for pregnancy status if applicable.
     *
     * @param requestType The SPICE request type.
     * @param isPregnant  Indicates whether the patient is pregnant.
     * @param country     The country ID.
     * @return The observation component for pregnancy status.
     */
    @NotNull
    private Observation.ObservationComponentComponent getObservationComponentComponent(String requestType, Boolean isPregnant, Long country) {
        Observation.ObservationComponentComponent pregnantComponent =
                new Observation.ObservationComponentComponent();
        if (null != isPregnant && !requestType.equals(Constants.ASSESSMENT_DATA)) {
            pregnantComponent.getCode().addCoding()
                    .setSystem(FhirConstants.LOINC_SYSTEM_URL)
                    .setCode(FhirConstants.PREGNANT_STATUS)
                    .setDisplay(FhirConstants.PREGNANT_STATUS_DISPLAY);

            if (Objects.equals(countryId, country)) {
                Coding nhddPregnantStatusCoding = new Coding();
                nhddPregnantStatusCoding.setSystem(FhirConstants.NHDD_SYSTEM_URL);
                nhddPregnantStatusCoding.setCode(FhirConstants.NHDD_PREGNANT_STATUS_CODE);
                nhddPregnantStatusCoding.setDisplay(FhirConstants.NHDD_PREGNANT_STATUS_DISPLAY);
                pregnantComponent.getCode().addCoding(nhddPregnantStatusCoding);
            }

            pregnantComponent.setValue(new BooleanType(isPregnant));
        }
        return pregnantComponent;
    }
}
