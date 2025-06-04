package org.lessons.java.mymoviedb.service;

import java.util.List;

import org.lessons.java.mymoviedb.model.Director;
import org.lessons.java.mymoviedb.repository.DirectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DirectorService {
    @Autowired
    private DirectorRepository directorRepository;

    public List<Director> findAll() {
        return directorRepository.findAll();
    }

    public Director getById(Integer id) {
        return directorRepository.findById(id).get();
    }

    public Director create(Director director) {
        return directorRepository.save(director);
    }

    public void deleteById(Integer id) {
        directorRepository.deleteById(id);
    }

    public Director update(Director director) {
        return directorRepository.save(director);
    }
}
