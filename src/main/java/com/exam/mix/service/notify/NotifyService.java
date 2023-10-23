package com.exam.mix.service.notify;


import com.exam.mix.dto.notify.notifyDTO;
import com.exam.mix.dto.page.PageRequestDTO;
import com.exam.mix.dto.page.PageResultDTO;
import com.exam.mix.entity.notify.Notify;

import java.util.List;

public interface NotifyService {

    notifyDTO getnno(Long bno);
    PageResultDTO getList(PageRequestDTO pageRequestDTO);

    void writeNotify(notifyDTO notifyDTO);

    default notifyDTO EntityToDto(Notify notify) {
        notifyDTO notifyDTO = com.exam.mix.dto.notify.notifyDTO.builder()
                .nno(notify.getNno())
                .content(notify.getContent())
                .title(notify.getTitle())
                .build();
        return notifyDTO;
    }

    default Notify DtoToEntity(notifyDTO notifyDTO) {
        Notify notify = Notify.builder()
                .content(notifyDTO.getContent())
                .title(notifyDTO.getTitle())
                .build();
        return notify;
    }
}
