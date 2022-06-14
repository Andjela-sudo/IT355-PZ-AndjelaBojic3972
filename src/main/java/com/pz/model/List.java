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
public class List {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private Users createdBy;

    @OneToMany(mappedBy = "listId")
    //@JoinTable(name = "material_list", joinColumns = @JoinColumn(name = "list_id", referencedColumnName = "id"))
    private java.util.List<MaterialList> items;

}
