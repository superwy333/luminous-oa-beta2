package cn.luminous.squab.service;

import cn.luminous.squab.entity.Posts;
import cn.luminous.squab.model.PostsModel;

import java.util.List;
import java.util.Map;

public interface PostsService extends BaseService<Posts> {


    List<PostsModel> queryPostsPage(Map condition);


}
