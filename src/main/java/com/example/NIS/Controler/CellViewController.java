package com.example.NIS.Controler;

import com.example.NIS.Service.CellViewService;
import com.example.NIS.dtoforview.CellViewDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cells")
@CrossOrigin(origins = "http://localhost:4200")
public class CellViewController {

    private static final Logger logger =
            LoggerFactory.getLogger(CellViewController.class);

    private final CellViewService cellViewService;

    public CellViewController(CellViewService cellViewService) {
        this.cellViewService = cellViewService;
    }

    // ===================== GET CELLS (VIEW) =====================
    @GetMapping
    public ResponseEntity<Page<CellViewDto>> getCells(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {

            if (page < 0 || size <= 0) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .build();
            }

            Page<CellViewDto> result =
                    cellViewService.getCells(page, size);

            return ResponseEntity.ok(result);

        } catch (Exception ex) {
            logger.error("Error while fetching cells view", ex);

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
}
