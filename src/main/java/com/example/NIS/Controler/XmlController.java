package com.example.NIS.Controler;

import com.example.NIS.Service.XmlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/xml")
public class XmlController {

    private static final Logger logger =
            LoggerFactory.getLogger(XmlController.class);

    private final XmlService xmlService;

    public XmlController(XmlService xmlService) {
        this.xmlService = xmlService;
    }

    // ===================== UPLOAD XML =====================
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> uploadXml(
            @RequestParam("file") MultipartFile file,
            @RequestParam("type") Integer type
    ) {

        // ===== BASIC VALIDATION =====
        if (file == null || file.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("XML file is required"));
        }

        if (type == null || (type != 2 && type != 3 && type != 4)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Allowed types are 2, 3, or 4"));
        }

        try {
            xmlService.extract(file, type);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(ApiResponse.success(
                            "XML data processed and saved successfully"
                    ));

        } catch (IllegalArgumentException ex) {
            logger.warn("Validation error: {}", ex.getMessage());

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(ex.getMessage()));

        } catch (Exception ex) {
            logger.error("Unexpected error while processing XML", ex);

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(
                            "Internal server error while processing XML"
                    ));
        }
    }
}

/* ===================== API RESPONSE ===================== */
record ApiResponse(
        boolean success,
        String message
) {
    public static ApiResponse success(String message) {
        return new ApiResponse(true, message);
    }

    public static ApiResponse error(String message) {
        return new ApiResponse(false, message);
    }
}
