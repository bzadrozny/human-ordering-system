package com.bz.hos.services;

import com.bz.hos.db.BazaDanych;
import com.bz.hos.db.CommentTable;
import com.bz.hos.model.CommentEntity.Comment;
import com.bz.hos.model.UserAndLocationEntity.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class SupportServices {

    public void addNewComment(Comment comment, User user){

        CommentTable commentTable = new CommentTable(
                comment.getTitle(),
                comment.getDescription(),
                user.getMail(),
                LocalDate.now(),
                "nowy"
        );

        BazaDanych.getCommentList().put(BazaDanych.getCommentList().size(), commentTable);
    }

    public Collection<CommentTable> getAllComment(){

        return BazaDanych.getCommentList().values();
    }

}
