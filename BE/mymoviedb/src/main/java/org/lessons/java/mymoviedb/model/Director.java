package org.lessons.java.mymoviedb.model;

import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "directors")
public class Director {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "A director must have a name")
    private String name;

    @NotBlank(message = "A director must have a surname")
    private String surname;

    @NotBlank(message = "A director must have a date of birth")
    private LocalDate dateOfBirth;

    @Lob
    private String career;

    @OneToMany(mappedBy = "director", cascade = { CascadeType.REMOVE })
    private List<Movie> movies;

}
