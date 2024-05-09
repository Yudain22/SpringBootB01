package org.zerock.b01.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Notice extends BaseEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long no;
  @Column(length = 500, nullable = false)
  private String title;
  @Column(length = 2000, nullable = false)
  private String content;
  @Builder.Default
  private Long count = 0L;

  public void change(String title, String content){
    this.title = title;
    this.content = content;
  }
}
