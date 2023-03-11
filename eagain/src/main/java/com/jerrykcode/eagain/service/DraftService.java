package com.jerrykcode.eagain.service;

import com.jerrykcode.eagain.model.Draft;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DraftService {

    public void create(Draft draft);
    public void updte(Draft draft);
    public void createOrUpdate(Draft draft);
    public Draft queryById(Long id);
    public Draft queryByCreatorIdAndRelatedId(Long creatorId, Long relatedId);
    public List<Draft> listByCreatorId(Long creatorId);
}
