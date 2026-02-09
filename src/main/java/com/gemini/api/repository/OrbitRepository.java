package com.gemini.api.repository;

import com.gemini.api.model.Orbit;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrbitRepository extends JpaRepository<Orbit, String> {
    // This magic method automatically writes the SQL to find orbits by User ID
    List<Orbit> findByUserIdOrderByCreatedAtDesc(String userId);

    // Secure Delete: Ensure user owns the orbit
    void deleteByIdAndUserId(String id, String userId);

    // Secure Find: Ensure user owns the orbit
    Orbit findByIdAndUserId(String id, String userId);
}