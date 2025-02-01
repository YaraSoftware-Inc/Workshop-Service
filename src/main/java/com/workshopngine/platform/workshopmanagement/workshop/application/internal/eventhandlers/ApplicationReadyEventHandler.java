package com.workshopngine.platform.workshopmanagement.workshop.application.internal.eventhandlers;

import com.workshopngine.platform.workshopmanagement.workshop.domain.model.commands.SeedDaysCommand;
import com.workshopngine.platform.workshopmanagement.workshop.domain.services.DayCommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class ApplicationReadyEventHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(ApplicationReadyEventHandler.class);
    private final DayCommandService dayCommandService;

    public ApplicationReadyEventHandler(DayCommandService dayCommandService) {
        this.dayCommandService = dayCommandService;
    }

    @EventListener
    public void on(ApplicationReadyEvent event) {
        var applicationName = event.getApplicationContext().getId();
        LOGGER.info("Starting to verify if days seeding is needed for {} at {}", applicationName, getCurrentTimestamp());
        var seedDaysCommand = new SeedDaysCommand();
        dayCommandService.handle(seedDaysCommand);
        LOGGER.info("Days seeding verification finished for {} at {}", applicationName, getCurrentTimestamp());
    }

    private Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}
