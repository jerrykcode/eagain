package com.jerrykcode.eagain.controller;

import com.jerrykcode.eagain.service.likes.LikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikeController {

    @Autowired
    private LikesService likesService;

    @PostMapping("/like/{dbModelName}/{id}")
    public Integer like(@PathVariable("dbModelName") String dbModelName,
                        @PathVariable("id") String id) {
        Long likesCount = likesService.increaseLikesCount(dbModelName, id);
        if (likesCount != null)
            return likesCount.intValue();
        else
            return 0;
    }
}
