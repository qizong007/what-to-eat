package com.fzufood.repository;

import com.fzufood.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author buzhouI
 */
@Repository
@Mapper
public interface TagMapper {

    /**
     * 查询所有tag(tagId, content)
     * @return
     */
    List<Tag> listTags();

    /**
     * 根据tagId查询对应tag(tagId, content)
     * @param tagId
     * @return Tag
     */
    Tag getTagById(Integer tagId);

    /**
     * 插入新的tag(tagId, content)
     * @param tag
     * @return int
     */
    int saveTag(Tag tag);

    /**
     * 根据tagId更新tag(tagId, content)
     * @param tag
     * @return int
     */
    int updateTag(Tag tag);

    /**
     * 根据tagId删除tag(tagId, content)
     * @param tagId
     * @return int
     */
    int removeTagById(Integer tagId);
}
