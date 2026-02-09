package com.gemini.api.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "orbits")
@Data
public class Orbit {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String userId; // Stores the Clerk User ID

    private String title;
    private String type; // e.g., "Physics", "AI"

    @Column(columnDefinition = "TEXT")
    private String graphData; // Stores the JSON graph

    private LocalDateTime createdAt = LocalDateTime.now();
    
    // Getters and Setters (in case Lombok isn't working for you)
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getGraphData() { return graphData; }
    public void setGraphData(String graphData) { this.graphData = graphData; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}