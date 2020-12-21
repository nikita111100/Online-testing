package org.nikita111100.onlinetesting.service;

import org.nikita111100.onlinetesting.model.persistent.PossibleAnswer;
import org.nikita111100.onlinetesting.repository.PossibleAnswerRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PossibleAnswerService {

    private final PossibleAnswerRepo pAnswerRepo;

    public PossibleAnswerService(PossibleAnswerRepo pAnswerRepo) {
        this.pAnswerRepo = pAnswerRepo;
    }

    public List<PossibleAnswer> findAll() {
        return pAnswerRepo.findAll();
    }

    public PossibleAnswer findById(Long id) {
        return pAnswerRepo.getOne(id);
    }

    @Transactional
    public PossibleAnswer savePossibleAnswer(PossibleAnswer possibleAnswer) {
        return pAnswerRepo.save(possibleAnswer);
    }

    @Transactional
    public void deleteById(Long id){
        pAnswerRepo.deleteById(id);
    }

}
