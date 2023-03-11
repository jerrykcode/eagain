package com.jerrykcode.eagain.controller;

import com.jerrykcode.eagain.dto.DraftDTO;
import com.jerrykcode.eagain.model.Draft;
import com.jerrykcode.eagain.request.SaveAnswerDraftRequest;
import com.jerrykcode.eagain.service.DraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/draft")
public class DraftController {

    @Autowired
    private DraftService draftService;

    @PostMapping("/saveAnswer")
    public void saveAnswerDraft(@RequestBody SaveAnswerDraftRequest request) {
        Long currentGMT = System.currentTimeMillis();
        Draft oldDraft = null;
        if ((oldDraft = draftService.queryByCreatorIdAndRelatedId(request.getCreatorId()
                , request.getQuestionId())) == null) {
            draftService.create(new Draft()
                    .setCreatorId(request.getCreatorId())
                    .setAnswerDraftType()
                    .setRelatedId(request.getQuestionId())
                    .setContent(request.getContent())
                    .setGmtUpdated(currentGMT));
        } else {
            draftService.updte(new Draft()
                    .setId(oldDraft.getId())
                    .setContent(request.getContent())
                    .setGmtUpdated(currentGMT));
        }
    }

    @GetMapping("/query")
    public DraftDTO queryById(@RequestParam("id") Long id) {
        return draftService.queryById(id).toDTO();
    }

    @GetMapping("/queryAnswer")
    public DraftDTO queryByCreatorIdAndQuestionId(@RequestParam("creatorId") Long creatorId,
                                               @RequestParam("questionId") Long questionId) {
        return draftService.queryByCreatorIdAndRelatedId(creatorId, questionId).toDTO();
    }

    @GetMapping("/list")
    public List<DraftDTO> listByCreatorId(@RequestParam("creatorId") Long creatorId) {
        List<DraftDTO> dtos = new ArrayList<>();
        draftService.listByCreatorId(creatorId).forEach(draft -> {dtos.add(draft.toDTO());});
        return dtos;
    }
}
