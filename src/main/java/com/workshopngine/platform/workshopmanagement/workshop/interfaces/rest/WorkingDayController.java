package com.workshopngine.platform.workshopmanagement.workshop.interfaces.rest;

import com.workshopngine.platform.workshopmanagement.workshop.domain.model.queries.GetAllWorkingDaysByWorkshopIdQuery;
import com.workshopngine.platform.workshopmanagement.workshop.domain.services.WorkshopCommandService;
import com.workshopngine.platform.workshopmanagement.workshop.domain.services.WorkshopQueryService;
import com.workshopngine.platform.workshopmanagement.workshop.interfaces.rest.dto.CreateWorkingDayResource;
import com.workshopngine.platform.workshopmanagement.workshop.interfaces.rest.dto.WorkingDayResource;
import com.workshopngine.platform.workshopmanagement.workshop.interfaces.rest.transform.CreateWorkingDayCommandFromResourceAssembler;
import com.workshopngine.platform.workshopmanagement.workshop.interfaces.rest.transform.WorkingDayResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/workshops/{workshopId}/working-days", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Workshops", description = "Workshop Management Endpoints")
public class WorkingDayController {
    private final WorkshopCommandService workshopCommandService;
    private final WorkshopQueryService workshopQueryService;

    @PostMapping
    @Operation(summary = "Create a new working day", description = "Create a new working day with the given data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Working day created"),
            @ApiResponse(responseCode = "400", description = "Invalid data")
    })
    public ResponseEntity<WorkingDayResource> createWorkingDay(@PathVariable String workshopId, @RequestBody CreateWorkingDayResource resource){
        var command = CreateWorkingDayCommandFromResourceAssembler.toCommandFromResource(workshopId, resource);
        var workingDay = workshopCommandService.handle(command);
        if (workingDay.isEmpty()) return ResponseEntity.badRequest().build();
        var workingDayResource = WorkingDayResourceFromEntityAssembler.toResourceFromEntity(workingDay.get());
        return new ResponseEntity<>(workingDayResource, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all working days", description = "Get all working days for the given workshop")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Working days found"),
            @ApiResponse(responseCode = "404", description = "No working days found")
    })
    public ResponseEntity<Collection<WorkingDayResource>> getWorkingDays(@PathVariable String workshopId){
        var query = new GetAllWorkingDaysByWorkshopIdQuery(workshopId);
        var workingDays = workshopQueryService.handle(query);
        var workingDayResources = workingDays.stream()
                .map(WorkingDayResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return new ResponseEntity<>(workingDayResources, HttpStatus.OK);
    }
}
