package com.example.voicenote.controller;

import com.example.voicenote.model.Note;
import com.example.voicenote.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/notes")
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping
    public Note saveNote(@RequestBody String content) {
        return noteService.saveNote(content);
    }

    @GetMapping
    public List<Note> getAllNotes() {
        return noteService.getAllNotes();
    }

    @GetMapping("/{id}")
    public Optional<Note> getNoteById(@PathVariable UUID id) {
        return noteService.getNoteById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteNoteById(@PathVariable UUID id) {
        noteService.deleteNoteById(id);
    }

    @PostMapping("/save-text")
    public ResponseEntity<Note> saveText(@RequestBody Map<String, String> payload) {
        String content = payload.get("content");
        if (content == null || content.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        Note savedNote = noteService.saveNote(content);
        return ResponseEntity.ok(savedNote);
    }

    @PostMapping("/transcribe")
    public ResponseEntity<Map<String, String>> transcribeAudio(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        try {
            String transcribedText = "변환된 예제 텍스트"; 
            Map<String, String> response = new HashMap<>();
            response.put("transcribedText", transcribedText);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
