package cl.bree2003.AlkeWalletM6.service;

import cl.bree2003.AlkeWalletM6.entity.CommentEntity;

import java.util.Optional;

public interface ICommentService {

    Optional<CommentEntity> getCommentById(Long id);
    void createComment(CommentEntity comment);
    void updateComment(Long id, CommentEntity comment);
    void deleteComment(Long id);
}
