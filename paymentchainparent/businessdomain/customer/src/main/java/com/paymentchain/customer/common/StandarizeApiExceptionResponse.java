package com.paymentchain.customer.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Standardized API exception response following RFC 7807
 * This class represents a Problem Details object as defined in RFC 7807
 * for HTTP APIs
 */
@NoArgsConstructor
@Data
@Schema(description = "Standardized API exception response following RFC 7807")
public class StandarizeApiExceptionResponse {
  	
    @Schema(description = "A URI reference that identifies the problem type", name="type",
    		example = "https://example.com/problems/constraint-violation", requiredMode =  Schema.RequiredMode.REQUIRED)
    private String type;

    @Schema(description = "A short, human-readable summary of the problem type", name="title",
    		example = "Constraint Violation",  requiredMode =  Schema.RequiredMode.REQUIRED)
    private String title;
 
    @Schema(description = "The HTTP status code", example = "400",  name="code",
    		requiredMode =  Schema.RequiredMode.NOT_REQUIRED)
    private String code;

    @Schema(description = "A human-readable explanation specific to this occurrence of the problem", name="detail",
    		example = "The request parameter 'id' must be positive", requiredMode =  Schema.RequiredMode.REQUIRED)
    private String detail;
    
    @Schema(description = "A URI reference that identifies the specific occurrence of the problem", name="instance",
    		example = "/customers/validation/123",  requiredMode =  Schema.RequiredMode.REQUIRED)
    private String instance;

    public StandarizeApiExceptionResponse(String type, String title, String code, String detail) {
    	super();
        this.type = type;
        this.title = title;
        this.code = code;
        this.detail = detail;
    }
}