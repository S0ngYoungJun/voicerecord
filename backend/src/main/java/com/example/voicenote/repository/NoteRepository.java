package com.example.voicenote.repository;

import com.example.voicenote.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface NoteRepository extends JpaRepository<Note, UUID> {
}