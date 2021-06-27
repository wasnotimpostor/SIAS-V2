package com.example.demo.serviceimpl;

import com.example.demo.dto.dtoMataPelajaran;
import com.example.demo.model.MataPelajaran;
import com.example.demo.repository.MataPelajaranRepository;
import com.example.demo.service.MataPelajaranService;
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
public class MataPelajaranServiceImpl implements MataPelajaranService {

    @Autowired
    private MataPelajaranRepository mataPelajaranRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<dtoMataPelajaran> getMatpelByPage(Integer id, String name, Long koordinator, Pageable pageable) {
        String query = "select new com.example.demo.dto.dtoMataPelajaran(m.id, m.name, u.fullname) " +
                "from MataPelajaran as m " +
                "left join Users as u on m.koordinator = u.id ";

        String count = "select count(m.id) from MataPelajaran as m " +
                "left join Users as u on m.koordinator = u.id ";

        StringBuilder stringBuilder = new StringBuilder();

        String counts = entityManager.createQuery(count + stringBuilder).getSingleResult().toString();
        Integer inCount = Integer.parseInt(counts);

        Query queries = entityManager.createQuery(query + stringBuilder, dtoMataPelajaran.class);
        queries.setMaxResults(pageable.getPageSize());
        queries.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        List<dtoMataPelajaran> list = queries.getResultList();
        Page<dtoMataPelajaran> page = new PageImpl(list, pageable, inCount);
        return page;
    }

    @Override
    public List<dtoMataPelajaran> getMatpelByList() {
        String query = "select new com.example.demo.dto.dtoMataPelajaran(m.id, m.name, u.fullname) " +
                "from MataPelajaran as m " +
                "left join Users as u on m.koordinator = u.id ";
        Query queries = entityManager.createQuery(query, dtoMataPelajaran.class);
        List<dtoMataPelajaran> list = queries.getResultList();
        return list;
    }

    @Override
    public MataPelajaran save(MataPelajaran mataPelajaran) {
        return mataPelajaranRepository.save(mataPelajaran);
    }

    @Override
    public MataPelajaran delete(Integer id) {
        MataPelajaran mataPelajaran = mataPelajaranRepository.findById(id).orElse(null);
        mataPelajaranRepository.deleteById(id);
        return mataPelajaran;
    }

    @Override
    public Optional<MataPelajaran> findById(Integer id) {
        return mataPelajaranRepository.findById(id);
    }
}
