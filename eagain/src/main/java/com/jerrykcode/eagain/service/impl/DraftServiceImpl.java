package com.jerrykcode.eagain.service.impl;

import com.jerrykcode.eagain.mapper.DraftMapper;
import com.jerrykcode.eagain.model.Draft;
import com.jerrykcode.eagain.service.DraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DraftServiceImpl implements DraftService {

    @Autowired
    private DraftMapper draftMapper;

    @Override
    public void create(Draft draft) {
        draftMapper.create(draft);
    }

    @Override
    public void updte(Draft draft) {
        draftMapper.update(draft);
    }

    @Override
    public void createOrUpdate(Draft draft) {
        if (draftMapper.queryById(draft.getId()) == null) {
            draftMapper.create(draft);
        } else {
            draftMapper.update(draft);
        }
    }

    @Override
    public Draft queryById(Long id) {
        return draftMapper.queryById(id);
    }

    @Override
    public Draft queryByCreatorIdAndRelatedId(Long creatorId, Long relatedId) {
        return draftMapper.queryByCreatorIdAndRelatedId(creatorId, relatedId);
    }

    @Override
    public List<Draft> listByCreatorId(Long creatorId) {
        return draftMapper.listByCreatorId(creatorId);
    }

    @Override
    public void delete(Long id) {
        draftMapper.delete(id);
    }
}
