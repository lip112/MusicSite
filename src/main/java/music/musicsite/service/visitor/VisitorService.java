package music.musicsite.service.visitor;

import music.musicsite.dto.visitor.VisitorDTO;
import music.musicsite.entity.visitor.Visitor;

import java.time.LocalDateTime;

public interface VisitorService {

    void incrementVisitorCount(final String email);

    Boolean CheckTodayVisit(final String email, LocalDateTime date);

    int getTotalVisitorCount();

    int getTodayVisitorCount();

    default Visitor DtoToEntity(VisitorDTO visitorDTO) {
        Visitor visitor = Visitor.builder()
                .email(visitorDTO.getEmail())
                .build();
        return visitor;
    }

    default VisitorDTO EntityToDto(Visitor visitor) {
        VisitorDTO visitorDTO = VisitorDTO.builder()
                .email(visitor.getEmail())
                .regDate(visitor.getRegDate())
                .build();
        return visitorDTO;
    }
}
