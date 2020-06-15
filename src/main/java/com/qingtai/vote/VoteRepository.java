package com.qingtai.vote;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Integer> {
    List<Vote> findByVid(Integer vid);

	Vote findByIid(Integer iid);
}