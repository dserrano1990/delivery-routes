package com.david.projects.springboot.delivery_routes.services;

import com.david.projects.springboot.delivery_routes.dto.response.OrderResponse;
import com.david.projects.springboot.delivery_routes.services.MapGeneratorService.MapPoint;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Service
public class PdfGeneratorService {

    @Autowired
    private MapGeneratorService mapGeneratorService;

    public byte[] generateOrderPdf(OrderResponse order) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        
        PdfWriter.getInstance(document, baos);
        document.open();
        
        // Título
        Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
        Font subtitleFont = new Font(Font.HELVETICA, 14, Font.BOLD);
        Font normalFont = new Font(Font.HELVETICA, 10, Font.NORMAL);
        
        Paragraph title = new Paragraph("Delivery Routes - Reporte de Orden", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        
        document.add(new Paragraph(" "));
        
        // Información de la orden
        Paragraph orderInfo = new Paragraph("Información de la Orden", subtitleFont);
        orderInfo.setSpacingAfter(10);
        document.add(orderInfo);
        
        document.add(new Paragraph("ID de Orden: " + order.getId(), normalFont));
        document.add(new Paragraph("Estado: " + order.getStatus().getSpanish(), normalFont));
        document.add(new Paragraph("Fecha de Creación: " + 
            order.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")), normalFont));
        document.add(new Paragraph(" "));
        
        // Vehículo
        if (order.getVehicle() != null) {
            Paragraph vehicleInfo = new Paragraph("Vehículo Asignado", subtitleFont);
            vehicleInfo.setSpacingAfter(10);
            document.add(vehicleInfo);
            
            document.add(new Paragraph("Patente: " + order.getVehicle().getPlate(), normalFont));
            document.add(new Paragraph("Modelo: " + order.getVehicle().getModel(), normalFont));
            document.add(new Paragraph("Estado: " + order.getVehicle().getStatus().getSpanish(), normalFont));
            document.add(new Paragraph(" "));
        }
        
        // Puntos de entrega
        Paragraph pointsTitle = new Paragraph("Puntos de Entrega", subtitleFont);
        pointsTitle.setSpacingAfter(10);
        document.add(pointsTitle);
        
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{1, 4, 2, 2});
        
        // Headers
        addTableCell(table, "#", true);
        addTableCell(table, "Dirección", true);
        addTableCell(table, "Latitud", true);
        addTableCell(table, "Longitud", true);
        
        // Data
        int i = 1;
        for (var point : order.getDeliveryPoints()) {
            addTableCell(table, String.valueOf(i++), false);
            addTableCell(table, point.getAddress(), false);
            addTableCell(table, String.valueOf(point.getLatitude()), false);
            addTableCell(table, String.valueOf(point.getLongitude()), false);
        }
        
        document.add(table);
        document.add(new Paragraph(" "));
        
        // Mapa
        if (order.getDeliveryPoints() != null && !order.getDeliveryPoints().isEmpty()) {
            Paragraph mapTitle = new Paragraph("Mapa de Ruta", subtitleFont);
            mapTitle.setSpacingAfter(10);
            document.add(mapTitle);
            
            try {
                // Convertir puntos a MapPoint
                var points = order.getDeliveryPoints().stream()
                    .map(p -> new MapPoint(p.getLatitude(), p.getLongitude(), p.getAddress()))
                    .collect(Collectors.toList());
                
                String mapUrl = mapGeneratorService.generateAlternativeMapUrl(points);
                
                // Intentar descargar e incrustar la imagen del mapa
                if (mapUrl != null) {
                    try {
                        Image mapImage = Image.getInstance(new URL(mapUrl));
                        mapImage.scaleToFit(500, 300);
                        mapImage.setAlignment(Element.ALIGN_CENTER);
                        document.add(mapImage);
                    } catch (Exception e) {
                        document.add(new Paragraph("Mapa disponible en: " + mapUrl, normalFont));
                    }
                }
            } catch (Exception e) {
                document.add(new Paragraph("No se pudo generar el mapa", normalFont));
            }
        }
        
        // Footer
        Paragraph footer = new Paragraph("Documento generado automáticamente por Delivery Routes", normalFont);
        footer.setAlignment(Element.ALIGN_CENTER);
        footer.setSpacingBefore(50);
        document.add(footer);
        
        document.close();
        
        return baos.toByteArray();
    }
    
    private void addTableCell(PdfPTable table, String text, boolean isHeader) {
        Font font = isHeader ? 
            new Font(Font.HELVETICA, 12, Font.BOLD) : 
            new Font(Font.HELVETICA, 10);
        
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(5);
        
        if (isHeader) {
            cell.setBackgroundColor(new Color(76, 175, 80));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        }
        
        table.addCell(cell);
    }
}
