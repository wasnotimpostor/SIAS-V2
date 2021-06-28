package com.example.demo.serviceimpl;

import com.example.demo.dto.dtoJadwal;
import com.example.demo.model.Jadwal;
import com.example.demo.repository.JadwalRepository;
import com.example.demo.service.JadwalService;
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
public class JadwalServiceImpl implements JadwalService {

    @Autowired
    private JadwalRepository jadwalRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<dtoJadwal> getJadwalByPage(Integer id, Integer idHari, Long guruPengajar, Pageable pageable) {
        String query = "select new com.example.demo.dto.dtoJadwal(j.id, h.name, ja.jam, mp.name, u.fullname, k.name, r.name) " +
                "from Jadwal as j " +
                "left join Hari as h on j.hari = h.id " +
                "left join Jam as ja on j.jam = ja.id " +
                "left join MataPelajaran as mp on j.matpel = mp.id " +
                "left join Users as u on j.guruPengajar = u.id " +
                "left join Kelas as k on j.kelas = k.id " +
                "left join Ruang as r on j.ruang = r.id " +
                "where h.id = " + idHari;

        String count = "select count(j.id) from Jadwal as j " +
                "left join Hari as h on j.hari = h.id " +
                "left join Jam as ja on j.jam = ja.id " +
                "left join MataPelajaran as mp on j.matpel = mp.id " +
                "left join Users as u on j.guruPengajar = u.id " +
                "left join Kelas as k on j.kelas = k.id " +
                "left join Ruang as r on j.ruang = r.id " +
                "where h.id = " + idHari;

        StringBuilder stringBuilder = new StringBuilder();
        if (id > 0){
            stringBuilder.append(" and j.id = " + id);
        }
        if (guruPengajar > 0){
            stringBuilder.append(" and j.guruPengajar = " + guruPengajar);
        }

        String counts = entityManager.createQuery(count + stringBuilder).getSingleResult().toString();
        Integer inCount = Integer.parseInt(counts);

        Query queries = entityManager.createQuery(query + stringBuilder, dtoJadwal.class);
        queries.setMaxResults(pageable.getPageSize());
        queries.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        List<dtoJadwal> list = queries.getResultList();
        Page<dtoJadwal> page = new PageImpl<>(list, pageable, inCount);
        return page;
    }

    @Override
    public List<dtoJadwal> getJadwalByList() {
        String query = "select new com.example.demo.dto.dtoJadwal(j.id, h.name, ja.jam, mp.name, u.fullname, k.name, r.name) " +
                "from Jadwal as j " +
                "left join Hari as h on j.hari = h.id " +
                "left join Jam as ja on j.jam = ja.id " +
                "left join MataPelajaran as mp on j.matpel = mp.id " +
                "left join Users as u on j.guruPengajar = u.id " +
                "left join Kelas as k on j.kelas = k.id " +
                "left join Ruang as r on j.ruang = r.id ";
        Query queries = entityManager.createQuery(query, dtoJadwal.class);
        List<dtoJadwal> list = queries.getResultList();
        return list;
    }

    @Override
    public Jadwal save(Jadwal jadwal) {
        return jadwalRepository.save(jadwal);
    }

    @Override
    public Jadwal delete(Integer id) {
        Jadwal jadwal = jadwalRepository.findById(id).orElse(null);
        jadwalRepository.deleteById(id);
        return jadwal;
    }

    @Override
    public Optional<Jadwal> findById(Integer id) {
        return jadwalRepository.findById(id);
    }
}
