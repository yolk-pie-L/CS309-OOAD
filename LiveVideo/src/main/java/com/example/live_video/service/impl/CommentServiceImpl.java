package com.example.live_video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.live_video.entity.Comment;
import com.example.live_video.entity.User;
import com.example.live_video.mapper.CommentMapper;
import com.example.live_video.mapper.UserMapper;
import com.example.live_video.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    CommentMapper commentMapper;

    private List<Comment> getRootLevelCommentList(Long sectionId) {
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("parent_id", -1);
        commentQueryWrapper.eq("section_id", sectionId);
        commentQueryWrapper.orderByDesc("create_time");
        List<Comment> commentList = commentMapper.selectList(commentQueryWrapper);
        for (Comment comment : commentList) {
            comment.setThisUser(userMapper.selectById(comment.getUserId()));
        }
        return commentList;
    }

    private List<Comment> getCommentTree(Long id, User parentUser) {
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>(); // First Level
        commentQueryWrapper.eq("parent_id", id);
        commentQueryWrapper.orderByDesc("create_time");
        List<Comment> firstLevelCommentList = commentMapper.selectList(commentQueryWrapper);
        List<Comment> resList = new ArrayList<>(firstLevelCommentList);
        for (Comment firstLevelComment : firstLevelCommentList) {
            firstLevelComment.setThisUser(userMapper.selectById(firstLevelComment.getUserId()));
            firstLevelComment.setParentUser(parentUser);
            List<Comment> secondaryCommentList = getSecondLevelCommentList(firstLevelComment.getId(), firstLevelComment.getThisUser());
            resList.addAll(secondaryCommentList);
        }
        return resList;
    }

    private List<Comment> getSecondLevelCommentList(Long id, User parentUser) {
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("parent_id", id);
        commentQueryWrapper.orderByDesc("create_time");
        List<Comment> secondaryCommentList = commentMapper.selectList(commentQueryWrapper);
        List<Comment> resList = new ArrayList<>(secondaryCommentList);
        for (Comment secondaryComment : secondaryCommentList) {
            secondaryComment.setThisUser(userMapper.selectById(secondaryComment.getUserId()));
            secondaryComment.setParentUser(parentUser);
            List<Comment> temptCommentList = getSecondLevelCommentList(secondaryComment.getId(), secondaryComment.getThisUser());
            resList.addAll(temptCommentList);
        }
        return resList;
    }

    @Override
    public List<Comment> getCommentList(Long sectionId) {
        List<Comment> rootLevelCommentList = getRootLevelCommentList(sectionId);
        for (Comment rootLevelComment : rootLevelCommentList) {
            List<Comment> commentTree = getCommentTree(rootLevelComment.getId(), rootLevelComment.getThisUser());
            rootLevelComment.setReplyCommentList(commentTree);
        }
        return rootLevelCommentList;
    }

    @Override
    public boolean saveComment(Comment comment) {
        return commentMapper.insert(comment) == 1;
    }

    @Override
    public boolean deleteComment(Long commentId) {
        List<Comment> commentList = getCommentTree(commentId, null);
        commentList.forEach(c -> commentMapper.deleteById(c));
        commentMapper.deleteById(commentId);
        return true;
    }

    @Override
    public Comment getOneComment(Long commentId) {
        Comment comment = commentMapper.selectById(commentId);
        comment.setThisUser(userMapper.selectById(comment.getId()));
        if(comment.getParentId() != -1){
            Comment parentComment = commentMapper.selectById(comment.getParentId());
            comment.setParentUser(userMapper.selectById(parentComment.getUserId()));
        }
        return comment;
    }

    @Override
    public long countComments(Long sectionId) {
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("section_id", sectionId);
        return commentMapper.selectCount(commentQueryWrapper);
    }

    @Override
    public boolean updateComment(Comment comment) {
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("id", comment.getId());
        commentMapper.update(comment, commentQueryWrapper);
        return true;
    }
}
