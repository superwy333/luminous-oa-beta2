package cn.luminous.squab.mapper;

import cn.luminous.squab.entity.Posts;
import cn.luminous.squab.model.PostsModel;
import cn.luminous.squab.mybatis.imapper.IMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PostsMapper extends IMapper<Posts> {

    List<PostsModel> queryPostsPage(@Param("condition") Map<String,Object> condition);
}