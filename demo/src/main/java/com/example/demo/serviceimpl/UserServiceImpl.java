package com.example.demo.serviceimpl;

import com.example.demo.dto.dtoUser;
import com.example.demo.model.Users;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
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
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<dtoUser> getUserByPage(Long id, Integer status, Pageable pageable){
        String query = "select new com.example.demo.dto.dtoUser(u.id, u.username, u.email, u.firstname, u.lastname, u.gender, u.status) " +
                "from Users as u " +
                "where u.status = "+status;

        String count = "select count(u.id) from Users as u where u.status = "+status;


        StringBuilder stringBuilder = new StringBuilder();

        String counts = entityManager.createQuery(count + stringBuilder).getSingleResult().toString();
        Integer inCount = Integer.parseInt(counts);

        Query queries = entityManager.createQuery(query + stringBuilder, dtoUser.class);
        queries.setMaxResults(pageable.getPageSize());
        queries.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        List<dtoUser> list = queries.getResultList();
        Page<dtoUser> page = new PageImpl(list, pageable, inCount);
        return page;
    }

    @Override
    public List<dtoUser> getUserByList() {
        String query = "select new com.example.demo.dto.dtoUser(u.id, u.username, u.email, u.firstname, u.lastname, u.gender, u.status) from Users as u ";
        Query queries = entityManager.createQuery(query, dtoUser.class);
        List<dtoUser> list = queries.getResultList();
        return list;
    }

    @Override
    public Users save(Users users) {
        return userRepository.save(users);
    }

    @Override
    public Users delete(Long id) {
        Users users = userRepository.findById(id).orElse(null);
        userRepository.deleteById(id);
        return users;
    }

    @Override
    public Optional<Users> findById(Long id) {
        return userRepository.findById(id);
    }
}
