package dev.ziggeek.api.model.mapper;


import dev.ziggeek.api.model.entity.User;
import dev.ziggeek.api.model.dto.respomse.UserResponse;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toBasicResp(User user);
}
