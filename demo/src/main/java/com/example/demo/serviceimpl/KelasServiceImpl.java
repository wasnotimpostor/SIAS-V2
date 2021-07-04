package com.example.demo.serviceimpl;

import com.example.demo.dto.dtoKelas;
import com.example.demo.model.Kelas;
import com.example.demo.repository.KelasRepository;
import com.example.demo.service.KelasService;
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
public class KelasServiceImpl implements KelasService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<dtoKelas> getKelasByPage(Integer id, String namaKelas, Integer lantai, Pageable pageable) {
        String query = "select new com.example.demo.dto.dtoKelas(k.id, k.name, u.fullname, r.name, r.floor, u1.fullname, u1.nimOrNik, count(u1.id)) " +
                "from Kelas as k " +
                "left join Users as u on k.users = u.id " +
                "left join Ruang as r on k.ruang = r.id " +
                "left join Users as u1 on k.users = u1.id and u1.status = 6 ";

        String count = "select count(k.id) from Kelas as k " +
                "left join Users as u on k.users = u.id " +
                "left join Ruang as r on k.ruang = r.id " +
                "left join Users as u1 on k.users = u1.id and u1.status = 6 ";

        StringBuilder stringBuilder = new StringBuilder();

        String counts = entityManager.createQuery(count + stringBuilder).getSingleResult().toString();
        Integer inCount = Integer.parseInt(counts);

        Query queries = entityManager.createQuery(query + stringBuilder, dtoKelas.class);
        queries.setMaxResults(pageable.getPageSize());
        queries.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        List<dtoKelas> list = queries.getResultList();
        Page<dtoKelas> page = new PageImpl(list, pageable, inCount);
        return page;
    }

    @Override
    public List<dtoKelas> getKelasByList() {
        return null;
    }

    @Override
    public Kelas save(Kelas kelas) {
        return null;
    }

    @Override
    public Kelas delete(Integer id) {
        return null;
    }

    @Override
    public Optional<Kelas> findById(Integer id) {
        return Optional.empty();
    }
}
