package ir.maralani.flighttracker.service.Impl;

import ir.maralani.flighttracker.repository.RoleRepository;
import ir.maralani.flighttracker.model.Role;
import ir.maralani.flighttracker.repository.RoleRepository;
import ir.maralani.flighttracker.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public boolean exists(Role entity) {
        return roleRepository.exists(Example.of(entity));
    }

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role getById(Long id) {
        return roleRepository.getOne(id);
    }

    @Override
    public Role save(Role entity) {
        return roleRepository.save(entity);
    }

    @Override
    public void remove(Role entity) {
        roleRepository.delete(entity);
    }
}
