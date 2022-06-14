package com.pz.model;

import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "material_list", schema = "elektricarapi", catalog = "")
public class MaterialList {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "list_id")
    private Long listId;

    @OneToOne
    private Materijal material;

    @Column(name = "kolicina")
    private Integer kolicina;

}
