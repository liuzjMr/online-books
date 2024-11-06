package com.online.books.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "books", schema = "online-books")
public class Book {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "book_number", nullable = false, length = 64)
    private String bookNumber;

    @Column(name = "book_name")
    private String bookName;

    @Column(name = "book_author")
    private String bookAuthor;

    @Column(name = "book_release_date")
    private String bookReleaseDate;

    @Column(name = "book_star")
    private String bookStar;

    @Lob
    @Column(name = "book_introduction")
    private String bookIntroduction;

    @Lob
    @Column(name = "book_contents")
    private String bookContents;


    @Column(name = "book_cove_image")
    private String bookCoveImage;

    @Column(name = "book_type")
    private String bookType;

    @Column(name = "book_language", length = 32)
    private String bookLanguage;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "update_by", length = 32)
    private String updateBy;

    @Column(name = "is_ad")
    private Integer isAd;

    @Column(name = "is_new")
    private Integer isNew;

    @Column(name = "is_hot")
    private Integer isHot;

}