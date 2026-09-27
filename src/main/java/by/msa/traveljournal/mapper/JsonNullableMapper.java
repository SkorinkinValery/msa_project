package by.msa.traveljournal.mapper;

import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.openapitools.jackson.nullable.JsonNullable;

@Mapper(componentModel = "spring")
public class JsonNullableMapper {

    public <T> T unwrap(JsonNullable<T> value) {
        return value == null ? null : value.orElse(null);
    }

    @Condition
    public <T> boolean isPresent(JsonNullable<T> value) {
        return value != null && value.isPresent();
    }
}
