package com.workshopngine.platform.workshopmanagement.workshop.interfaces.rest;

import com.workshopngine.platform.workshopmanagement.workshop.domain.model.queries.GetWorkshopByIdQuery;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.queries.GetWorkshopByOwnerIdQuery;
import com.workshopngine.platform.workshopmanagement.workshop.domain.services.WorkshopCommandService;
import com.workshopngine.platform.workshopmanagement.workshop.domain.services.WorkshopQueryService;
import com.workshopngine.platform.workshopmanagement.workshop.interfaces.rest.dto.CreateWorkshopResource;
import com.workshopngine.platform.workshopmanagement.workshop.interfaces.rest.dto.UpdateWorkshopByFieldsResource;
import com.workshopngine.platform.workshopmanagement.workshop.interfaces.rest.dto.UpdateWorkshopResource;
import com.workshopngine.platform.workshopmanagement.workshop.interfaces.rest.dto.WorkshopResource;
import com.workshopngine.platform.workshopmanagement.workshop.interfaces.rest.transform.CreateWorkshopCommandFromResourceAssembler;
import com.workshopngine.platform.workshopmanagement.workshop.interfaces.rest.transform.UpdateWorkshopByFieldsCommandFromResourceAssembler;
import com.workshopngine.platform.workshopmanagement.workshop.interfaces.rest.transform.UpdateWorkshopCommandFromResourceAssembler;
import com.workshopngine.platform.workshopmanagement.workshop.interfaces.rest.transform.WorkshopResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/workshops", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Workshops", description = "Workshop Management Endpoints")
public class WorkshopController {
    private final WorkshopCommandService workshopCommandService;
    private final WorkshopQueryService workshopQueryService;

    public WorkshopController(WorkshopCommandService workshopCommandService, WorkshopQueryService workshopQueryService) {
        this.workshopCommandService = workshopCommandService;
        this.workshopQueryService = workshopQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a new workshop", description = "Create a new workshop with the given data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Workshop created"),
            @ApiResponse(responseCode = "400", description = "Invalid data")
    })
    public ResponseEntity<WorkshopResource> createWorkshop(@RequestBody CreateWorkshopResource resource){
        var command = CreateWorkshopCommandFromResourceAssembler.toCommandFromResource(resource);
        var workshopId = workshopCommandService.handle(command);
        if (workshopId.isBlank()) return ResponseEntity.badRequest().build();
        return new ResponseEntity<>(getWorkshop(workshopId).getBody(), HttpStatus.CREATED);
    }

    @PutMapping("/{workshopId}")
    @Operation(summary = "Update a workshop", description = "Update a workshop with the given data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Workshop updated"),
            @ApiResponse(responseCode = "400", description = "Invalid data")
    })
    public ResponseEntity<WorkshopResource> updateWorkshop(@PathVariable String workshopId, @RequestBody UpdateWorkshopResource resource){
        var command = UpdateWorkshopCommandFromResourceAssembler.toCommandFromResource(workshopId, resource);
        var id = workshopCommandService.handle(command);
        if (id.isBlank()) return ResponseEntity.badRequest().build();
        return new ResponseEntity<>(getWorkshop(workshopId).getBody(), HttpStatus.OK);
    }

    @PatchMapping("/{workshopId}")
    @Operation(summary = "Update a workshop", description = "Update a workshop with the given data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Workshop updated"),
            @ApiResponse(responseCode = "400", description = "Invalid data")
    })
    public ResponseEntity<WorkshopResource> updateByFieldsWorkshop(@PathVariable String workshopId, @RequestBody UpdateWorkshopByFieldsResource resource){
        var command = UpdateWorkshopByFieldsCommandFromResourceAssembler.toCommandFromResource(workshopId, resource);
        var id = workshopCommandService.handle(command);
        if (id.isBlank()) return ResponseEntity.badRequest().build();
        return new ResponseEntity<>(getWorkshop(workshopId).getBody(), HttpStatus.OK);
    }

    @GetMapping("/{workshopId}")
    @Operation(summary = "Get a workshop", description = "Get a workshop by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Workshop found"),
            @ApiResponse(responseCode = "404", description = "Workshop not found")
    })
    public ResponseEntity<WorkshopResource> getWorkshop(@PathVariable String workshopId){
        var query = new GetWorkshopByIdQuery(workshopId);
        var workshop = workshopQueryService.handle(query);
        if (workshop.isEmpty()) return ResponseEntity.notFound().build();
        var workshopResource = WorkshopResourceFromEntityAssembler.toResourceFromEntity(workshop.get());
        return new ResponseEntity<>(workshopResource, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get a workshop by owner ID", description = "Get a workshop by its owner ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Workshop found"),
            @ApiResponse(responseCode = "404", description = "Workshop not found")
    })
    public ResponseEntity<WorkshopResource> getWorkshopByOwnerID(@RequestParam String ownerId){
        var query = new GetWorkshopByOwnerIdQuery(ownerId);
        var workshop = workshopQueryService.handle(query);
        if (workshop.isEmpty()) return ResponseEntity.notFound().build();
        var workshopResource = WorkshopResourceFromEntityAssembler.toResourceFromEntity(workshop.get());
        return new ResponseEntity<>(workshopResource, HttpStatus.OK);
    }
}
