package com.example.demo.serviceimpl;

import com.example.demo.dto.dtoRuang;
import com.example.demo.model.Ruang;
import com.example.demo.repository.RuangRepository;
import com.example.demo.service.RuangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Service
public class RuangServiceImpl implements RuangService {

    @Autowired
    private RuangRepository ruangRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<dtoRuang> getRuangByPage(Integer id, String name, Integer nomor, Integer floor, Pageable pageable) {
        String query = "select new com.example.demo.dto.dtoRuang(r.id, r.name, r.nomor, r.floor) " +
                "from Ruang as r ";

        String count = "select count(r.id) from Ruang as r ";
        StringBuilder stringBuilder = new StringBuilder();

        String counts = entityManager.createQuery(count + stringBuilder).getSingleResult().toString();
        Integer inCount = Integer.parseInt(counts);

        Query queries = entityManager.createQuery(query + stringBuilder, dtoRuang.class);
        queries.setMaxResults(pageable.getPageSize());
        queries.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        List<dtoRuang> list = queries.getResultList();
        Page<dtoRuang> page = new PageImpl<>(list, pageable, inCount);
        return page;
    }

    @Override
    public List<dtoRuang> getRuangByList() {
        String query = "select new com.example.demo.dto.dtoRuang(r.id, r.name, r.nomor, r.floor) " +
                "from Ruang as r ";
        Query queries = entityManager.createQuery(query, dtoRuang.class);
        List<dtoRuang> list = queries.getResultList();
        return list;
    }

    @Override
    public Ruang save(Ruang ruang) {
        return ruangRepository.save(ruang);
    }

    @Override
    public Ruang delete(Integer id) {
        Ruang ruang = ruangRepository.findById(id).orElse(null);
        ruangRepository.deleteById(id);
        return ruang;
    }

    @Override
    public Optional<Ruang> findById(Integer id) {
        return ruangRepository.findById(id);
    }
}
