package cl.bree2003.AlkeWalletM6.service;

import cl.bree2003.AlkeWalletM6.entity.PostEntity;

import java.util.List;
import java.util.Optional;

public interface IPostService{

    List<PostEntity> getAllPost();
    Optional<PostEntity> getPostById(Long id);
    List<PostEntity> getPostByUserId(Long userId);
    void createPost(PostEntity post);
    void updatePost(Long id, PostEntity post);
    void deletePostById(Long id);
    List<PostEntity> searchPostByTitle(String title);
}
