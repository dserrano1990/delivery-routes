package com.david.projects.springboot.delivery_routes.controllers;

import com.david.projects.springboot.delivery_routes.services.OrderService;
import com.david.projects.springboot.delivery_routes.services.PdfGeneratorService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final OrderService orderService;
    private final PdfGeneratorService pdfGeneratorService;

    public ReportController(OrderService orderService, PdfGeneratorService pdfGeneratorService) {
        this.orderService = orderService;
        this.pdfGeneratorService = pdfGeneratorService;
    }

    @GetMapping("/orders/{orderId}/pdf")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<byte[]> generateOrderPdf(@PathVariable Long orderId) {
        try {
            // Buscar la orden
            var orderOpt = orderService.findById(orderId);
            if (orderOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            // Generar PDF
            byte[] pdfBytes = pdfGeneratorService.generateOrderPdf(orderOpt.get());
            
            // Configurar headers para la descarga
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "orden_" + orderId + ".pdf");
            headers.setContentLength(pdfBytes.length);
            
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
