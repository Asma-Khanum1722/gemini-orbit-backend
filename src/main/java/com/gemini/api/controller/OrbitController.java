package com.gemini.api.controller;

import com.gemini.api.model.Orbit;
import com.gemini.api.repository.OrbitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional; // Import needed for delete

@RestController
@RequestMapping("/api/orbits")
@CrossOrigin(origins = "http://localhost:3000") // CORS safety
public class OrbitController {

    @Autowired
    private OrbitRepository orbitRepository;

    // GET all orbits for the logged-in user
    @GetMapping
    public List<Orbit> getMyOrbits(@AuthenticationPrincipal Jwt principal) {
        String userId = principal.getSubject(); // Clerk User ID from token
        return orbitRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    // GET a single orbit securely
    @GetMapping("/{id}")
    public Orbit getOrbitById(@AuthenticationPrincipal Jwt principal, @PathVariable String id) {
        String userId = principal.getSubject();
        return orbitRepository.findByIdAndUserId(id, userId);
    }

    // SAVE a new orbit
    @PostMapping
    public Orbit saveOrbit(@AuthenticationPrincipal Jwt principal, @RequestBody Map<String, String> payload) {
        String userId = principal.getSubject();
        
        Orbit orbit = new Orbit();
        orbit.setUserId(userId); // Auto-set from token (Secure!)
        orbit.setTitle(payload.get("title"));
        orbit.setType(payload.get("type"));
        orbit.setGraphData(payload.get("graphData"));
        orbit.setCreatedAt(LocalDateTime.now());
        
        return orbitRepository.save(orbit); // Save to Neon DB
    }

    // DELETE an orbit (Secure: only if owned by user)
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteOrbit(@AuthenticationPrincipal Jwt principal, @PathVariable String id) {
        String userId = principal.getSubject();
        orbitRepository.deleteByIdAndUserId(id, userId);
    }
}