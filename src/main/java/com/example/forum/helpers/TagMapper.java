package com.example.forum.helpers;
import com.example.forum.models.Tag;
import com.example.forum.models.dtos.TagDto;
import com.example.forum.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class TagMapper {

    private final TagService tagService;
    @Autowired
    public TagMapper(TagService tagService) {
        this.tagService = tagService;
    }

        public Tag fromDto(TagDto dto) {
        Tag tag = new Tag();
        tag.setName(dto.getTagName());
        return tag;
    }

}
