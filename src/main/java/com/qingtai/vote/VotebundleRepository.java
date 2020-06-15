package com.qingtai.vote;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VotebundleRepository extends JpaRepository<Votebundle, Integer> {

	List<Votebundle> findByVcreator(String username);

}
