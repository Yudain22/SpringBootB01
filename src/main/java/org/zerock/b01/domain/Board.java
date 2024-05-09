package org.zerock.b01.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "imageSet")
public class Board extends BaseEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long bno;

  @Column(length = 500, nullable = false)
  private String title;
  @Column(length = 2000, nullable = false)
  private String content;
  @Column(length = 50, nullable = false)
  private String writer;

  // 양방향을 한다면,
  @OneToMany(mappedBy = "board") //BoardImage의 board 변수
  @Builder.Default
  private Set<BoardImage> imageSet = new HashSet<>();

  public void change(String title, String content){
    this.title = title;
    this.content = content;
  }


}













