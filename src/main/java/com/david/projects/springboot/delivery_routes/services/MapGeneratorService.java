package com.david.projects.springboot.delivery_routes.services;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Locale;

@Service
public class MapGeneratorService {

    // Método alternativo usando el servicio de mapas de OpenStreetMap oficial
    public String generateAlternativeMapUrl(List<MapPoint> points) {
        if (points == null || points.isEmpty()) {
            return null;
        }
        
        // Calcular centro
        double sumLat = 0, sumLng = 0;
        for (MapPoint p : points) {
            sumLat += p.getLatitude();
            sumLng += p.getLongitude();
        }
        double centerLat = sumLat / points.size();
        double centerLng = sumLng / points.size();
        
        // Usar OpenStreetMap embed (funciona mejor en navegadores)
        return String.format(Locale.US,
            "https://www.openstreetmap.org/export/embed.html?bbox=%f,%f,%f,%f&layer=mapnik&marker=%f,%f",
            points.get(0).getLongitude() - 0.05, points.get(0).getLatitude() - 0.05,
            points.get(0).getLongitude() + 0.05, points.get(0).getLatitude() + 0.05,
            centerLat, centerLng);
    }
    
    public static class MapPoint {
        private double latitude;
        private double longitude;
        private String label;
        
        public MapPoint(double latitude, double longitude, String label) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.label = label;
        }
        
        public double getLatitude() { return latitude; }
        public double getLongitude() { return longitude; }
        public String getLabel() { return label; }
    }
}
