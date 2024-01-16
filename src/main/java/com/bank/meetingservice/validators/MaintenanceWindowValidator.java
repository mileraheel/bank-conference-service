package com.bank.meetingservice.validators;

import com.bank.meetingservice.dto.common.ServiceTransferDto;
import com.bank.meetingservice.dto.common.ErrorDetail;
import com.bank.meetingservice.enums.ResponseErrorCode;
import com.bank.meetingservice.repository.MaintenanceWindowRepository;
import lombok.CustomLog;
import org.springframework.stereotype.Component;

@Component
@CustomLog
public class MaintenanceWindowValidator {

    private final MaintenanceWindowRepository maintenanceWindowRepository;

    public MaintenanceWindowValidator(MaintenanceWindowRepository maintenanceWindowRepository) {
        this.maintenanceWindowRepository = maintenanceWindowRepository;
    }

    public void validateMaintenanceWindow(ServiceTransferDto serviceTransferDto) {
        if (!maintenanceWindowRepository.checkIfMaintenanceWindowValidInGivenTime(serviceTransferDto.getMeetingStartTime(),
                serviceTransferDto.getMeetingEndTIme()).isEmpty()) {
            serviceTransferDto.getErrorDetails().add(new ErrorDetail(ResponseErrorCode.MAINTENANCE_WINDOW_STARTING_IN_MEETING));
        }
    }
}
