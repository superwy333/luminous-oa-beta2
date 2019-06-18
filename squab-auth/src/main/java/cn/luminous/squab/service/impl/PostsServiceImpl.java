package cn.luminous.squab.service.impl;

import cn.luminous.squab.entity.Posts;
import cn.luminous.squab.mapper.PostsMapper;
import cn.luminous.squab.model.PostsModel;
import cn.luminous.squab.mybatis.imapper.IMapper;
import cn.luminous.squab.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("postsService")
public class PostsServiceImpl extends BaseServiceImpl<Posts> implements PostsService {

    @Autowired
    private PostsMapper postsMapper;

    @Override
    protected IMapper<Posts> getBaseMapper() {
        return postsMapper;
    }

    @Override
    public List<PostsModel> queryPostsPage(Map condition) {
        return postsMapper.queryPostsPage(condition);
    }
}
