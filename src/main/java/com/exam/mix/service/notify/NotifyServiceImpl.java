package com.exam.mix.service.notify;

import com.exam.mix.dto.board.BoardDTO;
import com.exam.mix.dto.notify.notifyDTO;
import com.exam.mix.dto.page.PageRequestDTO;
import com.exam.mix.dto.page.PageResultDTO;
import com.exam.mix.entity.board.Board;
import com.exam.mix.entity.notify.Notify;
import com.exam.mix.repository.notify.NotifyRepository;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class NotifyServiceImpl implements NotifyService{
    private final NotifyRepository notifyRepository;
    @Override
    public notifyDTO getnno(Long bno) {
        Optional<Notify> notify = notifyRepository.findById(bno);
        return EntityToDto(notify.get());
    }

    @Override
    public PageResultDTO getList(PageRequestDTO pageRequestDTO) {
        Function<Notify, notifyDTO> fn = (tuple -> EntityToDto(tuple));
        Page<Notify> result = notifyRepository.findAll(pageRequestDTO.getPageable(Sort.by("nno").descending()));

        PageResultDTO pageResultDTO = new PageResultDTO(result, fn);
        System.out.println("pageResultDTO = " + pageResultDTO);
        return pageResultDTO;
    }

    @Override
    public void writeNotify(notifyDTO notifyDTO) {
        notifyRepository.save(DtoToEntity(notifyDTO));
    }
}
