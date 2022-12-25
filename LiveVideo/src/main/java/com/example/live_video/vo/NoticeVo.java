package com.example.live_video.vo;

import com.example.live_video.entity.Notice;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

@Data
public class NoticeVo {
    long id;
    String title;
    String context;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    Timestamp date;

    public NoticeVo(long id, String title, String context, Timestamp date) {
        this.id = id;
        this.title = title;
        this.context = context;
        this.date = date;
    }

    public static NoticeVo parseNotice(Notice notice){
        return new NoticeVo(
                notice.getId(),
                notice.getNoticeName(),
                notice.getContext(),
                notice.getCreateTime()
        );
    }

    public static List<NoticeVo> parseNoticeList(List<Notice> noticeList){
        List<NoticeVo> noticeVoList = new LinkedList<>();
        for(Notice notice: noticeList){
            noticeVoList.add(NoticeVo.parseNotice(notice));
        }
        return noticeVoList;
    }
}
