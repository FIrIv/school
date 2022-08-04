package ru.hogwarts.school.repository;

import java.awt.print.Pageable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Avatar;

// JpaRepository<Avatar, Long> расширяет PagingAndSortingRepository<Avatar, Long>, поэтому не меняем
public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Optional<Avatar> findByStudentId(Long studentId);

    //Page<List<Avatar>> findAll(Pageable pageable);
}
