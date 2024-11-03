package com.online.books.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "book_chapter", schema = "online-books")
public class BookChapter {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "book_number", nullable = false, length = 64)
    private String bookNumber;

    @Column(name = "chapter_title")
    private String chapterTitle;

    @Lob
    @Column(name = "chapter_content")
    private String chapterContent;

    @Column(name = "chapter_audio")
    private String chapterAudio;

    @Column(name = "chapter_cover_image")
    private String chapterCoverImage;

}