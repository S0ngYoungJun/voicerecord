package com.example.voicenote.service;

import com.example.voicenote.model.Note;
import com.example.voicenote.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    // 새로운 노트를 저장하는 메서드
    public Note saveNote(String content) {
        Note note = new Note(content);
        return noteRepository.save(note);
    }

    // 모든 노트를 가져오는 메서드
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    // ID로 특정 노트를 조회하는 메서드
    public Optional<Note> getNoteById(UUID id) {
        return noteRepository.findById(id);
    }

    // ID로 특정 노트를 삭제하는 메서드
    public void deleteNoteById(UUID id) {
        noteRepository.deleteById(id);
    }
}
