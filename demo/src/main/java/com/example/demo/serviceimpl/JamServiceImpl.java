package com.example.demo.serviceimpl;

import com.example.demo.dto.dtoJam;
import com.example.demo.model.Jam;
import com.example.demo.service.JamService;
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
public class JamServiceImpl implements JamService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<dtoJam> getJamByPage(Integer id, String jam, Pageable pageable) {
        String query = "select new com.example.demo.dto.dtoJam(j.id, j.jam) from Jam as j ";

        String count = "select count(j.id) from Jam as j ";

        StringBuilder stringBuilder = new StringBuilder();
        if(id > 0){
            if (stringBuilder.length() > 0) stringBuilder.append(" and ");
            else stringBuilder.append(" where ");
            stringBuilder.append("j.id = "+id);
        }
        if(jam != null){
            if (stringBuilder.length() > 0) stringBuilder.append(" and ");
            else stringBuilder.append(" where ");
            stringBuilder.append("j.jam like '%"+jam+"%'");
        }

        String counts = entityManager.createQuery(count + stringBuilder).getSingleResult().toString();
        Integer inCount = Integer.parseInt(counts);

        Query queries = entityManager.createQuery(query + stringBuilder, dtoJam.class);
        queries.setMaxResults(pageable.getPageSize());
        queries.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        List<dtoJam> list = queries.getResultList();
        Page<dtoJam> page = new PageImpl<>(list, pageable, inCount);
        return page;
    }

    @Override
    public List<dtoJam> getJamByList() {
        return null;
    }

    @Override
    public Jam save(Jam jam) {
        return null;
    }

    @Override
    public Jam delete(Integer id) {
        return null;
    }

    @Override
    public Optional<Jam> findById(Integer id) {
        return Optional.empty();
    }
}
