package com.example.demo;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class BetService {

    private final BetRepository repo;

    public BetService(BetRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public Page<Bet> list(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Bet get(Long id) {
        return repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Bet not found: " + id));
    }

    public Bet create(Bet bet) {
        bet.setId(null); // ensure insert
        return repo.save(bet);
    }

    /** Partial or full update: non-null fields in payload overwrite existing */
    public Bet update(Long id, Bet payload) {
        Bet existing = get(id);
        copyNonNullProperties(payload, existing, "id");
        return repo.save(existing);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) throw new EntityNotFoundException("Bet not found: " + id);
        repo.deleteById(id);
    }

    // ---------- helpers ----------
    private static void copyNonNullProperties(Object src, Object target, String... ignore) {
        Set<String> ignoreSet = new HashSet<>();
        if (ignore != null) for (String i : ignore) ignoreSet.add(i);

        BeanWrapper srcWrap = new BeanWrapperImpl(src);
        BeanWrapper trgWrap = new BeanWrapperImpl(target);

        for (var pd : srcWrap.getPropertyDescriptors()) {
            String name = pd.getName();
            if (!trgWrap.isWritableProperty(name) || ignoreSet.contains(name)) continue;
            Object val = srcWrap.getPropertyValue(name);
            if (val != null) trgWrap.setPropertyValue(name, val);
        }
    }
}
