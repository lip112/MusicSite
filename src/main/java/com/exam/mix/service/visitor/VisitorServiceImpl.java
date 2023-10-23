package com.exam.mix.service.visitor;

import com.exam.mix.dto.visitor.VisitorDTO;
import com.exam.mix.entity.visitor.Visitor;
import com.exam.mix.repository.visitor.VisitorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class VisitorServiceImpl implements VisitorService{
    private final VisitorRepository visitorRepository;

    @Override
    public void incrementVisitorCount(final String email) {
        log.info("incrementVisitorCount");
        //오늘 방문자중에 같은 이메일과 시간이 있는지 체크 후 없으면 추가하고 있으면 방문자 수를 늘리지 않는다.
        LocalDateTime date = LocalDateTime.now();
        if (CheckTodayVisit(email,date)) {
            VisitorDTO visitorDTO = VisitorDTO.builder()
                    .email(email)
                    .build();
            visitorRepository.save(DtoToEntity(visitorDTO));
        }
    }

    @Override
    public Boolean CheckTodayVisit(String email, LocalDateTime date) {
        Optional<Visitor> byEmailAndRegDataLike = visitorRepository.findByEmailAndRegDate(email, date);
        System.out.println("byEmailAndRegDataLike = " + byEmailAndRegDataLike);
        System.out.println("byEmailAndRegDataLike.isEmpty() = " + byEmailAndRegDataLike.isEmpty());
        return byEmailAndRegDataLike.isEmpty();//비어있으면 금일 접속 기록이 없으니 true
    }

    @Override
    public int getTotalVisitorCount() {
        return visitorRepository.countBy();
    }

    @Override
    public int getTodayVisitorCount() {
        LocalDateTime date = LocalDateTime.now();
        return visitorRepository.countByTodayCount(date);
    }
}
