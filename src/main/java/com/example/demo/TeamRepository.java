package com.example.demo;

import com.example.demo.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {

    Page<Team> findByConferenceIgnoreCase(String conference, Pageable pageable);

    Page<Team> findByDivisionIgnoreCase(String division, Pageable pageable);

    Page<Team> findByCityContainingIgnoreCase(String city, Pageable pageable);

    Page<Team> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
