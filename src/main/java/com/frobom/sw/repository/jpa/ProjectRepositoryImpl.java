package com.frobom.sw.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.frobom.sw.entity.Project;
import com.frobom.sw.repository.ProjectRepository;

@Repository
public class ProjectRepositoryImpl implements ProjectRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Project project) {
        if (project.getId() == null) {
            entityManager.persist(project);
        } else {
            entityManager.merge(project);
        }
    }

    @Override
    public List<Project> findAll() {
        return entityManager.createQuery("select p from Project p", Project.class).getResultList();
    }

    @Override
    public Project findById(int id) {
        return entityManager.find(Project.class, id);
    }

    @Override
    public Project findByName(String name) {
        Project project = null;
        try {
            Query q = entityManager.createQuery("select p from Project p where p.name=?");
            q.setParameter(1, name);
            project = (Project) q.getSingleResult();
        } catch (NoResultException e) {

        }
        if (project != null) {
            System.out.println("project name is " + project.getName());
        }
        return project;
    }

    @Override
    public void update(Project project) {
        // Query query= entityManager.createQuery("update Project p set p.name=:name where p.id=:id");
        // query.setParameter("id", project.getId());
        // query.setParameter("name", project.getName());
        // query.executeUpdate();
        entityManager.merge(project);
    }

    @Override
    public void delete(Project project) {
        entityManager.remove(project);
    }

}
